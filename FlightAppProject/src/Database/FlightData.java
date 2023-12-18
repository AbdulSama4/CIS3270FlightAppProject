package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightData {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/CustomerData";
    private static final String USER = "root";
    private static final String PASSWORD = "Ionicwave805.";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public void addFlight(String flightNums, String flightDate, String departTime,
                          String departFrom, String arrivalTo, String airline, String seatPrices)
            throws SQLException {
        String sql = "INSERT INTO flights VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setString(1, flightNums);
            myStmt.setString(2, flightDate);
            myStmt.setString(3, departTime);
            myStmt.setString(4, departFrom);
            myStmt.setString(5, arrivalTo);
            myStmt.setString(6, airline);
            myStmt.setString(7, seatPrices);

            myStmt.executeUpdate();
        }
    }

    public boolean unique(int customerID, String flightNum) throws SQLException {
        String sql = "SELECT * FROM bookings WHERE customerID = ? AND flightNum = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setInt(1, customerID);
            myStmt.setString(2, flightNum);

            try (ResultSet myRs = myStmt.executeQuery()) {
                return !myRs.next();
            }
        }
    }

    public void book(int customerID, String flightNum) throws SQLException {
        String sql = "INSERT INTO bookings VALUES (?, ?, ?)";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setInt(1, 0);
            myStmt.setInt(2, customerID);
            myStmt.setString(3, flightNum);

            myStmt.executeUpdate();
        }
    }

    public void deleteBook(int customerID, String flightNum) throws SQLException {
        String sql = "DELETE FROM bookings WHERE customerID = ? AND flightNum = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setInt(1, customerID);
            myStmt.setString(2, flightNum);

            myStmt.executeUpdate();
        }
    }

    public boolean flightFull(String flightNum) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bookings WHERE flightNum = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setString(1, flightNum);

            try (ResultSet myRs = myStmt.executeQuery()) {
                if (myRs.next()) {
                    int count = myRs.getInt(1);
                    return count >= 5;
                }
            }
        }
        return false;
    }

    public boolean flightTimeConflict(String date, String time, int customerID) throws SQLException {
        String sql = "SELECT flights.date, flights.departureTime FROM flights " +
                "INNER JOIN bookings ON flights.flightNum = bookings.flightNum " +
                "WHERE customerID = ? AND flights.date = ? AND flights.departureTime = ?";
        try (Connection myConn = getConnection();
             PreparedStatement myStmt = myConn.prepareStatement(sql)) {

            myStmt.setInt(1, customerID);
            myStmt.setString(2, date);
            myStmt.setString(3, time);

            try (ResultSet rs = myStmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
