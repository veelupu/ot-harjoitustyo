/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 *
 * @author veeralupunen
 */
public class DayTrip implements Comparable<DayTrip> {
    
    private LocalDate date;
    private int weekday;
    private String startingPoint;
    private String endingPoint;
    private double walkDist;
    private double walkTime;
    private String weather;
    
    public DayTrip(LocalDate date) {
        this.date = date;
    }

    public DayTrip(LocalDate date, String startingPoint, String endingPoint, double walkDist, double walkTime, String weather) {
        this.date = date;
        this.weekday = DayOfWeek.from((TemporalAccessor) this.date).getValue();
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.walkDist = walkDist;
        this.walkTime = walkTime;
        this.weather = weather;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
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

    public double getWalkDist() {
        return walkDist;
    }

    public void setWalkDist(double walkDist) {
        this.walkDist = walkDist;
    }

    public double getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(double walkTime) {
        this.walkTime = walkTime;
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

    @Override
    public int compareTo(DayTrip d) {
        if (this.date.compareTo(date) > 0) {
            return 1;
        } else if (this.date.compareTo(date) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
    
    
    
}
