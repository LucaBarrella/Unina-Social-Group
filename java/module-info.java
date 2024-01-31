module MainPackage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens MainPackage to javafx.fxml;
    exports MainPackage;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Controller.RegistrationPhase;
    opens Controller.RegistrationPhase to javafx.fxml;
}