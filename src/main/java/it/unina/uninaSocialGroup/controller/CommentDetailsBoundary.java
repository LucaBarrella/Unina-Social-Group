package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CommentDetailsBoundary {
    private Comment comment;
    @FXML
    private Label usernameAuthor;
    @FXML
    private Text commentText;
    private static LogicalController logic = new LogicalController();

    /**
     * setComment
     * Metodo che mostra i dettagli del post sul quale è stato scritto il commento,
     * ovvero l'autore e il contenuto del post
     */
    public void setComment(Comment comment) {
        this.comment = comment;
        logic.setComment(comment);
        setLabelAuthor();
        setLabelContent();
    }

    /**
     * setLabelAuthor
     * Metodo che mostra l'autore del commento
     */
    public void setLabelAuthor() {
        logic.setComment(comment);
        this.usernameAuthor.setText(logic.getAuthorOfComment());
    }

    /**
     * setLabelContent
     * Metodo che mostra il contenuto del commento
     */
    public void setLabelContent() {
        logic.setComment(comment);
        this.commentText.setText(logic.getCommentContent());
    }
}