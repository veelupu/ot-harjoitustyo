/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class CreateHikeView {

    Controller c;
    boolean upcoming;

    public CreateHikeView(Controller c) {
        this.c = c;
    }

    public Parent getView() {
        GridPane gp = new GridPane();
        VBox boxV = new VBox();

        Label lName = new Label("Name of the hike:");
        TextField tfName = new TextField();
//        gp.add(lName, 0, 0);
//        gp.add(tfName, 0, 1);

        Label lYear = new Label("Year of the hike:");
        TextField tfYear = new TextField();
//        gp.add(lYear, 0, 2);
//        gp.add(tfYear, 0, 3);

        Label lUpcoming = new Label("Past or\nupcoming\nhike?");
        lUpcoming.setPadding(new Insets(0, 5, 0, 0));
        
        HBox twoButtons = new HBox();
        Button bPast = new Button("Past");
        Button bUpcoming = new Button("Upcoming");
        
        bPast.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 81px; "
                + "-fx-min-height: 81px; "
                + "-fx-max-width: 81px; "
                + "-fx-max-height: 81px;");
        
        bUpcoming.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 81px; "
                + "-fx-min-height: 81px; "
                + "-fx-max-width: 81px; "
                + "-fx-max-height: 81px;");
        
        twoButtons.getChildren().addAll(lUpcoming, bPast, bUpcoming);
        twoButtons.setPadding(new Insets(5, 5, 5, 5));
        twoButtons.setAlignment(Pos.CENTER);
        //gp.add(lUpcoming, 0, 4);
//        gp.add(twoButtons, 0, 4);
        
//        gp.add(bPast, 0, 5);
//        gp.add(bUpcoming, 0, 5);

        bPast.setOnAction((event) -> {
            upcomingFalse();
        });
        bUpcoming.setOnAction((event) -> {
            upcomingTrue();
        });
        
        Label rucksacBeg = new Label("How much your rucksac weighted in the beginning?");
        TextField rucksacWBeg = new TextField();
//        gp.add(rucksacBeg, 0, 7);
//        gp.add(rucksacWBeg, 0, 8);
        Label rucksacEnd = new Label("How much your rucksac weighted in the end?");
        TextField rucksacWEnd = new TextField();
//        gp.add(rucksacEnd, 0, 9);
//        gp.add(rucksacWEnd, 0, 10);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(1);
        gp.setHgap(1);
        //gp.setPadding(new Insets(5, 5, 5, 5));

        VBox boxR = new VBox();
        Button bReady = new Button("Create\nnew\nhike!");
        boxR.getChildren().add(bReady);
        boxR.setPadding(new Insets(5, 5, 5, 5));
        boxR.setAlignment(Pos.CENTER);
        
        bReady.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 85px; "
                + "-fx-min-height: 85px; "
                + "-fx-max-width: 85px; "
                + "-fx-max-height: 85px;");
        
        Label done = new Label("New hike created succesfully!");
        Label error = new Label("Oops, year should be an integer.\nTry again!");
//        gp.add(bReady, 0, 11);
        bReady.setOnMouseClicked((event) -> {
            try {
                int year = Integer.parseInt(tfYear.getText());
                String name = tfName.getText();
                Hike hike = c.createNewHike(name, Integer.valueOf(tfYear.getText()), this.upcoming);
                
                if (rucksacWBeg.getLength() != 0) {
                    hike.setRucksackWeightBeg(Double.parseDouble(rucksacWBeg.getText()));
                    c.updateHike(hike);
                }
                
                if (rucksacWEnd.getLength() != 0) {
                    hike.setRucksackWeightEnd(Double.parseDouble(rucksacWEnd.getText()));
                    c.updateHike(hike);
                }

                rucksacWBeg.clear();
                rucksacWEnd.clear();
                tfName.clear();
                tfYear.clear();
                
            } catch (Exception e) {
                boxR.getChildren().add(error);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Hike creation failed: " + e.getMessage());
            }
            boxR.getChildren().add(done);
        });
        
        tfName.setOnAction((event) -> {
            boxR.getChildren().remove(done);
            boxR.getChildren().remove(error);
        });

        boxV.getChildren().addAll(lName, tfName, lYear, tfYear, twoButtons, rucksacBeg, rucksacWBeg, rucksacEnd, rucksacWEnd, boxR);
        gp.add(boxV, 0, 0);
        
        return gp;
    }

    private void upcomingFalse() {
        this.upcoming = false;
    }

    private void upcomingTrue() {
        this.upcoming = true;
    }

}
