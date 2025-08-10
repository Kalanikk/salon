import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/salon";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void saveBooking(String packageName, String customerName, String phone, String date, String time) {
        String sql = "INSERT INTO bookings (package_name, customer_name, phone, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, packageName);
            ps.setString(2, customerName);
            ps.setString(3, phone);
            ps.setDate(4, Date.valueOf(date));
            ps.setTime(5, Time.valueOf(time));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
