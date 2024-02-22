package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.ReportDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Report;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import it.unina.uninaSocialGroup.Model.User;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HomePageController{
    private @FXML Label LabelNameSurname, LabelMatricola, LabelName, LabelBirthDate, LabelEmail, LabelSurname, LabelRegistrationDate;
    private @FXML TableView<Group> TableGroups;
    private @FXML TableColumn<Group, String> IDColumn, NameColumn, CreationDateColumn, CategoryColumn;
    private @FXML ToggleButton CreatedGroups;
    private @FXML Circle Circle;
    private @FXML ChoiceBox<Integer> MonthBox;
    private Integer[] Months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private @FXML TableView TableReport;
    private @FXML TableColumn<Group, String> NameGroupColumn, PostPlusLikeColumn, PostMinusLikeColumn, PostPlusCommentColumn, PostMinusCommentColumn;
    private @FXML TableColumn<Group, Integer> AveragePostColumn;
    @FXML
    private Button LogOutButton, CreationGroupButton;
    private SwitchScene switchScene = new SwitchScene();
    private static String userEmail;
    @FXML
    private ListView<Group> groupListView;
    @FXML
    private TextField searchField;
    @FXML
    private TabPane tabPane;
    private Tab newTab;
    private ObservableList<Group> allGroups;
    @FXML
    private Tab ProfileTab, GroupsTab, ReportTab;
    @FXML
    private Button profileButton, groupButton, reportButton;
    public HomePageController(){
        allGroups = FXCollections.observableArrayList();
    }
    @FXML
    public void initialize() {
        CreationGroupButton.setOnAction(this::CreationGroup);
        LoadProfileData();
        displayName();
        LoadDataTableUserGroups();
        LogOutButton.setOnAction(this::Logout);
        MonthBox.getItems().addAll(Months);
        profileButton.setOnAction(this::goToProfileTab);
        groupButton.setOnAction(this::goToGroupsTab);
        reportButton.setOnAction(this::goToReportTab);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), Circle);
        CreatedGroups.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                transition.setToX(30);
                LoadDataTableAdminGroups();
            } else {
                transition.setToX(0);
                LoadDataTableUserGroups();
            }
            transition.play();
        });
        MonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            LoadDataTableReport(newValue);
        });
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
                        SearchBarCellController controller = fxmlLoader.getController();
                        controller.setGroup(item, searchField, HomePageController.this);
                        setGraphic(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (oldTab == ProfileTab) {
                profileButton.getStyleClass().remove("button-selected");
            } else if (oldTab == GroupsTab) {
                groupButton.getStyleClass().remove("button-selected");
            } else if (oldTab == ReportTab) {
                reportButton.getStyleClass().remove("button-selected");
            }

            if (newTab == ProfileTab) {
                profileButton.getStyleClass().add("button-selected");
            } else if (newTab == GroupsTab) {
                groupButton.getStyleClass().add("button-selected");
            } else if (newTab == ReportTab) {
                reportButton.getStyleClass().add("button-selected");
            }
        });

    }

    public void setUserEmail(String email){
        this.userEmail = email;
        System.out.println("User email: " + userEmail);
    }

    private @FXML void goToProfileTab(ActionEvent event) {
        tabPane.getSelectionModel().select(ProfileTab);;
    }
    private @FXML void goToGroupsTab(ActionEvent event) {
        tabPane.getSelectionModel().select(GroupsTab);
    }
    private @FXML void goToReportTab(ActionEvent event) {
        tabPane.getSelectionModel().select(ReportTab);
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
        UserDAO userDAO = new UserDAO();
        GroupDAO groupDAO = new GroupDAO();
        User user = userDAO.getUserByEmail(userEmail);
        groupDAO.getUserGroups(user);
        List<Group> dati = user.getUserGroups();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        TableGroups.getItems().clear();
        TableGroups.getItems().addAll(dati);
    }


    public void LoadDataTableAdminGroups(){
        UserDAO userDAO = new UserDAO();
        GroupDAO groupDAO = new GroupDAO();
        User user = userDAO.getUserByEmail(userEmail);
        groupDAO.getAdminGroups(user);
        List<Group> dati = user.getOwnerGroups();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        TableGroups.getItems().clear();
        TableGroups.getItems().addAll(dati);
    }

    public void LoadDataTableReport(Integer month){
        UserDAO userDAO = new UserDAO();
        ReportDAO report = new ReportDAO();
        User user = userDAO.getUserByEmail(userEmail);
        List<Report> dati = report.getGroupsReport(user,month);
        NameGroupColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        PostPlusLikeColumn.setCellValueFactory(new PropertyValueFactory<>("PostPiuLike"));
        PostMinusLikeColumn.setCellValueFactory(new PropertyValueFactory<>("PostMenoLike"));
        PostPlusCommentColumn.setCellValueFactory(new PropertyValueFactory<>("PostPiuCommenti"));
        PostMinusCommentColumn.setCellValueFactory(new PropertyValueFactory<>("PostMenoCommenti"));
        AveragePostColumn.setCellValueFactory(new PropertyValueFactory<>("NumMedioPost"));
        TableReport.getItems().clear();
        TableReport.getItems().addAll(dati);
    }

    private @FXML void Logout(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/LoginPage.fxml", "topToBottom");
            System.out.println("Logout successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public @FXML void onSearch(KeyEvent event) {
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

    private @FXML void CreationGroup(ActionEvent event) {
        if (newTab == null) {
            newTab = new Tab("Creazione Gruppo");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/GroupCreationPage.fxml"));
                Parent interfaceContent = fxmlLoader.load();
                GroupCreationController controller = fxmlLoader.getController();
                controller.setUserEmail(userEmail);
                newTab.setContent(interfaceContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tabPane.getTabs().add(newTab);
        }
        tabPane.getSelectionModel().select(newTab);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (this.newTab != null && newTab != this.newTab) {
                tabPane.getTabs().remove(this.newTab);
                this.newTab = null;
            }
        });
    }

}
