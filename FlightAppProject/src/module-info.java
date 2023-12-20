module FlightAppProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    //requires javafx.base;
    
    requires transitive javafx.base;

    opens GUI to javafx.graphics, javafx.fxml;
    opens BusinessLogic to javafx.base;

    exports GUI to javafx.fxml, javafx.graphics;
}