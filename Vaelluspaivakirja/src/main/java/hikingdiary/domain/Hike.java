/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    private String locationStart;
    private String locationEnd;
    private HashMap<LocalDate, DayTrip> dayTrips;
    private ArrayList<String> companions;
    private ArrayList<Meal> meals;
    private HashMap<String, Item> equipment;
    private double rucksackWeightBeg;
    private double rucksackWeightEnd;

    public Hike(String name) {
        this.name = name;
    }
    
    public Hike(String name, int year, boolean upcoming) {
        this(name, year, upcoming, 0, 0);
    }

    public Hike(String name, int year, boolean upcoming, double rucksackWeightBeg, double rucksackWeightEnd) {
        this.name = name;
        this.year = year;
        this.upcoming = upcoming;
        this.locationStart = " ";
        this.locationEnd = " ";
        this.rucksackWeightBeg = rucksackWeightBeg;
        this.rucksackWeightEnd = rucksackWeightEnd;
        this.dayTrips = new HashMap<>();
        this.companions = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.equipment = new HashMap<>();
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

    public void setLocationStart(String start) {
        this.locationStart = start;
    }
    
    public void setLocationEnd(String end) {
        this.locationEnd = end;
    }

    public String getLocationStart() {
        return locationStart;
    }

    public String getLocationEnd() {
        return locationEnd;
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
     * Method creates a new day trip and adds it to this hike's day trips if there was not already a day trip for that date.
     * 
     * @param DayTrip day trip to be added
     * @return whether adding succeeded or not
     */
    public boolean addDayTrip(DayTrip dt) {
        if (this.dayTrips.containsKey(dt.getDate())) {
            return false;
        }
        this.dayTrips.put(dt.getDate(), dt);
        return true;
    }
    
    /**
     * Method forms an arraylist of this hike's day trips and sorts it to the descending order.
     * 
     * @return list of day trips
     */
    public ArrayList<DayTrip> getDayTrips() {
        ArrayList<DayTrip> days = new ArrayList<>();
        for (DayTrip day : this.dayTrips.values()) {
            days.add(day);
        }
        Collections.sort(days);
        Collections.reverse(days);
        return days;
    }
    
    /**
     * Method set the list of day trips to this hike.
     * 
     * @param dayTrips list of day trips to be set
     */
    public void setDayTrips(List<DayTrip> dayTrips) {
        this.dayTrips.clear();
        for (DayTrip dt : dayTrips) {
            this.dayTrips.put(dt.getDate(), dt);
        }
    }

    /**
     * Method checks if this hike already has a companion with that name.
     * If not, it adds the new companion and returns true.
     * 
     * @param name name of the new companion
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
    
    /**
     * Method removes a companion with the given name from this hike.
     * 
     * @param name name of the companion to be removed
     * @return whether the removing succeeded or not
     */
    public boolean removeCompanion(String name) {
        if (this.companions.contains(name)) {
            this.companions.remove(name);
            return true;
        }
        return false;
    }

    /**
     * Method sets the given arraylist of companions to this hike and sorts it.
     * 
     * @param companion the arraylist to be set
     */
    public void setCompanions(ArrayList<String> companion) {
        this.companions = companion;
        Collections.sort(companions);
    }

    /**
     * Method checks if this hike already has a item with that name.
     * If not, it adds the new item and returns true.
     * 
     * @param item the item to be added
     * @return whether adding succeeded or not
     */
    public boolean addItem(Item item) {
        if (containsItem(item.getName())) {
            return false;
        }
        this.equipment.put(item.getName(), item);
        return true;
    }
    
    /**
     * Method checks if this hike has an item with given name.
     * 
     * @param name the name of the item
     * @return whether this hike has the item or not
     */
    public boolean containsItem(String name) {
        return this.equipment.containsKey(name);
    }
    
    /**
     * Method checks if this hike has an item with the given name.
     * If yes, it sets the given count to the item.
     * 
     * @param name name of the item to be updated
     * @param count the new count of the item
     * @return whether updating succeeded or not
     */
    public boolean updateItem(String name, int count) {
        if (containsItem(name)) {
            Item i = this.equipment.get(name);
            i.setCount(count);
            return true;
        }
        return false;
    }

    public void setEquipment(HashMap<String, Item> equList) {
        this.equipment = equList;
    }

    /**
     * Method builds a string containing names, count and combined weight 
     * of the items of this hike in specified form.
     * 
     * @return items of the hike in specified form
     */
    public String formatEquipment() {
        StringBuilder items = new StringBuilder();
        for (Item item : this.equipment.values()) {
            items.append(item.toString() + "\n");
        }
        return items.toString();
    }
    
    /**
     * Method forms a sorted arraylist of items belonging to this hike.
     * 
     * @return arraylist of items
     */
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        for (Item item : this.equipment.values()) {
            items.add(item);
        }
        Collections.sort(items);
        return items;
    }
    
    /**
     * Method checks if this hike has an item with the given name.
     * If yes, it removes the item from this hike.
     * 
     * @param name name of the item to be removed
     * @return whether removing succeeded or not
     */
    public boolean removeItem(String name) {
        if (this.equipment.containsKey(name)) {
            this.equipment.remove(name);
            return true;
        }
        return false;
    }
    
    /**
     * Method checks if this hike already has a meal with that name.
     * If not, it adds the new meal and returns true.
     * 
     * @param meal meal to add
     * @return whether adding succeeded or not
     */
    public boolean addMeal(Meal meal) {
        if (this.meals.contains(meal)) {
            return false;
        }
        this.meals.add(meal);
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
        for (Meal meal : this.meals) {
            items.append(meal.toString() + "\n");
        }
        return items.toString();
    }
    
    /**
     * Method forms a sorted arraylist containing meals of this hike.
     * 
     * @return a sorted arraylist containing meals of this hike
     */
    public ArrayList<Meal> getMeals() {
        ArrayList<Meal> meals = new ArrayList<>();
        for (Meal meal : this.meals) {
            meals.add(meal);
        }
        Collections.sort(meals);
        return meals;
    }
    
    /**
     * Method removes the given meal from this hike.
     * 
     * @param meal meal to be removed
     * @return whether removing succeeded or not
     */
    public boolean removeMeal(Meal meal) {
        if (this.meals.contains(meal)) {
            for (Meal m : this.meals) {
                if (m.equals(meal)) {
                    meals.remove(m);
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
    public void setMeals(ArrayList<Meal> mealList) {
        this.meals = mealList;
    }

    /**
     * Method counts the total kilometres of this hike out of the hike's day trips.
     * 
     * @return total kilometres of the hike
     */
    public double getKilometres() {
        double total = 0;
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
        return (this.name.equals(oHike.name)); 
    }
}
