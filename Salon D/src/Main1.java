import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main1 {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/book", new BookingHandler());
        server.setExecutor(null);
        System.out.println("Server started at http://localhost:8080/");
        server.start();
    }
}
