package com.example.uninasocialgroup.Controller;

import com.example.uninasocialgroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginPageController {
    @FXML
    private Button SwitchToSignUpButton;

    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::changeScene);
    }

    private void changeScene(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/com/example/uninasocialgroup/RegistrationPage.fxml", "ltr");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}