/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import hikingdiary.dao.DayTripDao;
import hikingdiary.dao.HikeDao;
import hikingdiary.dao.UserDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
     * Method creates a new hike with given parametres and saves it to the
     * database.
     *
     * @param name the name of the new hike
     * @param year the happening year of the new hike
     * @param upcoming whether the hike is upcoming or past
     *
     * @see hikingdiary.dao.DBHikeDao#createHike(Hike)
     *
     * @return a new hike or null if creation failed
     */
    public Hike createNewHike(String name, int year, boolean upcoming) {
        Hike hike = new Hike(name, year, upcoming);
        if (hikeDao.createHike(hike) == 1) {
            return hike;
        }
        return null;
    }

    /**
     * Method updates the given hike in the database.
     *
     * @param hike hike to be updated
     * @see hikingdiary.dao.DBHikeDao#updateHike(Hike)
     * @return whether the update succeeded or not
     */
    public boolean updateHike(Hike hike) {
        if (hikeDao.updateHike(hike) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Method gets hike with the given name from the database.
     *
     * @param name name of the hike wanted
     *
     * @see hikingdiary.dao.DBHikeDao#readHike(String)
     * @see hikingdiary.dao.DBDayTripDao#list(int)
     *
     * @return the hike with the name
     */
    public Hike getHike(String name) {
        Hike hike = hikeDao.readHike(name);
        List<DayTrip> dayTrips = dtDao.list(hike.getId());
        hike.setDayTrips(dayTrips);
        return hike;
    }

    /**
     * Method removes the given hike from the database
     *
     * @param hike hike to be deleted
     *
     * @see hikingdiary.dao.DBHikeDao#list(boolean)
     * @see hikingdiary.dao.DBHikeDao#deleteHike(String)
     *
     * @return whether the deletion succeeded or not
     */
    public boolean removeHike(Hike hike) {
        if (hikeDao.list(hike.isUpcoming()).contains(hike)) {
            if (hikeDao.deleteHike(hike.getName()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method gets all the hikes from the database.
     *
     * @see hikingdiary.dao.DBHikeDao#list(boolean)
     * @return list of hikes or null if an error occurred
     */
    public List<Hike> listHikes() {
        List<Hike> hikes = new ArrayList<>();

        try {
            hikes = hikeDao.list(false);
            for (Hike h : hikeDao.list(true)) {
                hikes.add(h);
            }
        } catch (Exception e) {
            return null;
        }

        Collections.sort(hikes);
        Collections.reverse(hikes);
        return hikes;
    }

    /**
     * Method gets all the past-marked hikes from the database and sorts them.
     *
     * @see hikingdiary.dao.DBHikeDao#list(boolean)
     * @return list of the past hikes or null if an error occurred
     */
    public List<Hike> listPastHikes() {
        List<Hike> pastHikes = new ArrayList<>();

        try {
            pastHikes = hikeDao.list(false);
        } catch (Exception e) {
            return null;
        }

        Collections.sort(pastHikes);
        Collections.reverse(pastHikes);
        return pastHikes;
    }

    /**
     * Method gets all the upcoming-marked hikes from the database and sorts it.
     *
     * @see hikingdiary.dao.DBHikeDao#list(boolean)
     * @return list of the upcoming hikes in the database
     */
    public List<Hike> listUpcomingHikes() {
        List<Hike> upcomingHikes = new ArrayList<>();

        try {
            upcomingHikes = hikeDao.list(true);
        } catch (Exception e) {
            return null;
        }

        Collections.sort(upcomingHikes);
        return upcomingHikes;
    }

    /**
     * Method checks if the given hike has already the given companion. If not,
     * it adds the companion for the hike into the database.
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
            if (hikeDao.createCompanion(hike, comp) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method removes the given companion from the given hike, if the hike has
     * the companion.
     *
     * @param hike hike to remove the companion from
     * @param name name of the companion to be removed
     * 
     * @see hikingdiary.dao.DBHikeDao#deleteCompanion(Hike, String)
     * 
     * @return whether removing succeeded or not
     */
    public boolean removeCompanion(Hike hike, String name) {
        if (hike.removeCompanion(name)) {
            hikeDao.deleteCompanion(hike, name);
            return true;
        }
        return false;
    }

    /**
     * Method adds the given day trip to the given hike into the database if the
     * hike did not alredy have that day trip.
     *
     * @param hike hike to add the day trip to
     * @param dt day trip to be added
     * 
     * @see hikingdiary.dao.DBDayTripDao#create(int, DayTrip)
     * 
     * @return whether adding succeeded or not
     */
    public boolean addDayTrip(Hike hike, DayTrip dt) {
        if (hike.addDayTrip(dt)) {
            if (dtDao.create(hike.getId(), dt) == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method updates the given day trip belonging to the given hike.
     *
     * @param hike hike day trip to be updated belongs to
     * @param dt day trip to be updated
     * 
     * @see hikingdiary.dao.DBDayTripDao#update(DayTrip)
     * 
     * @return whether updating succeeded or not
     */
    public boolean updateDayTrip(Hike hike, DayTrip dt) {
        if (!hike.addDayTrip(dt)) {
            if (dtDao.update(dt) == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method removes the given meal from the given hike from the database.
     * 
     * @param hike hike to remove the meal from
     * @param meal meal to be removed
     * 
     * @see hikingdiary.dao.DBDayTripDao#deleteMeal(Hike, Meal)
     * 
     * @return whether removing succeeded or not
     */
    public boolean removeMeal(Hike hike, Meal meal) {
        if (hike.removeMeal(meal)) {
            hikeDao.deleteMeal(hike, meal);
            return true;
        }
        return false;
    }

    /**
     * Method checks if the given hike has already the given item. If not, it
     * adds the item for the hike into the database.
     *
     * @param hike hike to add the item for
     * @param item item to be added for the hike
     *
     * @see hikingdiary.dao.DBHikeDao#createItem(Hike, Item)
     *
     * @return whether the item was added to the hike or not
     */
    public boolean addItem(Hike hike, Item item) {
        if (!hike.addItem(item)) {
            hike.updateItem(item.getName(), item.getCount());
        }
        if (hikeDao.createItem(hike, item) > 0) {
            return true;
        }
        return false;
    }

    /**
     * Method removes the given item from the given hike from the database.
     * 
     * @param hike
     * @param name
     * @return 
     */
    public boolean removeItem(Hike hike, String name) {
        if (hike.removeItem(name)) {
            if (hikeDao.deleteItem(hike, name) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if the given hike has already the given item. If not, it
     * adds the item for the hike into the database.
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
            if (hikeDao.createMeal(hike, meal) > 0) {
                return true;
            }
        }
        return false;
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
