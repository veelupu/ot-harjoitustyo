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
    
    public MealView(Controller c, Meal meal) {
        this.c = c;
        this.meal = meal;
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
        
        VBox box = new VBox();
        box.getChildren().addAll(name, empty, ingr);
        
        gp.add(box, 0, 0);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
}
