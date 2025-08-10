import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/CustomerRegisterServlet", new CustomerRegisterHandler());
        server.setExecutor(null);
        System.out.println("Server running on http://localhost:8080/");
        server.start();
    }
}
