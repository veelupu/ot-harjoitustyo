/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingDiary.ui;

import hikingDiary.domain.Hike;
import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class Controller implements ControllerInterface {
    
    private HashMap<String, Hike> hikes;
    
    public Controller() {
        this.hikes = new HashMap<>();
    }
    
    @Override
    public boolean createNewHike(String name, int year) {
        if (!hikes.containsKey(name)) {
            hikes.put(name, new Hike(name, year));
            return true;
        }
        return false;
    }
}
