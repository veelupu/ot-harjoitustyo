/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.DayTrip;
import hikingdiary.domain.Hike;
import java.time.LocalDate;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class DayTripView {

    Controller c;
    GraphicalUserInterface ui;
    ListView<Button> dayLabels;
    VBox upperBox;
//    VBox lowerBox;

    public DayTripView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
        upperBox = new VBox();
//        lowerBox = new VBox();
    }
    
    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();
        
        Label title = new Label("Day trips during " + hike.toString());
        title.setPadding(new Insets(10, 10, 10, 10));
        
        upperBox.setAlignment(Pos.CENTER);
        upperBox.getChildren().addAll(title);
//        lowerBox.setAlignment(Pos.CENTER);
        formatDayButtons(hike);
        
        Button add = new Button("Add\nmore day\ntrips!");
        style(add);
        
        add.setOnAction((event) -> {
            ui.bp.setCenter(new CreateDayTripView(c).getView(hike));
        });
        
        upperBox.getChildren().add(add);
        
        gp.add(upperBox, 0, 0);
//        gp.add(lowerBox, 0, 0);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
    private void formatDayButtons(Hike hike) {
        upperBox.getChildren().remove(dayLabels);
        dayLabels = new ListView<>();

        ObservableList<Button> buttons = FXCollections.observableArrayList();
        
        for (DayTrip dt : hike.getDayTrips()) {            
            Button b = new Button(dt.toString());
            
            b.setOnAction((event) -> {
                ui.bp.setCenter(new UpdateDayTripView(c).getView(hike, dt));
            });
            
            buttons.add(b);
        }
        
        dayLabels.setItems(buttons);
        dayLabels.setPrefWidth(250);
        dayLabels.setPrefHeight(290);
        dayLabels.setFixedCellSize(30);
        dayLabels.setStyle("-fx-background-color: transparent;");
        dayLabels.setPadding(Insets.EMPTY);

        upperBox.getChildren().add(dayLabels);
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
