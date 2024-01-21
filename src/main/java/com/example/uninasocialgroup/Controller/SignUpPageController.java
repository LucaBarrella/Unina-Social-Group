package com.example.uninasocialgroup.Controller;

import com.example.uninasocialgroup.Model.SwitchScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;

public class SignUpPageController {
    @FXML
    private Button SwitchToSignInButton;

    private SwitchScene SwitchScene = new SwitchScene();
    @FXML
    public void initialize() {
        SwitchToSignInButton.setOnAction(this::changeScene);
    }

    private void changeScene(ActionEvent event) {
        try {
            SwitchScene.switchToScene(event, "/com/example/uninasocialgroup/LoginPage.fxml", "rtl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}