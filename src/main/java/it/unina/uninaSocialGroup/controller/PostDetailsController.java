package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PostDetailsController {
    @FXML
    private Label UserName, PostMessage, PostCategory;
    public String IDPost;

    @FXML
    public void initialize(){
         displayPostDetails();
    }

    public void setIDPost(String IDPost) {
        this.IDPost = IDPost;
    }

    public void displayPostDetails(){
        PostDAO postDAO = new PostDAO();
        Post post = postDAO.getPost(IDPost);
        PostMessage.setText(post.getMessaggioTestuale());
        PostCategory.setText(post.getCategoria());
    }
}
