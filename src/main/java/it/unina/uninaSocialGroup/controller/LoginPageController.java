package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

//TO-DO: add a "remember me" checkbox and a "forgot password" button and "show me password" button
//TO-DO: add a "login with google" button
//TO-DO: add a "" button
public class LoginPageController {
    @FXML
    private Button SwitchToSignUpButton, LoginButton;

    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
        LoginButton.setOnAction(this::SwitchToHomePage);
    }

    private void SwitchToSignUpButton(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/RegistrationPage.fxml", "leftToRight");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SwitchToHomePage(ActionEvent actionEvent) {
        try {
            SwitchScene.switchToScene(actionEvent, "/it/unina/uninaSocialGroup/view/HomePageBeta.fxml", "bottomToTop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}