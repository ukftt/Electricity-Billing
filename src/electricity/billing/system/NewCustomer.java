////package electricity.billing.system;
////
////import javax.swing.*;
////import java.awt.*;
////import java.util.*;
////import java.awt.event.*;
////
////public class NewCustomer extends JFrame implements ActionListener{
////
////    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
////    JButton next, cancel;
////    JLabel lblmeter;
////    NewCustomer() {
////        setSize(700, 500);
////        setLocation(400, 200);
////        
////        JPanel p = new JPanel();
////        p.setLayout(null);
////        p.setBackground(new Color(173, 216, 230));
////        add(p);
////        
////        JLabel heading = new JLabel("New Customer");
////        heading.setBounds(180, 10, 200, 25);
////        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
////        p.add(heading);
////        
////        JLabel lblname = new JLabel("Customer Name");
////        lblname.setBounds(100, 80, 100, 20);
////        p.add(lblname);
////        
////        tfname = new JTextField();
////        tfname.setBounds(240, 80, 200, 20);
////        p.add(tfname);
////        
////        JLabel lblmeterno = new JLabel("Meter Number");
////        lblmeterno.setBounds(100, 120, 100, 20);
////        p.add(lblmeterno);
////        
////        lblmeter = new JLabel("");
////        lblmeter.setBounds(240, 120, 100, 20);
////        p.add(lblmeter);
////        
////        Random ran = new Random();
////        long number = ran.nextLong() % 1000000;
////        lblmeter.setText("" + Math.abs(number));
////        
////        JLabel lbladdress = new JLabel("Address");
////        lbladdress.setBounds(100, 160, 100, 20);
////        p.add(lbladdress);
////        
////        tfaddress = new JTextField();
////        tfaddress.setBounds(240, 160, 200, 20);
////        p.add(tfaddress);
////        
////        JLabel lblcity = new JLabel("City");
////        lblcity.setBounds(100, 200, 100, 20);
////        p.add(lblcity);
////        
////        tfcity = new JTextField();
////        tfcity.setBounds(240, 200, 200, 20);
////        p.add(tfcity);
////        
////        JLabel lblstate = new JLabel("State");
////        lblstate.setBounds(100, 240, 100, 20);
////        p.add(lblstate);
////        
////        tfstate = new JTextField();
////        tfstate.setBounds(240, 240, 200, 20);
////        p.add(tfstate);
////        
////        JLabel lblemail = new JLabel("Email");
////        lblemail.setBounds(100, 280, 100, 20);
////        p.add(lblemail);
////        
////        tfemail = new JTextField();
////        tfemail.setBounds(240, 280, 200, 20);
////        p.add(tfemail);
////        
////        JLabel lblphone = new JLabel("Phone Number");
////        lblphone.setBounds(100, 320, 100, 20);
////        p.add(lblphone);
////        
////        tfphone = new JTextField();
////        tfphone.setBounds(240, 320, 200, 20);
////        p.add(tfphone);
////        
////        
////        cancel = new JButton("Cancel");
////        cancel.setBounds(250, 390, 100,25);
////        cancel.setBackground(Color.BLACK);
////        cancel.setForeground(Color.WHITE);
////        cancel.addActionListener(this);
////        p.add(cancel);
////        
////        next = new JButton("Submit");
////        next.setBounds(120, 390, 100,25);
////        next.setBackground(Color.BLACK);
////        next.setForeground(Color.WHITE);
////        next.addActionListener(this);
////        p.add(next);
////        
////        setLayout(new BorderLayout());
////        
////        add(p, "Center");
////        
////        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
////        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
////        ImageIcon i3 = new ImageIcon(i2);
////        JLabel image = new JLabel(i3);
////        add(image, "West");
////        
////        getContentPane().setBackground(Color.WHITE);
////        
////        setVisible(true);
////    }
////    
////    public void actionPerformed(ActionEvent ae) {
////        if (ae.getSource() == next) {
////            String name = tfname.getText();
////            String meter = lblmeter.getText();
////            String address = tfaddress.getText();
////            String city = tfcity.getText();
////            String state = tfstate.getText();
////            String email = tfemail.getText();
////            String phone = tfphone.getText();
////            
//////            String query1 = "insert into customer values('"+name+"', '"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
//////            String query2 = "insert into login values('"+meter+"', '', '"+name+"', '', '')";
////              
////
////            String query1 = "insert into customer values('"+meter+"', '"+address+"', '"+city+"', '"+state+"', '"+email+"', '"+phone+"')";
////            String query2 = "insert into login values('"+meter+"', '', '"+name+"', '', '')";
////            
////            try {
////                Conn c = new Conn();
////                c.s.executeUpdate(query1);
////                c.s.executeUpdate(query2);
////                
////                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
////                setVisible(false);
////                
////                // new frame
//////                new MeterInfo(meter);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        } else {
////            setVisible(false);
////        }
////    }
////    
////    public static void main(String[] args) {
////        new NewCustomer();
////    }
////}
//
//
//
//package electricity.billing.system;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.*;
//import java.awt.event.*;
//
//public class NewCustomer extends JFrame implements ActionListener{
//
//    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
//    JButton next, cancel;
//    JLabel lblmeter;
//    
//    NewCustomer() {
//        setSize(700, 500);
//        setLocation(400, 200);
//        
//        JPanel p = new JPanel();
//        p.setLayout(null);
//        p.setBackground(new Color(173, 216, 230));
//        add(p);
//        
//        JLabel heading = new JLabel("New Customer");
//        heading.setBounds(180, 10, 200, 25);
//        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
//        p.add(heading);
//        
//        JLabel lblname = new JLabel("Customer Name");
//        lblname.setBounds(100, 80, 100, 20);
//        p.add(lblname);
//        
//        tfname = new JTextField();
//        tfname.setBounds(240, 80, 200, 20);
//        p.add(tfname);
//        
//        JLabel lblmeterno = new JLabel("Meter Number");
//        lblmeterno.setBounds(100, 120, 100, 20);
//        p.add(lblmeterno);
//        
//        lblmeter = new JLabel("");
//        lblmeter.setBounds(240, 120, 100, 20);
//        p.add(lblmeter);
//        
//        // Generate a unique 6-digit meter number
//        Random ran = new Random();
//        long number = Math.abs(ran.nextLong() % 1000000);
//        lblmeter.setText("" + number);
//        
//        JLabel lbladdress = new JLabel("Address");
//        lbladdress.setBounds(100, 160, 100, 20);
//        p.add(lbladdress);
//        
//        tfaddress = new JTextField();
//        tfaddress.setBounds(240, 160, 200, 20);
//        p.add(tfaddress);
//        
//        JLabel lblcity = new JLabel("City");
//        lblcity.setBounds(100, 200, 100, 20);
//        p.add(lblcity);
//        
//        tfcity = new JTextField();
//        tfcity.setBounds(240, 200, 200, 20);
//        p.add(tfcity);
//        
//        JLabel lblstate = new JLabel("State");
//        lblstate.setBounds(100, 240, 100, 20);
//        p.add(lblstate);
//        
//        tfstate = new JTextField();
//        tfstate.setBounds(240, 240, 200, 20);
//        p.add(tfstate);
//        
//        JLabel lblemail = new JLabel("Email");
//        lblemail.setBounds(100, 280, 100, 20);
//        p.add(lblemail);
//        
//        tfemail = new JTextField();
//        tfemail.setBounds(240, 280, 200, 20);
//        p.add(tfemail);
//        
//        JLabel lblphone = new JLabel("Phone Number");
//        lblphone.setBounds(100, 320, 100, 20);
//        p.add(lblphone);
//        
//        tfphone = new JTextField();
//        tfphone.setBounds(240, 320, 200, 20);
//        p.add(tfphone);
//        
//        next = new JButton("Submit");
//        next.setBounds(120, 390, 100, 25);
//        next.setBackground(Color.BLACK);
//        next.setForeground(Color.WHITE);
//        next.addActionListener(this);
//        p.add(next);
//        
//        cancel = new JButton("Cancel");
//        cancel.setBounds(250, 390, 100, 25);
//        cancel.setBackground(Color.BLACK);
//        cancel.setForeground(Color.WHITE);
//        cancel.addActionListener(this);
//        p.add(cancel);
//        
//        setLayout(new BorderLayout());
//        add(p, "Center");
//        
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
//        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        JLabel image = new JLabel(i3);
//        add(image, "West");
//        
//        getContentPane().setBackground(Color.WHITE);
//        setVisible(true);
//    }
//    
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == next) {
//            String name = tfname.getText();
//            String meter = lblmeter.getText();
//            String address = tfaddress.getText();
//            String city = tfcity.getText();
//            String state = tfstate.getText();
//            String email = tfemail.getText();
//            String phone = tfphone.getText();
//            
//            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//                JOptionPane.showMessageDialog(null, "All fields are required!");
//                return;
//            }
//
//            // ✅ Ensure column names match your database structure
////            String query1 = "INSERT INTO customer (name, meter_no, address, city, state, email, phone) VALUES ('"
////                            + name + "', '" + meter + "', '" + address + "', '" + city + "', '" + state + "', '" + email + "', '" + phone + "')";
////            
////            String query2 = "INSERT INTO login (meter_no, password, name, usertype, status) VALUES ('"
////                            + meter + "', '', '" + name + "', '', '')";
////            
//
//
// String query1 = "INSERT INTO customer ( meter_no, address, city, state, email, phone) VALUES ('" + meter + "', '" + address + "', '" + city + "', '" + state + "', '" + email + "', '" + phone + "')";
//
//        String query2 = "INSERT INTO login (meter_no, name, username, pass) VALUES ('"
//                        + meter + "', '" + name + "', '" + name + "', 'password')";
//            try {
//                Conn c = new Conn();
//                c.s.executeUpdate(query1);
//                c.s.executeUpdate(query2);
//                
//                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
//                setVisible(false);
//
//                // Open the MeterInfo screen with the correct meter number
//                new MeterInfo(meter);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            setVisible(false);
//        }
//    }
//    
//    public static void main(String[] args) {
//        new NewCustomer();
//    }
//}


package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class NewCustomer extends JFrame implements ActionListener, Runnable {

    private JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    private JButton next, cancel;
    private JLabel lblmeter;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private boolean serverRunning;
    
    public NewCustomer() {
        setSize(700, 500);
        setLocation(400, 200);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);
        
        JLabel heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
        
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(240, 80, 200, 20);
        p.add(tfname);
        
        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        lblmeter = new JLabel("");
        lblmeter.setBounds(240, 120, 100, 20);
        p.add(lblmeter);
        
        // Generate a unique 6-digit meter number
        Random ran = new Random();
        long number = Math.abs(ran.nextLong() % 1000000);
        lblmeter.setText("" + number);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(240, 160, 200, 20);
        p.add(tfaddress);
        
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);
        
        tfcity = new JTextField();
        tfcity.setBounds(240, 200, 200, 20);
        p.add(tfcity);
        
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);
        
        tfstate = new JTextField();
        tfstate.setBounds(240, 240, 200, 20);
        p.add(tfstate);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(240, 280, 200, 20);
        p.add(tfemail);
        
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(240, 320, 200, 20);
        p.add(tfphone);
        
        next = new JButton("Submit");
        next.setBounds(120, 390, 100, 25);
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        p.add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 390, 100, 25);
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
            // Execute customer creation in a separate thread
            executorService.execute(this::createCustomer);
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }
    
    private void createCustomer() {
        String name = tfname.getText();
        String meter = lblmeter.getText();
        String address = tfaddress.getText();
        String city = tfcity.getText();
        String state = tfstate.getText();
        String email = tfemail.getText();
        String phone = tfphone.getText();
        
        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "All fields are required!");
            });
            return;
        }

        String query1 = "INSERT INTO customer (meter_no, address, city, state, email, phone) VALUES ('" + 
                       meter + "', '" + address + "', '" + city + "', '" + state + "', '" + email + "', '" + phone + "')";

        String query2 = "INSERT INTO login (meter_no, name, username, pass) VALUES ('" +
                       meter + "', '" + name + "', '" + name + "', 'password')";
        
        try {
            Conn c = new Conn();
            c.s.executeUpdate(query1);
            c.s.executeUpdate(query2);
            
            // Send customer data to server
            sendCustomerDataToServer(name, meter, address, city, state, email, phone);
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                setVisible(false);
                new MeterInfo(meter);
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
            serverSocket = new ServerSocket(8082); // Different port from other services
            System.out.println("Customer Server started on port 8082");
            
            while (serverRunning) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new CustomerClientHandler(clientSocket));
            }
        } catch (IOException e) {
            if (serverRunning) {
                e.printStackTrace();
            }
        }
    }
    
    private class CustomerClientHandler implements Runnable {
        private Socket clientSocket;
        
        public CustomerClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                
                String request = in.readLine();
                if (request != null) {
                    switch (request) {
                        case "GET_CUSTOMER":
                            String meterNumber = in.readLine();
                            String customerData = getCustomerFromDB(meterNumber);
                            out.println(customerData != null ? customerData : "NOT_FOUND");
                            break;
                            
                        case "REGISTER_CUSTOMER":
                            String customerInfo = in.readLine();
                            System.out.println("Received customer registration: " + customerInfo);
                            // In a real application, you would store this in a database
                            out.println("ACK");
                            break;
                            
                        case "GENERATE_METER":
                            String newMeter = generateMeterNumber();
                            out.println(newMeter);
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
        
        private String getCustomerFromDB(String meterNumber) {
            // This would query your database in a real application
            // For demonstration, we'll return dummy data
            return "MeterNumber:" + meterNumber + ",Name:John Doe,Address:123 Main St,City:Metropolis,State:State,Email:john@example.com,Phone:1234567890";
        }
        
        private String generateMeterNumber() {
            Random ran = new Random();
            return String.valueOf(Math.abs(ran.nextLong() % 1000000));
        }
    }
    
    private void sendCustomerDataToServer(String name, String meter, String address, 
                                        String city, String state, String email, String phone) {
        executorService.execute(() -> {
            try (Socket socket = new Socket("localhost", 8082);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                
                out.println("REGISTER_CUSTOMER");
                out.println(String.format("%s,%s,%s,%s,%s,%s,%s", name, meter, address, city, state, email, phone));
                
                String response = in.readLine();
                System.out.println("Server response: " + response);
                
            } catch (IOException e) {
                System.err.println("Error sending customer data to server: " + e.getMessage());
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
        NewCustomer newCustomer = new NewCustomer();
        
        // Add shutdown hook to clean up resources
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            newCustomer.stopServer();
        }));
    }
}