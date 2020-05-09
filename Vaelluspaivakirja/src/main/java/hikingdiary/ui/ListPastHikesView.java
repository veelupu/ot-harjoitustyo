/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class ListPastHikesView {

    Controller c;
    GraphicalUserInterface ui;
    GridPane gp;
    BorderPane bp;
    ListView<Button> hikeButtons;

    public ListPastHikesView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
    }
    
    public Parent getView() {
        gp = new GridPane();
        
        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 0);
        
        gp.add(formatHikeButtons(), 0, 1);
        
        Button delete = new Button("Remove\nhike");
        
        delete.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;");
        
        VBox boxRm = new VBox();
        boxRm.getChildren().add(delete);
        boxRm.setAlignment(Pos.CENTER);

        delete.setOnAction((event) -> {
            boxRm.getChildren().remove(delete);
            delete(boxRm);
        });
        
        gp.add(boxRm, 0, 2);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }

    private void delete(VBox boxRm) {
        Label lHikeToRm = new Label("Which hike do you want to remove?");
        TextField hikeToRm = new TextField();
        Button remove = new Button("Remove\nthis hike\npermanently");
        
        remove.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 100px; "
                + "-fx-min-height: 100px; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 100px;");
        
        boxRm.getChildren().addAll(lHikeToRm, hikeToRm, remove);

        Label succ = new Label("Hike removed succesfully!");
        Label unsucc = new Label("Couldn't remove hike. Make sure you wrote the name correctly.");

        remove.setOnAction((event) -> {
            String name = hikeToRm.getText();
            Hike h = new Hike(name);
            if (c.removeHike(h)) {
                boxRm.getChildren().add(succ);
                hikeToRm.clear();
                gp.add(formatHikeButtons(), 0, 2);
            } else {
                boxRm.getChildren().add(unsucc);
            }
        });
    }

    private ListView formatHikeButtons() {
        gp.getChildren().remove(hikeButtons);
        hikeButtons = new ListView<>();

        ObservableList<Button> buttons = FXCollections.observableArrayList();
        
        for (Hike hike : c.listPastHikes()) {            
            Button b = new Button(hike.getName() + "\n" + hike.getYear());
            b.setUserData(hike.getName());

            b.setOnMouseClicked((event) -> {
                String hikeName = (String) b.getUserData();
                ui.bp.setCenter(new HikeView(c.getHike(hikeName), c, ui).getView());
            });
            style(b);
            buttons.add(b);
        }
        
        hikeButtons.setItems(buttons);
        hikeButtons.setPrefWidth(200);
        hikeButtons.setPrefHeight(290);
        hikeButtons.setFixedCellSize(110);
        hikeButtons.setStyle("-fx-background-color: transparent;");
        hikeButtons.setPadding(Insets.EMPTY);
        
        return hikeButtons;
    }

    private void style(Button b) {
        b.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 110px; "
                + "-fx-min-height: 110px; "
                + "-fx-max-width: 110px; "
                + "-fx-max-height: 110px;");
    }

}
