package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button SwitchToSignUpButton, LoginButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    private SwitchScene switchScene = new SwitchScene();
    @FXML
    private Label NoEmailPassword;


    @FXML
    public void initialize() {
        NoEmailPassword.setVisible(false);
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
        LoginButton.setOnAction(this::signIn);
    }

    private void signIn(ActionEvent event){
        AuthenticationDAO authenticate = new AuthenticationDAO();
        boolean result = authenticate.CheckCredentials(emailField.getText(), passwordField.getText());
        if(result){
            try {
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
                HomePageController homePageController = new HomePageController();
                homePageController.setUserEmail(emailField.getText());
                switchScene.loadSceneAndShow(event, loader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText(null);
            alert.setContentText("Accesso non riuscito, utente non trovato!");
            alert.showAndWait();
        }
    }


    private void SwitchToSignUpButton(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/RegistrationPage.fxml", "leftToRight");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}