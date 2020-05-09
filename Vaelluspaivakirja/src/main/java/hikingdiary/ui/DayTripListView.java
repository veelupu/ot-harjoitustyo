/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.DayTrip;
import hikingdiary.domain.Hike;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class DayTripListView {

    Controller c;
    GraphicalUserInterface ui;
    ListView<Button> dayButtons;
    VBox box;
    DayTrip dtToModify;

    public DayTripListView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
        box = new VBox();
    }
    
    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();
        
        Label title = new Label("Day trips during " + hike.toString());
        title.setPadding(new Insets(10, 10, 10, 10));
        
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title);
        formatDayButtons(hike);
        
        Button add = new Button("Add\nmore day\ntrips!");
        Button modify = new Button("Show\nthe day\ntrip");
        style(add);
        style(modify);
        
        add.setOnAction((event) -> {
            ui.bp.setCenter(new DayTripView(c).getCreationView(hike));
        });
        
        modify.setOnAction((event) -> {
            ui.bp.setCenter(new DayTripView(c).getModifyView(hike, dtToModify));
        });
        
        HBox boxB = new HBox();
        boxB.getChildren().addAll(add, modify);
        boxB.setAlignment(Pos.CENTER);
        box.getChildren().add(boxB);
        
        gp.add(box, 0, 0);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
    private void formatDayButtons(Hike hike) {
        box.getChildren().remove(dayButtons);
        dayButtons = new ListView<>();

        ObservableList<Button> buttons = FXCollections.observableArrayList();
        
        for (DayTrip dt : hike.getDayTrips()) {            
            Button b = new Button(dt.toString());
            
            b.setOnAction((event) -> {
                dtToModify = dt;
            });
            
            buttons.add(b);
        }
        
        dayButtons.setItems(buttons);
        dayButtons.setPrefWidth(250);
        dayButtons.setPrefHeight(290);
        dayButtons.setFixedCellSize(30);
        dayButtons.setStyle("-fx-background-color: transparent;");
        dayButtons.setPadding(Insets.EMPTY);

        box.getChildren().add(dayButtons);
    }
    
    private void style(Button b) {
        b.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 80px; "
                + "-fx-min-height: 80px; "
                + "-fx-max-width: 80px; "
                + "-fx-max-height: 80px;"
        );
    }
}
