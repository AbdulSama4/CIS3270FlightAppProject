package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBookingsData {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CustomerData";
    private static final String USER = "root";
    private static final String PASSWORD = "Ionicwave805.";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // Add a new booking for the current user
    public static void addBooking(String username, String flightNum) throws SQLException {
        String sql = "INSERT INTO user_bookings (username, flightNum, booking_date) VALUES (?, ?, NOW())";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setString(1, username);
            myStmt.setString(2, flightNum);
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Booking added successfully for username: " + username + ", flightNum: " + flightNum);
            } else {
                System.out.println("No rows were affected. Booking not added.");
            }
        }
    }

    // Delete a booking for the current user
    public static void deleteBooking(String username, String flightNum) throws SQLException {
        String sql = "DELETE FROM user_bookings WHERE username = ? AND flightNum = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setString(1, username);
            myStmt.setString(2, flightNum);
            myStmt.executeUpdate();
        }
    }

    // Check if the current user has booked a specific flight
    public static boolean isBooked(String username, String flightNum) throws SQLException {
        String sql = "SELECT * FROM user_bookings WHERE username = ? AND flightNum = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {
            myStmt.setString(1, username);
            myStmt.setString(2, flightNum);
            try (ResultSet myRs = myStmt.executeQuery()) {
                return myRs.next();
            }
        }
    }
}