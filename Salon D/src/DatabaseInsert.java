import java.sql.*;

public class DatabaseInsert {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // UCanAccess driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

           
            String dbPath = "C:/Users/User/Desktop/DiwyaSalon.accdb";  // <-- replace as needed
            String url = "jdbc:ucanaccess://" + dbPath;

            // Connect
            conn = DriverManager.getConnection(url);

            // Insert Appointment
            String sql = "INSERT INTO Appointments (customer_name, phone, date, time, total, services) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Sithara Geethanjali");
            ps.setString(2, "0711234567");
            ps.setDate(3, java.sql.Date.valueOf("2025-08-04"));
            ps.setString(4, "14:00");
            ps.setInt(5, 4500);
            ps.setString(6, "Hair Cut 1 - Rs.2500, Facial - Rs.2000");

            int rows = ps.executeUpdate();
            System.out.println("Inserted rows: " + rows);

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
