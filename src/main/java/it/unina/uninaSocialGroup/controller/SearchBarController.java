package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SearchBarController {
    @FXML
    private Button groupNameButton;
    @FXML
    private Button categoryButton, joinButton;
    @FXML
    private Label memberCountLabel;
    private TextField searchField;
    private HomePageController homePageController;
    private static String userEmail;
    private SwitchScene switchScene = new SwitchScene();


    public void setGroup(Group group, TextField searchField, HomePageController homePageController) {
        this.searchField = searchField;
        this.homePageController = homePageController;
        groupNameButton.setText(group.getNomeGruppo());
        groupNameButton.setOnAction(e -> {

        });

        GroupDAO groupDAO = new GroupDAO();
        UserDAO userDAO = new UserDAO();
        String matricola = userDAO.getMatricolaByEmail(userEmail);

        // Verifica se l'utente è già un membro del gruppo
        if (groupDAO.isUserMemberOfGroup(group, matricola)) {
            joinButton.setVisible(false);
            joinButton.setDisable(true);
        } else {
            joinButton.setOnAction(e -> {
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Conferma");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Sei sicuro di voler unirti a questo gruppo?");

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.get() == ButtonType.OK){
                    groupDAO.addNewMemberToGroup(group, matricola);
                    try {
                        FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
                        GroupChatController groupchat = new GroupChatController();
                        groupchat.setGroupID(group.getIDGruppo());
                        groupchat.setUserEmail(userEmail);
                        switchScene.loadSceneAndShow(e, loader);
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("BENVENUTO!");
                            alert.setHeaderText(null);
                            alert.setContentText("Adesso fai parte del gruppo : " + group.getNomeGruppo());
                            alert.showAndWait();
                        });
                    } catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            });
        }

        categoryButton.setText(group.getCategoriaGruppo());
        categoryButton.setOnAction(e -> {
            this.searchField.setText(group.getCategoriaGruppo());
            homePageController.onSearch(null);
        });

        int memberCount = groupDAO.getNumberOfMemberGroup(group.getIDGruppo());
        memberCountLabel.setText(String.valueOf(memberCount) + " membri");
    }


    public void setUserEmail(String email){
        this.userEmail = email;
    }
}