package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CommentDetailsController {
    private Comment comment;
    @FXML
    private Label usernameAuthor;
    @FXML
    private Text commentText;


    public void setComment(Comment comment) {
        this.comment = comment;
        setLabelAuthor(comment.getMatricola());
        setLabelContent(comment.getTesto());
    }

    public void setLabelAuthor(String title) {
        this.usernameAuthor.setText(title);
    }

    public void setLabelContent(String content) {
        this.commentText.setText(content);
    }

}