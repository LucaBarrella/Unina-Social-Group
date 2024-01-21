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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());//, 320, 240);
        stage.setTitle("Unina Social Group");
        Image icon = new Image ("file:src/main/image/icon.png");
        stage.getIcons().add(icon);
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}