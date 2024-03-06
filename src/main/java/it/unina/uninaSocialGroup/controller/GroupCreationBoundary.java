package it.unina.uninaSocialGroup.controller;

import it.unina.uninaSocialGroup.DAO.GroupDAO;
import it.unina.uninaSocialGroup.DAO.UserDAO;
import it.unina.uninaSocialGroup.Model.SwitchScene;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class GroupCreationBoundary {
    @FXML
    private Button CreationGroupButton;
    @FXML
    private TextField NameGroupField,CategoryGroupField;
    private @FXML Label NoNameCategory;
    private SwitchScene switchScene = new SwitchScene();
    private String userEmail;
    @FXML
    public void initialize(){
        CreationGroupButton.setOnAction(this::GroupCreation);
        NoNameCategory.setVisible(false);
    }

    /**
     * setUserEmail
     * Funzione che viene chiamata nella HomePage
     * Usata principalmente per ottenere la Email dell'utente dalla HomePage
     */
    public void setUserEmail(String email){
        this.userEmail = email;
    }

    /**
     * GroupCreation
     * Metodo che viene chiamato quando viene cliccato il bottone CREA.
     * Viene creato nel database il nuovo gruppo con i dettagli scritti sull'interfaccia.
     */
    public @FXML void GroupCreation(ActionEvent event){
        //Se non viene scritto nulla nel campo Email o Password, si mostra un messaggio di avvertenza
        if(NameGroupField.getText().isEmpty() || CategoryGroupField.getText().isEmpty()){
            NoNameCategory.setVisible(true);
            return;
        }
        UserDAO user = new UserDAO();
        GroupDAO group = new GroupDAO();
        String matricola = user.getMatricolaByEmail(userEmail);
        group.addNewGroup(NameGroupField.getText(),CategoryGroupField.getText(),matricola);
        try {
            FXMLLoader loader = switchScene.createFXML("/it/unina/uninaSocialGroup/view/HomePage.fxml");
            switchScene.loadSceneAndShow(event, loader);
            //mostra un messaggio di conferma
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Creazione Gruppo");
                alert.setHeaderText(null);
                alert.setContentText("Il nuovo gruppo è stato creato con successo!");
                alert.showAndWait();
            });
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
