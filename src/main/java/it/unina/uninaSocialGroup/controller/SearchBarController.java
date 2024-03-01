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


    /**
     * setGroup
     * Metodo che mostra i gruppi (cercati nel db per nome o categoria)
     */
    public void setGroup(Group group, TextField searchField, HomePageController homePageController) {
        this.searchField = searchField;
        this.homePageController = homePageController;
        groupNameButton.setText(group.getNomeGruppo());
        groupNameButton.setOnAction(e -> {

        });

        GroupDAO groupDAO = new GroupDAO();
        UserDAO userDAO = new UserDAO();
        String matricola = userDAO.getMatricolaByEmail(userEmail);

        //Se l'utente già fa parte del gruppo, non verrà mostrato il bottone per parteciparci
        if (groupDAO.isUserMemberOfGroup(group, matricola)) {
            joinButton.setVisible(false);
            joinButton.setDisable(true);
        } else {
            joinButton.setOnAction(e -> {
                //Mostra una domanda di conferma all'utente
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Conferma");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Sei sicuro di voler unirti a questo gruppo?");
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                //Se viene cliccato OK allora aggiungi l'utente al gruppo
                if (result.get() == ButtonType.OK){
                    groupDAO.addNewMemberToGroup(group, matricola);
                    try {
                        //Scambia la scena con la chat del gruppo
                        FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
                        GroupChatController groupchat = new GroupChatController();
                        //Passa l'ID del gruppo alla GroupChat
                        groupchat.setGroupID(group.getIDGruppo());
                        //Passa la email dell'utente alla GroupChat
                        groupchat.setUserEmail(userEmail);
                        switchScene.loadSceneAndShow(e, loader);
                        //Mostra un messaggio di conferma
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

    /**
     * setUserEmail
     * Funzione che viene chiamata nella HomePage
     * Usata principalmente per ottenere la Email dell'utente dalla HomePage
     */
    public void setUserEmail(String email){
        this.userEmail = email;
    }
}