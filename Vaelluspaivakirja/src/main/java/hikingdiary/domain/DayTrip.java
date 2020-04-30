/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 *
 * @author veeralupunen
 */
public class DayTrip {
    
    private Date date;
    private int weekday;
    private String startingPoint;
    private String endingPoint;
    private int walkDist;
    private int walkTime;
    private Meal meal;
    private String weather;
    
    public DayTrip(Date date) {
        this.date = date;
    }

    public DayTrip(Date date, String startingPoint, String endingPoint, int walkDist, int walkTime, Meal meal, String weather) {
        this.date = date;
        this.weekday = DayOfWeek.from((TemporalAccessor) this.date).getValue();
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.walkDist = walkDist;
        this.walkTime = walkTime;
        this.meal = meal;
        this.weather = weather;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public int getWalkDist() {
        return walkDist;
    }

    public void setWalkDist(int walkDist) {
        this.walkDist = walkDist;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "" + weekday + " " + date + " " + walkDist + " km";
    }
    
    
    
    
}
