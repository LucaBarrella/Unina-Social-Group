package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

public class PostDetailsController extends ListCell<Post> {
    private Post post;
    //private LikeDAO likeDAO;
    private @FXML Label usernameAuthor;
    private @FXML Text postText;
    private @FXML Button likeButton, commentButton;

    public void setPost(Post post) {
        this.post = post;
        setLabelAuthor(post.getCreatorePost());
        setLabelContent(post.getMessaggioTestuale());
    }


    public void setLabelAuthor(String title) {
        this.usernameAuthor.setText(title);
    }

    public void setLabelContent(String content) {
        this.postText.setText(content);
    }

}