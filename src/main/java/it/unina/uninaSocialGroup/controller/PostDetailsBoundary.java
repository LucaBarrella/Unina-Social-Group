package it.unina.uninaSocialGroup.controller;

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
    private static LogicalController logic = new LogicalController();

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
        logic.setPost(this.post);
        setLabelAuthor();
        setLabelContent();
        setLikeStatus();
        setLikesNumber();
        setCommentsNumber();
    }

    private void setLikesNumber() {
        //logic.setPost(post);
        likeCounter.setText(logic.getNumberOfLike(post.getIDPost()));
    }

    private void setCommentsNumber() {
        commentCounter.setText(logic.getNumberOfComments(post.getIDPost()));
    }

    /**
     * setLabelAuthor
     * Metodo che mostra l'autore del post
     */
    public void setLabelAuthor() {
        logic.setPost(post);
        usernameAuthor.setText(logic.getAuthor());
    }

    /**
     * setLabelContent
     * Metodo che mostra il messaggio scritto del post
     */
    public void setLabelContent() {
        postText.setText(logic.getPostContent());
    }

    /**
     * setLikeStatus
     * Metodo che modifica lo stato del like a seconda se è gia stato messo oppure no
     */
    public void setLikeStatus() {
        logic.setPost(post);
        logic.isLikeAlreadyAdd();
    }

    /**
     * handleLikeButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Like
     * Controlla lo stato del like (se gia stato messo oppure no)
     * A seconda dello stato, cambia l'immagine del like (cuore)
     */
    public void handleLikeButton(ActionEvent actionEvent) {
        logic.setPost(post);
        if (logic.isLikeAlreadyAdd()){
            System.out.println("Like removed");
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
            logic.removeLike();
        } else {
            System.out.println("Like added");
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
            logic.addLike();
        }
        setLikesNumber();
    }

    /**
     * handleCommentButton
     * Metodo che viene chiamato quando viene cliccato il bottone del Commento
     * Mostra i commenti che sono stati messi a quel post
     */
    public void handleCommentButton(ActionEvent actionEvent) {
        logic.setPost(post);
        try {
            SwitchScene switchScene = new SwitchScene();
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/CommentSection.fxml");
            switchScene.loadSceneAndShow(actionEvent, loader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}