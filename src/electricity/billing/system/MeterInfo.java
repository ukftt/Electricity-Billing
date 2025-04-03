//package electricity.billing.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.*;
//import java.awt.event.*;
//import java.sql.*; 
//
//public class MeterInfo extends JFrame implements ActionListener{
//
//    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
//    JButton next, cancel;
//    JLabel lblmeter;
//    Choice meterlocation, metertype, phasecode, billtype;
//    String meternumber;
//    MeterInfo(String meternumber) {
//        this.meternumber = meternumber;
//        
//        setSize(700, 500);
//        setLocation(400, 200);
//        
//        JPanel p = new JPanel();
//        p.setLayout(null);
//        p.setBackground(new Color(173, 216, 230));
//        add(p);
//        
//        JLabel heading = new JLabel("Meter Information");
//        heading.setBounds(180, 10, 200, 25);
//        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
//        p.add(heading);
//        
//        JLabel lblname = new JLabel("Meter Number");
//        lblname.setBounds(100, 80, 100, 20);
//        p.add(lblname);
//        
//        JLabel lblmeternumber = new JLabel(meternumber);
//        lblmeternumber.setBounds(240, 80, 100, 20);
//        p.add(lblmeternumber);
//        
//        JLabel lblmeterno = new JLabel("Meter Location");
//        lblmeterno.setBounds(100, 120, 100, 20);
//        p.add(lblmeterno);
//        
//        meterlocation = new Choice();
//        meterlocation.add("Outside");
//        meterlocation.add("Inside");
//        meterlocation.setBounds(240, 120, 200, 20);
//        p.add(meterlocation);
//        
//        JLabel lbladdress = new JLabel("Meter Type");
//        lbladdress.setBounds(100, 160, 100, 20);
//        p.add(lbladdress);
//        
//        metertype = new Choice();
//        metertype.add("Electric Meter");
//        metertype.add("Solar Meter");
//        metertype.add("Smart Meter");
//        metertype.setBounds(240, 160, 200, 20);
//        p.add(metertype);
//        
//        JLabel lblcity = new JLabel("Phase Code");
//        lblcity.setBounds(100, 200, 100, 20);
//        p.add(lblcity);
//        
//        phasecode = new Choice();
//        phasecode.add("011");
//        phasecode.add("022");
//        phasecode.add("033");
//        phasecode.add("044");
//        phasecode.add("055");
//        phasecode.add("066");
//        phasecode.add("077");
//        phasecode.add("088");
//        phasecode.add("099");
//        phasecode.setBounds(240, 200, 200, 20);
//        p.add(phasecode);
//        
//        JLabel lblstate = new JLabel("Bill Type");
//        lblstate.setBounds(100, 240, 100, 20);
//        p.add(lblstate);
//        
//        billtype = new Choice();
//        billtype.add("Normal");
//        billtype.add("Industial");
//        billtype.setBounds(240, 240, 200, 20);
//        p.add(billtype);
//        
//        JLabel lblemail = new JLabel("Days");
//        lblemail.setBounds(100, 280, 100, 20);
//        p.add(lblemail);
//        
//        JLabel lblemails = new JLabel("30 Days");
//        lblemails.setBounds(240, 280, 100, 20);
//        p.add(lblemails);
//        
//        JLabel lblphone = new JLabel("Note");
//        lblphone.setBounds(100, 320, 100, 20);
//        p.add(lblphone);
//        
//        JLabel lblphones = new JLabel("By Default Bill is calculated for 30 days only");
//        lblphones.setBounds(240, 320, 500, 20);
//        p.add(lblphones);
//        
//        next = new JButton("Submit");
//        next.setBounds(220, 390, 100,25);
//        next.setBackground(Color.BLACK);
//        next.setForeground(Color.WHITE);
//        next.addActionListener(this);
//        p.add(next);
//
//        
//        setLayout(new BorderLayout());
//        
//        add(p, "Center");
//        
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
//        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        JLabel image = new JLabel(i3);
//        add(image, "West");
//        
//        getContentPane().setBackground(Color.WHITE);
//        
//        setVisible(true);
//    }
//    
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == next) {
//            String meter = meternumber;
//            String location = meterlocation.getSelectedItem();
//            String type = metertype.getSelectedItem();
//            String code = phasecode.getSelectedItem();
//            String typebill = billtype.getSelectedItem();
//            String days = "30";
//            
//            String query = "insert into meter_info values('"+meter+"', '"+location+"', '"+type+"', '"+code+"', '"+typebill+"', '"+days+"')";
//            
//            try {
//                Conn c = new Conn();
//                c.s.executeUpdate(query);
//                
//                JOptionPane.showMessageDialog(null, "Meter Information Added Successfully");
//                setVisible(false);
////                new MeterInfo("");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            setVisible(false);
//        }
//    }
//    
//    public static void main(String[] args) {
//        new MeterInfo("");
////try {
////            Conn c = new Conn();
////            String query = "SELECT meter_no FROM customers WHERE name = 'John Doe'"; // Replace with actual logic
////            ResultSet rs = c.s.executeQuery(query);
////
////            if (rs.next()) {  // Check if a meter number exists
////                String meterNumber = rs.getString("meter_no");
////                new MeterInfo(meterNumber);  // Pass the meter number to the MeterInfo form
////            } else {
////                System.out.println("No meter number found for the customer.");
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//}


package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MeterInfo extends JFrame implements ActionListener, Runnable {

    private JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    private JButton next, cancel;
    private JLabel lblmeter;
    private Choice meterlocation, metertype, phasecode, billtype;
    private String meternumber;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private boolean serverRunning;
    
    public MeterInfo(String meternumber) {
        this.meternumber = meternumber;
        
        setSize(700, 500);
        setLocation(400, 200);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);
        
        JLabel heading = new JLabel("Meter Information");
        heading.setBounds(180, 10, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
        
        JLabel lblname = new JLabel("Meter Number");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);
        
        JLabel lblmeternumber = new JLabel(meternumber);
        lblmeternumber.setBounds(240, 80, 100, 20);
        p.add(lblmeternumber);
        
        JLabel lblmeterno = new JLabel("Meter Location");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        meterlocation = new Choice();
        meterlocation.add("Outside");
        meterlocation.add("Inside");
        meterlocation.setBounds(240, 120, 200, 20);
        p.add(meterlocation);
        
        JLabel lbladdress = new JLabel("Meter Type");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);
        
        metertype = new Choice();
        metertype.add("Electric Meter");
        metertype.add("Solar Meter");
        metertype.add("Smart Meter");
        metertype.setBounds(240, 160, 200, 20);
        p.add(metertype);
        
        JLabel lblcity = new JLabel("Phase Code");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);
        
        phasecode = new Choice();
        phasecode.add("011");
        phasecode.add("022");
        phasecode.add("033");
        phasecode.add("044");
        phasecode.add("055");
        phasecode.add("066");
        phasecode.add("077");
        phasecode.add("088");
        phasecode.add("099");
        phasecode.setBounds(240, 200, 200, 20);
        p.add(phasecode);
        
        JLabel lblstate = new JLabel("Bill Type");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);
        
        billtype = new Choice();
        billtype.add("Normal");
        billtype.add("Industrial");
        billtype.setBounds(240, 240, 200, 20);
        p.add(billtype);
        
        JLabel lblemail = new JLabel("Days");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);
        
        JLabel lblemails = new JLabel("30 Days");
        lblemails.setBounds(240, 280, 100, 20);
        p.add(lblemails);
        
        JLabel lblphone = new JLabel("Note");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);
        
        JLabel lblphones = new JLabel("By Default Bill is calculated for 30 days only");
        lblphones.setBounds(240, 320, 500, 20);
        p.add(lblphones);
        
        next = new JButton("Submit");
        next.setBounds(220, 390, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(340, 390, 100, 25);
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        
        // Initialize thread pool
        executorService = Executors.newFixedThreadPool(5);
        
        // Start networking server in a separate thread
        new Thread(this).start();
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            // Execute meter info submission in a separate thread
            executorService.execute(this::submitMeterInfo);
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }
    
    private void submitMeterInfo() {
        String meter = meternumber;
        String location = meterlocation.getSelectedItem();
        String type = metertype.getSelectedItem();
        String code = phasecode.getSelectedItem();
        String typebill = billtype.getSelectedItem();
        String days = "30";
        
        String query = "insert into meter_info values('"+meter+"', '"+location+"', '"+type+"', '"+code+"', '"+typebill+"', '"+days+"')";
        
        try {
            Conn c = new Conn();
            c.s.executeUpdate(query);
            
            // Send meter info to server
            sendMeterInfoToServer(meter, location, type, code, typebill, days);
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Meter Information Added Successfully");
                setVisible(false);
            });
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    }
    
    // Networking server implementation
    @Override
    public void run() {
        serverRunning = true;
        try {
            serverSocket = new ServerSocket(8081); // Different port from GenerateBill
            System.out.println("Meter Info Server started on port 8081");
            
            while (serverRunning) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new MeterInfoClientHandler(clientSocket));
            }
        } catch (IOException e) {
            if (serverRunning) {
                e.printStackTrace();
            }
        }
    }
    
    private class MeterInfoClientHandler implements Runnable {
        private Socket clientSocket;
        
        public MeterInfoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                
                String request = in.readLine();
                if (request != null) {
                    switch (request) {
                        case "GET_METER_INFO":
                            String meterNumber = in.readLine();
                            String meterInfo = getMeterInfoFromDB(meterNumber);
                            out.println(meterInfo != null ? meterInfo : "NOT_FOUND");
                            break;
                            
                        case "STORE_METER_INFO":
                            // In a real application, you would store this in a database
                            String data = in.readLine();
                            System.out.println("Received meter info: " + data);
                            out.println("ACK");
                            break;
                            
                        default:
                            out.println("INVALID_REQUEST");
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private String getMeterInfoFromDB(String meterNumber) {
            // This would query your database in a real application
            // For demonstration, we'll return dummy data
            return "MeterNumber:" + meterNumber + ",Location:Inside,Type:Electric,Code:011,BillType:Normal,Days:30";
        }
    }
    
    private void sendMeterInfoToServer(String meter, String location, String type, 
                                     String code, String billType, String days) {
        executorService.execute(() -> {
            try (Socket socket = new Socket("localhost", 8081);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                out.println("STORE_METER_INFO");
                out.println(String.format("%s,%s,%s,%s,%s,%s", meter, location, type, code, billType, days));
                
                String response = in.readLine();
                System.out.println("Server response: " + response);
                
            } catch (IOException e) {
                System.err.println("Error sending meter info to server: " + e.getMessage());
            }
        });
    }
    
    public void stopServer() {
        serverRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
    
    public static void main(String[] args) {
        // Proper way to initialize with actual meter number
        try {
            Conn c = new Conn();
            String query = "SELECT meter_no FROM customer WHERE name = 'John Doe'"; // Example query
            ResultSet rs = c.s.executeQuery(query);

            if (rs.next()) {
                String meterNumber = rs.getString("meter_no");
                MeterInfo meterInfo = new MeterInfo(meterNumber);
                
                // Add shutdown hook to clean up resources
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    meterInfo.stopServer();
                }));
            } else {
                JOptionPane.showMessageDialog(null, "No meter number found for the customer.");
                // Fallback to empty constructor if needed
                new MeterInfo("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to empty constructor if there's an error
            new MeterInfo("");
        }
    }
}