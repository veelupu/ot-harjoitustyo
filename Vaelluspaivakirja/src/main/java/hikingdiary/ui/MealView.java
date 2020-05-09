/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Meal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class MealView {
    
    Controller c;
    Meal meal;
    Hike hike;
    
    public MealView(Controller c, Meal meal, Hike hike) {
        this.c = c;
        this.meal = meal;
        this.hike = hike;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label name = new Label(meal.getName() + " (" + meal.getCategoryName() + ")");
        Label empty = new Label("");
        
        StringBuilder ingredients = new StringBuilder();
        for (String i : meal.getIngredients()) {
            ingredients.append(i + "\n");
        }
        Label ingr = new Label(ingredients.toString());
        
        Button remove = new Button("Remove\nthis meal from\n" + hike.toString());
        remove.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 100px; "
                + "-fx-min-height: 100px; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 100px;");
        
        VBox box = new VBox();
        box.getChildren().addAll(name, empty, ingr, remove);
        box.setAlignment(Pos.CENTER);
        remove.setPadding(new Insets(5, 5, 5, 5));
        ingr.setPadding(new Insets(5, 5, 5, 5));
        
        remove.setOnAction((event) -> {
            if (c.removeMeal(hike, meal)) {
                box.getChildren().add(new Label("Meal removed!"));
            } else {
                box.getChildren().add(new Label("Removing meal failed for some reason."));
            }
        });

        gp.add(box, 0, 0);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
}
