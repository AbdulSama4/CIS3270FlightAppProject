package GUI;

import BusinessLogic.Flight;
import BusinessLogic.SessionManager;
import Database.FlightData;
import Database.UserBookingsData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FlightController extends MainMenuController implements Initializable {

    @FXML
    private TableColumn<Flight, String> colFlightNum;

    @FXML
    private TableView<Flight> Table;

    @FXML
    private TableColumn<Flight, String> colDate;

    @FXML
    private TableColumn<Flight, String> colDepartureTime;

    @FXML
    private TableColumn<Flight, String> colDepartFrom;

    @FXML
    private TableColumn<Flight, String> colArrivalTo;

    @FXML
    private TableColumn<Flight, String> colAirline;

    @FXML
    private TableColumn<Flight, String> colSeatPrice;

    @FXML
    private TableColumn<Flight, Integer> colCapacity;

    @FXML
    private TableColumn<Flight, Integer> colNumBooked;

    @FXML
    private Label lblflightBooked;

    @FXML
    private TextField origin;

    @FXML
    private TextField destination;

    @FXML
    private TextField flightDate;

    private ObservableList<Flight> observableList = FXCollections.observableArrayList();
    private String date;
    

    @FXML
    void enterButtonClicked(ActionEvent event) {
        lblflightBooked.setText("");

        date = flightDate.getText();
        String from = origin.getText();
        String to = destination.getText();

        if (date.isEmpty() || from.isEmpty() || to.isEmpty()) {
            lblflightBooked.setText("One or more search fields are empty.");
        } else {
            try {
                observableList.clear();
                ObservableList<Flight> searchResults = getSearch(date, from, to);
                observableList.addAll(searchResults);
                Table.setItems(observableList);
                Table.setVisible(true);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                lblflightBooked.setText("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    void seeFlightsButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Book.fxml"));
            Parent bookParent = loader.load();
            Scene bookScene = new Scene(bookParent);

            // Get the current stage and set the new scene
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(bookScene);

            // Show the stage
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
       

    @FXML
    void bookFlightsButtonClicked(ActionEvent event) {
        Flight selectedFlight = Table.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            lblflightBooked.setText("Please select a flight to book.");
            return;
        }

        try {
        	String username = SessionManager.getCurrentUsername(); // Get the username of the current signed-in user customerID = getcustomerID(); // Get the ID of the current signed-in user
            String flightNum = selectedFlight.getFlightNum();

            if (!UserBookingsData.isBooked(username, flightNum)) {
                // Check if the user has not already booked this flight
                FlightData flightData = new FlightData(); // Instantiate FlightData
                if (!flightData.flightFull(flightNum)) {
                    // Check if the flight is not full
                	UserBookingsData.addBooking(username, flightNum);

                    // Update the TableView to reflect the changes
                    ObservableList<Flight> updatedFlights = getSearch(date, origin.getText(), destination.getText());
                    Table.setItems(updatedFlights);

                    lblflightBooked.setText("Flight booked successfully!");
                } else {
                    lblflightBooked.setText("Sorry, the flight is already full.");
                }
            } else {
                lblflightBooked.setText("You have already booked this flight.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblflightBooked.setText("Error booking the flight: " + e.getMessage());
        }
        
        String selectedFlightNum = selectedFlight.getFlightNum();
        SessionManager.setSelectedFlightNum(selectedFlightNum);
        
}

    private ObservableList<Flight> getSearch(String date, String from, String to)
            throws ClassNotFoundException, SQLException {
        ObservableList<Flight> searchResults = FXCollections.observableArrayList();

        String sql = "SELECT * FROM flights WHERE `from` = ? AND `to` = ? AND departureDate = ?";
        try (Connection con = FlightData.getConnection();
             PreparedStatement myStmt = con.prepareStatement(sql)) {

            myStmt.setString(1, from);
            myStmt.setString(2, to);
            myStmt.setString(3, date);

            try (ResultSet rs = myStmt.executeQuery()) {
                while (rs.next()) {
                    Flight flight = new Flight(
                            rs.getString("flightNum"),
                            rs.getString("departureDate"),
                            rs.getString("departureTime"),
                            rs.getString("from"),
                            rs.getString("to"),
                            rs.getString("airlineName"),
                            rs.getInt("capacity"),
                            rs.getInt("numBooked"),
                            rs.getDouble("flightPrice"),
                            rs.getString("flightID")
                    );
                    searchResults.add(flight);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            lblflightBooked.setText("Error: " + ex.getMessage());
        }
        return searchResults;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colFlightNum.setCellValueFactory(new PropertyValueFactory<>("flightNum"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        colDepartFrom.setCellValueFactory(new PropertyValueFactory<>("from"));
        colArrivalTo.setCellValueFactory(new PropertyValueFactory<>("to"));
        colAirline.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        colSeatPrice.setCellValueFactory(new PropertyValueFactory<>("flightPrice"));
        colCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colNumBooked.setCellValueFactory(new PropertyValueFactory<>("numBooked"));

        try {
            Connection con = FlightData.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM flights");

            while (rs.next()) {
                observableList.add(new Flight(
                        rs.getString("flightNum"),
                        rs.getString("departureDate"),
                        rs.getString("departureTime"),
                        rs.getString("from"),
                        rs.getString("to"),
                        rs.getString("airlineName"),
                        rs.getInt("capacity"),
                        rs.getInt("numBooked"),
                        rs.getDouble("flightPrice"),
                        rs.getString("flightID")
                ));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            lblflightBooked.setText("Error: " + ex.getMessage());
        }

        Table.setItems(observableList);
        Table.setVisible(false);
    }
}