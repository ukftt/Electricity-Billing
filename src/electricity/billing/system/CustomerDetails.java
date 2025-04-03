//package electricity.billing.system;
//
//import java.awt.*;
//import javax.swing.*;
//import java.sql.*;
//import net.proteanit.sql.DbUtils;
//import java.awt.event.*;
//
//public class CustomerDetails extends JFrame implements ActionListener{
//
//    Choice meternumber, cmonth;
//    JTable table;
//    JButton search, print;
//    
//    CustomerDetails(){
//        super("Customer Details");
//        
//        setSize(1200, 650);
//        setLocation(200, 150);
//        
//        table = new JTable();
//        
//        try {
//            Conn c = new Conn();
//            ResultSet rs = c.s.executeQuery("select * from customer");
//            
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        JScrollPane sp = new JScrollPane(table);
//        add(sp);
//        
//        print = new JButton("Print");
//        print.addActionListener(this);
//        add(print, "South");
//        
//        
//        setVisible(true);
//    }
//    
//    public void actionPerformed(ActionEvent ae) {
//        try {
//            table.print();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        new CustomerDetails();
//    }
//}


package electricity.billing.system;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import java.util.concurrent.*;

public class CustomerDetails extends JFrame implements ActionListener {

    JTable table;
    JButton print, refresh;
    JLabel searchLabel;
    JTextField searchField;
    JComboBox<String> searchOptions;
    
    CustomerDetails() {
        super("Customer Details");
        
        setSize(1200, 650);
        setLocation(200, 150);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        
        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        searchLabel = new JLabel("Search By:");
        searchOptions = new JComboBox<>(new String[]{"Meter Number", "Name", "City"});
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        refresh = new JButton("Refresh");
        
        searchButton.addActionListener(this);
        refresh.addActionListener(this);
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchOptions);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refresh);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Loading indicator
        JLabel loadingLabel = new JLabel("Loading customer data...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        add(loadingLabel, BorderLayout.CENTER);
        
        // Print button at bottom
        print = new JButton("Print");
        print.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(print);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
        
        // Initial data load
        loadCustomerData(loadingLabel, "");
    }
    
    private void loadCustomerData(JLabel loadingLabel, String searchQuery) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Conn c = new Conn();
                    String query = "select * from customer";
                    
                    if (!searchQuery.isEmpty()) {
                        int searchType = searchOptions.getSelectedIndex();
                        String searchTerm = searchField.getText().trim();
                        
                        if (!searchTerm.isEmpty()) {
                            switch(searchType) {
                                case 0: // Meter Number
                                    query += " where meter_no like '%"+searchTerm+"%'";
                                    break;
                                case 1: // Name
                                    query += " where name like '%"+searchTerm+"%'";
                                    break;
                                case 2: // City
                                    query += " where city like '%"+searchTerm+"%'";
                                    break;
                            }
                        }
                    }
                    
                    Future<ResultSet> future = c.executeQueryAsync(query);
                    ResultSet rs = future.get();
                    
                    SwingUtilities.invokeLater(() -> {
                        table = new JTable(DbUtils.resultSetToTableModel(rs));
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                        
                        remove(loadingLabel);
                        JScrollPane scrollPane = new JScrollPane(table);
                        add(scrollPane, BorderLayout.CENTER);
                        revalidate();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        loadingLabel.setText("Error loading data: " + e.getMessage());
                    });
                }
                return null;
            }
        }.execute();
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == print) {
            new Thread(() -> {
                try {
                    boolean complete = table.print();
                    SwingUtilities.invokeLater(() -> {
                        if (complete) {
                            JOptionPane.showMessageDialog(this, "Printing completed successfully");
                        } else {
                            JOptionPane.showMessageDialog(this, "Printing was cancelled");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Printing error: " + e.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    });
                }
            }).start();
        } 
        else if (ae.getSource() instanceof JButton && 
                ((JButton)ae.getSource()).getText().equals("Search")) {
            JLabel loadingLabel = new JLabel("Searching...", SwingConstants.CENTER);
            loadingLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            getContentPane().removeAll();
            add(loadingLabel, BorderLayout.CENTER);
            add(new JPanel(), BorderLayout.NORTH); // Empty search panel
            add(new JPanel(), BorderLayout.SOUTH); // Empty button panel
            revalidate();
            
            loadCustomerData(loadingLabel, "search");
        }
        else if (ae.getSource() == refresh) {
            JLabel loadingLabel = new JLabel("Refreshing data...", SwingConstants.CENTER);
            loadingLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            getContentPane().removeAll();
            add(loadingLabel, BorderLayout.CENTER);
            add(new JPanel(), BorderLayout.NORTH); // Empty search panel
            add(new JPanel(), BorderLayout.SOUTH); // Empty button panel
            revalidate();
            
            searchField.setText("");
            loadCustomerData(loadingLabel, "");
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}