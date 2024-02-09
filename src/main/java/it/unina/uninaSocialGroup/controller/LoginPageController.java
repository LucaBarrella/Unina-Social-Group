package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.AuthenticationDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {
    @FXML
    private Button SwitchToSignUpButton, LoginButton;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    private SwitchScene switchScene = new SwitchScene();
    private Parent root;

    @FXML
    public void initialize() {
        SwitchToSignUpButton.setOnAction(this::SwitchToSignUpButton);
        LoginButton.setOnAction(this::signIn);
    }

    private void signIn(ActionEvent event){
        AuthenticationDAO authenticate = new AuthenticationDAO();
        boolean result = authenticate.CheckCredentials(emailField.getText(), passwordField.getText());
        if(result){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/HomePage.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.show();
                loader.<HomePageController>getController().setUserEmail(emailField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Login failed, probably wrong credentials");
        }
    }

    private void SwitchToSignUpButton(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/Registration.fxml", "leftToRight");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}