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

public class RegistrationPageController {
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

    private void SwitchToSignInButton(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/LoginPage.fxml", "rightToLeft");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean areFieldsNotNull() {
        return NameField.getText() != null &&
                SurnameField.getText() != null &&
                StudentIDField.getText() != null &&
                BirthDateField.getValue() != null &&
                EmailField.getText() != null &&
                PasswordField.getText() != null &&
                ConfirmPasswordField.getText() != null;
    }

    private void registration (ActionEvent event) {
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
                    System.out.println("Registration successful");
                    HomePageController homePageController = new HomePageController();
                    homePageController.setUserEmail(EmailField.getText());
                    switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/HomePage.fxml", "buttonToTop");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Password and Confirm Password are not the same");
            }
        } else {
            System.out.println("User already exists");
        }
    }
}