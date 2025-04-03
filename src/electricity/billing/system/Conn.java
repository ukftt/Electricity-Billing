//package electricity.billing.system;
//
//import java.sql.*;
//
//public class Conn {
//
//    Connection c;
//    Statement s;
//    Conn() {
//        try {
//            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bills", "root", "root");
//            s = c.createStatement();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}


//
//package electricity.billing.system;
//
//import java.sql.*;
//
//public class Conn {
//
//    Connection c;
//    Statement s;
//
//    // Constructor for establishing connection
//    public Conn() {
//        try {
//            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bills", "root", "1234");
//            s = c.createStatement();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Method to fetch name and address using meter number
//    public String[] getCustomerDetails(String meterNumber) {
//        String[] details = new String[2];
//        try {
//            String query = "SELECT login.name, customer.address FROM login JOIN customer ON login.meter_no = customer.meter_no WHERE login.meter_no = ?";
//            PreparedStatement stmt = c.prepareStatement(query);
//            stmt.setString(1, meterNumber);
//
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                details[0] = rs.getString("name");
//                details[1] = rs.getString("address");
//            } else {
//                details[0] = "Not Found";
//                details[1] = "Not Found";
//            }
//
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return details;
//    }
//}

package electricity.billing.system;

import java.sql.*;
import java.util.concurrent.*;

public class Conn {
    private static final int MAX_THREADS = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
    
    Connection c;
    Statement s;
    

    public Conn() {
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/bills", "root", "root");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Future<ResultSet> executeQueryAsync(final String query) {
        return executor.submit(() -> {
            try {
                return s.executeQuery(query);
            } catch (SQLException e) {
                throw new RuntimeException("Database query failed", e);
            }
        });
    }

    public Future<Integer> executeUpdateAsync(final String query) {
        return executor.submit(() -> {
            try {
                return s.executeUpdate(query);
            } catch (SQLException e) {
                throw new RuntimeException("Database update failed", e);
            }
        });
    }

    public static void shutdown() {
        executor.shutdown();
    }
}
