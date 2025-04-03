//package electricity.billing.system;
//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//import net.proteanit.sql.DbUtils;
//
//public class BillDetails extends JFrame{
//
//    BillDetails(String meter) {
//        
//        setSize(700, 650);
//        setLocation(400, 150);
//        
//        getContentPane().setBackground(Color.WHITE);
//        
//        JTable table = new JTable();
//        
//        try {
//            Conn c = new Conn();
//            String query = "select * from login where meter_no = '"+meter+"'";
//            ResultSet rs = c.s.executeQuery(query);
//            
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        JScrollPane sp = new JScrollPane(table);
//        sp.setBounds(0, 0, 700, 650);
//        add(sp);
//        
//        setVisible(true);
//    }
//    public static void main(String[] args) {
//        new BillDetails("2222");
//    }
//}


package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.util.concurrent.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BillDetails extends JFrame implements ActionListener {

    private String meter;
    private JTable table;
    private JButton print, refresh, remoteFetch;
    private JLabel statusLabel;
    
    public BillDetails(String meter) {
        this.meter = meter;
        setTitle("Bill Details - Meter: " + meter);
        setSize(800, 700);
        setLocation(400, 100);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        // Status label
        statusLabel = new JLabel("Loading bill details...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(statusLabel, BorderLayout.NORTH);
        
        // Table with scroll pane
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        print = new JButton("Print");
        print.addActionListener(this);
        
        refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        
        remoteFetch = new JButton("Fetch Remotely");
        remoteFetch.addActionListener(this);
        
        buttonPanel.add(print);
        buttonPanel.add(refresh);
        buttonPanel.add(remoteFetch);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Initial data load
        loadBillDetails();
        
        setVisible(true);
    }
    
    private void loadBillDetails() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    statusLabel.setText("Loading data from database...");
                    
                    Conn c = new Conn();
                    String query = "SELECT * FROM bill WHERE meter_no = '" + meter + "' ORDER BY month DESC";
                    
                    Future<ResultSet> future = c.executeQueryAsync(query);
                    ResultSet rs = future.get();
                    
                    SwingUtilities.invokeLater(() -> {
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                        statusLabel.setText("Data loaded successfully");
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Error loading data: " + e.getMessage());
                    });
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    
    private void fetchRemoteBillDetails() {
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Connecting to server...");
                
                try (Socket socket = new Socket("localhost", 12345);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    
                    publish("Requesting bill data...");
                    out.println("GET_BILL:" + meter);
                    
                    publish("Waiting for response...");
                    String response = in.readLine();
                    
                    if (response != null && response.startsWith("BILL_INFO:")) {
                        publish("Processing response...");
                        processRemoteResponse(response);
                    } else {
                        publish("Error: " + (response == null ? "No response" : response));
                    }
                } catch (ConnectException e) {
                    publish("Server connection failed");
                } catch (IOException e) {
                    publish("Network error: " + e.getMessage());
                }
                return null;
            }
            
            @Override
            protected void process(java.util.List<String> chunks) {
                for (String msg : chunks) {
                    statusLabel.setText(msg);
                }
            }
            
            private void processRemoteResponse(String response) {
                String[] bills = response.substring(10).split(";");
                String[] columns = {"Month", "Units", "Amount", "Status"};
                Object[][] data = new Object[bills.length][4];
                
                for (int i = 0; i < bills.length; i++) {
                    String[] parts = bills[i].split(",");
                    if (parts.length >= 4) {
                        data[i][0] = parts[0]; // Month
                        data[i][1] = parts[1]; // Units
                        data[i][2] = parts[2]; // Amount
                        data[i][3] = parts[3]; // Status
                    }
                }
                
                SwingUtilities.invokeLater(() -> {
                    table.setModel(new DefaultTableModel(data, columns));
                    statusLabel.setText("Remote data loaded successfully");
                });
            }
        }.execute();
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == print) {
            new Thread(() -> {
                try {
                    boolean complete = table.print();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(BillDetails.this, 
                            complete ? "Printing completed" : "Printing cancelled");
                    });
                } catch (Exception e) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(BillDetails.this, 
                            "Printing error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        } 
        else if (ae.getSource() == refresh) {
            loadBillDetails();
        }
        else if (ae.getSource() == remoteFetch) {
            fetchRemoteBillDetails();
        }
    }

    public static void main(String[] args) {
        // Example usage
        new BillDetails("2222");
    }
}