package GUI;

import BusinessLogic.Customer;
import Database.CustomerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;

public class PasswordResetController extends Customer{
	
	@FXML
    private TextField username;

    @FXML
    private TextField securityAnswer;

    @FXML
    private TextField newPassword;

    @FXML
    private Button backButton;

    @FXML
    private Button resetPassButton;

    @FXML
    private Label usernameSecurityQuestionMissmatch;

    @FXML
    private Label usernameSecurityQuestionMatch;
    
    @FXML
    private AnchorPane passwordRecoveryMenu;
   
    


@FXML
public void backButtonClicked(ActionEvent event) {
    try {
        // Loads the FXML file for the main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent mainMenu = loader.load();

        // Sets up the main menu scene
        Scene mainMenuScene = new Scene(mainMenu);

        // Gets the current stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Sets the new scene
        window.setScene(mainMenuScene);
        window.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


@FXML
public void resetPasswordClicked(ActionEvent event) {
    String enteredUsername = username.getText();
    String enteredSecurityAnswer = securityAnswer.getText();
    String newPass = newPassword.getText();

    CustomerData customerData = new CustomerData();

    try {
        // Retrieve the existing password from the database
        String retrievedPassword = customerData.getPass(enteredUsername, enteredSecurityAnswer);

        if (retrievedPassword != null && !retrievedPassword.equals(newPass)) {
            // Password reset successful
            usernameSecurityQuestionMatch.setText("Password reset successful");

            // Update the password in the database
            customerData.updatePassword(enteredUsername, newPass);

            // Update the password in the Customer object
            setPassword(newPass);
        } else {
            // Invalid username or security answer
            usernameSecurityQuestionMissmatch.setText("Invalid username or security answer");
        }
    } catch (Exception e) {
        usernameSecurityQuestionMissmatch.setText("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}

}
