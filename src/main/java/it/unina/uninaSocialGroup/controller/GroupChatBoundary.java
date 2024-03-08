package it.unina.uninaSocialGroup.controller;

import javafx.application.Platform;
import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import it.unina.uninaSocialGroup.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GroupChatBoundary {
    @FXML
    private ListView<VBox> postListView;
    @FXML
    private Label groupName, NumberOfMembers;
    @FXML
    private Button PostButton, BackButton, LeaveGroupButton;
    @FXML
    private TextArea PostTextArea;
    @FXML
    private HBox HBoxPost;
    @FXML
    private ComboBox<User> members;
    private SwitchScene switchScene = new SwitchScene();
    private static LogicalController logic = new LogicalController();

    @FXML
    public void initialize() {
        BackButton.setOnAction(this::BackToHomePage);
        HBoxPost.setVisible(false);
        PostButton.setOnAction(event -> HBoxPost.setVisible(true));
        fillListView();
        displayData();
        PostTextArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    CreatePost();
                    event.consume();
                    break;
                default:
                    break;
            }
        });
        displayMembers();

        //Controlla se l'utente fa parte del gruppo che si sta visualizzando
        boolean isUserInMembers = false;
        isUserInMembers = logic.isUserMemberOfGroup();

        //Se l'utente non fa parte del gruppo, allora si impedisce di pubblicare post
        //e si impedisce di cliccare il tasto per abbandonare il gruppo
        if (!isUserInMembers) {
            PostButton.setDisable(true);
            LeaveGroupButton.setVisible(false);
            LeaveGroupButton.setDisable(true);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText("Unisciti al gruppo per pubblicare post");
                alert.showAndWait();
            });
        }
        LeaveGroupButton.setOnAction(this::LeaveGroup);
    }


    /**
     * LeaveGroup
     * Metodo che viene chiamato quando viene cliccato il bottone ABBANDONA
     * Rimuove l'utente dal gruppo
     */
    public void LeaveGroup(ActionEvent event){
        //Mostra una domanda di conferma all'utente
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Conferma");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Sei sicuro di voler uscire dal gruppo?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        //Se viene cliccato OK allora aggiungi l'utente al gruppo
        if (result.get() == ButtonType.OK){
            logic.removeMember();
            try {
                String NameGroup = logic.getGroupName();
                //Scambia la scena con la HomePage
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
                switchScene.loadSceneAndShow(event, loader);
                //Mostra un messaggio di conferma
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Gruppo Abbandonato");
                    alert.setHeaderText(null);
                    alert.setContentText("Sei uscito dal gruppo: " + NameGroup);
                    alert.showAndWait();
                });
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * displayMembers
     * Metodo che mostra i membri del gruppo
     */
    public void displayMembers() {
        // Imposta una factory per le celle della lista "members".
        // Ogni cella visualizza il toString() dell'oggetto User e viene disabilitata
        members.setCellFactory(lv -> new ListCell<User>() {
            @Override
            public void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                    setDisable(true);
                }
            }
        });
        ObservableList<User> memberList = FXCollections.observableArrayList(logic.getGroupMembers());
        members.setItems(memberList);
    }

    /**
     * CreatePost
     * Metodo che viene chiamato quando viene cliccato INVIO sulla tastiera.
     * Il post appena scritto viene salvato nel database e mostrato a schermo
     */
    public void CreatePost(){
        String text = PostTextArea.getText();
        if (text != null && !text.trim().isEmpty()) {
            logic.createPost(text);
            PostTextArea.clear();
            //ricarica la lista dei post
            fillListView();
            //mostra un messaggio di conferma
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Post Pubblicato");
                alert.setHeaderText(null);
                alert.setContentText("Il tuo post è stato pubblicato!");
                alert.showAndWait();
            });
        }
    }

    /**
     * displayData
     * Metodo che mostra il nome e il numero di membri del gruppo sull'interfaccia
     */
    public void displayData(){
        String name = logic.getGroupName();
        groupName.setText(name);
        NumberOfMembers.setText(String.valueOf(logic.numberOfMembers()));
    }


    /**
     * loadVBoxFromFXML
     * Metodo che carica un VBox da un file FXML. Il file FXML è specificato dal percorso.
     * Dopo aver caricato il VBox, ottiene il controller associato e imposta il post specificato.
     * Infine, restituisce il VBox caricato (o null se il caricamento non è riuscito).
     */
    public VBox loadVBoxFromFXML(Post post) {
        VBox vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/PostDetailsPage.fxml"));
            vBox = loader.load();
            PostDetailsBoundary controller = loader.getController();
            controller.setPost(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    /**
     * fillListView
     * Metodo che mostra i post pubblicati sul gruppo.
     * Si prendono i post dal db, li si aggiungono al VBox e poi alla ListView
     */
    public void fillListView() {
        ObservableList<VBox> vBoxList = FXCollections.observableArrayList();
        List<Post> posts = logic.ListPosts();

        for (Post post : posts) {
            vBoxList.add(loadVBoxFromFXML(post));
        }

        postListView.setItems(vBoxList);
    }

    /**
     * BackToHomePage
     * Metodo che viene chiamato quando viene cliccato il bottone per tornare alla homepage
     * Scambia la scena con la HomePage
     */
    public @FXML void BackToHomePage(ActionEvent event){
            try {
                FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
                switchScene.loadSceneAndShow(event, loader);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}