package Controller.RegistrationPhase;

import Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RegistrationPageController {
    @FXML
    private Button SwitchToSignInButton, ContinueButton;

    private SwitchScene switchScene = new SwitchScene();

    @FXML
    public void initialize() {
        SwitchToSignInButton.setOnAction(this::switchToScene);
        ContinueButton.setOnAction(this::switchToScene);
    }

    private void switchToScene(ActionEvent event) {
        try {
            String scenePath, direction;
            if (event.getSource() == SwitchToSignInButton) {
                scenePath = "/MainPackage/LoginPage.fxml";
                direction = "rtl";
            } else {
                scenePath = "/MainPackage/RegistrationPage2.fxml";
                direction = "btt";
            }
            switchScene.switchToScene(event, scenePath, direction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}