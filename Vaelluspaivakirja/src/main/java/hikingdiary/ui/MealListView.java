/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Meal;
import java.util.ArrayList;
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
public class MealListView {
    
    Controller c;
    GraphicalUserInterface ui;
    VBox mealList;
    int category;
    
    public MealListView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
        this.mealList = new VBox();
        this.category = 5;
    }
    
    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();

        Label title = new Label("Your meals during " + hike.toString() + ":");

        formatMealButtons(hike);
        
        Label empty = new Label("");
        Label add = new Label("Add more meals to the meal list!");
        
        Label lName = new Label("Meal name:");
        TextField tfName = new TextField();
        
        Label lCateg = new Label("In which category this meal belongs to?");
        
        ArrayList<Button> buttons = new ArrayList<>();
        
        Button b0 = new Button("Snacks");
        buttons.add(b0);
        Button b1 = new Button("Breakfast");
        buttons.add(b1);
        Button b2 = new Button("Lunch");
        buttons.add(b2);
        Button b3 = new Button("Dinner");
        buttons.add(b3);
        Button b4 = new Button("Dessert");
        buttons.add(b4);
        Button b5 = new Button("Outside of all categories");
        buttons.add(b5);
        
        for (int i = 0; i < buttons.size(); i++) {
            final int a = i;
            buttons.get(i).setOnAction((event) -> {
                setCategory(a);
            });
        }
        
        HBox buttons1 = new HBox();
        HBox buttons2 = new HBox();
        buttons1.getChildren().addAll(b0, b1, b2, b3);
        buttons2.getChildren().addAll(b4, b5);
        
        Label lIngr = new Label("Ingredients needed for this meal\n(Separate ingredients with comma and whitespace))");
        TextField tfIngr = new TextField();
        Button ready = new Button("Ready to add an item to the meal list!");
        
        VBox box = new VBox();
        box.getChildren().addAll(title, mealList, empty, add, lName, tfName, lCateg, buttons1, buttons2, lIngr, tfIngr, ready);
        
        Label done = new Label("Meal added!");
        Label exists = new Label("This hike already has this meal.");
        Label error = new Label("Oops, something went wrong.\nTry again!");
        
        //Painetaan nappia ja ruoan luominen alkaa
        ready.setOnAction((event) -> {
            try {
                Meal meal = new Meal(tfName.getText(), this.category);
                
                ArrayList<String> ingredients = new ArrayList<>();
                if (tfIngr.getLength() != 0) {
                    String ingr = tfIngr.getText();
                    String[] pcs = ingr.split(", ");
                    for (int i = 0; i < pcs.length; i++) {
                        ingredients.add(pcs[i]);
                    }
                }
                
                meal.setIngredients(ingredients);
                
                if (c.addMeal(hike, meal)) {
                    formatMealButtons(hike);
                    tfName.clear();
                    tfIngr.clear();
                    box.getChildren().add(done);
                } else {
                    box.getChildren().add(exists);
                }
            } catch (Exception e) {
                box.getChildren().add(error);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Adding item failed: " + e.getMessage());
            }
        });
        
        tfName.setOnMouseClicked((event) -> {
            box.getChildren().remove(done);
            box.getChildren().remove(exists);
            box.getChildren().remove(error);
        });
    
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        gp.add(box, 0, 0);

        return gp;
    }
    
    private void setCategory(int i) {
        this.category = i;
    }
    
//    private void updateMealsBox(Meal meal) {
//        Button b = new Button(meal.toString());
//            
//            b.setOnAction((event) -> {
//                ui.bp.setCenter(new MealView(c, meal, hike).getView());
//            });
//            
//            mealList.getChildren().add(b);
//    }
    
    private void formatMealButtons(Hike hike) {
        mealList.getChildren().clear();
        for (Meal meal : hike.getMeals()) {
            Button b = new Button(meal.toString());
            b.setOnAction((event) -> {
                ui.bp.setCenter(new MealView(c, meal, hike).getView());
            });
            mealList.getChildren().add(b);
        }
    }
}
