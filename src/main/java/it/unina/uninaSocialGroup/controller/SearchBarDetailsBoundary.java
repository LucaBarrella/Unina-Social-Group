package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.io.IOException;
import java.util.List;


public class SearchBarDetailsBoundary extends ListCell<Group> {
    private @FXML Button groupNameButton, categoryButton, joinButton;
    private @FXML Label memberCountLabel;
    private static LogicalController logic = new LogicalController();
    private static HomePageBoundary homePageControllerIstance = new HomePageBoundary();
    Group group;

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
        this.memberCountLabel.setText(String.valueOf(logic.numberOfMembers()));
    }

    private void handleCategoryButton(ActionEvent actionEvent) {
    logic.setGroup(this.group);
    homePageControllerIstance.updateByCategoryButton(logic.getGroupsBySearchField(logic.getGroupCategory()));
}

    private void handleGroupNameButton(ActionEvent actionEvent) {

        logic.setGroup(this.group);
        SwitchScene switchScene = new SwitchScene();
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
        logic.JoinGroup();
        joinButton.setVisible(false);
        //TODO: Mostra messaggio di successo e cambia scena.
    }

    public void setHomePageBoundary(HomePageBoundary homePageBoundary) {
        homePageControllerIstance=homePageBoundary;
    }
}
