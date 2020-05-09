/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */

public class LocationView {
    
    Controller c;
    
    public LocationView(Controller c) {
        this.c = c;
    }
    
    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();
        
        Label title = new Label("Location of " + hike.toString());
        title.setPadding(new Insets(5, 5, 5, 5));
        Label lStart = new Label("Where " + hike.toString() + " begings from?");
        lStart.setPadding(new Insets(5, 5, 5, 5));
        TextField tfStart = new TextField(hike.getLocationStart());
        tfStart.setPadding(new Insets(5, 5, 5, 5));
        
        Label lEnd = new Label("Where " + hike.toString() + " ends to?");
        lEnd.setPadding(new Insets(5, 5, 5, 5));
        TextField tfEnd = new TextField(hike.getLocationEnd());
        tfEnd.setPadding(new Insets(5, 5, 5, 5));
        
        Button ready = new Button("Ready!");
        ready.setPadding(new Insets(5, 5, 5, 5));
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title, lStart, tfStart, lEnd, tfEnd, ready);
        
        ready.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;"
        );
        
        Label done = new Label("Location added!");
        
        ready.setOnAction((event) -> {
            hike.setLocationStart(tfStart.getText());
            hike.setLocationEnd(tfEnd.getText());
            c.updateHike(hike);
            box.getChildren().add(done);
        });
        
        tfStart.setOnAction((event) -> {
            box.getChildren().remove(done);
        });

        gp.add(box, 0, 0);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }

}
