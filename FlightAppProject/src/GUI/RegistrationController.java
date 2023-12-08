package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import BusinessLogic.Customer;
import Database.CustomerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
//import javafx.scene.Node;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;


public class RegistrationController extends Customer{
	
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TextField address;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField firstName;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private AnchorPane mainMenu;

	    @FXML
	    private TextField password;

	    @FXML
	    private TextField securityAnswer;

	    @FXML
	    private Button signup;

	    @FXML
	    private TextField SSN;

	    @FXML
	    private TextField state;

	    @FXML
	    private TextField username;

	    @FXML
	    private TextField zipCode;
	    
	    @FXML
	    private Button backButton;
	    
	    @FXML
	    private Label lblUserTaken;

	    @FXML
	    private Label lblNotFilled;
	    
	    //private boolean registrationSuccess;
	   

	    @FXML
	    private void signupClicked(ActionEvent event) {
	        try {
	            // Validate and register the customer
	            boolean registrationSuccess = register(
	                    firstName.getText(),
	                    lastName.getText(),
	                    address.getText(),
	                    zipCode.getText(),
	                    state.getText(),
	                    username.getText(),
	                    password.getText(),
	                    email.getText(),
	                    SSN.getText(),
	                    securityAnswer.getText()
	            );

	            if (registrationSuccess) {
	                // Registration successful, update the database
	                CustomerData customerData = new CustomerData();
	                try {
	                    customerData.register(
	                            firstName.getText(),
	                            lastName.getText(),
	                            email.getText(),
	                            address.getText(),
	                            username.getText(),
	                            password.getText(),
	                            SSN.getText(),
	                            securityAnswer.getText(),
	                            zipCode.getText(),
	                            state.getText(),
	                            "example_birthday" // Replace with actual birthday
	                    );

	                    System.out.println("Success");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    // Handle database update error
	                }
	            } else {
	                lblUserTaken.setText("Username is already taken.");
	            }
	        } catch (Exception e) {
	            lblNotFilled.setText("One or more fields are empty.");
	        }}

	    @FXML
	    void initialize() {
	        // Set an action for the Back button
	        backButton.setOnAction(event -> {
	            try {
	                // Load the mainMenu.fxml file
	                AnchorPane mainMenuPane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

	                // Replaces the current scene content with the mainMenu.fxml content
	                mainMenu.getChildren().setAll(mainMenuPane);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        });
	    }

}
