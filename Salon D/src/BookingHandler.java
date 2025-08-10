import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class BookingHandler implements HttpHandler {
    @Override

    public static void main (String[]args){
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map<String, String> data = parseFormData(formData);

            String pkg = data.get("package");
            String name = data.get("name");
            String phone = data.get("phone");
            String date = data.get("date");
            String time = data.get("time");

            DBUtil.saveBooking(pkg, name, phone, date, time);

            String response = "Booking Successful!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }}

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyVal = pair.split("=");
            String key = URLDecoder.decode(keyVal[0], "UTF-8");
            String val = URLDecoder.decode(keyVal[1], "UTF-8");
            map.put(key, val);
        }
        return map;
    }
}
