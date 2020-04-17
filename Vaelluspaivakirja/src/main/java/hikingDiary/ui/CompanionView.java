/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Companion;
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
public class CompanionView {
    
    Controller c;
    
    public CompanionView(Controller c) {
        this.c = c;
    }
    
    public GridPane getView(Hike hike) {
        GridPane gp = new GridPane();
        
        Label title = new Label("Your company during this hike:");
        Label companion = new Label(hike.formatCompanions());
        Label add = new Label("Add more companions:");
        
        TextField newComp = new TextField();        
        Button ready = new Button("Ready to add a companion!");
        
        VBox box = new VBox();
        box.getChildren().addAll(title, companion, add, newComp, ready);
        
        Label done = new Label("Companion added!");
        ready.setOnAction((event) -> {
                Companion comp = new Companion(newComp.getText());
                if (!c.addCompanion(hike, comp)) {
                    done.setText("This hike has this company already.");
                }
                newComp.clear();
                box.getChildren().add(done);
                companion.setText(hike.formatCompanions());
            });
        
        newComp.setOnMouseClicked((event) -> {
            box.getChildren().remove(done);
            companion.setText(hike.formatCompanions());
        });
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        gp.add(box, 0, 0);
        
        return gp;
    }  
}
