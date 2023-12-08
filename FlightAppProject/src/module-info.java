module FlightAppProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	
	opens GUI to javafx.graphics, javafx.fxml;
}