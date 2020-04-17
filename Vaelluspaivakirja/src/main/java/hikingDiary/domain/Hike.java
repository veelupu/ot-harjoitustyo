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
    private MealList mealList;
    private EquipmentList equList;
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
        this.mealList = new MealList();
        this.equList = new EquipmentList();
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
    
    public void addADayTrip(Date date) {
        this.dayTrips.put(date, new DayTrip(date));
    }
    
    public void setCompanions(ArrayList<String> companion) {
        this.companions = companion;
    }
    
    public boolean addACompanion(String name) {
        if (!this.companions.contains(name)) {
            this.companions.add(name);
            Collections.sort(companions);
            return true;
        }
        return false;
    }
    
    public String formatCompanions() {
        StringBuilder companion = new StringBuilder();
        for (String name: this.companions) {
            companion.append(name + "\n");
        }
        return companion.toString();
    }
    
    public void addAMeal(Meal meal, DayTrip dayTrip) {
        this.mealList.addAMeal(meal);
        dayTrip.setMeal(meal);
    }
    
    public void addAMeal(Meal meal) {
        this.mealList.addAMeal(meal);
    }
    
    public void addItem(String item) {
        this.equList.addAnItem(item);
    }
    
    public void addItem(String item, double weight) {
        this.equList.addAnItem(item, weight);
    }
    
    public String getKilometres() {
        int total = 0;
        for (DayTrip dayTrip: this.dayTrips.values()) {
            total += dayTrip.getWalkDist();
        }
        return "" + total;
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
