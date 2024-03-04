package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import it.unina.uninaSocialGroup.Model.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public void initialize() {
        likeButton.setOnAction(this::handleLikeButton);
        commentButton.setOnAction(this::handleCommentButton);
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

    public void setLikeStatus(String matricola, String postID) {
    if (postDAO.isLikeAlreadyAdd(matricola, postID)) {
        likeStatus = true;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
    } else {
        likeStatus = false;
        likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
    }
}
    public void setEmail(String userEmail) {
        this.matricola = userDAO.getMatricolaByEmail(userEmail);
    }

    private void handleLikeButton(ActionEvent actionEvent) {
        if (likeStatus){
            //TODO: Animazione
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsNotPressed.png"));
            postDAO.removeLike(matricola, post.getIDPost());
            likeStatus = false;
        } else {
            //TODO: Animazione
            likeButtonImage.setImage(new Image("file:src/main/resources/it/unina/uninaSocialGroup/images/LikeIsPressed.png"));
            postDAO.addLike(matricola, post.getIDPost());
            likeStatus = true;
        }
    }

    private void handleCommentButton(ActionEvent actionEvent) {
        try {
            SwitchScene switchScene = new SwitchScene();
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/CommentSection.fxml");
            switchScene.loadSceneAndShow(actionEvent, loader);
            CommentController commentController = loader.getController();
            commentController.setEmail(this.matricola);
            commentController.setPostID(post.getIDPost());
            commentController.setOriginalPost(post.getCreatorePost(), post.getMessaggioTestuale());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}