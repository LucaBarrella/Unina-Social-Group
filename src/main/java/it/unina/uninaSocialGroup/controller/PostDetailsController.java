package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.io.IOException;

public class PostDetailsController extends ListCell<Post> {
    private Post post;
    private @FXML Label usernameAuthor;
    private @FXML Text postText;
    private @FXML Button likeButton, commentButton;
    private @FXML ImageView likeButtonImage;
    private String matricola;
    private PostDAO postDAO = new PostDAO();
    private UserDAO userDAO = new UserDAO();
    private Boolean likeStatus = false;

    public void initialize() {
        likeButton.setOnAction(this::handleLikeButton);
        commentButton.setOnAction(this::handleCommentButton);
    }

    /**
     * setPost
     * Metodo che mostra i dettagli del post
     */
    public void setPost(Post post) {
        this.post = post;
        setLabelAuthor(post.getCreatorePost());
        setLabelContent(post.getMessaggioTestuale());
        setLikeStatus(matricola, post.getIDPost());
    }

    /**
     * setLabelAuthor
     * Metodo che mostra l'autore del post
     */
    public void setLabelAuthor(String title) {
        this.usernameAuthor.setText(title);
    }

    /**
     * setLabelContent
     * Metodo che mostra il messaggio scritto del post
     */
    public void setLabelContent(String content) {
        this.postText.setText(content);
    }

    /**
     * setLikeStatus
     * Metodo che modifica lo stato del like a seconda se è gia stato messo oppure no
     */
    public void setLikeStatus(String matricola, String postID) {
    if (postDAO.isLikeAlreadyAdd(matricola, postID)) {
        likeStatus = true;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
    } else {
        likeStatus = false;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
    }
}

    /**
     * Metodo che viene chiamato nel GroupChatController
     * Utilizzato per ottenere la matricola dell'utente a partire dalla sua email
     */
    public void setMatricolaWithEmail(String userEmail) {
        this.matricola = userDAO.getMatricolaByEmail(userEmail);
    }

    /**
     * handleLikeButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Like
     * Controlla lo stato del like (se gia stato messo oppure no)
     * A seconda dello stato, cambia l'immagine del like (cuore)
     */
    private void handleLikeButton(ActionEvent actionEvent) {
        if (likeStatus){
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
            postDAO.removeLike(matricola, post.getIDPost());
            likeStatus = false;
        } else {
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
            postDAO.addLike(matricola, post.getIDPost());
            likeStatus = true;
        }
    }

    /**
     * handleCommentButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Commento
     * Mostra i commenti che sono stati messi a quel post
     */
    private void handleCommentButton(ActionEvent actionEvent) {
        try {
            SwitchScene switchScene = new SwitchScene();
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/CommentSection.fxml");
            switchScene.loadSceneAndShow(actionEvent, loader);
            CommentSectionController commentSectionController = loader.getController();
            commentSectionController.setMatricola(this.matricola);
            commentSectionController.setPostID(post.getIDPost());
            commentSectionController.setOriginalPost(post.getCreatorePost(), post.getMessaggioTestuale());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}