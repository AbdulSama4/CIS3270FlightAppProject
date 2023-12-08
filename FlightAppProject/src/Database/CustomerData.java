package Database;

import java.sql.*;

public class CustomerData {
	
	// checks if password and username are correct
	
	public boolean pass(String user, String pass) throws SQLException, ClassNotFoundException {
			
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			String sql = "select * from customer where username = ? and password = ? ";
			try {
			    myConn = DriverManager.getConnection
			      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
			    myStmt = myConn.prepareStatement(sql);
			    myStmt.setString(1, user);
			    myStmt.setString(2, pass);
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
//checks username and security answer 
	public boolean checkUserName(String user, String securityAnswer) 
					throws SQLException, ClassNotFoundException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from customer where username = ? and securityAnswer = ? ";
		
			try {
		    myConn = DriverManager.getConnection
		      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    myStmt.setString(1, user);
		    myStmt.setString(2, securityAnswer);
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
	// returns pass when called
	public String getPass(String user) 
			throws SQLException, ClassNotFoundException {
		
		String pass = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from customer where username = ? ";
		
			try {
		    myConn = DriverManager.getConnection
   	      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    myStmt.setString(1, user);
		    myRs = myStmt.executeQuery();
		    
		    while(myRs.next()) {
		    	
		    	pass = myRs.getString("password");
		    }
		    
			}
			
		    catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
			  }
			
			return pass;
			
		}
	// register is called then the users information is updated into database
	
	public void register(String firstName, String lastName, String email,
						String address, String username,
						String password, String ssn,
						String securityAnswer, String zipcode, String state, String birthday)
						throws SQLException, ClassNotFoundException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		String sql = "insert into customer values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			try {
		    myConn = DriverManager.getConnection
		      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    
		    myStmt.setInt(1, 0);
		    myStmt.setString(2, firstName);	
		    myStmt.setString(3, lastName);
		    myStmt.setString(4, email);
		    myStmt.setString(5, address);
		    myStmt.setString(6, username);
		    myStmt.setString(7, password);
		    myStmt.setString(8, ssn);
		    myStmt.setString(9, securityAnswer);
		    myStmt.setString(10, zipcode);
		    myStmt.setString(11, state);
		    myStmt.setString(12, birthday);
		    
		    myStmt.executeUpdate();
			}
		    catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
    		  }
			
	}
	// checks for preexisting username 
	public boolean uniqueUser(String username) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from customer where username = ?";
		
			try {
		    myConn = DriverManager.getConnection
		      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    
		    myStmt.setString(1, username);
		    
		    myRs = myStmt.executeQuery();
		    
			    if (myRs.next()) {
			    	
			    	return false;
			    }
			    else { 
			    	
			    	return true;
			    }
			}
			 catch(Exception ex) {
					ex.printStackTrace();
			 }
			
			finally {
				myConn.close();	
			}
		return false;
	}
	// retrive customer id
	public int custID(String user) throws Exception {
		
		int custID = 0;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		String sql = "select * from customer where username = ?";
		
			try {
		    myConn = DriverManager.getConnection
		      ("jdbc:sqlserver://localhost:3306/CustomerData","root","Ionicwave805.");
		    myStmt = myConn.prepareStatement(sql);
		    myStmt.setString(1, user);
		    myRs = myStmt.executeQuery();
		    
			    while(myRs.next()) {	
			    	custID = myRs.getInt("customerID");
			    }
			}
		    catch(Exception ex) {
				ex.printStackTrace();
			}
			finally {
			    myConn.close();
    		}
		return custID;
	}

}
