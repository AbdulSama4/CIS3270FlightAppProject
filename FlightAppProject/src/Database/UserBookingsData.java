package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BusinessLogic.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
             // Update the flights table - decrease capacity and increase numBooked
                updateFlight(flightNum);
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
    
    private static void updateFlight(String flightNum) throws SQLException {
        String selectSql = "SELECT capacity, numBooked FROM flights WHERE flightNum = ?";
        String updateSql = "UPDATE flights SET capacity = ?, numBooked = ? WHERE flightNum = ?";

        try (Connection myConn = getConnection();
             PreparedStatement selectStmt = myConn.prepareStatement(selectSql);
             PreparedStatement updateStmt = myConn.prepareStatement(updateSql)) {

            // Get the current capacity and numBooked values
            selectStmt.setString(1, flightNum);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    int currentCapacity = rs.getInt("capacity");
                    int currentNumBooked = rs.getInt("numBooked");

                    // Update the flights table
                    updateStmt.setInt(1, currentCapacity - 1); // Decrease capacity
                    updateStmt.setInt(2, currentNumBooked + 1); // Increase numBooked
                    updateStmt.setString(3, flightNum);
                    updateStmt.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
}
    
    
}