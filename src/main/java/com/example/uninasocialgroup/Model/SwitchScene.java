package com.example.uninasocialgroup.Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SwitchScene {
    private Stage stage;
    private Scene scene;
    public void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile))));
        stage.setScene(scene);
        stage.show();
    }
}