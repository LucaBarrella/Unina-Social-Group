package it.unina.uninaSocialGroup.controller.RegistrationPhase;

import it.unina.uninaSocialGroup.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class RegistrationPageController3 {
    @FXML
    private Button SwitchToSignInButton, ContinueButton, TurnBackButton;

    private SwitchScene switchScene = new SwitchScene();

    @FXML
    public void initialize() {
        SwitchToSignInButton.setOnAction(this::switchToScene);
        ContinueButton.setOnAction(this::switchToScene);
        TurnBackButton.setOnAction(this::switchToScene);
    }

    private void switchToScene(ActionEvent event) {
        try {
            String scenePath, direction;
            if (event.getSource() == SwitchToSignInButton) {
                scenePath = "/MainPackage/LoginPage.fxml";
                direction = "rtl";
            } else if (event.getSource() == ContinueButton) {
                // TO-DO:add the new page
                scenePath = "/MainPackage/RegistrationPage4.fxml";
                direction = "btt";
            } else {
                scenePath = "/MainPackage/RegistrationPage2.fxml";
                direction = "ttb";
            }
            switchScene.switchToScene(event, scenePath, direction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}