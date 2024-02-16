package it.unina.uninaSocialGroup.Model;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class SwitchScene {
    private Stage stage;
    private Scene scene;

    //NUOVO METODO
    public FXMLLoader createFXML(String fxmlFile) {
        return new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlFile)));
    }
    //NUOVO METODO
    public void loadSceneAndShow(ActionEvent event, FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void switchToScene(ActionEvent event, String fxmlFile, String direction) throws IOException {
        stage = getStageFromEvent(event);
        Scene currentScene = stage.getScene();

        TranslateTransition exitTransition = createExitTransition(currentScene.getRoot(), direction);
        exitTransition.play();

        scene = createNewScene(fxmlFile);
        setStageResizableBasedOnFXMLFile(fxmlFile);

        exitTransition.setOnFinished(e -> changeScene());
    }

    private Stage getStageFromEvent(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    private TranslateTransition createExitTransition(Node node, String direction) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(400), node);
        transition.setInterpolator(Interpolator.EASE_BOTH);
        setTransitionDirection(transition, direction, true);
        return transition;
    }

    private Scene createNewScene(String fxmlFile) throws IOException {
        return new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile))));
    }

    private void setStageResizableBasedOnFXMLFile(String fxmlFile) {
        stage.resizableProperty().setValue(!fxmlFile.contains("Login") && !fxmlFile.contains("Registration"));
    }

    private void changeScene() {
        stage.setScene(scene);
        stage.show();
    }


    //TO:DO replace the direction's string in the controller file.
    private void setTransitionDirection(TranslateTransition transition, String direction, boolean isExit) {
        switch (direction) {
            case "leftToRight":
                transition.setFromX(isExit ? 0f : -200f);
                transition.setToX(isExit ? 200f : 0f);
                break;
            case "rightToLeft":
                transition.setFromX(isExit ? 0f : 200f);
                transition.setToX(isExit ? -200f : 0f);
                break;
            case "topToBottom":
                transition.setFromY(isExit ? 0f : -200f);
                transition.setToY(isExit ? 200f : 0f);
                break;
            case "bottomToTop":
                transition.setFromY(isExit ? 0f : 200f);
                transition.setToY(isExit ? -200f : 0f);
                break;
            case "none":
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}