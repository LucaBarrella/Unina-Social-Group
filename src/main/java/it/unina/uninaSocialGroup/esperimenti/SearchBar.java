package it.unina.uninaSocialGroup.esperimenti;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SearchBar extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField searchField = new TextField();
        ListView<HBox> listView = new ListView<>();

        // Aggiungi alcuni gruppi alla listView
        for (int i = 0; i < 10; i++) {
            Button groupButton = new Button("Group " + i);
            Button addButton = new Button("+");
            addButton.setShape(new Circle(15)); // rende il pulsante sferico
            HBox hbox = new HBox(groupButton, addButton);
            hbox.setAlignment(Pos.CENTER);
            listView.getItems().add(hbox);
        }

        // Aggiungi un listener al campo di ricerca per filtrare i gruppi
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            listView.getItems().clear();
            for (int i = 0; i < 10; i++) {
                if (("Group " + i).contains(newValue)) {
                    Button groupButton = new Button("Group " + i);
                    Button addButton = new Button("+");
                    addButton.setShape(new Circle(15)); // rende il pulsante sferico
                    HBox hbox = new HBox(groupButton, addButton);
                    hbox.setAlignment(Pos.CENTER);
                    listView.getItems().add(hbox);
                }
            }
        });

        VBox vbox = new VBox(searchField, listView);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}