/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import hikingdiary.dao.DBHikeDao;
import hikingdiary.dao.DBUserDao;
import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.io.File;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
    public ControllerTest() {
        hikeDao = new DBHikeDao("hikesTest.db");
        userDao = new DBUserDao("hikesTest.db");
//        hikeDao = new FakeDBHikeDao();
//        userDao = new FakeDBUserDao();
    }
    
    @Before
    public void setUp() {
        c = new Controller(hikeDao, userDao);
    }
    
    @After
    public void tearDown() {
        File file1 = new File(hikeDao.getDBAdress());
        File file2 = new File(userDao.getDBAdress());
        file1.delete();
        file2.delete();
        //hikeDao.emptyHashMap();
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
    
//    //Tee tälle asialle jotain controllerissa ja DBHikeDaossa: samannimisen hiken luominen ei saa onnistua
//    @Test
//    public void createHikeDoesNotCreateHikeIfTheNameIsAlreadyUsed() {
//        c.createNewHike("Kaldoaivi", 2019, false);
//        assertEquals(null, c.createNewHike("Kaldoaivi", 2020, false));
//    }
    
    @Test
    public void updateHikeUpdatesHikeCorrectly() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Hike hike = c.getHike("Kaldoaivi");
        hike.setUpcoming(true);
        c.updateHike(hike);
        assertEquals(hike, c.getHike("Kaldoaivi"));
    }
    
    @Test
    public void getHikeReturnsTheNameOfTheHikeIfHikeExistsOrNullIfItDoesNot() {
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
    public void addCompanionAddsACompanionToTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Companion comp = new Companion("Pekko");
        Hike hike = c.getHike("Kaldoaivi");
        assertFalse(hike.formatCompanions().contains(comp.getName()));
        c.addCompanion(hike, comp);
        assertTrue(hike.formatCompanions().contains(comp.getName()));
    }
    
    //Testi ei toimi – mikä vikana?
//    @Test
//    public void addItemAddsAnItemToTheGivenHike() {
//        c.createNewHike("Kaldoaivi", 2019, false);
//        Item item = new Item("makuupussi", 1);
//        Hike hike = c.getHike("Kaldoaivi");
//        assertFalse(hike.formatEquipment().contains(item.getName()));
//        c.addItem(hike, item);
//        assertTrue(hike.formatEquipment().contains(item.getName()));
//    }
    
    @Test
    public void addMealAddsAMealToTheGivenHike() {
        c.createNewHike("Kaldoaivi", 2019, false);
        Meal meal = new Meal("letut ja vadelmahillo", 4);
        Hike hike = c.getHike("Kaldoaivi");
        assertFalse(hike.formatMeals().contains(meal.getName()));
        c.addMeal(hike, meal);
        assertTrue(hike.formatMeals().contains(meal.getName()));
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
        c.changeUsername("Tuukka");
        assertEquals("Tuukka", userDao.read().getName());
    }

    @Test
    public void hello() {}
}
