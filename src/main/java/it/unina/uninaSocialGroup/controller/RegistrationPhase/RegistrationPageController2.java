package it.unina.uninaSocialGroup.controller.RegistrationPhase;

import it.unina.uninaSocialGroup.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class RegistrationPageController2 {
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
                scenePath = "/it/unina/uninaSocialGroup/view/LoginPage.fxml";
                direction = "rtl";
            } else if (event.getSource() == ContinueButton) {
                scenePath = "/it/unina/uninaSocialGroup/view/RegistrationPage3.fxml";
                direction = "btt";
            } else {
                scenePath = "/it/unina/uninaSocialGroup/view/RegistrationPage.fxml";
                direction = "ttb";
            }
            switchScene.switchToScene(event, scenePath, direction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}