package it.unina.uninaSocialGroup.Boundary;

import it.unina.uninaSocialGroup.Model.*;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import javafx.util.Duration;

import java.awt.*;
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
    LogicalController logic = new LogicalController();
    public HomePageBoundary(){
        allGroups = FXCollections.observableArrayList();
    }
    @FXML
    public void initialize() {
        CreationGroupButton.setOnAction(this::GroupCreationTab);
        LoadProfileData();
        displayName();
        LoadDataTableUserGroups();
        LogOutButton.setOnAction(this::Logout);
        //Aggiungi i mesi alla ChoiceBox
        MonthBox.getItems().addAll(Months);
        profileButton.setOnAction(this::goToProfileTab);
        groupButton.setOnAction(this::goToGroupsTab);
        reportButton.setOnAction(this::goToReportTab);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), Circle);
        //Quando viene attivato il togglebutton, trasla il cerchio verso destra
        //Altrimenti quando viene disattivato, riportalo al punto di partenza
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
        //Chiama il metodo LoadDataTableReport dopo che è statoscelto un mese dalla MonthBox
        MonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int monthInt = java.util.Arrays.asList(Months).indexOf(newValue) + 1;
            LoadDataTableReport(monthInt);
        });
        List groups = logic.getGroupsBySF(searchField.getText());
        allGroups.addAll(groups);
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
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/SearchBar.fxml"));
                        HBox hbox = fxmlLoader.load();
                        SearchBarBoundary controller = fxmlLoader.getController();
                        controller.setGroup(item, searchField, HomePageBoundary.this);
                        setGraphic(hbox);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        //A seconda del tab su cui sta, fare un contorno giallo alla immagine associata al tab a sinistra della HomePgae
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
        OpenButton.setVisible(false);
        OpenButton.setDisable(true);
        ChatText.setVisible(false);
        //Quando viene scelto un gruppo dalla tabella, msotrare il bottone per aprire la chat
        TableGroups.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                OpenButton.setVisible(true);
                OpenButton.setDisable(false);
                ChatText.setVisible(true);
            }else {
                OpenButton.setVisible(false);
                ChatText.setVisible(false);
                OpenButton.setDisable(true);
            }
        });
        OpenButton.setOnAction(this::goToGroupChat);
        UninaWebSite.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.unina.it/home;jsessionid=DC037C4CC141CFA2AFBB715EF93B8997.node_publisher12"));
                } catch (IOException | URISyntaxException er) {
                    er.printStackTrace();
                }
        });
        Segrepass.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.segrepass1.unina.it/logout.do?dove=Uscita"));
            } catch (IOException | URISyntaxException er) {
                er.printStackTrace();
            }
        });
    }

    /**
     * goToProfileTab
     * Metodo che viene chiamato quando viene cliccata la prima gif a sinistra
     * Porta al tab del profilo
     */
    public @FXML void goToProfileTab(ActionEvent event) {
        tabPane.getSelectionModel().select(ProfileTab);;
    }

    /**
     * goToGroupsTab
     * Metodo che viene chiamato quando viene cliccata la second gif a sinistra
     * Porta al tab dell'elenco gruppi
     */
    public @FXML void goToGroupsTab(ActionEvent event) {
        tabPane.getSelectionModel().select(GroupsTab);
    }

    /**
     * goToReportTab
     * Metodo che viene chiamato quando viene cliccata la terza gif a sinistra
     * Porta al tab dei report mensili
     */
    public @FXML void goToReportTab(ActionEvent event) {
        tabPane.getSelectionModel().select(ReportTab);
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
        List dati = logic.getUserGroups();
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
        List dati = logic.getAdminGroups();
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
        List dati = logic.getReportGroups(Month);
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
            switchScene.loadSceneAndShow(event, loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * onSearch
     * Metodo che viene chiamato quando viene usata la searchBar
     * Mostra i gruppi cercati per nome o categoria
     */
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
