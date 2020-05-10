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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author veeralupunen
 */
public class CreateHikeView {

    Controller c;
    boolean upcoming;
    Hike hike;

    public CreateHikeView(Controller c) {
        this.c = c;
    }

    public Parent getView() {
        GridPane gp = new GridPane();
        VBox bigBox = new VBox();

        Label lName = new Label("Name of the hike:");
        TextField tfName = new TextField();

        Label lYear = new Label("Year of the hike:");
        TextField tfYear = new TextField();

        Label lUpcoming = new Label("Past or\nupcoming\nhike?");
        lUpcoming.setPadding(new Insets(0, 5, 0, 0));
        
        HBox middleBox = new HBox();
        Button bPast = new Button("Past");
        Button bUpcoming = new Button("Upcoming");
        
        style(bPast);
        style(bUpcoming);
        
        middleBox.getChildren().addAll(lUpcoming, bPast, bUpcoming);
        middleBox.setPadding(new Insets(5, 5, 5, 5));
        middleBox.setAlignment(Pos.CENTER);

        bPast.setOnAction((event) -> {
            upcomingFalse();
        });
        
        bUpcoming.setOnAction((event) -> {
            upcomingTrue();
        });
        
        Label rucksacBeg = new Label("How much your rucksac weighted in the beginning?");
        TextField rucksacWBeg = new TextField();

        Label rucksacEnd = new Label("How much your rucksac weighted in the end?");
        TextField rucksacWEnd = new TextField();

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(1);
        gp.setHgap(1);

        Button bReady = new Button("Create\nnew\nhike!");
        style(bReady);
        
        VBox messageBox = new VBox();
        messageBox.getChildren().add(bReady);
        messageBox.setPadding(new Insets(5, 5, 5, 5));
        messageBox.setAlignment(Pos.CENTER);
        
        Label done = new Label("New hike created succesfully!");
        Label exists = new Label("There already is a hike with that name.\nTry again with different name!");
        Label error = new Label("Oops, something went wrong.\nDid you fill all the textfields correctly?");
        
        BackgroundFill bgF = new BackgroundFill(Color.WHITE, new CornerRadii(1), null);
        done.setBackground(new Background(bgF));
        exists.setBackground(new Background(bgF));
        error.setBackground(new Background(bgF));

        bReady.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
            try {
                int year = Integer.parseInt(tfYear.getText());
                String name = tfName.getText();
                hike = c.createNewHike(name, Integer.valueOf(tfYear.getText()), this.upcoming);
                
                if (hike == null) {
                    messageBox.getChildren().add(exists);
                    return;
                }
                
                if (rucksacWBeg.getLength() != 0) {
                    hike.setRucksackWeightBeg(Double.parseDouble(rucksacWBeg.getText()));
                    if (!c.updateHike(hike)) {
                        messageBox.getChildren().add(error);
                        return;
                    }
                }
                
                if (rucksacWEnd.getLength() != 0) {
                    hike.setRucksackWeightEnd(Double.parseDouble(rucksacWEnd.getText()));
                    if (!c.updateHike(hike)) {
                        messageBox.getChildren().add(error);
                        return;
                    }
                }

                rucksacWBeg.clear();
                rucksacWEnd.clear();
                tfName.clear();
                tfYear.clear();
                
            } catch (Exception e1) {
                messageBox.getChildren().add(error);
                c.removeHike(hike);
                return;
            }
            messageBox.getChildren().add(done);
        });
        
        tfName.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });
        
        tfYear.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });
        
        rucksacWBeg.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });
        
        rucksacWEnd.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });

        bPast.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });
        
        bUpcoming.setOnMouseClicked((event) -> {
            messageBox.getChildren().removeAll(done, error, exists);
        });
        
        bigBox.getChildren().addAll(lName, tfName, lYear, tfYear, middleBox, rucksacBeg, rucksacWBeg, rucksacEnd, rucksacWEnd, messageBox);
        gp.add(bigBox, 0, 0);
        
        return gp;
    }

    private void upcomingFalse() {
        this.upcoming = false;
    }

    private void upcomingTrue() {
        this.upcoming = true;
    }

    private void style(Button b) {
        b.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 85px; "
                + "-fx-min-height: 85px; "
                + "-fx-max-width: 85px; "
                + "-fx-max-height: 85px;");
    }
}
