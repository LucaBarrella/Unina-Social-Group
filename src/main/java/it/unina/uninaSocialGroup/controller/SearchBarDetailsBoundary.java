package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class SearchBarDetailsBoundary extends ListCell<Group> {
    private @FXML Button groupNameButton, categoryButton, joinButton;
    private @FXML Label memberCountLabel;
    private static LogicalController logic = new LogicalController();
    private static HomePageBoundary homePageControllerIstance = new HomePageBoundary();
    Group group;
    SwitchScene switchScene = new SwitchScene();

    public void setGroup(Group group) {
        this.group = group;
        logic.setGroup(this.group);
        setLabelGroupNameButton();
        setLabelCategoryButton();
        setLabelMemberCount();
        setJoinButtonStatus();
        setupButtons();
    }

    private void setupButtons() {
        groupNameButton.setOnAction(this::handleGroupNameButton);
        categoryButton.setOnAction(this::handleCategoryButton);
        joinButton.setOnAction(this::handleJoinButton);
    }

    private void setLabelGroupNameButton() {
        this.groupNameButton.setText(logic.getGroupName());
    }

    private void setLabelCategoryButton() {
        this.categoryButton.setText(logic.getGroupCategory());
    }

    private void setLabelMemberCount() {
        this.memberCountLabel.setText(String.valueOf(logic.numberOfMembers()) + " membri");
    }

    private void handleCategoryButton(ActionEvent actionEvent) {
    logic.setGroup(this.group);
    homePageControllerIstance.updateByCategoryButton(logic.getGroupsBySearchField(logic.getGroupCategory()));
}

    private void handleGroupNameButton(ActionEvent actionEvent) {
        logic.setGroup(this.group);
        FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
        try {
            switchScene.loadSceneAndShow(actionEvent, loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setJoinButtonStatus() {
        if (logic.isUserMemberOfGroup()) {
            joinButton.setVisible(false);
        }
    }

    private void handleJoinButton(ActionEvent actionEvent) {
        logic.setGroup(this.group);
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
                switchScene.loadSceneAndShow(actionEvent, loader);
                //Mostra un messaggio di conferma
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("BENVENUTO!");
                    alert.setHeaderText(null);
                    alert.setContentText("Adesso fai parte del gruppo : " + logic.getGroupName());
                    alert.showAndWait();
                });
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public void setHomePageBoundary(HomePageBoundary homePageBoundary) {
        homePageControllerIstance=homePageBoundary;
    }
}
