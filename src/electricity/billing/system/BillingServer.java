package electricity.billing.system;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class BillingServer {
    private static final int PORT = 12345;
    private static ServerSocket serverSocket;
    private static final ExecutorService clientExecutor = Executors.newCachedThreadPool();
    
    public static void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Billing Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientExecutor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                
                String request = in.readLine();
                System.out.println("Received request: " + request);
                
                if (request.startsWith("PAY:")) {
                    handlePaymentRequest(request, out);
                } else if (request.startsWith("GET_BILL:")) {
                    handleBillRequest(request, out);
                } else {
                    out.println("INVALID_REQUEST");
                }
            } catch (IOException e) {
                System.err.println("Client handling error: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
        
        private void handlePaymentRequest(String request, PrintWriter out) {
            String[] parts = request.split(":");
            if (parts.length == 3) {
                String meterNo = parts[1];
                double amount = Double.parseDouble(parts[2]);
                
                try {
                    Conn c = new Conn();
                    String query = "UPDATE bill SET status = 'Paid' WHERE meter_no = '" + meterNo + "'";
                    c.s.executeUpdate(query);
                    out.println("PAYMENT_SUCCESS:" + meterNo);
                } catch (Exception e) {
                    out.println("PAYMENT_FAILED:" + meterNo);
                }
            } else {
                out.println("INVALID_PAYMENT_REQUEST");
            }
        }
        
        private void handleBillRequest(String request, PrintWriter out) {
            String[] parts = request.split(":");
            if (parts.length == 2) {
                String meterNo = parts[1];
                try {
                    Conn c = new Conn();
                    ResultSet rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_no = '" + meterNo + "'");
                    
                    StringBuilder response = new StringBuilder("BILL_INFO:");
                    while (rs.next()) {
                        response.append(rs.getString("month")).append(",")
                               .append(rs.getString("units")).append(",")
                               .append(rs.getString("totalbill")).append(",")
                               .append(rs.getString("status")).append(";");
                    }
                    out.println(response.toString());
                } catch (Exception e) {
                    out.println("BILL_ERROR:" + meterNo);
                }
            } else {
                out.println("INVALID_BILL_REQUEST");
            }
        }
    }
    
    public static void stopServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            clientExecutor.shutdown();
            System.out.println("Billing Server stopped");
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        startServer();
    }
}