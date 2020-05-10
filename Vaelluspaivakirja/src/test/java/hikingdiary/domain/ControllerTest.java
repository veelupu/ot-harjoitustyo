/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import hikingdiary.dao.DBDayTripDao;
import hikingdiary.dao.DBHikeDao;
import hikingdiary.dao.DBUserDao;
import java.io.File;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author veeralupunen
 */
public class ControllerTest {
    
    private Controller c;
    private DBHikeDao hikeDao;
    private DBUserDao userDao;
    private DBDayTripDao dtDao;
    
    public ControllerTest() {
        hikeDao = new DBHikeDao("hikesTest.db");
        userDao = new DBUserDao("hikesTest.db");
        dtDao = new DBDayTripDao("hikesTest.db");
    }
    
    @Before
    public void setUp() {
        c = new Controller(hikeDao, userDao, dtDao);
    }
    
    @After
    public void tearDown() {
        File file1 = new File(hikeDao.getDBAdress());
        File file2 = new File(userDao.getDBAdress());
        File file3 = new File(dtDao.getDBAdress());
        file1.delete();
        file2.delete();
        file3.delete();
    }
    
    @Test
    public void createHikeCreatesANewHikeAndAddsItToTheTestDB() {
        assertTrue(hikeDao.list(false).isEmpty());
        assertTrue(hikeDao.list(true).isEmpty());
        Hike hike = new Hike("Kaldoaivi", 2019, false);
        c.createNewHike("Kaldoaivi", 2019, false);
        assertEquals(1, hikeDao.list(false).size());
        assertTrue(hikeDao.list(false).contains(hike));
        assertEquals("Kaldoaivi", hikeDao.list(false).get(0).getName());
        assertEquals(2019, hikeDao.list(false).get(0).getYear());
        assertFalse(hikeDao.list(false).get(0).isUpcoming());
        assertTrue(hikeDao.list(true).isEmpty());
    }
    
    @Test
    public void createHikeDoesNotCreateHikeIfTheNameIsAlreadyUsed() {
        c.createNewHike("Kaldoaivi", 2019, false);
        assertEquals(null, c.createNewHike("Kaldoaivi", 2020, false));
    }
    
    @Test
    public void removeHikeRemovesTheHikeFromTheDatabase() {
        c.createNewHike("Kaldoaivi", 2019, false);
        assertEquals(1, c.listPastHikes().size());
        Hike hike = c.getHike("Kaldoaivi");
        c.removeHike(hike);
        assertEquals(0, c.listPastHikes().size());
        assertEquals(null, c.getHike("Kaldoaivi"));
    }
    
