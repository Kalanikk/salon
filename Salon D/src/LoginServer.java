import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;

public class LoginServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/login", new LoginHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }

    static class LoginHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
                String body = reader.readLine();
                String[] params = body.split("&");
                String username = "", password = "";

                for (String param : params) {
                    String[] pair = param.split("=");
                    if (pair[0].equals("username")) username = pair[1];
                    if (pair[0].equals("password")) password = pair[1];
                }

                String role = authenticate(username, password);
                String response = (role != null) ? role : "invalid";

                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private String authenticate(String username, String password) {
            try {
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/diwya_db", "root", "root"
                );
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT role FROM user WHERE username=? AND password=?"
                );
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) return rs.getString("role");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
