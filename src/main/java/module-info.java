module com.example.uninasocialgroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.uninasocialgroup to javafx.fxml;
    exports com.example.uninasocialgroup;
    exports com.example.uninasocialgroup.Controller;
    opens com.example.uninasocialgroup.Controller to javafx.fxml;
    exports com.example.uninasocialgroup.Controller.RegistrationPhase;
    opens com.example.uninasocialgroup.Controller.RegistrationPhase to javafx.fxml;
}