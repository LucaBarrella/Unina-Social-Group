package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.Model.Post;
import it.unina.uninaSocialGroup.DAO.PostDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class GroupController {
    @FXML
    private ListView<VBox> postListView;

    @FXML
    public void initialize() {
        fillListView();
    }

    private VBox loadVBoxFromFXML(Post post) {
        VBox vBox = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unina/uninaSocialGroup/view/postCell.fxml"));
            vBox = loader.load();
            PostListCellController controller = loader.getController();
            controller.setPost(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vBox;
    }

    private void fillListView() {
        ObservableList<VBox> vBoxList = FXCollections.observableArrayList();
        PostDAO postDAO = new PostDAO();

        //L'ID del gruppo deve essere passato da qualche bottone, qualcosa.

        List<Post> posts = postDAO.getAllPosts("1");

        for (Post post : posts) {
            vBoxList.add(loadVBoxFromFXML(post));
        }

        postListView.setItems(vBoxList);
    }
    public void onSearch(javafx.scene.input.KeyEvent event) {
        // your code here
    }
}