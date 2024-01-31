package it.unina.uninaSocialGroup.controller;

import DAO.AuthenticationDAO;
import Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

//TO-DO: add a "remember me" checkbox and a "forgot password" button and "show me password" button
//TO-DO: add a "login with google" button
//TO-DO: add a "" button
public class LoginPageController {
    boolean result;
    private Stage stage;
    @FXML
    private Button SwitchToSignUpButton, LoginButton;
    private @FXML TextField passwordField;
    private @FXML TextField emailField;
    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
    }

    private void signIn(){
        AuthenticationDAO autenticate = new AuthenticationDAO();
        if (LoginButton.isPressed()) {
            result = autenticate.CheckCredentials(emailField.getText(), passwordField.getText());
            if(result){
                switchToScene("HomePageBeta.fmxl");
            }
        }
    }

    private void SwitchToSignUpButton(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/FileFXML/RegistrationPage.fxml", "ltr");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToScene(String fxmlFile){
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}