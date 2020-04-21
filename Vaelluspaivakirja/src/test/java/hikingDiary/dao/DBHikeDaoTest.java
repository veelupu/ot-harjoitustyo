/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import java.io.File;
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
        assertEquals(hike, hikeDao.read("Kaldoaivi"));
    }
    
    @Test
    public void createCompanionWorksAsWanted() {
        Companion comp = new Companion("Pekko");
        hikeDao.createCompanion(hike, comp);
        assertTrue(hikeDao.read("Kaldoaivi").formatCompanions().contains("Pekko"));
    }
    
    @Test
    public void updatingHikeUpdatesHikeWithGivenUpdates() {
        hike.setUpcoming(true);
        hikeDao.updateHike(hike);
        assertTrue(hikeDao.read("Kaldoaivi").isUpcoming());
    }
    
    @Test
    public void listReturnsListOfHikes() {
        Hike hike2 = new Hike("Kevo", 2013, false);
        hikeDao.createHike(hike2);
        assertTrue(hikeDao.list().contains(hike));
        assertTrue(hikeDao.list().contains(hike2));
    }
    
    @Test
    public void listPastHikesReturnsListOfPastHikes() {
        Hike hike2 = new Hike("Kevo", 2013, false);
        hikeDao.createHike(hike2);
        Hike hike3 = new Hike("Lofootit", 2020, true);
        hikeDao.createHike(hike3);
        assertTrue(hikeDao.listPastHikes().contains(hike));
        assertTrue(hikeDao.listPastHikes().contains(hike2));
        assertFalse(hikeDao.listPastHikes().contains(hike3));
    }
    
    @Test
    public void listUpcomingHikesReturnsListOfUpcomingHikes() {
        Hike hike2 = new Hike("Halti", 2021, true);
        hikeDao.createHike(hike2);
        Hike hike3 = new Hike("Lofootit", 2020, true);
        hikeDao.createHike(hike3);
        assertFalse(hikeDao.listUpcomingHikes().contains(hike));
        assertTrue(hikeDao.listUpcomingHikes().contains(hike2));
        assertTrue(hikeDao.listUpcomingHikes().contains(hike3));
    }

    @Test
    public void hello() {}
}
