import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class connect {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // MS Access DB file path (absolute path)
            String dbPath = "C:/path/to/your/database.accdb";

            // JDBC URL
            String url = "jdbc:ucanaccess://" + dbPath;

            // Create connection
            conn = DriverManager.getConnection(url);

            System.out.println("✅ Connected to MS Access Database!");

            // Sample query
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM YourTableName");

            while (rs.next()) {
                System.out.println(rs.getString(1) + " - " + rs.getString(2));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
