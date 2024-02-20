package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.PostDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PostCreationController {
    @FXML
    private Button BackButton, PublishButton;
    @FXML
    private TextArea TextArea;
    @FXML
    private TextField CategoryField;
    @FXML
    private RadioButton NoComments;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail; //NON FUNZIONA ATTENTO !!!! DA RIVEDERE!!!
    private String gruppo; //TROVARE LA MANIERA DI OTTENERE L'ID DEL GRUPPO SU CUI SI PUBBLICA IL POST
    @FXML
    public void initialize(){
        BackButton.setOnAction(this::BackToGroupPage);
        PublishButton.setOnAction(this::GroupCreation);
    }

    public @FXML void GroupCreation(ActionEvent event){
        UserDAO user = new UserDAO();
        PostDAO post = new PostDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        post.CreateNewPost(CategoryField.getText(),TextArea.getText(),matricola,gruppo);
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/GroupPage.fxml");
            switchScene.loadSceneAndShow(event, loader);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void BackToGroupPage(ActionEvent event) {
        try {
            switchScene.switchToScene(event, "/it/unina/uninaSocialGroup/view/GroupPage.fxml", "topToBottom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
