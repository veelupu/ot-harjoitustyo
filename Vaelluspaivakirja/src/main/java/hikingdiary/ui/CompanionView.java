/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

        Label title = new Label("Your company during " + hike.toString() + ":");
        Label companion = new Label(hike.formatCompanions());
        Label empty = new Label();
        Label add = new Label("Add more companions:");

        TextField newComp = new TextField();
        Button ready = new Button("Ready to add a companion!");

        Button delete = new Button("Remove companion");
        VBox boxRm = new VBox();
        Label lCompToRm = new Label("Who you want to remove from this hike?");
        TextField compToRm = new TextField();
        Button remove = new Button("Delete");
        boxRm.getChildren().addAll(lCompToRm, compToRm, remove);
        
        delete.setOnAction((event) -> {
            gp.add(boxRm, 0, 1);
        });
        
        Label succ = new Label("Companion removed succesfully!");
        Label unsucc = new Label("Couldn't remove companion. Make sure you wrote the name correctly.");
        
        remove.setOnAction((event) -> {
            String name = compToRm.getText();
            if (c.removeCompanion(hike, name)) {
                boxRm.getChildren().add(succ);
                compToRm.clear();
                companion.setText(hike.formatCompanions());
            } else {
                boxRm.getChildren().add(unsucc);
            }
        });
        
        VBox box = new VBox();
        box.getChildren().addAll(title, companion, empty, add, newComp, ready, delete);

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
        });

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        gp.add(box, 0, 0);

        return gp;
    }
    
}
