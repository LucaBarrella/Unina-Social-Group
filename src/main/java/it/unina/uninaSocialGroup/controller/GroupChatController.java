package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.Group;
import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class GroupChatController {
    @FXML
    private ListView<VBox> postListView;
    @FXML
    private Label groupName, NumberOfMembers;
    @FXML
    private Button PostButton;
    @FXML
    private TextArea PostTextArea;
    @FXML
    private HBox HBoxPost;
    private static String userEmail;
    private static String groupId;

    @FXML
    public void initialize() {
        HBoxPost.setVisible(false);
        PostButton.setOnAction(event -> HBoxPost.setVisible(true));
        fillListView();
        displayData();
        PostTextArea.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    CreatePost();
                    event.consume();
                    break;
                default:
                    break;
            }
        });
    }

    public void CreatePost(){
        String text = PostTextArea.getText();
        if (text != null && !text.trim().isEmpty()) {
            GroupDAO group = new GroupDAO();
            PostDAO postDAO = new PostDAO();
            UserDAO user = new UserDAO();
            String category = group.getGroup(groupId).getCategoriaGruppo();
            String matricola = user.getMatricolaByEmail(userEmail);
            postDAO.CreateNewPost(category,text,matricola,groupId);
            PostTextArea.clear();
            fillListView();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Post Pubblicato");
                alert.setHeaderText(null);
                alert.setContentText("Il tuo post è stato pubblicato!");
                alert.showAndWait();
            });
        }
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setGroupID(String groupId) {
        this.groupId = groupId;
    }

    public void displayData(){
        GroupDAO groupDAO = new GroupDAO();
        Group result = groupDAO.getGroup(groupId);
        String name = result.getNomeGruppo();
        groupName.setText(name);
        NumberOfMembers.setText(String.valueOf(groupDAO.getNumberOfMemberGroup(groupId)));
    }

    private VBox loadVBoxFromFXML(Post post) {
        VBox vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/PostDetailsPage.fxml"));
            vBox = loader.load();
            PostDetailsController controller = loader.getController();
            controller.setPost(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    private void fillListView() {
        ObservableList<VBox> vBoxList = FXCollections.observableArrayList();
        PostDAO postDAO = new PostDAO();

        List<Post> posts = postDAO.getAllPosts(groupId);

        for (Post post : posts) {
            vBoxList.add(loadVBoxFromFXML(post));
        }

        postListView.setItems(vBoxList);
    }
    public void onSearch(javafx.scene.input.KeyEvent event) {
        // your code here
    }

}