package it.unina.uninaSocialGroup.Boundary;

import it.unina.uninaSocialGroup.DAO.PostDAO;
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

public class PostDetailsBoundary extends ListCell<Post> {
    private Post post;
    private @FXML Label usernameAuthor, likeCounter, commentCounter;
    private @FXML Text postText;
    private @FXML Button likeButton, commentButton;
    private @FXML ImageView likeButtonImage;
    private Boolean likeStatus = false;
    LogicalController logic = new LogicalController();

    public void initialize() {
        likeButton.setOnAction(this::handleLikeButton);
        commentButton.setOnAction(this::handleCommentButton);
    }

    /**
     * setPost
     * Metodo che mostra i dettagli del post
     */
    public void setPostDetails() {
        setLabelAuthor(logic.getPostAuthor());
        setLabelContent(logic.getPostContent());
        setLikeStatus();
        setLikesNumber();
        setCommentsNumber();
    }

    private void setLikesNumber() {
        this.likeCounter.setText(String.valueOf(logic.numberOfLike()));
    }

    private void setCommentsNumber() {
        this.commentCounter.setText(String.valueOf(logic.numberOfComment()));
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
    public void setLikeStatus() {
        boolean status = logic.CheckStatusLike();
    if (status) {
        likeStatus = true;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
    } else {
        likeStatus = false;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
    }
}

    /**
     * handleLikeButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Like
     * Controlla lo stato del like (se gia stato messo oppure no)
     * A seconda dello stato, cambia l'immagine del like (cuore)
     */
    public void handleLikeButton(ActionEvent actionEvent) {
        if (likeStatus){
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
            logic.removeLike();
            likeStatus = false;
        } else {
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
            logic.addLike();
            likeStatus = true;
        }
        setLikesNumber();
    }

    /**
     * handleCommentButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Commento
     * Mostra i commenti che sono stati messi a quel post
     */
    public void handleCommentButton(ActionEvent actionEvent) {
        try {
            SwitchScene switchScene = new SwitchScene();
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/CommentSection.fxml");
            switchScene.loadSceneAndShow(actionEvent, loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}