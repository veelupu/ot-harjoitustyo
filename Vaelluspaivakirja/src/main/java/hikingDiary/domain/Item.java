/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

/**
 *
 * @author veeralupunen
 */
public class Item implements Comparable<Item> {
    
    private int id;
    private String name;
    private double weight;
    private int count;
    
    public Item(String name, int count) {
        this.name = name;
        this.count = count;
        this.weight = 0;
    }
    
    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }
    
    public Item(String name, double weight, int count) {
        this.name = name;
        this.count = count;
        this.weight = weight;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    @Override
    public String toString() {
        return String.format("%s %d pcs (total %.1f kg)", this.name, this.count, (this.count * this.weight));
    }

    @Override
    public int compareTo(Item i) {
        if (this.name.compareTo(i.getName()) > 0) {
            return 1;
        } else if (this.name.compareTo(i.getName()) < 0) {
            return -1;
        } else {
            return 0;
        }
    }
    
    
    
}
