package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.CommentDAO;
import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.Model.Comment;
import it.unina.uninaSocialGroup.Model.Post;
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
    private static String postID;
    private static LogicalController logic = new LogicalController();
    private String matricola = logic.getMatricolaUser();
    private SwitchScene switchScene = new SwitchScene();

    @FXML
    public void initialize() {
        HBoxComment.setVisible(false);
        commentButton.setOnAction(event -> HBoxComment.setVisible(true));
        BackButton.setOnAction(this::BackToGroupChat);
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

    /**
     * CreateComment
     * Metodo che viene chiamato quando viene cliccato INVIO sulla tastiera
     * Il commento appena scritto viene salvato nel database e mostrato a schermo
     */
    public void CreateComment(){
        String text = CommentTextArea.getText();
        if (text != null && !text.trim().isEmpty()) {
            //TODO
            CommentDAO commentDAO = new CommentDAO();
            commentDAO.createNewComment(text, matricola, postID);
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
    public void setOriginalPost(String creatorePost, String messaggioTestuale) {
        this.usernameAuthor.setText(creatorePost);
        this.postText.setText(messaggioTestuale);
    }

    /**
     * setPostID
     * Metodo che viene chiamato nel PostDetailsController
     * Utilizzato per ottenere l'id del post
     */
        public void setPostID(String postId) {
        this.postID = postId;
        fillListView();
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
            //TODO
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
        //TODO
        CommentDAO commentDAO = new CommentDAO();
        PostDAO postDAO = new PostDAO();
        Post post = postDAO.getPostByID(postID);
        commentDAO.getCommentByPost(post);
        List<Comment> comments = post.getCommenti();

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