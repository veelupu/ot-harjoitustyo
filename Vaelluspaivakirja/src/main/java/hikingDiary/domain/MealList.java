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
public class MealList {
    
    private ArrayList<Meal> meals;

    public MealList() {
        this.meals = new ArrayList<>();
    }
    
    public void addAMeal(Meal meal) {
        meals.add(meal);
    }
    
}
