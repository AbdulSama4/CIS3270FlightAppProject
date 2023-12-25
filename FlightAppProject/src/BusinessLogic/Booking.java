package BusinessLogic;

public class Booking {
    private int bookingId;
    private String username;
    private String flightNum;
    private String bookingDate;

    public Booking(int bookingId, String username, String flightNum, String bookingDate) {
        this.bookingId = bookingId;
        this.username = username;
        this.flightNum = flightNum;
        this.bookingDate = bookingDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getUsername() {
        return username;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public String getBookingDate() {
        return bookingDate;
    }
}