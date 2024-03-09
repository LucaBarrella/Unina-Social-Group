package it.unina.uninaSocialGroup.Boundary;

import it.unina.uninaSocialGroup.LogicalController;
import it.unina.uninaSocialGroup.Model.*;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class HomePageBoundary {
    private @FXML Label LabelNameSurname, LabelMatricola, LabelName, LabelBirthDate, LabelEmail, LabelSurname, LabelRegistrationDate;
    private @FXML TableView<Group> TableGroups;
    private @FXML TableColumn<Group, String> NameColumn, CreationDateColumn, CategoryColumn;
    private @FXML ToggleButton CreatedGroups;
    private @FXML Circle Circle;
    private @FXML ChoiceBox<String> MonthBox;
    private @FXML Text ChatText;
    private @FXML Hyperlink UninaWebSite, Segrepass;
    private String[] Months = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicemre"};
    private @FXML TableView TableReport;
    private @FXML TableColumn<Group, String> NameGroupColumn;
    private @FXML TableColumn<Post, String> PostPlusLikeColumn, PostMinusLikeColumn, PostPlusCommentColumn, PostMinusCommentColumn;
    private @FXML TableColumn<Post, Integer> AveragePostColumn;
    @FXML
    private Button LogOutButton, CreationGroupButton, OpenButton;
    private SwitchScene switchScene = new SwitchScene();
    @FXML
    private ListView<HBox> groupListView;
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
    private static LogicalController logic = new LogicalController();

    public HomePageBoundary(){
        allGroups = FXCollections.observableArrayList();
    }
    public @FXML void initialize() {
        setupButtons();
        loadData();
        setupChoiceBox();
        setupTabPaneListener();
        setupGroupTableListener();
        setupOpenButton();
        setupWebLinks();
        setupToggleButtonListener();
    }
    private void setupButtons() {
        CreationGroupButton.setOnAction(this::GroupCreationTab);
        LogOutButton.setOnAction(this::Logout);
        profileButton.setOnAction(this::goToProfileTab);
        groupButton.setOnAction(this::goToGroupsTab);
        reportButton.setOnAction(this::goToReportTab);
        OpenButton.setOnAction(this::goToGroupChat);
    }


    public void getTextFromSearchField(KeyEvent event) {
        if (searchField.getText().isEmpty()) {
            groupListView.setVisible(false);
        } else {
            groupListView.setVisible(true);
            allGroups = logic.getGroupsBySearchField(searchField.getText());
            groupListView.getItems().clear();
            for (Group group : allGroups) {
                loadHBoxFromFXML(group);
            }
        }
    }

    public void updateByCategoryButton(List<Group> filtredListByCategory) {
        groupListView.getItems().clear();
        for (Group group : filtredListByCategory) {
            loadHBoxFromFXML(group);
        }
    }

    public HBox loadHBoxFromFXML(Group group) {
        HBox hBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/SearchBar.fxml"));
            hBox = loader.load();
            SearchBarDetailsBoundary hboxController = loader.getController();
            hboxController.setGroup(group);
            hboxController.setHomePageBoundary(this);
            groupListView.getItems().add(hBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hBox;
    }

    private void loadData() {
        LoadProfileData();
        displayName();
        LoadDataTableUserGroups();
    }

    private void setupChoiceBox() {
        MonthBox.getItems().addAll(Months);
        MonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int monthInt = java.util.Arrays.asList(Months).indexOf(newValue) + 1;
            LoadDataTableReport(monthInt);
        });
    }

    private void setupTabPaneListener() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            updateTabSelection(oldTab, newTab);
        });
    }

    private void setupGroupTableListener() {
        TableGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            toggleOpenButtonAndChatText(newSelection != null);
        });
    }

    private void setupOpenButton() {
        OpenButton.setVisible(false);
        OpenButton.setDisable(true);
        ChatText.setVisible(false);
    }

    private void setupWebLinks() {
        UninaWebSite.setOnAction(this::openUninaWebSite);
        Segrepass.setOnAction(this::openSegrepassWebSite);
    }

    private void updateTabSelection(Tab oldTab, Tab newTab) {
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
    }

    private void toggleOpenButtonAndChatText(boolean isVisible) {
        OpenButton.setVisible(isVisible);
        OpenButton.setDisable(!isVisible);
        ChatText.setVisible(isVisible);
    }

    private void openUninaWebSite(ActionEvent e) {
        openWebLink("https://www.unina.it/home;jsessionid=DC037C4CC141CFA2AFBB715EF93B8997.node_publisher12");
    }

    private void openSegrepassWebSite(ActionEvent e) {
        openWebLink("https://www.segrepass1.unina.it/logout.do?dove=Uscita");
    }


    private void openWebLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException er) {
            er.printStackTrace();
        }
    }

    /**
     * setupToggleButtonListener
     * Metodo che viene chiamato quando viene cliccato il togglebutton
     * Effettua una transizione del cerchio verso destra se il bottone è attivo
     */
    private void setupToggleButtonListener() {
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
    }

    public void goToGroupsTab(ActionEvent event) {
        tabPane.getSelectionModel().select(GroupsTab);
    }
    private void goToReportTab(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(ReportTab);
    }

    private void goToProfileTab(ActionEvent actionEvent) {
        tabPane.getSelectionModel().select(ProfileTab);
    }

    /**
     * LoadProfileData
     * Metodo che mostra i dati dell'utente nel tab del profilo
     */
    public void LoadProfileData() {
        LabelMatricola.setText(logic.getMatricolaUser());
        LabelEmail.setText(logic.getEmailUser());
        LabelName.setText(logic.getNameUser());
        LabelSurname.setText(logic.getSurnameUser());
        LabelBirthDate.setText(logic.getBirthDateUser());
        LabelRegistrationDate.setText(logic.getRegistrationDateUser());
    }

    /**
     * displayName
     * Metodo che mostra il nome e cognome dell'utente
     */
    public void displayName(){
        String name = logic.getNameUser();
        String surname = logic.getSurnameUser();
        LabelNameSurname.setText(name+ " " +surname);
    }

    /**
     * LoadDataTableUserGroups
     * Metodo che mostra i gruppi di cui l'utente fa parte o è creatore
     */
    public void LoadDataTableUserGroups(){
        List<Group> dati = logic.getUserGroups();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        TableGroups.getItems().clear();
        TableGroups.getItems().addAll(dati);
    }


    /**
     * LoadDataTableAdminGroups
     * Metodo che viene chiamato quando viene cliccato il togglebutton
     * Mostra solo i gruppi di cui l'utente è creatore
     */
    public void LoadDataTableAdminGroups(){
        List<Group> dati = logic.getAdminGroups();
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        CreationDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataDiCreazione"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<>("CategoriaGruppo"));
        TableGroups.getItems().clear();
        TableGroups.getItems().addAll(dati);
    }

    /**
     * LoadDataTableReport
     * Metodo che viene chiamato quando viene scelto un mese nel choicebox
     * Mostra i dati mensili inerenti ai gruppi creati dall'utente
     */
    public void LoadDataTableReport(Integer Month){
        List<Report> dati = logic.getReportGroups(Month);
        NameGroupColumn.setCellValueFactory(new PropertyValueFactory<>("NomeGruppo"));
        PostPlusLikeColumn.setCellValueFactory(new PropertyValueFactory<>("PostPiuLike"));
        PostMinusLikeColumn.setCellValueFactory(new PropertyValueFactory<>("PostMenoLike"));
        PostPlusCommentColumn.setCellValueFactory(new PropertyValueFactory<>("PostPiuCommenti"));
        PostMinusCommentColumn.setCellValueFactory(new PropertyValueFactory<>("PostMenoCommenti"));
        AveragePostColumn.setCellValueFactory(new PropertyValueFactory<>("NumMedioPost"));
        TableReport.getItems().clear();
        TableReport.getItems().addAll(dati);
    }


    /**
     * Logout
     * Metodo che viene chiamato quando viene cliccato il bottone ESCI nell'angolo a sinistra della HomePage
     * Scambia la scena con la LoginPage
     */
    public @FXML void Logout(ActionEvent event) {
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/LoginPage.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.resizableProperty().setValue(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GroupCreationTab
     * Metodo che viene chiamato quando viene cliccato il bottone CREA nel tab ELENCOGRUPPI
     * Mostra un tab di creazione gruppo (GroupChatPage)
     */
    public @FXML void GroupCreationTab(ActionEvent event) {
        if (newTab == null) {
            newTab = new Tab("Creazione Gruppo");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/GroupCreationPage.fxml"));
                Parent interfaceContent = fxmlLoader.load();
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

    /**
     * goToGroupChat
     * Metodo che viene chiamato quando viene cliccato il bottone APRI dopo aver scelto un gruppo
     * Scambia la scena con GroupChatPage
     */
    public @FXML void goToGroupChat(ActionEvent event) {
        try {
            Group selectedGroup = TableGroups.getSelectionModel().getSelectedItem();
            if (selectedGroup != null) {
                logic.setGroup(selectedGroup);
            }
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
            switchScene.loadSceneAndShow(event, loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
