package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.CommentDAO;
import it.unina.uninaSocialGroup.Model.Comment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.List;

public class CommentController {

    private @FXML ListView<VBox> commentListView;
    private @FXML Button commentButton;
    private @FXML TextArea CommentTextArea;
    private @FXML Label usernameAuthor;
    private @FXML Text postText;
    private  @FXML HBox HBoxComment;
    private static String postID;
    private String matricola;

    @FXML
    public void initialize() {
        HBoxComment.setVisible(false);
        commentButton.setOnAction(event -> HBoxComment.setVisible(true));
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

    public void CreateComment(){
        String text = CommentTextArea.getText();
        if (text != null && !text.trim().isEmpty()) {
            CommentDAO commentDAO = new CommentDAO();
            commentDAO.createNewComment(text, matricola, postID);
            CommentTextArea.clear();
            fillListView();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Commento Pubblicato");
                alert.setHeaderText(null);
                alert.setContentText("Il tuo commento è stato pubblicato!");
                alert.showAndWait();
            });
        }
    }

    public void setOriginalPost(String creatorePost, String messaggioTestuale) {
        this.usernameAuthor.setText(creatorePost);
        this.postText.setText(messaggioTestuale);
    }
        public void setPostID(String postId) {
        this.postID = postId;
        fillListView();
    }

    public void setEmail(String email) {
        this.matricola = email;
    }

    private VBox loadVBoxFromFXML(Comment comment) {
        VBox vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/CommentDetailes.fxml"));
            vBox = loader.load();
            CommentDetailsController controller = loader.getController();
            controller.setComment(comment);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    private void fillListView() {
        ObservableList<VBox> vBoxList = FXCollections.observableArrayList();
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> comments = commentDAO.getCommentByPost(postID);

        for (Comment comment : comments) {
            vBoxList.add(loadVBoxFromFXML(comment));
        }

        commentListView.setItems(vBoxList);
    }
    //TODO: Aggiungere bottone per tornare indietro
}