package it.unina.uninaSocialGroup.Boundary;

import it.unina.uninaSocialGroup.LogicalController;
import it.unina.uninaSocialGroup.Model.Comment;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.List;

public class CommentSectionBoundary {

    private @FXML ListView<VBox> commentListView;
    private @FXML Button commentButton, BackButton;
    private @FXML TextArea CommentTextArea;
    private @FXML Label usernameAuthor;
    private @FXML Text postText;
    private  @FXML HBox HBoxComment;
    private static LogicalController logic = new LogicalController();
    private SwitchScene switchScene = new SwitchScene();


    public @FXML void initialize() {
        setupHBoxCommentVisibility();
        setupButtonActions();
        setupCommentTextArea();
        setOriginalPost();
        fillListView();
    }

    private void setupHBoxCommentVisibility() {
        HBoxComment.setVisible(false);
    }

    private void setupButtonActions() {
        BackButton.setOnAction(this::BackToGroupChat);
        commentButton.setOnAction(event -> HBoxComment.setVisible(true));
        CommentTextArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    CreateComment();
                    event.consume();
                    break;
                default:
                    break;
            }
        });
    }

    private void setupCommentTextArea() {
        CommentTextArea.setWrapText(true);
    }

    /**
     * CreateComment
     * Metodo che viene chiamato quando viene cliccato INVIO sulla tastiera
     * Il commento appena scritto viene salvato nel database e mostrato a schermo
     */
    public void CreateComment(){
        String text = CommentTextArea.getText();
        if (text != null && !text.trim().isEmpty()) {
            //Aggiunge il commento al database
            logic.createNewComment(text);
            //Pulisce la TextArea
            CommentTextArea.clear();
            //Ricarica la lista dei commenti
            fillListView();
            //Mostra un messaggio di conferma
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commento Pubblicato");
                alert.setHeaderText(null);
                alert.setContentText("Il tuo commento è stato pubblicato!");
                alert.showAndWait();
            });
        }
    }

    /**
     * setOriginalPost
     * Metodo che mostra i dati del post, ovvero l'autore e il messaggio scritto
     */
    public void setOriginalPost() {
        this.usernameAuthor.setText(logic.getAuthor());
        this.postText.setText(logic.getPostContent());
    }

    /**
     * loadVBoxFromFXML
     * Metodo che carica un VBox da un file FXML. Il file FXML è specificato dal percorso.
     * Dopo aver caricato il VBox, ottiene il controller associato e imposta il commento specificato.
     * Infine, restituisce il VBox caricato (o null se il caricamento non è riuscito).
     */
    public VBox loadVBoxFromFXML(Comment comment) {
        VBox vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/CommentDetailes.fxml"));
            vBox = loader.load();
            CommentDetailsBoundary controller = loader.getController();
            controller.setComment(comment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    /**
     * fillListView
     * Metodo che mostra i commenti pubblicati sul post.
     * Si prendono i commenti dal db, li si aggiungono al VBox e poi alla ListView
     */
    public void fillListView() {
        ObservableList<VBox> vBoxList = FXCollections.observableArrayList();
        List <Comment> comments = logic.ListComments();
        for (Comment comment : comments) {
            vBoxList.add(loadVBoxFromFXML(comment));
        }
        commentListView.setItems(vBoxList);
    }

    /**
     * BackToGroupChat
     * Metodo che viene chiamato quando viene cliccata la gif della freccia
     * Scambia la scena con la GroupChat
     */
    public @FXML void BackToGroupChat(ActionEvent event){
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupChatPage.fxml");
            switchScene.loadSceneAndShow(event, loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}