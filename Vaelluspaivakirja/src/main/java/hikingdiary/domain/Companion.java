/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

/**
 * Class representing a single companion
 * 
 * @author veeralupunen
 */
public class Companion {
    
    private int id;
    private String name;
    
    public Companion(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
