/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Class representing a single hike
 * 
 * @author veeralupunen
 */
public class Hike implements Comparable<Hike> {

    private int id;
    private String name;
    private int year;
    private boolean upcoming;
    private String location;
    private HashMap<Date, DayTrip> dayTrips;
    private ArrayList<String> companions;
    private HashMap<String, Meal> mealList;
    private HashMap<String, Item> equList;
    private double rucksackWeightBeg;
    private double rucksackWeightEnd;

    public Hike(String name, int year, boolean upcoming) {
        this(name, year, upcoming, 0, 0);
    }

    public Hike(String name, int year, boolean upcoming, double rucksackWeightBeg, double rucksackWeightEnd) {
        this.name = name;
        this.year = year;
        this.upcoming = upcoming;
        this.rucksackWeightBeg = rucksackWeightBeg;
        this.rucksackWeightEnd = rucksackWeightEnd;
        this.dayTrips = new HashMap<>();
        this.companions = new ArrayList<>();
        this.mealList = new HashMap<>();
        this.equList = new HashMap<>();
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

    public int getYear() {
        return year;
    }

    public boolean isUpcoming() {
        return upcoming;
    }

    public void setUpcoming(boolean upcoming) {
        this.upcoming = upcoming;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
//        if (this.location.contains(",")) {
//            String[] pieces = this.location.split(",");
//            return pieces[0] + "\n" + pieces[1];
//        }
        return this.location;
    }

    public double getRucksackWeightBeg() {
        return rucksackWeightBeg;
    }

    public void setRucksackWeightBeg(double rucksackWeight) {
        this.rucksackWeightBeg = rucksackWeight;
    }

    public double getRucksackWeightEnd() {
        return rucksackWeightEnd;
    }

    public void setRucksackWeightEnd(double rucksackWeightEnd) {
        this.rucksackWeightEnd = rucksackWeightEnd;
    }

    /**
     * Method creates a new day trip and adds it to this hike's day trips.
     * 
     * @param date the date of the new day trip
     * @return a new day trip
     */
    public DayTrip addADayTrip(Date date) {
        DayTrip dayTrip = new DayTrip(date);
        this.dayTrips.put(date, dayTrip);
        return dayTrip;
    }

    /**
     * Method checks if this hike already has a companion with that name.
     * If not, it adds the new companion and returns true.
     * 
     * @param name Name of the new companion
     * @return whether did the method add the companion or not
     */
    public boolean addCompanion(String name) {
        if (!this.companions.contains(name)) {
            this.companions.add(name);
            Collections.sort(companions);
            return true;
        }
        return false;
    }

    /**
     * Method sets the given arraylist of companions to this hike and sorts it.
     * 
     * @param companion the arraylist to be given for the hike
     */
    public void setCompanions(ArrayList<String> companion) {
        this.companions = companion;
        Collections.sort(companions);
    }

    /**
     * Method returns name's of the companions of this hike in specified form.
     * 
     * @return formated list of names
     */
    public String formatCompanions() {
        StringBuilder companion = new StringBuilder();
        for (String name : this.companions) {
            companion.append(name + "\n");
        }
        return companion.toString();
    }
    
    public boolean removeCompanion(String name) {
        if (this.companions.contains(name)) {
            this.companions.remove(name);
            return true;
        }
        return false;
    }

    /**
     * Method checks if this hike already has a meal with that name.
     * If not, it adds the new meal and returns true.
     * 
     * @param meal meal to add
     * @return whether did the method add the meal or not
     */
    public boolean addMeal(Meal meal) {
        if (this.mealList.containsKey(meal.getName())) {
            return false;
        }
        this.mealList.put(meal.getName(), meal);
        return true;
    }

    /**
     * Method builds a string containing names and categories of the meals 
     * of this hike in specified form.
     * 
     * @return formatted list of meals
     */
    public String formatMeals() {
        StringBuilder items = new StringBuilder();
        for (Meal meal : this.mealList.values()) {
            items.append(meal.toString() + "\n");
        }
        return items.toString();
    }
    
    /**
     * Method makes a sorted arraylist containing meals of this hike.
     * 
     * @return a sorted arraylist containing meals of this hike
     */
    public ArrayList<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        for (Meal meal : this.mealList.values()) {
            meals.add(meal);
        }
        Collections.sort(meals);
        return meals;
    }
    
    public void setMeals(HashMap<String, Meal> mealList) {
        this.mealList = mealList;
    }

    /**
     * Method checks if this hike already has a item with that name.
     * If not, it adds the new item and returns true.
     * 
     * @param item the item to be added
     * @return whether the item was added for the hike or not
     */
    public boolean addItem(Item item) {
        if (containsItem(item.getName())) {
            return false;
        }
        this.equList.put(item.getName(), item);
        return true;
    }

    public void setEquipment(HashMap<String, Item> equList) {
        this.equList = equList;
    }

    /**
     * Method checks if this hike has an item with given name.
     * 
     * @param name the name of the item
     * @return whether this hike has the item or not
     */
    public boolean containsItem(String name) {
        return this.equList.containsKey(name);
    }

    /**
     * Method builds a string containing names, count and combined weight 
     * of the items of this hike in specified form.
     * 
     * @return items of the hike in specified form
     */
    public String formatEquipment() {
        StringBuilder items = new StringBuilder();
        for (Item item : this.equList.values()) {
            items.append(item.toString() + "\n");
        }
        return items.toString();
    }

    /**
     * Method counts the total kilometres of this hike out of the hike's day trips.
     * 
     * @return total kilometres of the hike
     */
    public int getKilometres() {
        int total = 0;
        for (DayTrip dayTrip : this.dayTrips.values()) {
            total += dayTrip.getWalkDist();
        }
        return total;
    }

    @Override
    public String toString() {
        return this.name + " " + this.year;
    }

    @Override
    public int compareTo(Hike h) {
        if (this.year > h.year) {
            return 1;
        } else if (this.year < h.year) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hike)) {
            return false;
        }
        Hike oHike = (Hike) o;
        return (this.name.equals(oHike.name) && this.year == oHike.year && this.upcoming == oHike.upcoming);
    }
}
