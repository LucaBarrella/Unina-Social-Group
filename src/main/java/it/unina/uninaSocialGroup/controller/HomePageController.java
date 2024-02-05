package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import it.unina.uninaSocialGroup.Model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class HomePageController{
    private @FXML Label LabelProfilo;
    private @FXML Label LabelGruppi;
    private @FXML Label LabelReport;
    private @FXML Label LabelNameSurname;
    private @FXML Label LabelMatricola;
    private @FXML Label LabelName;
    private @FXML Label LabelBirthDate;
    private @FXML Label LabelEmail;
    private @FXML Label LabelSurname;
    private @FXML Label LabelRegistrationDate;
    private @FXML TableView<Group> TableGroups;
    private @FXML TableColumn<Group, String> IDColumn;
    private @FXML TableColumn<Group, String> NameColumn;
    private @FXML TableColumn<Group, Date> CreationDateColumn;
    private @FXML TableColumn<Group, String> CategoryColumn;
    private @FXML ToggleButton CreatedGroups;
    private @FXML ChoiceBox<String> MonthBox;
    private String[] Months = {"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio",
                               "Agosto","Settembre","Ottobre","Novembre","Dicembre"};
    private @FXML TableView TableReport;
    @FXML
    private Button LogOutButton;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail;
    @FXML
    public void initialize() {
        LogOutButton.setOnAction(this::Logout);
        MonthBox.getItems().addAll(Months);
    }

    public void UserEmail(String email){
        this.userEmail = email;
    }

    public void displayName(){
        UserDAO user = new UserDAO();
        User result = user.getFullNameByEmail(userEmail);
        String name = result.getNome();
        String surname = result.getCognome();
        LabelNameSurname.setText(name+ " " +surname);
    }

    public void LoadDataTableGroups(){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("IDGruppo"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        List<Group> dati = group.getUserGroups(matricola);
        TableGroups.getItems().addAll(dati);
    }


    private void Logout(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/LoginPage.fxml", "topToBottom");
            System.out.println("Logout successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
