import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerRegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder buf = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                buf.append(line);
            }

            Map<String, String> formData = parseFormData(buf.toString());
            String name = formData.get("name");
            String username = formData.get("username");
            String password = formData.get("password");
            String phone = formData.get("phone");

            boolean success = insertIntoDatabase(name, username, password, phone);
            String response = success ? "Registration successful!" : "Registration failed!";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            String response = "Only POST method is allowed.";
            exchange.sendResponseHeaders(405, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private boolean insertIntoDatabase(String name, String username, String password, String phone) {
        String jdbcURL = "jdbc:mysql://localhost:3306/diwya_db?useSSL=false&serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO customers (name, username, password, phone) VALUES (?, ?, ?, ?)")) {

                stmt.setString(1, name);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.setString(4, phone);
                return stmt.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=", 2);
            String key = URLDecoder.decode(parts[0], "UTF-8");
            String value = parts.length > 1 ? URLDecoder.decode(parts[1], "UTF-8") : "";
            result.put(key, value);
        }
        return result;
    }
}
