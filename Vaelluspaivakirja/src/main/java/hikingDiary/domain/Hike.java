/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

/**
 *
 * @author veeralupunen
 */
public class Hike implements Comparable<Hike> {
    
    private String name;
    private int year;
    private boolean upcoming;
    
    public Hike(String name, int year, boolean upcoming) {
        this.name = name;
        this.year = year;
        this.upcoming = upcoming;
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
