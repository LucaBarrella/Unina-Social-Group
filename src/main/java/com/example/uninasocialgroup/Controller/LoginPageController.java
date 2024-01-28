package com.example.uninasocialgroup.Controller;

import com.example.uninasocialgroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

//TO-DO: add a "remember me" checkbox and a "forgot password" button and "show me password" button
//TO-DO: add a "login with google" button
//TO-DO: add a "" button
public class LoginPageController {
    @FXML
    private Button SwitchToSignUpButton, loginButton;

    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
        loginButton.setOnAction(this::SwitchToHomePage);
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