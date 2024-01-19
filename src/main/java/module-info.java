module com.example.uninasocialgroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.uninasocialgroup to javafx.fxml;
    exports com.example.uninasocialgroup;
}