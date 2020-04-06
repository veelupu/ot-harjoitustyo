/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

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
    
    public HikeView(Hike hike) {
        this.hike = hike;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label lName = new Label(hike.toString());
        Button bLocation = new Button(getOrDefault(hike.getLocation(), "Add location"));
        Button bCompanion = new Button(getOrDefault(hike.getCompanion(), "Add companion"));
        Button bMeals = new Button("Meal\nlist");
        Button bDayTrips = new Button("Day trips");
        Button bJourney = new Button(getOrDefault(hike.getKilometres(), "Add day trips"));
        Button bEquipment = new Button("Equipment\nlist");
        Button bRucksacStart = new Button("Rucksac\nin the beginning\n" + getOrDefault(hike.getRucksackWeightStart(), "?"));
        Button bRucksacEnd = new Button("Rucksac\nin the beginning\n" + getOrDefault(hike.getRucksackWeightEnd(), "?"));
        
        gp.add(lName, 0, 1);
        gp.add(bLocation, 1, 1);
        gp.add(bCompanion, 2, 1);
        gp.add(bMeals, 0, 2);
        gp.add(bDayTrips, 1, 2);
        gp.add(bJourney, 2, 2);
        gp.add(bEquipment, 0, 3);
        gp.add(bRucksacStart, 1, 3);
        gp.add(bRucksacEnd, 2, 3);
        
        Button[] buttons = new Button[]{bLocation, bMeals, bDayTrips, bJourney, bEquipment, bRucksacStart, bRucksacEnd};
        for (Button b: buttons) {
            b.setStyle(
                    "-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 120px; "
                    + "-fx-min-height: 120px; "
                    + "-fx-max-width: 120px; "
                    + "-fx-max-height: 120px;"
            );
        }
        
        bCompanion.setStyle(
        "-fx-text-alignment: center;" +
                "-fx-background-radius: 5em; " +
                "-fx-min-width: 100px; " +
                "-fx-min-height: 160px; " +
                "-fx-max-width: 100px; " +
                "-fx-max-height: 160px;");
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
    public static <T> T getOrDefault(T value, T defaultV) {
        return value == null ? defaultV : value;
    }
}
