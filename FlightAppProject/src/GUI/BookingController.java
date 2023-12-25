package GUI;

import BusinessLogic.Booking;
import BusinessLogic.SessionManager;
import Database.UserBookingsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BookingController implements Initializable {

    @FXML
    private TableView<Booking> Table;

   // @FXML
   // private TableColumn<Booking, Integer> colBookingId;

    @FXML
    private TableColumn<Booking, String> colUsername;

    @FXML
    private TableColumn<Booking, String> colFlightNum;

   // @FXML
   // private TableColumn<Booking, String> colBookingDate;

    //@FXML
    //private Label lblflightBooked;

    private ObservableList<Booking> observableList;  // Define observableList

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        observableList = FXCollections.observableArrayList();  // Initialize observableList

        //colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colFlightNum.setCellValueFactory(new PropertyValueFactory<>("flightNum"));
        //colBookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        try {
            // Retrieve user bookings from the database
            String username = SessionManager.getCurrentUsername();
            ObservableList<Booking> userBookings = getUserBookings(username);

            // Populate the table with user bookings
            observableList.addAll(userBookings);
            Table.setItems(observableList);
        } catch (Exception ex) {
            ex.printStackTrace();
            //lblflightBooked.setText("Error: " + ex.getMessage());
        }
    }

    // Method to get the user's bookings from the database
    private ObservableList<Booking> getUserBookings(String username) {
        ObservableList<Booking> userBookings = FXCollections.observableArrayList();

        // You can modify the SQL query based on your database schema
        String sql = "SELECT * FROM user_bookings WHERE username = ?";
        try (Connection con = UserBookingsData.getConnection();
             PreparedStatement myStmt = con.prepareStatement(sql)) {

            myStmt.setString(1, username);

            try (ResultSet rs = myStmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking(
                            rs.getInt("booking_id"),
                            rs.getString("username"),
                            rs.getString("flightNum"),
                            rs.getString("booking_date")
                    );
                    userBookings.add(booking);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //lblflightBooked.setText("Error: " + ex.getMessage());
        }
        return userBookings;
    }
    
    @FXML
    void goBack(ActionEvent event) {
        try {
            // Load the main menu FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("flightdata.fxml"));
            Parent mainMenuParent = loader.load();

            // Set the main menu scene
            Scene mainMenuScene = new Scene(mainMenuParent);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(mainMenuScene);

            // Show the stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void deleteBookedFlight(ActionEvent event) {
    }
}