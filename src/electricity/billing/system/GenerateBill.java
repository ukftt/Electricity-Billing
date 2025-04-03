//package electricity.billing.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//
//public class GenerateBill extends JFrame implements ActionListener{
//
//    String meter;
//    JButton bill;
//    Choice cmonth;
//    JTextArea area;
//    GenerateBill(String meter) {
//        this.meter = meter;
//        
//        setSize(500, 800);
//        setLocation(550, 30);
//        
//        setLayout(new BorderLayout());
//        
//        JPanel panel = new JPanel();
//        
//        JLabel heading = new JLabel("Generate Bill");
//        JLabel meternumber = new JLabel(meter);
//        
//        cmonth = new Choice();
//        
//        cmonth.add("January");
//        cmonth.add("February");
//        cmonth.add("March");
//        cmonth.add("April");
//        cmonth.add("May");
//        cmonth.add("June");
//        cmonth.add("July");
//        cmonth.add("August");
//        cmonth.add("September");
//        cmonth.add("October");
//        cmonth.add("November");
//        cmonth.add("December");
//        
//        area = new JTextArea(50, 15);
//        area.setText("\n\n\t--------Click on the---------\n\t Generate Bill Button to get\n\tthe bill of the Selected Month");
//        area.setFont(new Font("Senserif", Font.ITALIC, 18));
//        
//        JScrollPane pane = new JScrollPane(area);
//        
//        bill = new JButton("Generate Bill");
//        bill.addActionListener(this);
//        
//        panel.add(heading);
//        panel.add(meternumber);
//        panel.add(cmonth);
//        add(panel, "North");
//        
//        add(pane, "Center");
//        add(bill, "South");
//        
//        setVisible(true);
//    }
//    
//    public void actionPerformed(ActionEvent ae) {
//        try {
//            Conn c = new Conn();
//            
//            String month = cmonth.getSelectedItem();
//            
//            area.setText("\tReliance Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF "+month+", 2022\n\n\n");
//            
//            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
//        
//            if(rs.next()) {
//                area.append("\n    Customer Name: " + rs.getString("name"));
//                area.append("\n    Meter Number   : " + rs.getString("meter_no"));
//                area.append("\n    Address             : " + rs.getString("address"));
//                area.append("\n    City                 : " + rs.getString("city"));
//                area.append("\n    State                : " + rs.getString("state"));
//                area.append("\n    Email                : " + rs.getString("email"));
//                area.append("\n    Phone                 : " + rs.getString("phone"));
//                area.append("\n---------------------------------------------------");
//                area.append("\n");
//            }
//            
//            rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
//        
//            if(rs.next()) {
//                area.append("\n    Meter Location: " + rs.getString("meter_location"));
//                area.append("\n    Meter Type:     " + rs.getString("meter_type"));
//                area.append("\n    Phase Code:        " + rs.getString("phase_code"));
//                area.append("\n    Bill Type:          " + rs.getString("bill_type"));
//                area.append("\n    Days:                " + rs.getString("days"));
//                area.append("\n---------------------------------------------------");
//                area.append("\n");
//            }
//            
//            rs = c.s.executeQuery("select * from tax");
//        
//            if(rs.next()) {
//                area.append("\n");
//                area.append("\n    Cost Per Unit: " + rs.getString("cost_per_unit"));
//                area.append("\n    Meter Rent:     " + rs.getString("cost_per_unit"));
//                area.append("\n    Service Charge:        " + rs.getString("service_charge"));
//                area.append("\n    Service Tax:          " + rs.getString("service_charge"));
//                area.append("\n    Swacch Bharat Cess:                " + rs.getString("swacch_bharat_cess"));
//                area.append("\n    Fixed Tax: " + rs.getString("fixed_tax"));
//                area.append("\n");
//            }
//            
//            rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month='"+month+"'");
//        
//            if(rs.next()) {
//                area.append("\n");
//                area.append("\n    Current Month: " + rs.getString("month"));
//                area.append("\n    Units Consumed:     " + rs.getString("units"));
//                area.append("\n    Total Charges:        " + rs.getString("totalbill"));
//                area.append("\n-------------------------------------------------------");
//                area.append("\n    Total Payable: " + rs.getString("totalbill"));
//                area.append("\n");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        new GenerateBill("");
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

public class GenerateBill extends JFrame implements ActionListener, Runnable {

    private String meter;
    private JButton bill;
    private Choice cmonth;
    private JTextArea area;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private boolean serverRunning;
    
