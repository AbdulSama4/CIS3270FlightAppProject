package GUI;

import BusinessLogic.Flight;
import Database.FlightData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

   // @FXML
   // private TableView<Flight> tableview;

    @FXML
    private Label lblflightBooked;

    @FXML
    private TextField custDepartDate;

    @FXML
    private TextField custDepartFrom;

    @FXML
    private TextField custArrivalTo;

    @FXML
    private Button seeFlightsButton;
    
    @FXML
    private TextField origin;
    
    @FXML
    private TextField destination;
    
    //@FXML
   // private TextField date;
    
    @FXML
    private TableColumn<Flight, Integer> colCapacity;

    @FXML
    private TableColumn<Flight, Integer> colNumBooked;

    private ObservableList<Flight> observableList = FXCollections.observableArrayList();

    private String date;
    private String from;
    private String to;

    @FXML
    void seeFlightsButtonClicked(ActionEvent event) {
        // Handle the button click event, e.g., fetch flights from the database
        // and update the TableView.
        lblflightBooked.setText("");

        date = custDepartDate.getText().toString();
        from = custDepartFrom.getText().toString();
        to = custArrivalTo.getText().toString();

        if (date.trim().equals("") || from.trim().equals("") || to.trim().equals("")) {
            lblflightBooked.setText("One or more search fields are empty.");
        } else {
            // Fetch flights from the database based on the user's criteria
            try {
                ObservableList<Flight> searchResults = getSearch(date, from, to);
                Table.setItems(searchResults);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately (e.g., show an error message)
            }
        }
    }
    
    @FXML
    void bookFlightsButtonClicked(ActionEvent event) {
        // Get the selected flight from the TableView
        Flight selectedFlight = Table.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            // No flight selected, display a message or handle as needed
            lblflightBooked.setText("Please select a flight to book.");
            return;
        }

        try {
            // Check if the flight is full
            if (selectedFlight.flightFull()) {
                lblflightBooked.setText("Sorry, the selected flight is full.");
            } else if (selectedFlight.flightTimeConflict(selectedFlight)) {
                // Check if there is a time conflict with the selected flight
                lblflightBooked.setText("Sorry, there is a time conflict with the selected flight.");
            } else {
                // Book the flight for the user
                selectedFlight.bookPassenger();

                // Update the TableView to reflect the changes
                ObservableList<Flight> updatedFlights = getSearch(date, from, to);
                Table.setItems(updatedFlights);

                // Display a success message or navigate to the Book.fxml
                lblflightBooked.setText("Flight booked successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
            lblflightBooked.setText("Error booking the flight: " + e.getMessage());
        }
    }

    // Method to retrieve flights from the database based on user criteria
    private ObservableList<Flight> getSearch(String date, String from, String to)
            throws ClassNotFoundException, SQLException {
        ObservableList<Flight> searchResults = FXCollections.observableArrayList();

        String sql = "SELECT * FROM flights WHERE departFrom = ? AND arrivalTo = ? AND date = ?";
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
                            rs.getString("arrivalTime"),
                            rs.getString("from"),
                            rs.getString("to"),
                            rs.getString("airlineName"),
                            rs.getInt("capacity"),
                            rs.getInt("numBooked"),
                            rs.getDouble("flight_price"),
                            rs.getString("flightID")
                    );
                    searchResults.add(flight);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return searchResults;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize the controller, if needed
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
                    rs.getString("arrivalTime"),
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
        }

        Table.setItems(observableList);
    }

}