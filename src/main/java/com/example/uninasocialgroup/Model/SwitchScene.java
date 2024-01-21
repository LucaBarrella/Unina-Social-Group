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
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    Scene currentScene = stage.getScene();

    // Crea una transizione di uscita per la scena corrente
    TranslateTransition exitTransition = new TranslateTransition(Duration.millis(100), currentScene.getRoot());
    exitTransition.setInterpolator(Interpolator.EASE_BOTH);
    setTransitionDirection(exitTransition, direction, true);


        //Crea una transizione di ingresso per la nuova scena
    scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile))));
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

private void setTransitionDirection(TranslateTransition transition, String direction, boolean isExit) {
    switch (direction) {
        //left to right
        case "ltr":
            transition.setFromX(isExit ? 0f : -200f);
            transition.setToX(isExit ? 200f : 0f);
            break;
        //right to left
        case "rtl":
            transition.setFromX(isExit ? 0f : 200f);
            transition.setToX(isExit ? -200f : 0f);
            break;
        //top to bottom
        case "ttb":
            transition.setFromY(isExit ? 0f : -200f);
            transition.setToY(isExit ? 200f : 0f);
            break;
        //bottom to top
        case "btt":
            transition.setFromY(isExit ? 0f : 200f);
            transition.setToY(isExit ? -200f : 0f);
            break;
        default:
            throw new IllegalArgumentException("Invalid direction: " + direction);
    }
}
}