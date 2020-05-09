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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author veeralupunen
 */
public class CompanionView {

    Controller c;
    boolean visible;

    public CompanionView(Controller c) {
        this.c = c;
        this.visible = false;
    }

    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();
        
        Label title = new Label("Your company during " + hike.toString() + ":");
        Label companion = new Label(hike.formatCompanions());
        Label empty = new Label();
        Label add = new Label("Add more companions:");

        TextField newComp = new TextField();
        Button ready = new Button("Ready\nto add!");
        
        ready.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 80px; "
                + "-fx-min-height: 80px; "
                + "-fx-max-width: 80px; "
                + "-fx-max-height: 80px;");

        Button remove = new Button("Remove\ncompanion");
        
        remove.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 80px; "
                + "-fx-min-height: 80px; "
                + "-fx-max-width: 80px; "
                + "-fx-max-height: 80px;");
        
        VBox boxRm = new VBox();
        Label lCompToRm = new Label("Who you want to remove from this hike?");
        lCompToRm.setPadding(new Insets(5, 5, 5, 5));
        
        TextField compToRm = new TextField();
        Button delete = new Button("Delete");
        
        delete.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;");
        
        boxRm.getChildren().addAll(lCompToRm, compToRm, delete);
        boxRm.setAlignment(Pos.CENTER);
        
        remove.setOnAction((event) -> {
            if (visible) {
                gp.getChildren().remove(boxRm);
                visible = false;
            } else {
                gp.add(boxRm, 0, 1);
                visible = true;
            }
        });
        
        Label succ = new Label("Companion removed succesfully!");
        BackgroundFill bgF = new BackgroundFill(Color.WHITE, new CornerRadii(1), null);
        succ.setBackground(new Background(bgF));
        
        Label unsucc = new Label("Couldn't remove companion. Make sure you wrote the name correctly.");
        unsucc.setBackground(new Background(bgF));
        
        delete.setOnAction((event) -> {
            String name = compToRm.getText();
            if (name.length() < 1) {
                return;
            }
            if (c.removeCompanion(hike, name)) {
                boxRm.getChildren().add(succ);
                compToRm.clear();
                companion.setText(hike.formatCompanions());
            } else {
                boxRm.getChildren().add(unsucc);
            }
        });
        
        compToRm.setOnMouseClicked((event) -> {
            boxRm.getChildren().remove(succ);
            boxRm.getChildren().remove(unsucc);
        });
        
        VBox box = new VBox();
        box.getChildren().addAll(title, companion, empty, add, newComp, ready, remove);
        box.setAlignment(Pos.CENTER);
        
        title.setPadding(new Insets(5, 5, 5, 5));
        companion.setPadding(new Insets(5, 5, 5, 5));
        add.setPadding(new Insets(5, 5, 5, 5));
        newComp.setPadding(new Insets(5, 5, 5, 5));
        ready.setPadding(new Insets(5, 5, 5, 5));
        remove.setPadding(new Insets(5, 5, 5, 5));
        
        Label done = new Label("Companion added!");
        ready.setOnAction((event) -> {
            if (newComp.getText().length() < 1) {
                return;
            }
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