    @Test
    public void updateHikeUpdatesHikeCorrectly() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Hike hike = c.getHike("Kaldoaivi");
        assertFalse(hike.isUpcoming());
        assertTrue(0 == hike.getRucksackWeightBeg());
        assertTrue(0 == hike.getRucksackWeightEnd());
        assertEquals(null, hike.getLocationStart());
        assertEquals(null, hike.getLocationEnd());
        hike.setUpcoming(true);
        hike.setRucksackWeightBeg(28.5);
        hike.setRucksackWeightEnd(17.5);
        hike.setLocationStart("Pulmankijärvi");
        hike.setLocationEnd("Adolfin kammi");
        c.updateHike(hike);
        Hike hike2 = c.getHike("Kaldoaivi");
        assertEquals(hike, hike2);
        assertTrue(hike2.isUpcoming());
        assertTrue(28.5 == hike2.getRucksackWeightBeg());
        assertTrue(17.5 == hike2.getRucksackWeightEnd());
        assertEquals("Pulmankijärvi", hike2.getLocationStart());
        assertEquals("Adolfin kammi", hike2.getLocationEnd());
    }
    
    @Test
    public void getHikeReturnsTheHikeWithTheGivenNameIfHikeExistsOrNullIfItDoesNot() {
        Hike h = new Hike("Kaldoaivi", 2019, false);
        c.createNewHike("Kaldoaivi", 2019, false);
        assertEquals(h, c.getHike("Kaldoaivi"));
        assertEquals(null, c.getHike("Kevo"));
    }
    
    @Test
    public void listPastHikesReturnsAListOfPastHikesInDescendingOrder() {
        assertEquals(0, c.listPastHikes().size());
        c.createNewHike("Kevo", 2013, false);
        c.createNewHike("Kaldoaivi", 2019, false);
        c.createNewHike("Lofootit", 2020, true);
        assertEquals(2, hikeDao.list(false).size());
        assertEquals("Kaldoaivi", c.listPastHikes().get(0).getName());
        assertEquals(2019, c.listPastHikes().get(0).getYear());
        assertEquals("Kevo", c.listPastHikes().get(1).getName());
        assertEquals(2013, c.listPastHikes().get(1).getYear());
    }
    
    @Test
    public void listUpcomingHikesReturnsAListOfUpcomingHikesInAscendingOrder() {
        assertEquals(0, c.listUpcomingHikes().size());
        c.createNewHike("Kevo", 2013, false);
        c.createNewHike("Halti", 2021, true);
        c.createNewHike("Lofootit", 2020, true);
        assertEquals(2, hikeDao.list(true).size());
        assertEquals("Lofootit", c.listUpcomingHikes().get(0).getName());
        assertEquals(2020, c.listUpcomingHikes().get(0).getYear());
        assertEquals("Halti", c.listUpcomingHikes().get(1).getName());
        assertEquals(2021, c.listUpcomingHikes().get(1).getYear());
    }
    
    @Test
    public void addDayTripAddTheDayTripToTheGivenHike() {
        c.createNewHike("Kevo", 2013, false);
        Hike hike = c.getHike("Kevo");
        assertTrue(hike != null);
        assertTrue(0 == hike.getKilometres());
        DayTrip dt = new DayTrip(LocalDate.of(2013, 8, 7), "vesiputous", "Guivi", 15.3, 4.5, "aurinkoinen");
        c.addDayTrip(hike, dt);
        assertTrue(15.3 == hike.getKilometres());
    }
    
    @Test
    public void updateDayTripRemovesTheDayTripFromTheGivenHike() {
        c.createNewHike("Kevo", 2013, false);
        Hike hike = c.getHike("Kevo");
        DayTrip dt = new DayTrip(LocalDate.of(2013, 8, 7), "vesiputous", "Guivi", 15.3, 4.5, "aurinkoinen");
        c.addDayTrip(hike, dt);
        assertTrue(15.3 == hike.getKilometres());
        dt.setWalkDist(18.4);
        c.updateDayTrip(hike, dt);
        assertTrue(18.4 == hike.getKilometres());
    }
    
    @Test
    public void addCompanionAddsTheCompanionToTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2018, false);
        Companion comp = new Companion("Pekko");
        Hike hike = c.getHike("Kaldoaivi");
        assertFalse(hike.formatCompanions().contains(comp.getName()));
        c.addCompanion(hike, comp);
        assertTrue(hike.formatCompanions().contains(comp.getName()));
    }
    
    @Test
    public void removeCompanionRemovesCompanionOfTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2017, false);
        Companion comp = new Companion("Pekko");
        Hike hike = c.getHike("Kaldoaivi");
        c.addCompanion(hike, comp);
        assertTrue(hike.formatCompanions().contains("Pekko"));
        c.removeCompanion(hike, comp.getName());
        assertFalse(hike.formatCompanions().contains("Pekko"));
    }

    @Test
    public void addItemAddsTheItemToTheGivenHikeIfHikeDoesNotHaveTheItemYetAndAddsCountIfItAlreadyExists() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Item item = new Item("makuupussi", 1);
        Hike hike = c.getHike("Kaldoaivi");
        assertEquals(0, hike.getItems().size());
        assertFalse(hike.formatEquipment().contains(item.getName()));
        c.addItem(hike, item);
        assertEquals(1, hike.getItems().size());
        assertTrue(hike.formatEquipment().contains(item.getName()));
        item.setCount(5);
        assertTrue(c.addItem(hike, item));
        assertEquals(1, hike.getItems().size());
        assertEquals(5, hike.getItems().get(0).getCount());
    }
    
    @Test
    public void removeItemRemovesTheItemFromTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Item item = new Item("makuupussi", 1);
        Hike hike = c.getHike("Kaldoaivi");
        c.addItem(hike, item);
        assertTrue(hike.formatEquipment().contains(item.getName()));
        c.removeItem(hike, item.getName());
        assertFalse(hike.formatEquipment().contains(item.getName()));
    }
    
    @Test
    public void addMealAddsAMealToTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2020, true);
        Meal meal = new Meal("letut ja vadelmahillo", 4);
        Hike hike = c.getHike("Kaldoaivi");
        assertEquals(0, hike.getMeals().size());
        assertFalse(hike.formatMeals().contains(meal.getName()));
        c.addMeal(hike, meal);
        assertEquals(1, hike.getMeals().size());
        assertTrue(hike.formatMeals().contains(meal.getName()));
    }
    
    @Test
    public void removeMealRemovesTheMealFromTheGivenHike() {
        c.createNewHike("Karhunkierros", 2020, true);
        Meal meal = new Meal("letut ja vadelmahillo", 4);
        Hike hike = c.getHike("Karhunkierros");
        c.addMeal(hike, meal);
        assertEquals(1, hike.getMeals().size());
        assertTrue(hike.formatMeals().contains(meal.getName()));
        assertTrue(c.removeMeal(hike, meal));
        assertEquals(0, hike.getMeals().size());
        assertFalse(hike.formatMeals().contains(meal.getName()));
    }
    
    @Test
    public void createUserSavesUserNameInDB() {
        assertEquals(null, userDao.read());
        c.createUser("Veera");
        assertEquals("Veera", userDao.read().getName());
    }
    
    @Test
    public void changeUserNameChangesUserNameInDB() {
        c.createUser("Veera");
        assertEquals("Veera", userDao.read().getName());
        c.changeUsername("Joni");
        assertEquals("Joni", userDao.read().getName());
    }

    @Test
    public void hello() {}
}
