module it.unina.uninaSocialGroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens it.unina.uninaSocialGroup to javafx.fxml;
    exports it.unina.uninaSocialGroup;
    exports it.unina.uninaSocialGroup.controller;
    opens it.unina.uninaSocialGroup.controller to javafx.fxml;
    exports it.unina.uninaSocialGroup.controller.RegistrationPhase;
    opens it.unina.uninaSocialGroup.controller.RegistrationPhase to javafx.fxml;
}