    public GenerateBill(String meter) {
        this.meter = meter;
        
        setSize(500, 800);
        setLocation(550, 30);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel();
        
        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel(meter);
        
        cmonth = new Choice();
        
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        
        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Click on the---------\n\t Generate Bill Button to get\n\tthe bill of the Selected Month");
        area.setFont(new Font("Senserif", Font.ITALIC, 18));
        
        JScrollPane pane = new JScrollPane(area);
        
        bill = new JButton("Generate Bill");
        bill.addActionListener(this);
        
        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, "North");
        
        add(pane, "Center");
        add(bill, "South");
        
        // Initialize thread pool
        executorService = Executors.newFixedThreadPool(5);
        
        // Start networking server in a separate thread
        new Thread(this).start();
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bill) {
            // Execute bill generation in a separate thread
            executorService.execute(this::generateBill);
        }
    }
    
    private void generateBill() {
        try {
            String month = cmonth.getSelectedItem();
            
            // Update UI in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                area.setText("\tReliance Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF "+month+", 2022\n\n\n");
            });
            
            // Simulate network delay for demonstration
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Database operations
            Conn c = new Conn();
            
            SwingUtilities.invokeLater(() -> {
                try {
                    ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"'");
                
                    if(rs.next()) {
                        area.append("\n    Customer Name: " + rs.getString("name"));
                        area.append("\n    Meter Number   : " + rs.getString("meter_no"));
                        area.append("\n    Address             : " + rs.getString("address"));
                        area.append("\n    City                 : " + rs.getString("city"));
                        area.append("\n    State                : " + rs.getString("state"));
                        area.append("\n    Email                : " + rs.getString("email"));
                        area.append("\n    Phone                 : " + rs.getString("phone"));
                        area.append("\n---------------------------------------------------");
                        area.append("\n");
                    }
                    
                    rs = c.s.executeQuery("select * from meter_info where meter_no = '"+meter+"'");
                
                    if(rs.next()) {
                        area.append("\n    Meter Location: " + rs.getString("meter_location"));
                        area.append("\n    Meter Type:     " + rs.getString("meter_type"));
                        area.append("\n    Phase Code:        " + rs.getString("phase_code"));
                        area.append("\n    Bill Type:          " + rs.getString("bill_type"));
                        area.append("\n    Days:                " + rs.getString("days"));
                        area.append("\n---------------------------------------------------");
                        area.append("\n");
                    }
                    
                    rs = c.s.executeQuery("select * from tax");
                
                    if(rs.next()) {
                        area.append("\n");
                        area.append("\n    Cost Per Unit: " + rs.getString("cost_per_unit"));
                        area.append("\n    Meter Rent:     " + rs.getString("cost_per_unit"));
                        area.append("\n    Service Charge:        " + rs.getString("service_charge"));
                        area.append("\n    Service Tax:          " + rs.getString("service_charge"));
                        area.append("\n    Swacch Bharat Cess:                " + rs.getString("swacch_bharat_cess"));
                        area.append("\n    Fixed Tax: " + rs.getString("fixed_tax"));
                        area.append("\n");
                    }
                    
                    rs = c.s.executeQuery("select * from bill where meter_no = '"+meter+"' and month='"+month+"'");
                
                    if(rs.next()) {
                        area.append("\n");
                        area.append("\n    Current Month: " + rs.getString("month"));
                        area.append("\n    Units Consumed:     " + rs.getString("units"));
                        area.append("\n    Total Charges:        " + rs.getString("totalbill"));
                        area.append("\n-------------------------------------------------------");
                        area.append("\n    Total Payable: " + rs.getString("totalbill"));
                        area.append("\n");
                    }
                    
                    // Send bill to server
                    sendBillToServer(area.getText());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Networking server implementation
    @Override
    public void run() {
        serverRunning = true;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server started on port 8080");
            
            while (serverRunning) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            if (serverRunning) {
                e.printStackTrace();
            }
        }
    }
    
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                
                String request = in.readLine();
                if (request != null && request.equals("GET_BILL")) {
                    // In a real application, you would fetch the bill from database
                    String billData = "Sample bill data for meter: " + meter;
                    out.println(billData);
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
    }
    
    private void sendBillToServer(String billData) {
        executorService.execute(() -> {
            try (Socket socket = new Socket("localhost", 8080);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                out.println("STORE_BILL");
                out.println(meter);
                out.println(billData);
                
                String response = in.readLine();
                System.out.println("Server response: " + response);
                
            } catch (IOException e) {
                System.err.println("Error sending bill to server: " + e.getMessage());
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
        GenerateBill generateBill = new GenerateBill("");
        
        // Add shutdown hook to clean up resources
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            generateBill.stopServer();
        }));
    }
}