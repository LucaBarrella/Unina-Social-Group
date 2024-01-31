package it.unina.uninaSocialGroup;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Taskbar.Feature;
import java.util.Objects;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/it/unina/uninaSocialGroup/view/LoginPage.fxml")));

        //Set icon on the application bar
        var appIcon = new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/icon.png");
        primaryStage.getIcons().add(appIcon);

        //Set icon on the taskbar/dock
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            try {
                if (Taskbar.isTaskbarSupported()) {
                    var taskbar = Taskbar.getTaskbar();

                    if (taskbar.isSupported(Feature.ICON_IMAGE)) {
                        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                        var dockIcon = defaultToolkit.getImage(getClass().getResource("/it/unina/uninaSocialGroup/images/icon.png"));
                        taskbar.setIconImage(dockIcon);
                    }
                }
            } catch (UnsupportedOperationException e) {
                System.out.println("The operation is not supported on the current platform.");
            } catch (SecurityException e) {
                System.out.println("There was a security exception.");
            }
        }
        //TO-DO: custom fonts?

        primaryStage.setTitle("Unina Social Group");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
    }
}