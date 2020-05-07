/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author veeralupunen
 */
public class ListPastHikesView {

    Controller c;
    GraphicalUserInterface ui;
    GridPane gp;
    BorderPane bp;
//    ArrayList<Button> buttons;
    ListView<Button> hikeButtons;

    public ListPastHikesView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
//        this.buttons = new ArrayList<>();
    }
    
    public Parent getView() {
        gp = new GridPane();
        
        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 0);
        
        gp.add(formatHikeButtons(), 0, 1);
        
        //poistaminen
        Button deleteButton = new Button("Remove\nhike");
        
        deleteButton.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;");
        
        VBox boxRm = new VBox();
        boxRm.getChildren().add(deleteButton);
        boxRm.setAlignment(Pos.CENTER);

        deleteButton.setOnAction((event) -> {
            boxRm.getChildren().remove(deleteButton);
            //gp.add(boxRm, 0, 2);
            delete(boxRm);
        });
        
//        deleteButton.setOnAction((event) -> {
//            boxRm.getChildren().remove(deleteButton);
//            //gp.add(boxRm, 0, 3);
//            delete(boxRm);
//        });
        
        gp.add(boxRm, 0, 2);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;

        /*
        //this.bp = new BorderPane();
        this.gp = new GridPane();
        gp.setGridLinesVisible(true);
        
        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 0);

        Label lName2 = new Label("Past Hikes 2");
        gp.add(lName2, 0, 1);
        
        return gp;
        */

        /*
        Label lName = new Label("Past Hikes");
        //gp.add(lName, 0, 0);

        VBox buttonsBox = new VBox();
        buttonsBox.getChildren().addAll(lName, formatHikeButtons());
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPrefWidth(100);
        buttonsBox.setPrefHeight(300);
        
        //bp.setCenter(buttonsBox);
        gp.add(buttonsBox, 0, 0);

        //poistaminen
        Button deleteButton = new Button("Remove hike");
        VBox boxRm = new VBox();

        deleteButton.setOnAction((event) -> {
            gp.getChildren().remove(deleteButton);
            gp.add(boxRm, 0, 3);
            delete(boxRm);
        });

//        gp.add(deleteButton, 0, 3);

//        bp.setPrefSize(300, 450);
//        gp.setPrefSize(100, 200);
//        
//        gp.setAlignment(Pos.CENTER);
//        gp.setVgap(10);
//        gp.setHgap(10);
//        gp.setPadding(new Insets(5, 5, 5, 5));
//
//        bp.setCenter(gp);
//        
//        BackgroundFill bgF = new BackgroundFill(Color.YELLOW, new CornerRadii(1), null);
//        bp.setBackground(new Background(bgF));
//        
//        BackgroundFill bgF2 = new BackgroundFill(Color.GREEN, new CornerRadii(1), null);
//        gp.setBackground(new Background(bgF2));
        
        return gp;
        */
    }

//    public Parent getView() {
//        this.gp = new GridPane();
//
//        Label lName = new Label("Past Hikes");
//        gp.add(lName, 0, 1);
//
//        gp.add(formatHikeButtons(), 0, 2);
//
//        //poistaminen
//        Button deleteButton = new Button("Remove hike");
//        VBox boxRm = new VBox();
//
//        deleteButton.setOnAction((event) -> {
//            gp.getChildren().remove(deleteButton);
//            gp.add(boxRm, 0, 3);
//            delete(boxRm);
//        });
//
//        gp.add(deleteButton, 0, 3);
//
//        gp.setAlignment(Pos.CENTER);
//        gp.setVgap(10);
//        gp.setHgap(10);
//        gp.setPadding(new Insets(5, 5, 5, 5));
//
//        return gp;
//    }

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
//        buttons.clear();
        gp.getChildren().remove(hikeButtons);
        hikeButtons = new ListView<>();
        
//        int i = 1;
//        int j = 0;
//        int max = c.listPastHikes().size() / 2;

        ObservableList<Button> buttons = FXCollections.observableArrayList();
        
        for (Hike hike : c.listPastHikes()) {
//            if (i > max) {
//                i = 0;
//                j++;
//            }
            
            Button b = new Button(hike.getName() + "\n" + hike.getYear());
            b.setUserData(hike.getName());

            b.setOnMouseClicked((event) -> {
                String hikeName = (String) b.getUserData();
                ui.bp.setCenter(new HikeView(c.getHike(hikeName), c, ui).getView());
            });

            style(b);
//            buttons.add(b);
            buttons.add(b);
//            i++;
            //hikeButtons.getChildren().add(b);
        }
        
        hikeButtons.setItems(buttons);
        hikeButtons.setPrefWidth(200);
        hikeButtons.setPrefHeight(290);
        hikeButtons.setFixedCellSize(110);
        hikeButtons.setStyle("-fx-background-color: transparent;");
        hikeButtons.setPadding(Insets.EMPTY);
//        hikeButtons.setOrientation(Orientation.HORIZONTAL);

//        hikeButtons.setPrefSize(600, 600);
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
