package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginBoundary {
    @FXML
    private Button LoginButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    private SwitchScene switchScene = new SwitchScene();
    @FXML
    private Label NoEmailPassword;
    LogicalController logic = new LogicalController();


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
    public void signIn(ActionEvent event){
        boolean result = logic.signIn(emailField.getText(), passwordField.getText());
        if(result){
            try {
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
                //Passa la email al LogicalController
                logic.setUserEmail(emailField.getText());
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.resizableProperty().setValue(true);
                stage.centerOnScreen();
                stage.show();
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