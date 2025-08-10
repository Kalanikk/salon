import java.sql.*;
import java.time.LocalDate;

public class InsertAppointment {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            // Step 1: Load UCanAccess JDBC Driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            // Step 2: Database file path
            String dbPath = "C:/Users/User/Desktop/DiwyaSalon.accdb"; // <-- Change to your real path
            String url = "jdbc:ucanaccess://" + dbPath;

            // Step 3: Connect to the database
            conn = DriverManager.getConnection(url);

            // Step 4: Insert data
            String sql = "INSERT INTO Appointments (customer_name, phone, date, time, total, services) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Sample form values
            String name = "Sithara Geethanjali";
            String phone = "0711234567";
            String date = "2025-08-05";  // format: yyyy-MM-dd
            String time = "14:00";
            int total = 4500;
            String services = "Hair Cut - Rs.2500, Facial - Rs.2000";

            // Set parameters
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setDate(3, Date.valueOf(LocalDate.parse(date)));
            ps.setString(4, time);
            ps.setInt(5, total);
            ps.setString(6, services);

            // Execute insert
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Appointment booked successfully!");
            }

            // Clean up
            ps.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
