package com.example.uninasocialgroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());//, 320, 240);
        stage.setTitle("Unina Social Group");
        Image icon = new Image ("file:src/main/image/icon.png");
        stage.getIcons().add(icon);
        stage.resizableProperty().setValue(false);
        stage.setScene(scene);
        stage.show();

        // Set the dock icon (Mac OS)
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) {
            java.awt.Image dockIcon = ImageIO.read(getClass().getResource("file:src/main/image/icon.png"));
            Taskbar.getTaskbar().setIconImage(dockIcon);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}