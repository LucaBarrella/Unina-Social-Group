package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;

public class RegistrationController {
    @FXML
    private Label WarningLabel;
    @FXML
    private TextField NameField, SurnameField, StudentIDField, EmailField;
    @FXML
    private DatePicker BirthDateField;
    @FXML
    private PasswordField PasswordField, ConfirmPasswordField;
    @FXML
    private Button SwitchToSignInButton,  ContinueButton;

    private SwitchScene switchScene = new SwitchScene();

    @FXML
    public void initialize() {
        WarningLabel.setVisible(false);
        SwitchToSignInButton.setOnAction(this::SwitchToSignInButton);
        ContinueButton.setOnAction(this::registration);
    }

    /**
     * SwitchToSignInButton
     * Metodo che viene chiamato quando viene cliccato il bottone LOGIN
     * Scambia la scena con la LoginPage
     */
    private void SwitchToSignInButton(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/LoginPage.fxml", "rightToLeft");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * areFieldsNotNull
     * Metodo che controlla se i campi sono tutti compilati (non siano vuoti)
     */
    private boolean areFieldsNotNull() {
        return NameField.getText() != null &&
                SurnameField.getText() != null &&
                StudentIDField.getText() != null &&
                BirthDateField.getValue() != null &&
                EmailField.getText() != null &&
                PasswordField.getText() != null &&
                ConfirmPasswordField.getText() != null;
    }

    /**
     * registration
     * Metodo che viene chiamato quando viene cliccato il bottone CONTINUA
     * Salva i dati scritti nel database e scambia la scena con la HomePage
     */
    private void registration (ActionEvent event) {
        //se i campi non sono tutti compilati, mostra un messaggio di avertenza
        if (!areFieldsNotNull()) {
            WarningLabel.setVisible(true);
            return;
        }

        AuthenticationDAO authenticate = new AuthenticationDAO();
        UserDAO user = new UserDAO();
        Date currentDate =  Date.valueOf(LocalDateTime.now().toLocalDate());
        Date birthDate = Date.valueOf(BirthDateField.getValue());
        boolean result = user.UserAlreadyExists(StudentIDField.getText()) && authenticate.CheckCredentials(EmailField.getText(), PasswordField.getText());
        if (!result) {
            if (PasswordField.getText().equals(ConfirmPasswordField.getText())) {
                try {
                    user.addNewUser(StudentIDField.getText(), NameField.getText(), SurnameField.getText(), birthDate, currentDate);
                    authenticate.addNewUserToAuthTable(EmailField.getText(), PasswordField.getText());
                    HomePageController homePageController = new HomePageController();
                    //Passa la email inserita alla HomePage
                    homePageController.setUserEmail(EmailField.getText());
                    switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/HomePage.fxml", "buttonToTop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //Mostra il messaggio di errore (Password != ConfermaPassword)
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Errore di registrazione");
                alert.setHeaderText(null);
                alert.setContentText("Password e Conferma Password non coincidono");
                alert.showAndWait();
            }
        } else {
            //Mostra il messaggio di avvertenza (Utente gia esistente)
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Errore di registrazione");
            alert.setHeaderText(null);
            alert.setContentText("Utente gia esistente. Effettua il login");
            alert.showAndWait();
        }
    }
}