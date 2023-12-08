package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import BusinessLogic.Customer;
import BusinessLogic.Flight;
import Database.FlightData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FlightController extends MainMenuController implements Initializable {
	
	@FXML
    private TableColumn<String, String> airlineName;
    
    @FXML
    private TableColumn<String, String> colFkightNum;

    @FXML
    private TextField custDepartDate;

    @FXML
    private TableColumn<String, String> colDate;

    @FXML
    private TableColumn<String, String> colDepartureTime;

   // @FXML
   // private TextField custDepartFrom;

    @FXML
    private TableColumn<String, String> colFlightNum;

    @FXML
    private TableColumn<String, String> colDepartFrom;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField custArrivalTo;

   // @FXML
   // private TableColumn<String, String> colTo;

    @FXML
    private TextField custDepartFrom;

    @FXML
    private TableColumn<String, String> colArrivalTo;
    
    @FXML
    private TableColumn<String, String> colAirline;
    
    @FXML
    private TableColumn<String, String> colSeatPrice;

    @FXML
    private Button seeFlightsButton;

    @FXML
    private TableColumn<String, String> colTo;

    private ObservableList<Flight> observableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Flight> tableview;

    @FXML
    private Label lblflightBooked;
    
    @FXML
    private TableColumn<String, String> colPrice;

    private String date;
    private String from;
    private String to;
	
	//ObservableList<Flights> observableList = FXCollections.observableArrayList();
	
	// when main menu button is clicked anywhere it returns to login screen.
	public void mainMenuBtnClicked(ActionEvent event) throws Exception {
			Parent register = FXMLLoader.load(getClass().getResource("main.fxml"));
			Scene registerScene = new Scene(register);	
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(registerScene);
			window.show();
		}
	
	// when user clicks book flights CONSTRUCTION
	@FXML
    void bookFlightsBtnClicked(ActionEvent event) {
        lblflightBooked.setText("");

        Flight selectedFlight = tableview.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            lblflightBooked.setText("Please select a flight first.");
        } else {
            String flight = selectedFlight.getFlightNum();
            String flightDate = selectedFlight.getDepartureDate();
            String flightTime = selectedFlight.getDepartureTime();

            Customer customer = new Customer();
            int id = (customer).getCustomerID();

            if (unique(customer, flight)) {
                lblflightBooked.setText("You already have flight " + flight + " booked.");
            } else {
                if (flightFull(flight)) {
                    lblflightBooked.setText("Sorry, the flight is full.");
                } else {
                    if (flightTimeConflict(flightDate, flightTime)) {
                        lblflightBooked.setText("You already have a flight scheduled at this time.");
                    } else {
                        try {
                            //book flight
                            selectedFlight.bookPassenger();
                            lblflightBooked.setText("Flight " + flight + " is now booked.");
                        } catch (Exception e) {
                            lblflightBooked.setText(e.getMessage());
                        }
                    }
                }
            }
        }
    }
		
	// when search button is clicked it shows list of flights user specified
	public void searchBtnClicked(ActionEvent event) throws Exception {
		
		lblflightBooked.setText("");
		date = custDepartDate.getText().toString();
		from = custDepartFrom.getText().toString();
		to = custArrivalTo.getText().toString();
			
		if (date.trim().equals("") || from.trim().equals("") || to.trim().equals("")) {
				
			lblflightBooked.setText("One or more search fields are empty.");
		}
			else {
			colFlightNum.setCellValueFactory(new PropertyValueFactory<>("FlightNum"));
			colDate.setCellValueFactory(new PropertyValueFactory<>("FlightDate"));
			colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("DepartTime"));
			colDepartFrom.setCellValueFactory(new PropertyValueFactory<>("DepartFrom"));
			colArrivalTo.setCellValueFactory(new PropertyValueFactory<>("ArrivalTo"));
			colAirline.setCellValueFactory(new PropertyValueFactory<>("Airline"));
			colSeatPrice.setCellValueFactory(new PropertyValueFactory<>("SeatPrice"));
			tableview.setItems(getSearch(date,from,to));
			}
		}

	// when see all flights button is clicked it shows list of all flights available
	public void seeAllFlightsClicked(ActionEvent event) throws Exception {
		lblflightBooked.setText("");
		try {
			
			Connection con = FlightData.getConnection();
			ResultSet rs = con.createStatement().executeQuery("select * from flights");
				
			while(rs.next()) {
				observableList.add(new Flights(rs.getString("FlightNum"), rs.getString("date"),
									rs.getString("departureTime"), rs.getString("departFrom"),
									rs.getString("arrivalDestination"), rs.getString("airline"),
									rs.getString("seatPrice")));		
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		colFlightNum.setCellValueFactory(new PropertyValueFactory<>("FlightNum"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("FlightDate"));
		colDepartureTime.setCellValueFactory(new PropertyValueFactory<>("DepartTime"));
		colDepartFrom.setCellValueFactory(new PropertyValueFactory<>("DepartFrom"));
		colArrivalTo.setCellValueFactory(new PropertyValueFactory<>("ArrivalTo"));
		colAirline.setCellValueFactory(new PropertyValueFactory<>("Airline"));
		colSeatPrice.setCellValueFactory(new PropertyValueFactory<>("SeatPrice"));
		tableview.setItems(observableList);
	}
	
	//when this method is called it searches through flight database and returns
	// flights in an observable list based off of user entered criteria
	public static ObservableList<Flight> getSearch(String date, String from, String to) 
			throws ClassNotFoundException, SQLException {
		
	ObservableList<Flight> obList = FXCollections.observableArrayList();
			
	PreparedStatement myStmt = null;
	ResultSet rs = null;
	String sql = " select * from flights where departFrom = " + "'" + from + "'"
		+ "and arrivalDestination = " + "'" + to + "'" + " and date = " + "'" + date + "'";
			
		try {	
			Connection con = FlightData.getConnection();
			myStmt = con.prepareStatement(sql);
			rs = myStmt.executeQuery();
				
			while(rs.next()) {
				obList.add(new Flights(rs.getString("FlightNum"), rs.getString("date"),
						rs.getString("departureTime"), rs.getString("departFrom"),
						rs.getString("arrivalDestination"), rs.getString("airline"),
						rs.getString("seatPrice")));
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return obList;
	}

	@FXML
	void bookFlightsButtonClicked(ActionEvent event) {
	    try {
	        // Load the Book.fxml file
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Book.fxml"));
	        Parent bookForm = loader.load();

	        // Create a new scene
	        Scene bookScene = new Scene(bookForm);

	        // Get the stage information
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	        // Set the new scene on the stage
	        stage.setScene(bookScene);

	        // Show the stage
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the exception appropriately (e.g., show an error message)
	    }
    }
    
    // Method to retrieve all flights from the database
    private List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();

        try {
            Connection con = FlightData.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from flights");

            while (rs.next()) {
                Flight flight = new Flight(
                        rs.getString("flightNum"),
                        rs.getString("departureDate"),
                        rs.getString("departureTime"),
                        rs.getString("arrivalTime"),
                        rs.getString("flightDuration"),
                        rs.getString("to"),
                        rs.getString("from"),
                        rs.getString("airlineName"),
                        rs.getInt("capacity"),
                        rs.getInt("numBooked"),
                        rs.getString("destinationAirport"),
                        rs.getString("flight_price"),
                        rs.getString("boardingTime"),
                        rs.getString("flightID")
                );

                flights.add(flight);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flights;
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	

}

