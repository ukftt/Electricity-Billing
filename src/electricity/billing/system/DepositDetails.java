//package electricity.billing.system;
//
//import java.awt.*;
//import javax.swing.*;
//import java.sql.*;
//import net.proteanit.sql.DbUtils;
//import java.awt.event.*;
//
//public class DepositDetails extends JFrame implements ActionListener{
//
//    Choice meternumber, cmonth;
//    JTable table;
//    JButton search, print;
//    
//    DepositDetails(){
//        super("Deposit Details");
//        
//        setSize(700, 700);
//        setLocation(400, 100);
//        
//        setLayout(null);
//        getContentPane().setBackground(Color.WHITE);
//        
//        JLabel lblmeternumber = new JLabel("Search By Meter Number");
//        lblmeternumber.setBounds(20, 20, 150, 20);
//        add(lblmeternumber);
//        
//        meternumber = new Choice();
//        meternumber.setBounds(180, 20, 150, 20);
//        add(meternumber);
//        
//        try {
//            Conn c  = new Conn();
//            ResultSet rs = c.s.executeQuery("select * from customer");
//            while(rs.next()) {
//                meternumber.add(rs.getString("meter_no"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        JLabel lblmonth = new JLabel("Search By Month");
//        lblmonth.setBounds(400, 20, 100, 20);
//        add(lblmonth);
//        
//        cmonth = new Choice();
//        cmonth.setBounds(520, 20, 150, 20);
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
//        add(cmonth);
//        
//        table = new JTable();
//        
//        try {
//            Conn c = new Conn();
//            ResultSet rs = c.s.executeQuery("select * from bill");
//            
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        JScrollPane sp = new JScrollPane(table);
//        sp.setBounds(0, 100, 700, 600);
//        add(sp);
//        
//        search = new JButton("Search");
//        search.setBounds(20, 70, 80, 20);
//        search.addActionListener(this);
//        add(search);
//        
//        print = new JButton("Print");
//        print.setBounds(120, 70, 80, 20);
//        print.addActionListener(this);
//        add(print);
//        
//        
//        setVisible(true);
//    }
//    
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == search) {
//            String query = "select * from bill where meter_no = '"+meternumber.getSelectedItem()+"' and month = '"+cmonth.getSelectedItem()+"'";
//            
//            try {
//                Conn c = new Conn();
//                ResultSet rs = c.s.executeQuery(query);
//                table.setModel(DbUtils.resultSetToTableModel(rs));
//            } catch (Exception e) {
//                
//            }
//        } else  {
//            try {
//                table.print();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new DepositDetails();
//    }
//}



package electricity.billing.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.util.concurrent.*;

public class DepositDetails extends JFrame implements ActionListener {

    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;
    
    DepositDetails() {
        super("Deposit Details");
        
        setSize(700, 700);
        setLocation(400, 100);
        
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        JLabel lblmeternumber = new JLabel("Search By Meter Number");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);
        
        meternumber = new Choice();
        meternumber.setBounds(180, 20, 150, 20);
        add(meternumber);
        
        JLabel lblmonth = new JLabel("Search By Month");
        lblmonth.setBounds(400, 20, 100, 20);
        add(lblmonth);
        
        cmonth = new Choice();
        cmonth.setBounds(520, 20, 150, 20);
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
        add(cmonth);
        
        table = new JTable();
        
        // Show loading message
        JLabel loadingLabel = new JLabel("Loading data...", JLabel.CENTER);
        loadingLabel.setBounds(0, 100, 700, 600);
        add(loadingLabel);
        
        // Load meter numbers asynchronously
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Conn c = new Conn();
                    Future<ResultSet> future = c.executeQueryAsync("select * from customer");
                    ResultSet rs = future.get();
                    
                    while(rs.next()) {
                        final String meter = rs.getString("meter_no");
                        SwingUtilities.invokeLater(() -> meternumber.add(meter));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        
        // Load bill data asynchronously
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Conn c = new Conn();
                    Future<ResultSet> future = c.executeQueryAsync("select * from bill");
                    ResultSet rs = future.get();
                    
                    SwingUtilities.invokeLater(() -> {
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                        remove(loadingLabel);
                        JScrollPane sp = new JScrollPane(table);
                        sp.setBounds(0, 100, 700, 600);
                        add(sp);
                        revalidate();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        loadingLabel.setText("Error loading data");
                    });
                }
                return null;
            }
        }.execute();
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String meter = meternumber.getSelectedItem();
            String month = cmonth.getSelectedItem();
            String query = "select * from bill where meter_no = '" + meter + "' and month = '" + month + "'";
            
            // Show loading during search
            JLabel searchLoading = new JLabel("Searching...", JLabel.CENTER);
            searchLoading.setBounds(0, 100, 700, 600);
            add(searchLoading);
            revalidate();
            
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try {
                        Conn c = new Conn();
                        Future<ResultSet> future = c.executeQueryAsync(query);
                        ResultSet rs = future.get();
                        
                        SwingUtilities.invokeLater(() -> {
                            remove(searchLoading);
                            table.setModel(DbUtils.resultSetToTableModel(rs));
                            JScrollPane sp = new JScrollPane(table);
                            sp.setBounds(0, 100, 700, 600);
                            add(sp);
                            revalidate();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        SwingUtilities.invokeLater(() -> {
                            remove(searchLoading);
                            JOptionPane.showMessageDialog(null, "Error during search");
                        });
                    }
                    return null;
                }
            }.execute();
        } else if (ae.getSource() == print) {
            new Thread(() -> {
                try {
                    boolean complete = table.print();
                    if (complete) {
                        SwingUtilities.invokeLater(() -> 
                            JOptionPane.showMessageDialog(null, "Printing complete"));
                    } else {
                        SwingUtilities.invokeLater(() -> 
                            JOptionPane.showMessageDialog(null, "Printing cancelled"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> 
                        JOptionPane.showMessageDialog(null, "Printing error: " + e.getMessage()));
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}