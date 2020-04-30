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
import javafx.scene.layout.GridPane;

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

        Label lName = new Label("Name of the hike:");
        TextField tfName = new TextField();
        gp.add(lName, 0, 1);
        gp.add(tfName, 0, 2);

        Label lYear = new Label("Year of the hike:");
        TextField tfYear = new TextField();
        gp.add(lYear, 0, 3);
        gp.add(tfYear, 0, 4);

        Label lUpcoming = new Label("Past or upcoming hike?");
        Button bPast = new Button("Past");
        Button bUpcoming = new Button("Upcoming");
        gp.add(lUpcoming, 0, 5);
        gp.add(bPast, 0, 6);
        gp.add(bUpcoming, 0, 7);

        bPast.setOnAction((event) -> {
            upcomingFalse();
        });
        bUpcoming.setOnAction((event) -> {
            upcomingTrue();
        });
        
        Label rucksacBeg = new Label("How much your rucksac weighted in the beginning?");
        TextField rucksacWBeg = new TextField();
        gp.add(rucksacBeg, 0, 8);
        gp.add(rucksacWBeg, 0, 9);
        Label rucksacEnd = new Label("How much your rucksac weighted in the end?");
        TextField rucksacWEnd = new TextField();
        gp.add(rucksacEnd, 0, 10);
        gp.add(rucksacWEnd, 0, 11);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));

        Button bReady = new Button("Ready to create a new hike!");
        gp.add(bReady, 0, 12);
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
                gp.add(new Label("New hike created succesfully!"), 0, 13);
            } catch (Exception e) {
                gp.add(new Label("Oops, year should be an integer.\nTry again!"), 0, 14);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Hike creation failed: " + e.getMessage());
            }
        });

        return gp;
    }

    private void upcomingFalse() {
        this.upcoming = false;
    }

    private void upcomingTrue() {
        this.upcoming = true;
    }

}
