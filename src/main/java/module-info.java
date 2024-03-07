module it.unina.uninaSocialGroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens it.unina.uninaSocialGroup.Model to javafx.base;
    opens it.unina.uninaSocialGroup to javafx.fxml;
    exports it.unina.uninaSocialGroup;
    exports it.unina.uninaSocialGroup.Boundary;
    opens it.unina.uninaSocialGroup.Boundary to javafx.fxml;
}