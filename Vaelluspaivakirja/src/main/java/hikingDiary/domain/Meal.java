/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.util.ArrayList;

/**
 *
 * @author veeralupunen
 */
public class Meal {
    
    private int id;
    private String name;
    private int category;
    private String categoryName;
    private ArrayList<String> ingredients;

    public Meal(String name, int category) {
        this.name = name;
        this.category = category;
        this.categoryName = getCategoryName(category);
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }
    
    public void addIngredient(String i) {
        this.ingredients.add(i);
    }

    public void setIngredients(ArrayList<String> ingrediens) {
        this.ingredients = ingrediens;
    }
    
    public String toString() {
        return this.name + " (" + this.categoryName + ")";
    }
    
    private String getCategoryName(int c) {
        if (c == 0) {
            return "Snack";
        } else if (c == 1) {
            return "Breakfast";
        } else if (c == 2) {
            return "Lunch";
        } else if (c == 3) {
            return "Dinner";
        } else if (c == 4) {
            return "Dessert";
        } else {
            return "Outside of all categories";
        }
    }
 
}
