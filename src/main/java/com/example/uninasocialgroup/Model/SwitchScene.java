package com.example.uninasocialgroup.Model;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class SwitchScene {
    private Stage stage;
    private Scene scene;

    public void switchToScene(ActionEvent event, String fxmlFile, String direction) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();

        // Crea una transizione di uscita per la scena corrente
        TranslateTransition exitTransition = new TranslateTransition(Duration.millis(100), currentScene.getRoot());
        exitTransition.setInterpolator(Interpolator.EASE_BOTH);
        setTransitionDirection(exitTransition, direction, true);

        //TO-DO: add a fade transition and fix the resize problem (cuz it's not a flexible solution)

        scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile))));
        stage.resizableProperty().setValue(!fxmlFile.contains("Login") && !fxmlFile.contains("Registration"));
        //Crea una transizione di ingresso per la nuova scena
    /*TranslateTransition enterTransition = new TranslateTransition(Duration.millis(500), scene.getRoot());
    setTransitionDirection(enterTransition, direction, false);*/

        // Avvia la transizione di uscita
        exitTransition.play();

        // Cambia la scena e avvia la transizione di ingresso quando la transizione di uscita è terminata
        exitTransition.setOnFinished(e -> {
            stage.setScene(scene);
            // enterTransition.play();
            stage.show();
        });
    }

    //TO:DO replace the direction's string in the controller file.
    private void setTransitionDirection(TranslateTransition transition, String direction, boolean isExit) {
        switch (direction) {
            case "leftToRight":
            case "ltr":
                transition.setFromX(isExit ? 0f : -200f);
                transition.setToX(isExit ? 200f : 0f);
                break;
            case "rightToLeft":
            case "rtl":
                transition.setFromX(isExit ? 0f : 200f);
                transition.setToX(isExit ? -200f : 0f);
                break;
            case "topToBottom":
            case "ttb":
                transition.setFromY(isExit ? 0f : -200f);
                transition.setToY(isExit ? 200f : 0f);
                break;
            case "bottomToTop":
            case "btt":
                transition.setFromY(isExit ? 0f : 200f);
                transition.setToY(isExit ? -200f : 0f);
                break;
            case "none":
            case "n":
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}