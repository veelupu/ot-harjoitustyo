/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.dao.HikeDao;
import hikingdiary.dao.UserDao;
import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
//import java.util.HashMap;

/**
 *
 * @author veeralupunen
 */
public class Controller {

    //HashMap<String, Hike> hikes;
    private HikeDao hikeDao;
    private UserDao userDao;

    public Controller(HikeDao hikeDao, UserDao userDao) {
        //this.hikes = new HashMap<>();
        this.hikeDao = hikeDao;
        this.userDao = userDao;
    }
    
    //Hike actions
    public Hike createNewHike(String name, int year, boolean upcoming) {
        Hike hike = new Hike(name, year, upcoming);
        hikeDao.create(hike);
        return hike;
    }
    
    public void updateHike(Hike hike) {
        hikeDao.update(hike);
    }

    public Hike getHike(String name) {
        Hike hike = hikeDao.read(name);
        return hike;
    }

    public List<Hike> listPastHikes() {
        List<Hike> pastHikes = new ArrayList<>();

        try {
            pastHikes = hikeDao.listPastHikes();
        } catch (Exception e) {
            System.err.println("Something went wrong with listing past hikes.");
        }
        
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

        Collections.sort(upcomingHikes);
        return upcomingHikes;
    }
    
    //lisää tänne metodit rinkan alku- ja loppupainon lisäämiseksi
//    public double getRucksacWeightBeg(String hikeName) {
//        return hikeDao.getRucksacWeightBeg(hikeName);
//    }
//    
//    public double getRucksacWeightEnd(String hikeName) {
//        return hikeDao.getRucksacWeightEnd(hikeName);
//    }
//    
//    public void setRucksacWeightBeg(double w, String name) {
//        hikeDao.addRucksacWeightBeg(w, name);
//    }
//    
//    public void setRucksacWeightEnd(double w, String name) {
//        hikeDao.addRucksacWeightEnd(w, name);
//    }
    
    //User actions
    public void createUser(String name) {
        userDao.create(new User(name));
    }
    
    public void changeUsername(String newName) {
        userDao.update(userDao.read(), newName);
    }

}
