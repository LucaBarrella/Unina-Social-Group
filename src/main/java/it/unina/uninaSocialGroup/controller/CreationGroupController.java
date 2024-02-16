package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class CreationGroupController {
    @FXML
    private Button BackButton, CreationGroupButton;
    @FXML
    private TextField NameGroupField,CategoryGroupField;
    @FXML
    private TableView<Group> AlreadyCreatedGroupsTable;
    @FXML
    private TableColumn<Group, String> IDColumn, NameColumn, CategoryColumn;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail; //NON FUNZIONA ATTENTO !!!! DA RIVEDERE!!!
    @FXML
    public void initialize(){
        BackButton.setOnAction(this::BackToHomePage);
    }

    public @FXML void LoadAlreadyCreatedGroups(){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("IDGruppo"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        List<Group> dati = group.getAdminGroups(matricola);
        AlreadyCreatedGroupsTable.getItems().clear();
        AlreadyCreatedGroupsTable.getItems().addAll(dati);
    }

    public @FXML void GroupCreation(){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        group.CreateNewGroup(NameGroupField.getText(),CategoryGroupField.getText(),matricola);
    }

    private void BackToHomePage(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/HomePage.fxml", "topToBottom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
