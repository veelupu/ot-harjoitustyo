/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingDiary.ui;

import hikingDiary.domain.Hike;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class Controller {
    
    HashMap<String, Hike> hikes;
    
    public Controller() {
        this.hikes = new HashMap<>();
    }
    
    public Hike getHike(String name) {
        if (hikes.containsKey(name)) {
            return hikes.get(name);
        }
        return null;
    }
    
    public boolean createNewHike(String name, int year, boolean upcoming) {
        if (!hikes.containsKey(name)) {
            hikes.put(name, new Hike(name, year, upcoming));
            return true;
        }
        return false;
    }
    
    public ArrayList<Hike> listPastHikes() {
        ArrayList<Hike> pastHikes = new ArrayList<>();
        for (String hike: hikes.keySet()) {
            if (!hikes.get(hike).isUpcoming()) {
                pastHikes.add(hikes.get(hike));
            }
        }
        Collections.sort(pastHikes);
        Collections.reverse(pastHikes);
        return pastHikes;
    }
    
}
