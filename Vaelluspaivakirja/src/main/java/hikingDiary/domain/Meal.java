/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.util.ArrayList;

/**
 * Class representing a single meal
 *
 * @author veeralupunen
 */
public class Meal implements Comparable<Meal> {

    private int id;
    private String name;
    private int category;
    private String categoryName;
    private ArrayList<String> ingredients;

    public Meal(String name, int category) {
        this.name = name;
        this.category = category;
        this.categoryName = getCategoryName();
        this.ingredients = new ArrayList<>();
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

    public String getCategoryName() {
        if (category == 0) {
            return "Snack";
        } else if (category == 1) {
            return "Breakfast";
        } else if (category == 2) {
            return "Lunch";
        } else if (category == 3) {
            return "Dinner";
        } else if (category == 4) {
            return "Dessert";
        } else {
            return "Outside of all categories";
        }
    }

    @Override
    public String toString() {
        return this.name + " (" + this.categoryName + ")";
    }

    @Override
    public int compareTo(Meal m) {
        if (this.category == m.getCategory()) {
            if (this.name.compareTo(m.getName()) > 0) {
                return 1;
            } else if (this.name.compareTo(m.getName()) < 0) {
                return -1;
            }
        } else if (this.category > m.getCategory()) {
            return 1;
        } else if (this.category < m.getCategory()) {
            return -1;
        }
        return 0;
    }

}
