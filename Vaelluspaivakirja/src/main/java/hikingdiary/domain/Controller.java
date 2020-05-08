/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import hikingdiary.dao.DayTripDao;
import hikingdiary.dao.HikeDao;
import hikingdiary.dao.UserDao;
import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
//import java.util.HashMap;

/**
 * Class responsible for the application logic
 * 
 * @author veeralupunen
 */
public class Controller {

    private HikeDao hikeDao;
    private UserDao userDao;
    private DayTripDao dtDao; 

    public Controller(HikeDao hikeDao, UserDao userDao, DayTripDao dtDao) {
        this.hikeDao = hikeDao;
        this.userDao = userDao;
        this.dtDao = dtDao;
    }
    
    /**
     * Method creates a new hike with given parametres 
     * and saves it to the database.
     * 
     * @param name the name of the new hike
     * @param year the happening year of the new hike
     * @param upcoming whether the hike is upcoming or past
     * 
     * @see hikingdiary.dao.DBHikeDao#createHike(Hike)
     * 
     * @return a new hike
     */
    public Hike createNewHike(String name, int year, boolean upcoming) {
        Hike hike = new Hike(name, year, upcoming);
        hikeDao.createHike(hike);
        return hike;
    }
    
    /**
     * Method updates the given hike in the database.
     * 
     * @see hikingdiary.dao.DBHikeDao#updateHike(Hike)
     * 
     * @param hike hike to be updated
     */
    public void updateHike(Hike hike) {
        hikeDao.updateHike(hike);
    }

    /**
     * Method gets hike with the given name from the database.
     * 
     * @param name name of the hike wanted
     * 
     * @see hikingdiary.dao.DBHikeDao#readHike(String)
     * 
     * @return the hike with the name
     */
    public Hike getHike(String name) {
        Hike hike = hikeDao.readHike(name);
        List<DayTrip> dayTrips = dtDao.list(hike.getId());
        hike.setDayTrips(dayTrips);
        return hike;
    }
    
    public boolean removeHike(Hike h) {
        if (hikeDao.list(h.isUpcoming()).contains(h)) {
            hikeDao.deleteHike(h.getName());
            return true;
        }
        return false;
    }
    
    public List<Hike> listHikes() {
        List<Hike> hikes = new ArrayList<>();

        try {
            hikes = hikeDao.list(false);
            for (Hike h : hikeDao.list(true)) {
                hikes.add(h);
            }
        } catch (Exception e) {
            //poista tulostus lopullisesta versiosta – käsittele muutoin
            System.err.println("Something went wrong with listing past hikes.");
        }
        
        Collections.sort(hikes);
        Collections.reverse(hikes);
        return hikes;
    }

    /**
     * Method gets all the past-marked hikes from the database and sorts it.
     * 
     * @see hikingdiary.dao.DBHikeDao#listPastHikes()
     * 
     * @return list of the past hikes in the database
     */
    public List<Hike> listPastHikes() {
        List<Hike> pastHikes = new ArrayList<>();

        try {
            pastHikes = hikeDao.list(false);
        } catch (Exception e) {
            //poista tulostus lopullisesta versiosta – käsittele muutoin
            System.err.println("Something went wrong with listing past hikes.");
        }
        
        Collections.sort(pastHikes);
        Collections.reverse(pastHikes);
        return pastHikes;
    }

    /**
     * Method gets all the upcoming-marked hikes from the database and sorts it.
     * 
     * @see hikingdiary.dao.DBHikeDao#listUpcomingHikes()
     * 
     * @return list of the upcoming hikes in the database
     */
    public List<Hike> listUpcomingHikes() {
        List<Hike> upcomingHikes = new ArrayList<>();

        try {
            upcomingHikes = hikeDao.list(true);
        } catch (Exception e) {
            System.err.println("Something went wrong with listing upcoming hikes.");
        }

        Collections.sort(upcomingHikes);
        return upcomingHikes;
    }
    
    /**
     * Method checks if the given hike has already the given companion.
     * If not, it adds the companion for the hike into the database.
     * 
     * @param hike hike to add the companion for
     * @param comp companion to be added for the hike
     * 
     * @see hikingdiary.dao.DBHikeDao#createCompanion(Hike, Companion)
     * 
     * @return whether the companion was added to the hike or not
     */
    public boolean addCompanion(Hike hike, Companion comp) {
        if (hike.addCompanion(comp.getName())) {
            hikeDao.createCompanion(hike, comp);
            return true;
        }
        return false;
    }
    
    public boolean removeCompanion(Hike hike, String name) {
        if (hike.removeCompanion(name)) {
            hikeDao.deleteCompanion(hike, name);
            return true;
        }
        return false;
    }
    
    //tehdään tämä näin: dayTripDaossa on taulu ja liitostaulu hikeen
    //kun daytripit haetaan jollekin hikelle, c lukee ne erikseen ja liittää hikeen tms.
    public boolean addDayTrip(Hike hike, DayTrip dt) {
        if (hike.addDayTrip(dt)) {
            dtDao.create(hike.getId(), dt);
            return true;
        }
        return false;
    }
    
    public void updateDayTrip(Hike hike, DayTrip dt) {
        hike.updateDayTrip(dt);
        dtDao.update(hike.getId(), dt);
    }
    
    public boolean removeMeal(Hike hike, String name) {
        if (hike.removeMeal(name)) {
            hikeDao.deleteMeal(hike, name);
            return true;
        }
        return false;
    }
    
    /**
     * Method checks if the given hike has already the given item.
     * If not, it adds the item for the hike into the database.
     * 
     * @param hike hike to add the item for
     * @param item item to be added for the hike
     * 
     * @see hikingdiary.dao.DBHikeDao#createItem(Hike, Item)
     * 
     * @return whether the item was added to the hike or not
     */
    public void addItem(Hike hike, Item item) {
        if (!hike.addItem(item)) {
            hike.updateItem(item.getName(), item.getCount());
        }
        hikeDao.createItem(hike, item);
    }
    
    public void removeItem(Hike hike, String name) {
//        if (hike.removeItem(name)) {
            hikeDao.deleteItem(hike, name);
//            return true;
//        }
//        return false;
    }
    
    /**
     * Method checks if the given hike has already the given item.
     * If not, it adds the item for the hike into the database.
     * 
     * @param hike hike to add the meal for
     * @param meal meal to be added for the hike
     * 
     * @see hikingdiary.dao.DBHikeDao#createMeal(Hike, Meal)
     * 
     * @return whether the meal was added to the hike or not
     */
    public boolean addMeal(Hike hike, Meal meal) {
        if (hike.addMeal(meal)) {
            hikeDao.createMeal(hike, meal);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method creates a new user into the database.
     * 
     * @param name the user's username
     * @see hikingdiary.dao.DBUserDao#create(User)
     */
    public void createUser(String name) {
        userDao.create(new User(name));
    }
    
    /**
     * Method updates the user's username in the database.
     * 
     * @param newName the new username for the user
     * @see hikingdiary.dao.DBUserDao#read()
     * @see hikingdiary.dao.DBUserDao#update(User, String)
     */
    public void changeUsername(String newName) {
        userDao.update(userDao.read(), newName);
    }

}
