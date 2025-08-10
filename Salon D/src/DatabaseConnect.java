import java.sql.*;

public class DatabaseConnect {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // MS Access DB path
            String dbPath = "C:/Users/User/Desktop/SalonDB.accdb"; 
            String url = "jdbc:ucanaccess://" + dbPath;

            conn = DriverManager.getConnection(url);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Appoinment"); // <-- ඔබේ table name එක දාන්න

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("ID")); // <-- column name එකට සරිලන එක දාන්න
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
