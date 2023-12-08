package BusinessLogic;
import java.util.Date;


public class Booking extends Flight{
	
	// Counter for generating unique confirmation numbers
    private static int confirmationNumCounter = 0;

    // Unique confirmation number for each booking
    private int confirmationNum;

    // Time when the booking was made
    private long timeBooked;

    // Status of the booking (e.g., "Booked", "Cancelled", etc.)
    private String status;

    // Price of the booked flight
    private String bookedPrice;

    // Social Security Number of the passenger
    private String SSN;

    // Default constructor
    public Booking() {}

    // Parameterized constructor for creating a booking with specific details
    public Booking(String flightNum, String departureDate, String departureTime, String arrivalTime,
                   String flightDuration, String to, String from, String airlineName, int capacity, int numBooked,
                   String destinationAirport, String flight_price, String boardingTime, String flightID,
                   String SSN) {
        // Call the constructor of the superclass (Flight) to set flight-related details
        super(flightNum, departureDate, departureTime, arrivalTime, flightDuration, to, from, airlineName, capacity, numBooked,
                destinationAirport, flight_price, boardingTime, flightID);

        // Increment the confirmation number counter and set the unique confirmation number
        this.confirmationNum = ++confirmationNumCounter;

        // Set the time when the booking was made to the current time
        this.timeBooked = new Date().getTime();

        // Set the initial status to "Booked"
        this.status = "Booked";

        // Set the booked price
        this.bookedPrice = flight_price;

        // Set the Social Security Number of the passenger
        this.SSN = SSN;
    }

    // Getter method for retrieving the confirmation number
    public int getConfirmationNum() {
        return confirmationNum;
    }

    // Getter method for retrieving the time when the booking was made
    public long getTimeBooked() {
        return timeBooked;
    }

    // Getter method for retrieving the status of the booking
    public String getStatus() {
        return status;
    }

    // Setter method for updating the status of the booking
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter method for retrieving the booked price
    public String getBookedPrice() {
        return bookedPrice;
    }

    // Setter method for updating the booked price
    public void setBookedPrice(String bookedPrice) {
        this.bookedPrice = bookedPrice;
    }

    // Getter method for retrieving the Social Security Number of the passenger
    public String getSSN() {
        return SSN;
    }

    // Setter method for updating the Social Security Number of the passenger
    public void setSSN(String ssn) {
        this.SSN = ssn;
    }

}
