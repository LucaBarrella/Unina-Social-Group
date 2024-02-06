package it.unina.uninaSocialGroup;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Taskbar.Feature;
import java.util.Objects;

public class HomePage extends javafx.application.Application {
    private static final double BORDER_WIDTH = 10.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/it/unina/uninaSocialGroup/view/HomePageBeta.fxml")));

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

        primaryStage.setTitle("Unina Social Group");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();

        scene.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            double width = primaryStage.getWidth();
            double height = primaryStage.getHeight();

            if (x <= 0 || y <= 0 || x >= width || y >= height) {
                scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            } else {
                scene.setFill(javafx.scene.paint.Color.WHITE); // Set the default color
            }
        });
    }
}