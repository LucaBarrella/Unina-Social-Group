package com.example.uninasocialgroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Untitled.fxml"));
        Scene scene = new Scene(fxmlLoader.load());//, 320, 240);
        stage.setTitle("Unina Social Group");
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("file:src/main/resources/com/example/uninasocialgroup/icon.png"));
    }

    public static void main(String[] args) {
        launch();
    }
}