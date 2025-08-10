import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookingServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/submitBooking", new BookingHandler());
        server.setExecutor(null);
        System.out.println("Server started at http://localhost:8080");
        server.start();
    }
}

class BookingHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            // Read request body
            InputStream is = exchange.getRequestBody();
            String formData = new String(is.readAllBytes());
            Map<String, String> params = parseFormData(formData);

            // Extract values
            String name = params.get("name");
            String phone = params.get("phone");
            String date = params.get("date");
            String time = params.get("time");
            String service = params.get("service");

            // Insert into DB
            String response;
            try {
                insertBooking(name, phone, date, time, service);
                response = "Booking saved successfully!";
            } catch (Exception e) {
                e.printStackTrace();
                response = "Error saving booking.";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private Map<String, String> parseFormData(String formData) {
        Map<String, String> params = new HashMap<>();
        for (String pair : formData.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(decode(keyValue[0]), decode(keyValue[1]));
            }
        }
        return params;
    }

    private String decode(String value) {
        return value.replace("+", " ").replaceAll("%40", "@").replaceAll("%3A", ":");
    }

    private void insertBooking(String name, String phone, String date, String time, String service) throws Exception {
        String jdbcURL = "jdbc:mysql://localhost:3306/diwya_db";
        String dbUser = "root";
        String dbPass = "root";

        Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPass);
        String sql = "INSERT INTO bookings (name, phone, date, time, service) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, phone);
        stmt.setDate(3, Date.valueOf(date));
        stmt.setTime(4, Time.valueOf(time));
        stmt.setString(5, service);
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
}
