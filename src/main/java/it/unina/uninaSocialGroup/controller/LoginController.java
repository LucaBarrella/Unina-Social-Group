package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
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
    private Button LoginButton;
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
        LoginButton.setOnAction(this::signIn);
        Platform.runLater(() -> LoginButton.requestFocus());
    }

    /**
     * signIn
     * Metodo che viene chiamato quando viene cliccato il bottone LOGIN
     * Controlla se l'utente è presente nel database.
     * In caso affermativo, scambia la scena con la HomePage
     * In caso negativo, mostra un messaggio di errore
     */
    private void signIn(ActionEvent event){
        //Se non viene scritto nulla nel campo Email o Password, si mostra un messaggio di avvertenza
        if(emailField.getText().isEmpty() || passwordField.getText().isEmpty()){
            NoEmailPassword.setVisible(true);
            return;
        }
        AuthenticationDAO authenticate = new AuthenticationDAO();
        boolean result = authenticate.CheckCredentials(emailField.getText(), passwordField.getText());
        if(result){
            try {
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
                HomePageController homePageController = new HomePageController();
                //Passa la email inserita alla HomePage
                homePageController.setUserEmail(emailField.getText());
                switchScene.loadSceneAndShow(event, loader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //Mostra il messaggio di errore
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText(null);
            alert.setContentText("Accesso non riuscito, utente non trovato!");
            alert.showAndWait();
        }
    }
}