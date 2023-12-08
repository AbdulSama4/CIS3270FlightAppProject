package GUI;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SplashController {
	
	@FXML
    private AnchorPane parent;

    public void initialize() {
        // initializes and Creates a FadeTransition with a duration of 5 seconds
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), parent);

        // Sets the starting and ending opacity values
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Sets the event handler to be triggered when the transition finishes
        fadeTransition.setOnFinished(event -> loadMainMenu());

        // Starts the fade transition
        fadeTransition.play();
    }

    private void loadMainMenu() {
        try {
            // Loads the mainMenu.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Parent mainMenuRoot = loader.load();

            // Creates a new scene with the loaded root and set it on the stage
            Scene mainMenuScene = new Scene(mainMenuRoot, 600, 305);
            Stage primaryStage = (Stage) parent.getScene().getWindow();
            primaryStage.setScene(mainMenuScene);


            // Shows the stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

