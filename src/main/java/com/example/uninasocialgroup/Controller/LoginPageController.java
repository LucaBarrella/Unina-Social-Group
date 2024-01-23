package com.example.uninasocialgroup.Controller;

import com.example.uninasocialgroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LoginPageController {
    @FXML
    private Button SwitchToSignUpButton, SwitchToHomePageButton;

    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
        SwitchToHomePageButton.setOnAction(this::SwitchToHomePage);
    }

    private void SwitchToSignUpButton(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/com/example/uninasocialgroup/RegistrationPage.fxml", "ltr");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SwitchToHomePage(ActionEvent actionEvent) {
        try {
            SwitchScene.switchToScene(actionEvent, "/com/example/uninasocialgroup/HomePageBeta.fxml", "btt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}