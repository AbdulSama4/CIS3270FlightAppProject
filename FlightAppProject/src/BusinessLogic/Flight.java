package BusinessLogic;

public class Flight {

	protected String flightID;
	
	private String flightNum;
	
	private String departureDate;
	
	private String departureTime;
	
	private String arrivalTime;
	
	//private String flightDuration;
	
	private String to;
	
	private String from;
	
	private String airlineName;
	
	private int capacity;
	
	private int numBooked;
	
	private String destinationAirport;
	
	private Double flightPrice;
	
	private String boardingTime;

	public Flight() {}
		
	public Flight(String flightNum, String departureDate, String departureTime,
            String from, String to, String airlineName, int capacity,
            int numBooked, double flightPrice, String flightID) {
  // Set the values in the correct order to match the SQL table
  this.flightNum = flightNum;
  this.departureDate = departureDate;
  this.departureTime = departureTime;
  this.from = from;
  this.to = to;
  this.airlineName = airlineName;
  this.capacity = capacity;
  this.numBooked = numBooked;
  this.flightPrice = flightPrice;
  this.flightID = flightID;
}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getBoardingTime() {
		return boardingTime;
	}

	public void setBoardingTime(String boardingTime) {
		this.boardingTime = boardingTime;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	/*
	public String getFlightDuration() {
		return flightDuration;
	}

	public void setFlightDuration(String flightDuration) {
		this.flightDuration = flightDuration;
	}
	*/

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNumBooked() {
		return numBooked;
	}

	public void setNumBooked(int numBooked) {
		this.numBooked = numBooked;
	}

	public String toString() {
		System.out.println(flightID + "");
		return flightID + "";
	}

	public boolean flightCheck(String flightID) {
		if (flightID.equals(this.getFlightID())) {
			return true;
		}

		return false;

	}


	public Double getFlightPrice() {
		return this.flightPrice;
	}

	//public void setFlight_price(Double flight_price) {
	//	this.flightPrice = flightPrice;
	//}
	
	// Method to check if the flight is full and notify the user
	
    public boolean flightFull() {
        if (isFull()) {
            System.out.println("Sorry, the flight is full. Cannot book more passengers.");
            return true;
        } else {
            System.out.println("The flight is not full. Seats are available.");
            return false;
        }
    }
    
    // Method to check for time conflict with another flight and notify the user
    
    public boolean flightTimeConflict(Flight otherFlight) {
        if (hasConflict(otherFlight)) {
            System.out.println("There is a time conflict with another flight (ID: " + otherFlight.getFlightID() + ").");
            return true;
        } else {
            System.out.println("No time conflict with other flights.");
            return false;
        }
    }
    
    // Method to check if the user has already booked this flight using a unique identifier
    
    public boolean unique(String passengerID) {
        if (passengerID != null && passengerID.equals(this.flightID)) {
            System.out.println("You have already booked this flight (ID: " + this.flightID + ").");
            return true;
        } else {
            System.out.println("This flight is not booked by you. You can proceed with the booking.");
            return false;
        }
    }
	
	// Method to check if a flight has a conflicting date and time with another flight
	
	public boolean hasConflict(Flight otherFlight) {
		return this.departureDate.equals(otherFlight.getDepartureDate()) &&
                this.departureTime.equals(otherFlight.getDepartureTime());
    }
	
	// Method to increment the number of booked passengers for the flight
	
    public void incrementNumBooked() {
    	this.numBooked++;
    }
    
    // Method to decrement the number of booked passengers for the flight
    
    public void decrementNumBooked() {
    	this.numBooked = Math.max(0, this.numBooked - 1);
    }

    // Method to get the remaining capacity (seats available) for the flight
    
    public int getRemainingCapacity() {
        return capacity - numBooked;
    }
    
    // Method to check if the flight is full and cannot accommodate more passengers
    
    public boolean isFull() {
        return numBooked >= capacity;
    }
    
    // Method to book a passenger for the flight
    
    public void bookPassenger() throws Exception {
        // Check if the flight is full before booking a passenger
        if (isFull()) {
            throw new Exception("The flight is full. Cannot book more passengers.");
        }

        // Log information about the booking process
        System.out.println("Booking passenger for flight " + this.getFlightID());
        System.out.println("Number of booked passengers before: " + this.getNumBooked());

        // Increment the number of booked passengers
        incrementNumBooked();

        // Log information after the booking
        System.out.println("Number of booked passengers after: " + this.getNumBooked());
    }
        
    // Method to cancel a passenger's booking for the flight
    
    public void cancelBooking() throws Exception {
        // Check if there are booked passengers to cancel
        if (numBooked > 0) {
            // Decrement the number of booked passengers
            decrementNumBooked();
        } else {
            throw new Exception("No passengers booked for this flight.");
        }
    } 
    
    
}
    