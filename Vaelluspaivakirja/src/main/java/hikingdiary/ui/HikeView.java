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
import javafx.scene.layout.GridPane;

/**
 *
 * @author veeralupunen
 */
public class HikeView {

    Hike hike;
    Controller c;
    GraphicalUserInterface ui;

    public HikeView(Hike hike, Controller c, GraphicalUserInterface ui) {
        this.hike = hike;
        this.c = c;
        this.ui = ui;
    }

    public Parent getView() {
        GridPane gp = new GridPane();
//        gp.setGridLinesVisible(true);

        Label lName = new Label(hike.toString());
        Button bLocation = new Button(getOrDefault(hike.getLocation(), "Add location"));
        Button bCompanion = new Button(("Companion"));
        Button bMeals = new Button("Meal\nlist");
        Button bDayTrips = new Button("Day trips");
        Button bJourney = new Button("" + getOrDefault(hike.getKilometres(), "Add day trips"));
        Button bEquipment = new Button("Equipment\nlist");
        Button bRucksacBeg = new Button("Rucksac\nin the\nbeginning\n" + getOrDefault(hike.getRucksackWeightBeg(), "?"));
        Button bRucksacEnd = new Button("Rucksac\nin the end\n" + getOrDefault(hike.getRucksackWeightEnd(), "?"));

        bCompanion.setOnAction((event) -> {
            ui.bp.setCenter(new CompanionView(c).getView(hike));
        });
        
        bEquipment.setOnAction((event) -> {
            ui.bp.setCenter(new EquipmentView(c).getView(hike));
        });
        
        bMeals.setOnAction((event) -> {
            ui.bp.setCenter(new MealListView(c, ui).getView(hike));
        });
        
        bRucksacBeg.setOnAction((event) -> {
            ui.bp.setCenter(new RucksacWView(c, hike, true).getView());
        });
        
        bRucksacEnd.setOnAction((event) -> {
            ui.bp.setCenter(new RucksacWView(c, hike, false).getView());
        });
        
        bDayTrips.setOnAction((event) -> {
            ui.bp.setCenter(new DayTripView(c, ui).getView(hike));
        });
        
        gp.add(lName, 0, 0);
        gp.add(bLocation, 1, 0);
        gp.add(bCompanion, 2, 0);
        gp.add(bMeals, 0, 1);
        gp.add(bDayTrips, 1, 1);
        gp.add(bJourney, 2, 1);
        gp.add(bEquipment, 0, 2);
        gp.add(bRucksacBeg, 1, 2);
        gp.add(bRucksacEnd, 2, 2);

        Button[] buttons = new Button[]{bLocation, bMeals, bDayTrips, bJourney, bEquipment, bRucksacBeg, bRucksacEnd};
        for (Button b : buttons) {
            b.setStyle(
                    "-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 100px; "
                    + "-fx-min-height: 100px; "
                    + "-fx-max-width: 100px; "
                    + "-fx-max-height: 100px;"
            );
        }

        bCompanion.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 100px; "
                + "-fx-min-height: 100px; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 100px;");

        //Miten ihmeessä tämän näkymän saisi oikean kokoiseksi heti kättelyssä!?
        //gp.setPrefSize(1200, 600);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        return gp;
    }

    public static <T> T getOrDefault(T value, T defaultV) {
        return value == null ? defaultV : value;
    }
}
