/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.dao.HikeDao;
import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class Controller {

    HashMap<String, Hike> hikes;
    User user;
    private HikeDao hikeDao;

    public Controller(User user, HikeDao hikeDao) {
        this.hikes = new HashMap<>();
        this.user = user;
        this.hikeDao = hikeDao;
    }

    public boolean createNewHike(String name, int year, boolean upcoming) {
        Hike hike = new Hike(name, year, upcoming);

        if (!hikeDao.list().contains(hike)) {
            hikeDao.create(hike);
            return true;
        }

//        if (!hikes.containsKey(name)) {
//            hikes.put(name, new Hike(name, year, upcoming));
//            return true;
//        }
        return false;
    }

    public Hike getHike(String name) {
        if (hikes.containsKey(name)) {
            return hikes.get(name);
        }
        return null;
    }

    public List<Hike> listPastHikes() {
        List<Hike> pastHikes = new ArrayList<>();

        try {
            pastHikes = hikeDao.listPastHikes();
        } catch (Exception e) {
            System.err.println("Something went wrong with listing past hikes.");
        }

//        ArrayList<Hike> pastHikes = new ArrayList<>();
//        for (String hike: hikes.keySet()) {
//            if (!hikes.get(hike).isUpcoming()) {
//                pastHikes.add(hikes.get(hike));
//            }
//        }
        Collections.sort(pastHikes);
        Collections.reverse(pastHikes);
        return pastHikes;
    }

    public List<Hike> listUpcomingHikes() {
        List<Hike> upcomingHikes = new ArrayList<>();

        try {
            upcomingHikes = hikeDao.listUpcomingHikes();
        } catch (Exception e) {
            System.err.println("Something went wrong with listing upcoming hikes.");
        }

//        for (String hike: hikes.keySet()) {
//            if (hikes.get(hike).isUpcoming()) {
//                upcomingHikes.add(hikes.get(hike));
//            }
//        }
        Collections.sort(upcomingHikes);
        return upcomingHikes;
    }

    public void changeUsername(String newName) {
        user.setName(newName);
    }

}
