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

    /**
     * setComment
     * Metodo che mostra i dettagli del post sul quale è stato scritto il commento,
     * ovvero l'autore e il contenuto del post
     */
    public void setComment(Comment comment) {
        this.comment = comment;
        setLabelAuthor(comment.getAutoreCommento());
        setLabelContent(comment.getTesto());
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
     * Metodo che mostra il contenuto del post
     */
    public void setLabelContent(String content) {
        this.commentText.setText(content);
    }

}