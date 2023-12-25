package GUI;

import java.io.IOException;

import BusinessLogic.Flight;
import BusinessLogic.SessionManager;
import Database.UserBookingsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BookingController {

    @FXML
    private Label lblflightBooked;

    @FXML
    private TableView<Flight> Table;  // Specify the type

    @FXML
    private TableColumn<Flight, String> colFlightNum;  // Specify the types

    @FXML
    private TableColumn<Flight, String> colDate;  // Specify the types

    @FXML
    private TableColumn<Flight, String> colDepartureTime;  // Specify the types

    @FXML
    private TableColumn<Flight, String> colDepartFrom;  // Specify the types

    @FXML
    private TableColumn<Flight, String> colArrivalTo;  // Specify the types

    @FXML
    private TableColumn<Flight, Double> colSeatPrice;  // Specify the types

    @FXML
    private TableColumn<Flight, String> colAirline;  // Specify the types

    @FXML
    private TableColumn<Flight, Integer> colCapacity;  // Specify the types

    @FXML
    private TableColumn<Flight, Integer> colNumBooked;  // Specify the types

    @FXML
    private Button backButton;

    @FXML
    private Button deleteBookedFlightButton;

    @FXML
    void deleteBookedFlight(ActionEvent event) {
        Flight selectedBooking = Table.getSelectionModel().getSelectedItem();

        if (selectedBooking != null) {
            // Get the flight number of the selected booking
            String flightNum = selectedBooking.getFlightNum();

            // Add logic to delete the booked flight (use the flightNum)
            try {
                // Call the method to delete the booking
                deleteBookedFlight(SessionManager.getCurrentUsername(), flightNum);

                // Refresh the table with updated data
                refreshUserBookingsTable();

                lblflightBooked.setText("Booking deleted successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                lblflightBooked.setText("Error deleting the booking: " + e.getMessage());
            }
        } else {
            lblflightBooked.setText("Please select a booked flight to delete.");
        }
    }

    private void deleteBookedFlight(String username, String flightNum) {
        try {
            // Call your method to delete the booked flight from the database
            UserBookingsData.deleteBooking(username, flightNum);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    // Method to refresh the user's bookings table
    private void refreshUserBookingsTable() {
        try {
            // Retrieve the user's booked flights from the database
            String username = SessionManager.getCurrentUsername();
            ObservableList<Flight> userBookings = getUserBookings(username);

            // Update the TableView
            Table.setItems(userBookings);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    // Method to get the user's booked flights from the database
    private ObservableList<Flight> getUserBookings(String username) {
        // Implement logic to retrieve user's booked flights from the database
        // (Similar to how you retrieve flights in FlightController)

        // For demonstration purposes, return an empty list
        return FXCollections.observableArrayList();
    }
    
    @FXML
    void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("flightdata.fxml"));
            AnchorPane flightParent = loader.load();
            Scene flightScene = new Scene(flightParent);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(flightScene);

            // Show the stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        // Add any initialization logic here
    }
}