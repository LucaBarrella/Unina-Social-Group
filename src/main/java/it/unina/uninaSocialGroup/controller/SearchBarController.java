package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.Model.Group;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SearchBarController {
    @FXML
    private Button groupNameButton;
    @FXML
    private Button categoryButton;
    @FXML
    private Label memberCountLabel;
    private TextField searchField;
    private HomePageController homePageController;

    public void setGroup(Group group, TextField searchField, HomePageController homePageController) {
        this.searchField = searchField;
        this.homePageController = homePageController;
        groupNameButton.setText(group.getNomeGruppo());
        groupNameButton.setOnAction(e -> {
            // Handle group name button click
        });

        categoryButton.setText(group.getCategoriaGruppo());
        categoryButton.setOnAction(e -> {
            this.searchField.setText(group.getCategoriaGruppo());
            homePageController.onSearch(null);
        });

        GroupDAO groupDAO = new GroupDAO();
        int memberCount = groupDAO.getNumberOfMemberGroup(group.getIDGruppo());
        memberCountLabel.setText(String.valueOf(memberCount) + " membri");
    }
}