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
public class RucksacWView {
    
    Controller c;
    Hike hike;
    boolean beginning;

    public RucksacWView(Controller c, Hike hike, boolean beginning) {
        this.c = c;
        this.hike = hike;
        this.beginning = beginning;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label error = new Label("Oops, that does not seem to be a number!");
        Label done = new Label("Weight changed succesfully!");
        
        VBox box = new VBox();
        gp.add(box, 0, 0);

        if (this.beginning) {
            Label lHike = new Label("In the beginning of\nthe hike " + hike.toString() + "\nmy rucksac weighted:");
            TextField weight = new TextField("" + hike.getRucksackWeightBeg());
            Button ready = new Button("Change!");
            style(ready);
            box.getChildren().addAll(lHike, weight, ready);
            
            ready.setOnAction((event) -> {
                try {
                    double w = Double.valueOf(weight.getText());
                    hike.setRucksackWeightBeg(w);
                    c.updateHike(hike);
                    gp.add(done, 0, 1);
                } catch (Exception e) {
                    gp.add(error, 0, 1);
                }
                
            });
            
        } else {
            Label lHike = new Label("In the end of\nthe hike " + hike.toString() + "\nmy rucksac weighted:");
            TextField weight = new TextField("" + hike.getRucksackWeightEnd());
            Button ready = new Button("Change!");
            style(ready);
            box.getChildren().addAll(lHike, weight, ready);
            
            ready.setOnAction((event) -> {
                try {
                    double w = Double.valueOf(weight.getText());
                    hike.setRucksackWeightEnd(w);
                    c.updateHike(hike);
                    gp.add(done, 0, 1);
                } catch (Exception e) {
                    gp.add(error, 0, 1);
                }
                
            });
        }
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
    private void style(Button b) {
        b.setStyle(
                    "-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 70px; "
                    + "-fx-min-height: 70px; "
                    + "-fx-max-width: 70px; "
                    + "-fx-max-height: 70px;"
            );
    }
}
