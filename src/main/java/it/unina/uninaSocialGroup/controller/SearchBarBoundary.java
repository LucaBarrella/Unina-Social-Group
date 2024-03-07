package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class SearchBarBoundary {
    @FXML
    private Button groupNameButton;
    @FXML
    private Button categoryButton, joinButton;
    @FXML
    private Label memberCountLabel;
    private TextField searchField;
    private HomePageBoundary homePageController;
    private SwitchScene switchScene = new SwitchScene();
    private static LogicalController logic = new LogicalController();

    /**
     * setGroup
     * Metodo che mostra i gruppi (cercati per nome o categoria)
     */
    public void setGroup(Group group, TextField searchField, HomePageBoundary homePageController) {
        logic.setGroup(group, String.valueOf(searchField));
        this.searchField = searchField;
        this.homePageController = homePageController;
        groupNameButton.setText(logic.getGroupName());
        groupNameButton.setOnAction(e -> {
            try {
                //Scambia la scena con la chat del gruppo
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
                switchScene.loadSceneAndShow(e, loader);
                //Se l'utente non fa parte del gruppo, mostra un messaggio di avvertenza
                if (!logic.isUserMemberOfGroup()) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Attenzione!");
                        alert.setHeaderText(null);
                        alert.setContentText("Unisciti al gruppo per pubblicare post");
                        alert.showAndWait();
                    });
                }
            } catch(IOException ex){
                ex.printStackTrace();
            }
        });

        //Se l'utente già fa parte del gruppo, non verrà mostrato il bottone per parteciparci
        if (logic.isUserMemberOfGroup()) {
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
                    logic.JoinGroup();
                    try {
                        //Scambia la scena con la chat del gruppo
                        FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
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
        categoryButton.setText(logic.getGroupCategory());
        categoryButton.setOnAction(e -> {
            this.searchField.setText(logic.getGroupCategory());
            homePageController.onSearch(null);
        });
        int memberCount = logic.numberOfMembers();
        memberCountLabel.setText(String.valueOf(memberCount) + " membri");
    }

}