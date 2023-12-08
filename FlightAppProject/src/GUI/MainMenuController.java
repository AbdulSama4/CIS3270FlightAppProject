package GUI;

import BusinessLogic.Customer;
//import BusinessLogic.Flight;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//import javax.security.auth.login.LoginException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainMenuController extends Customer {
	
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private AnchorPane mainMenu;

	    @FXML
	    private Button registerButton; 
	    
	    @FXML
	    private Button resetPassButton;
	    
	    @FXML
	    private TextField username;
	    
	    @FXML
	    private TextField password;
	    
	    @FXML
	    private Button signinButton;
	    
	    @FXML
	    private Label lblErrors;
	    
	    @FXML
	    void initialize() {
	        // Sets an action for the Register button
	        registerButton.setOnAction(this::openRegistrationForm);

	        // Sets an action for the Reset Password button
	        resetPassButton.setOnAction(this::openPasswordResetForm);
	    }

	    private void openRegistrationForm(ActionEvent event) {
	        try {
	            // Load the RegistrationForm.fxml file
	            Parent registrationForm = FXMLLoader.load(getClass().getResource("RegistrationForm.fxml"));

	            // Create a new scene
	            Scene registrationScene = new Scene(registrationForm);

	            // Get the stage information
	            Stage stage = (Stage) mainMenu.getScene().getWindow();

	            // Set the new scene on the stage
	            stage.setScene(registrationScene);

	            // Show the stage
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately (e.g., show an error message)
	        }
	    }

	    private void openPasswordResetForm(ActionEvent event) {
	        try {
	            // Loads the PasswordResetForm.fxml file
	            Parent passwordResetForm = FXMLLoader.load(getClass().getResource("PasswordResetForm.fxml"));

	            // Creates a new scene
	            Scene passwordResetScene = new Scene(passwordResetForm);

	            // Gets the stage information
	            Stage stage = (Stage) mainMenu.getScene().getWindow();

	            // Set the new scene on the stage
	            stage.setScene(passwordResetScene);

	            // Shows the stage
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately (e.g., show an error message)
	        }
	    }
	    
	    public void userSignInBtnClicked(ActionEvent event) throws Exception {
	    	
	    	try {
	            // Get the entered username and password from the text fields
	            String enteredUsername = username.getText();
	            String enteredPassword = password.getText();

	            // Call the login method from the Customer class
	            boolean loginSuccessful = login(enteredUsername, enteredPassword);

	            if (loginSuccessful) {
	                // If login is successful, you can perform additional actions or navigate to another scene
	                System.out.println("Login successful!");
	                // For example, you can load a new FXML file and set it as the scene
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("flights.fxml"));
	                Parent otherScene = loader.load();
	                Scene scene = new Scene(otherScene);

	                // Get the stage information
	                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	                // Set the new scene on the stage
	                stage.setScene(scene);

	                // Show the stage
	                stage.show();
	            } else {
	                // If login is unsuccessful, display an error message
	                lblErrors.setText("Invalid username or password");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle the exception appropriately (e.g., show an error message)
	            lblErrors.setText("An error occurred during login");
	        }
	        
	        }

}
