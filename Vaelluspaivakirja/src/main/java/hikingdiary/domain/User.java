/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import hikingdiary.dao.UserDao;

/**
 * Class representing a single user
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

}
