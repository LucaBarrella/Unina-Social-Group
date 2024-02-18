package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

public class CreationGroupController {
    @FXML
    private Button BackButton, CreationGroupButton;
    @FXML
    private TextField NameGroupField,CategoryGroupField;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail; //NON FUNZIONA ATTENTO !!!! DA RIVEDERE!!!
    @FXML
    public void initialize(){
        BackButton.setOnAction(this::BackToHomePage);
        CreationGroupButton.setOnAction(this::GroupCreation);
    }

    public @FXML void GroupCreation(ActionEvent event){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        group.addNewGroup(NameGroupField.getText(),CategoryGroupField.getText(),matricola);
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
            switchScene.loadSceneAndShow(event, loader);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void BackToHomePage(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/HomePage.fxml", "topToBottom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
