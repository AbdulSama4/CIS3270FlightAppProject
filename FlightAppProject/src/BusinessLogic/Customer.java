package BusinessLogic;

import java.util.Random;

import Database.CustomerData;

public class Customer extends Flight{
	
	// Private fields to store customer information
	
			protected String firstName;
			
			protected String lastName;
			
			protected String address;
			
			protected String zipCode;
			
			protected String state;
			
			protected String username;
			
			protected String password;
			
			protected String email;
			
			protected String SSN;
			
			//protected String securityQuestion;
			
			protected String securityAnswer;
			
			public int customerID;
			
			// Default constructor for the Customer class
			
			public Customer() {

		}
			
			// Parameterized constructor to initialize customer information

			public Customer(String firstName, String lastName, String address, String zipCode, String state,
	                String username, String password, String email, String SSN,
	                String securityAnswer, int customerID) {
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.address = address;
	    this.zipCode = zipCode;
	    this.state = state;
	    this.username = username;
	    this.password = password;
	    this.email = email;
	    this.SSN = SSN;
	    this.securityAnswer = securityAnswer;
	    this.customerID = customerID;
	}
			// Getter and setter methods for customer attributes
			
			public String getFirstName() {
				return firstName;
			}
			
			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}
			
			public String getLastName() {
				return lastName;
			}
			
			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
			
			public String getAddress() {
				return address;
			}
			
			public void setAddress(String address) {
				this.address = address;
			}
			public String getZipcode() {
				return zipCode;
			}
			public void setZipcode(String zipcode) {
				this.zipCode = zipcode;
			}
			public String getState() {
				return state;
			}
			public void setState(String state) {
				this.state = state;
			}
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {		
				this.username = username;
			}
			public String getPassword() {		
				return password;
			}
			public void setPassword(String password) {		
				this.password = password;
			}
			public String getEmail() {		
				return email;
			}
			public void setEmail(String email) {		
				this.email = email;
			}
			public String getSSN() {		
				return SSN;
			}
			public void setSSN(String SSN) {	
				this.SSN = SSN;
			}
			
			public String getSecurityAnswer() {			
				return securityAnswer;
			}
			public void setSecurityAnswer(String securityAnswer) {			
				this.securityAnswer = securityAnswer;
			}
			
			// Method to check login credentials and throw an exception if invalid
			
			public boolean login(String username, String password) {
		        CustomerData customerData = new CustomerData();
		        return customerData.pass(username, password);
		    }
			
			public String retrievePassword(String enteredUsername, String enteredSecurityAnswer, String newPassword) {
			    // Method to retrieve password based on username and security answer
			    if (enteredUsername.equals(this.username) && enteredSecurityAnswer.equals(this.securityAnswer)) {
			        if (newPassword != null && !newPassword.isEmpty()) {
			            // Update the password with the new one
			            this.password = newPassword;
			            // Return the new password
			            return newPassword;
			        } else {
			            // Handle the case where the new password is not provided
			            return null;
			        }
			    } else {
			        // Return null or an empty string to indicate a mismatch
			        return null;
			    }
			}
			
			public int generateRandomCustomerID() {
		        // Generate a random 6-digit customerID
		        Random random = new Random();
		        return 100000 + random.nextInt(900000); // Generates a random number between 100000 and 999999
		    }
			
			// Method to check if an email is in a valid format
		    private boolean isValidEmail(String email) {
		        return email.contains("@");
		    }
		    
		    private void setCustomerInfo(String firstName, String lastName, String address, String zipCode, String state,
		            String username, String password, String email, String SSN,
		            String securityAnswer, int customerID) {
		        this.firstName = firstName;
		        this.lastName = lastName;
		        this.address = address;
		        this.zipCode = zipCode;
		        this.state = state;
		        this.username = username;
		        this.password = password;
		        this.email = email;
		        this.SSN = SSN;
		        this.securityAnswer = securityAnswer;
		        this.customerID = customerID;
		    }

		    public boolean register(String firstName, String lastName, String address, String zipCode, String state,
		            String username, String password, String email, String SSN,
		            String securityAnswer) throws Exception {

		        // Basic validation for required fields
		        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || zipCode.isEmpty()
		                || state.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()
		                || SSN.isEmpty() || securityAnswer.isEmpty()) {
		            throw new Exception("*All fields are required for registration*");
		        }

		        // Validate email format
		        if (!isValidEmail(email)) {
		            throw new Exception("Invalid email format!");
		        }

		        // Call the parameterized constructor to set values
		        setCustomerInfo(firstName, lastName, address, zipCode, state, username, password, email, SSN,
		                securityAnswer, generateRandomCustomerID());

		        // Registration successful
		        System.out.println("Registration successful");
		        
		        return true;

		    }
		    
		    public int getcustomerID() {
		        return this.customerID;
		    }
	
	

}
