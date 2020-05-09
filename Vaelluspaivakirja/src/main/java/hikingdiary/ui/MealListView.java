/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Meal;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
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
//    VBox mealList;
    int category;
    GridPane gp;
    ListView<Button> mealButtons;
    VBox upperBox;

    public MealListView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
//        this.mealList = new VBox();
        this.category = 5;
    }

    public Parent getView(Hike hike) {
        gp = new GridPane();

        Label title = new Label("Your meals during " + hike.toString() + ":");
        upperBox = new VBox();
        upperBox.getChildren().add(title);
        upperBox.setAlignment(Pos.CENTER);
        
        
        getMealButtons(hike);
        
//        gp.add(getMealButtons(hike), 0, 1);

//        formatMealButtons(hike);
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
        Button b5 = new Button("Other");
        buttons.add(b5);

        for (int i = 0; i < buttons.size(); i++) {
            final int a = i;
            buttons.get(i).setOnAction((event) -> {
                setCategory(a);
            });
            buttons.get(i).setStyle("-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 77px; "
                    + "-fx-min-height: 77px; "
                    + "-fx-max-width: 77px; "
                    + "-fx-max-height: 77px;");
        }

        HBox bBox = new HBox();
        //HBox buttons2 = new HBox();
        bBox.getChildren().addAll(b0, b1, b2, b3, b4, b5);
        //buttons2.getChildren().addAll(b3, b4, b5);
        bBox.setAlignment(Pos.CENTER);
        //buttons2.setAlignment(Pos.CENTER);

        Label lIngr = new Label("Ingredients needed for this meal\n(Separate ingredients with comma and whitespace)");
        TextField tfIngr = new TextField();
        Button ready = new Button("Ready\nto add!");
        ready.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;");

        VBox lowerBox = new VBox();
        lowerBox.getChildren().addAll(add, lName, tfName, lCateg, bBox, lIngr, tfIngr, ready);
        lowerBox.setAlignment(Pos.CENTER);

        Label done = new Label("Meal added!");
        Label noName = new Label("Please give your meal a name!");
        Label exists = new Label("This hike already has this meal.");
        Label error = new Label("Oops, something went wrong.\nTry again!");

        //Painetaan nappia ja ruoan luominen alkaa
        ready.setOnAction((event) -> {
            if (tfName.getText().length() < 1) {
                lowerBox.getChildren().add(noName);
                return;
            }
            Meal meal = new Meal(tfName.getText(), this.category);
            try {
                ArrayList<String> ingredients = new ArrayList<>();
                if (tfIngr.getLength() > 0) {
                    String ingr = tfIngr.getText();
                    String[] pcs = ingr.split(", ");
                    for (int i = 0; i < pcs.length; i++) {
                        ingredients.add(pcs[i]);
                    }
                }

                meal.setIngredients(ingredients);

                if (c.addMeal(hike, meal)) {
                    getMealButtons(hike);
                    tfName.clear();
                    tfIngr.clear();
                    lowerBox.getChildren().add(done);
                } else {
                    lowerBox.getChildren().add(exists);
                }
            } catch (Exception e) {
                lowerBox.getChildren().add(error);
                c.removeMeal(hike, meal);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Adding meal failed: " + e.getMessage());
            }
        });

        tfName.setOnMouseClicked((event) -> {
            lowerBox.getChildren().remove(done);
            lowerBox.getChildren().remove(noName);
            lowerBox.getChildren().remove(exists);
            lowerBox.getChildren().remove(error);
        });

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setPrefWidth(300);

        gp.add(upperBox, 0, 0);
        gp.add(lowerBox, 0, 1);

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
//    private void formatMealButtons(Hike hike) {
//        mealList.getChildren().clear();
//        for (Meal meal : hike.getMeals()) {
//            Button b = new Button(meal.toString());
//            b.setOnAction((event) -> {
//                ui.bp.setCenter(new MealView(c, meal, hike).getView());
//            });
//            mealList.getChildren().add(b);
//        }
//    }
    private void getMealButtons(Hike hike) {
        upperBox.getChildren().remove(mealButtons);
        mealButtons = new ListView<>();

        ObservableList<Button> buttons = FXCollections.observableArrayList();

        for (Meal meal : hike.getMeals()) {
            Button b = new Button(meal.toString());
            b.setOnAction((event) -> {
                ui.bp.setCenter(new MealView(c, meal, hike).getView());
            });
            //style(b);
            buttons.add(b);
        }
        mealButtons.setItems(buttons);
        //mealButtons.setPrefWidth(120);
        mealButtons.setPrefHeight(120);
        //mealButtons.setFixedCellSize(110);
        //mealButtons.setOrientation(Orientation.HORIZONTAL);
        mealButtons.setStyle("-fx-background-color: transparent;");
        upperBox.getChildren().add(mealButtons);
        upperBox.setPrefWidth(300);
    }

    private void style(Button b) {
        b.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 100px; "
                + "-fx-min-height: 100px; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 100px;");
    }
}
