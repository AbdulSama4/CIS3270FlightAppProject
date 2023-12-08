package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import BusinessLogic.Customer;
import BusinessLogic.Flight;

public class FlightData extends AdminData {
	
public static Connection getConnection() throws SQLException {
		
		Connection myConn = DriverManager.getConnection
	    ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");

			return myConn;		
	}

	// adds a flight to flight database
	public void addFlight(String flightNums, String flightDate, String departTime, 
						String departFrom, String arrivalTo, String airline, String seatPrices)
						throws SQLException, ClassNotFoundException {

			Connection myConn = null;
			PreparedStatement myStmt = null;
			String sql = "insert into flights values (?, ?, ?, ?, ?, ?, ?)";
			
			try {
			myConn = DriverManager.getConnection
			("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, flightNums);
			myStmt.setString(2, flightDate);	
			myStmt.setString(3, departTime);
			myStmt.setString(4, departFrom);
			myStmt.setString(5, arrivalTo);
			myStmt.setString(6, airline);
			myStmt.setString(7, seatPrices);
			
			myStmt.executeUpdate();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
			  }
			
			}
	
	public boolean unique(int customerID, String flightNum) throws SQLException, ClassNotFoundException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from bookings where customerID = ? and flightNum = ? ";
		
			try {
		    myConn = DriverManager.getConnection
		    ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    myStmt.setInt(1, customerID);
		    myStmt.setString(2, flightNum);
		    myRs = myStmt.executeQuery();
		    
		    if (myRs.next()) {
		    	return true;
		    }
		    else 
		    	return false;
			}
			
		    catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
    		  }
			
			return false;
	}
	//  After the Book flight button is Pressed This Method Books the flight inside the database 
	public void book(int customerID, String flightNum) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "insert into bookings values (?, ?, ?)";
		
		
			try {
			    myConn = DriverManager.getConnection
			    ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
			    myStmt = myConn.prepareStatement(sql);
			    myStmt.setInt(1, 0);
			    myStmt.setInt(2, customerID);
			    myStmt.setString(3, flightNum);
			    myStmt.executeUpdate();
		 	}
		 		    catch(Exception ex) {
		 				ex.printStackTrace();
		 			}
		 			finally {
		 			    myConn.close();
		     		  }
	}
	//this method is called and deletes the flight from bookings if user clicks Delete Flight 
	public void deleteBook(int customerID, String flightNum) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "delete from bookings where customerID = " + "'" + customerID + "'" 
				+ "and flightNum = " + "'" + flightNum + "'";
	
			try {
			    myConn = DriverManager.getConnection
			      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
			    myStmt = myConn.prepareStatement(sql);
			    myStmt.executeUpdate();
		 	}
		   catch(Exception ex) {
			   	 ex.printStackTrace();
		 	}
			finally {
		 			myConn.close();
		    }
	}

	// when user presses book flight button this method is called and sees if flight is fully
	// booked or not and returns boolean.
	public boolean flightFull(String flightNum) throws SQLException {
		
		int count = 1;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from bookings where flightNum = ? ";
		
			try {
		    myConn = DriverManager.getConnection
		    ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    myStmt.setString(1, flightNum);
		    myRs = myStmt.executeQuery();
		    
		    if (myRs.next()) {
		    	count++;
		    }
		    
		    if (count == 6) {
		    	return true;
		    }
		    else 
		    	return false;
			}
			
		    catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
    		}
		return true;
	}

	// when user clicks book flight button this method is called and sees if they have a time
	// conflict and returns boolean
	public boolean flightTimeConflict(String date, String time) {
		
		Customer customer = new Customer();
		int id = customer.getcustomerID();
		
		PreparedStatement myStmt = null;
		ResultSet rs = null;
		String sql = "SELECT flights.date, flights.departureTime FROM flights " + 
				"INNER JOIN bookings ON flights.flightNum = bookings.flightNum and customerID = "
				+ "'" + id + "'" + "and flights.date = " + "'" + date + "'" + 
				"and flights.departureTime =" + "'" + time + "'";
		
		try {
			Connection con = FlightData.getConnection();
			myStmt = con.prepareStatement(sql);
			rs = myStmt.executeQuery();
				
			while(rs.next()) {
				return true;	
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
