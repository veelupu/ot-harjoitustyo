/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class Controller {
    
    HashMap<String, Hike> hikes;
    User user;
    
    public Controller(User user) {
        this.hikes = new HashMap<>();
        this.user = user;
    }
    
    public boolean createNewHike(String name, int year, boolean upcoming) {
        if (!hikes.containsKey(name)) {
            hikes.put(name, new Hike(name, year, upcoming));
            return true;
        }
        return false;
    }
    
    public Hike getHike(String name) {
        if (hikes.containsKey(name)) {
            return hikes.get(name);
        }
        return null;
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
    
    public ArrayList<Hike> listUpcomingHikes() {
        ArrayList<Hike> upcomingHikes = new ArrayList<>();
        for (String hike: hikes.keySet()) {
            if (hikes.get(hike).isUpcoming()) {
                upcomingHikes.add(hikes.get(hike));
            }
        }
        Collections.sort(upcomingHikes);
        return upcomingHikes;
    }
    
    public void changeUsername(String newName) {
        user.setName(newName);
    }
    
}
