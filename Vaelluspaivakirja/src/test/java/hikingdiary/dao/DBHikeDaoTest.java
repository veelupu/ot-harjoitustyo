/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Item;
import hikingdiary.domain.Meal;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author veeralupunen
 */
public class DBHikeDaoTest {
    
    private DBHikeDao hikeDao;
    private Hike hike;
    
    public DBHikeDaoTest() {
    }
    
    @Before
    public void setUp() {
        hikeDao = new DBHikeDao("hikesTest.db");
        hike = new Hike("Kaldoaivi", 2019, false);
        hikeDao.createHike(hike);
    }
    
    @After
    public void tearDown() {
        File file = new File(hikeDao.getDBAdress());
        file.delete();
    }

    @Test
    public void createHikeCreatesAHikeAndSavesItToTheDB() {
        assertEquals(hike, hikeDao.readHike("Kaldoaivi"));
    }
    
    @Test
    public void readHikeReturnsAHikeWithAllItsElements() {
        Companion comp = new Companion("Antti");
        Item item = new Item("trangia", 1);
        Meal meal = new Meal("chili con papu", 3);
        hikeDao.createCompanion(hike, comp);
        hikeDao.createItem(hike, item);
        hikeDao.createMeal(hike, meal);
        Hike h = hikeDao.readHike("Kaldoaivi");
        assertTrue(h.formatCompanions().contains("Antti"));
        assertTrue(h.formatEquipment().contains("trangia"));
        assertTrue(h.formatMeals().contains("chili"));
        assertEquals(1, h.getMeals().size());
    }
    
    @Test
    public void createCompanionWorksAsWanted() {
        Companion comp = new Companion("Pekko");
        hikeDao.createCompanion(hike, comp);
        assertTrue(hikeDao.readHike("Kaldoaivi").formatCompanions().contains("Pekko"));
    }
    
    @Test
    public void updatingHikeUpdatesHikeWithGivenUpdates() {
        hike.setUpcoming(true);
        hikeDao.updateHike(hike);
        assertTrue(hikeDao.readHike("Kaldoaivi").isUpcoming());
    }
    
    @Test
    public void listReturnsListOfHikes() {
        Hike hike2 = new Hike("Kevo", 2013, false);
        hikeDao.createHike(hike2);
        assertTrue(hikeDao.list(false).contains(hike));
        assertTrue(hikeDao.list(false).contains(hike2));
    }
    
    @Test
    public void listPastHikesReturnsListOfPastHikes() {
        Hike hike2 = new Hike("Kevo", 2013, false);
        hikeDao.createHike(hike2);
        Hike hike3 = new Hike("Lofootit", 2020, true);
        hikeDao.createHike(hike3);
        assertTrue(hikeDao.list(false).contains(hike));
        assertTrue(hikeDao.list(false).contains(hike2));
        assertFalse(hikeDao.list(false).contains(hike3));
    }
    
    @Test
    public void listUpcomingHikesReturnsListOfUpcomingHikes() {
        Hike hike2 = new Hike("Halti", 2021, true);
        hikeDao.createHike(hike2);
        Hike hike3 = new Hike("Lofootit", 2020, true);
        hikeDao.createHike(hike3);
        assertFalse(hikeDao.list(true).contains(hike));
        assertTrue(hikeDao.list(true).contains(hike2));
        assertTrue(hikeDao.list(true).contains(hike3));
    }

    @Test
    public void hello() {}
}
