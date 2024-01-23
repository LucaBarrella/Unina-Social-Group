package com.example.uninasocialgroup.Controller;

import com.example.uninasocialgroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.io.IOException;

public class RegistrationPageController {
    @FXML
    private Button SwitchToSignInButton, ContinueButton;

    private SwitchScene SwitchScene = new SwitchScene();

    @FXML
    public void initialize() {
        SwitchToSignInButton.setOnAction(this::switchScene);
        ContinueButton.setOnAction(this::switchScene2);
    }

    private void switchScene(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/com/example/uninasocialgroup/LoginPage.fxml", "rtl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void switchScene2(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/com/example/uninasocialgroup/RegistrationPage2.fxml", "n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}