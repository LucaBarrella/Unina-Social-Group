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

    public FXMLLoader createFXML(String fxmlFile) {
        return new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlFile)));
    }

    public void loadSceneAndShow(ActionEvent event, FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}