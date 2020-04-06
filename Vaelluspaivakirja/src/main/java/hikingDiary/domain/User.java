/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

/**
 *
 * @author veeralupunen
 */
public class User {
    
    private String name;
    
    public User(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String newName) {
        this.name = newName;
        //LISÄÄ TÄHÄN: Päivitä tiedot tietokantaan daon kautta
    }
    
}
