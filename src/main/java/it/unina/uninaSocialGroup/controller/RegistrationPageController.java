package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;

public class RegistrationPageController {
    @FXML
    private TextField NameField, SurnameField, StudentIDField, EmailField, PhoneNumberField;
    @FXML
    private DatePicker BirthDateField;
    @FXML
    private PasswordField PasswordField, ConfirmPasswordField;
    @FXML
    private Button SwitchToSignInButton, SwitchToSignInButton2 , ContinueButton;

    private SwitchScene switchScene = new SwitchScene();

    @FXML
    public void initialize() {
        SwitchToSignInButton.setOnAction(this::SwitchToSignInButton);
        SwitchToSignInButton2.setOnAction(this::SwitchToSignInButton);
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
                PhoneNumberField.getText() != null &&
                PasswordField.getText() != null &&
                ConfirmPasswordField.getText() != null;
    }

    private void registration (ActionEvent event) {
        if (!areFieldsNotNull()) {
            System.out.println("All fields must be filled");
            return;
        }

        AuthenticationDAO authenticate = new AuthenticationDAO();
        UserDAO user = new UserDAO();
        LocalDateTime currentDate = LocalDateTime.now();
        boolean result = authenticate.CheckCredentials(EmailField.getText(), PasswordField.getText());
        if (!result) {
            if (PasswordField.getText().equals(ConfirmPasswordField.getText())) {
                try {
                    user.addNewUser(StudentIDField.getText(), NameField.getText(), SurnameField.getText(), String.valueOf(BirthDateField.getValue()), String.valueOf(currentDate));
                    authenticate.addNewUserToAuthTable(EmailField.getText(), PasswordField.getText(), PhoneNumberField.getText());
                    System.out.println("Registration successful");
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