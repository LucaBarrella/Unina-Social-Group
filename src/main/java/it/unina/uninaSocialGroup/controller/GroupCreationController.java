package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GroupCreationController {
    @FXML
    private Button CreationGroupButton;
    @FXML
    private TextField NameGroupField,CategoryGroupField;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail;
    @FXML
    public void initialize(){
        CreationGroupButton.setOnAction(this::GroupCreation);
    }

    public void setUserEmail(String email){
        this.userEmail = email;
        System.out.println("User email: " + userEmail);
    }


    public @FXML void GroupCreation(ActionEvent event){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        group.addNewGroup(NameGroupField.getText(),CategoryGroupField.getText(),matricola);
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
            switchScene.loadSceneAndShow(event, loader);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creazione Gruppo");
                alert.setHeaderText(null);
                alert.setContentText("Il nuovo gruppo è stato creato con successo!");
                alert.showAndWait();
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}