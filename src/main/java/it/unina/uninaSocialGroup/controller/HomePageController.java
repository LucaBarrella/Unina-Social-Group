package it.unina.uninaSocialGroup.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import it.unina.uninaSocialGroup.SwitchScene;
import javafx.event.ActionEvent;
import java.io.IOException;

public class HomePageController {

    @FXML
    private Button LogoutButton;
    private SwitchScene switchScene = new SwitchScene();
    @FXML
    public void initialize() {
        LogoutButton.setOnAction(this::Logout);
    }

    private void Logout(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/LoginPage.fxml", "topToBottom");
            System.out.println("Logout successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
