package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import it.unina.uninaSocialGroup.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class HomePageController{
    private @FXML Label LabelProfilo, LabelGruppi, LabelReport, LabelNameSurname, LabelMatricola, LabelName, LabelBirthDate, LabelEmail, LabelSurname, LabelRegistrationDate;
    private @FXML TableView<Group> TableGroups;
    private @FXML TableColumn<Group, String> IDColumn, NameColumn, CreationDateColumn, CategoryColumn;
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
    private ListView<Group> groupListView;
    @FXML
    private TextField searchField;

    private ObservableList<Group> allGroups;
    public HomePageController() {
        allGroups = FXCollections.observableArrayList();
    }
    @FXML
    public void initialize() {
        LogOutButton.setOnAction(this::Logout);
        MonthBox.getItems().addAll(Months);
        CreatedGroups.setOnAction(event -> {if (CreatedGroups.isSelected()) {
                                                LoadDataTableAdminGroups();
                                            }else{
                                                LoadDataTableUserGroups();}});

        GroupDAO groupDao = new GroupDAO();
        allGroups.addAll(groupDao.getGroupsBySearchField(searchField.getText()));
        groupListView.setItems(allGroups);
        groupListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Group item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/GroupCell.fxml"));
                        HBox hbox = fxmlLoader.load();
                        GroupCellController controller = fxmlLoader.getController();
                        controller.setGroup(item, searchField, HomePageController.this);
                        setGraphic(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

//   TO:DO Va fatto un file css per i bottoni per fargli cambiare colore, poi si dovrà fare i listener per fargli cambiare tab
//    <effect>
//        <ColorAdjust hue="0.15" saturation="0.2" brightness="0.1" />
//    </effect>
    public void setUserEmail(String email){
        this.userEmail = email;
        LoadProfileData();
        displayName();
    }

    public void LoadProfileData() {
    UserDAO userDAO = new UserDAO();
    User user = userDAO.getUserData(userDAO.getMatricolaByEmail(userEmail));
    LabelMatricola.setText(user.getMatricola());
    LabelEmail.setText(userEmail);
    LabelName.setText(user.getNome());
    LabelSurname.setText(user.getCognome());
    LabelBirthDate.setText(user.getDataDiNascita().toString());
    LabelRegistrationDate.setText(user.getDataDiRegistrazione().toString());
}

    public void displayName(){
        UserDAO user = new UserDAO();
        User result = user.getFullNameByEmail(userEmail);
        String name = result.getNome();
        String surname = result.getCognome();
        LabelNameSurname.setText(name+ " " +surname);
    }

    public void LoadDataTableUserGroups(){
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

    public void LoadDataTableAdminGroups(){
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("IDGruppo"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        List<Group> dati = group.getAdminGroups(matricola);
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

    @FXML
    public void onSearch(KeyEvent event) {
        String searchText = searchField.getText().toLowerCase();

        if (searchText.isEmpty()) {
            groupListView.setVisible(false);
        } else {
            groupListView.setVisible(true);
            ObservableList<Group> filteredGroups = allGroups.filtered(group ->
                    group.getNomeGruppo().toLowerCase().contains(searchText) ||
                            group.getCategoriaGruppo().toLowerCase().contains(searchText)
            );
            groupListView.setItems(filteredGroups);
        }
    }
}
