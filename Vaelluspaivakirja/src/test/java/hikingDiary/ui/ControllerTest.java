/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.dao.FakeDBHikeDao;
import hikingdiary.dao.FakeDBUserDao;
import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
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
    private User u;
    private FakeDBHikeDao hikeDao;
    private FakeDBUserDao userDao;
    
    public ControllerTest() {
        u = new User("Veera");
        hikeDao = new FakeDBHikeDao();
        userDao = new FakeDBUserDao();
    }
    
    @Before
    public void setUp() {
        c = new Controller(hikeDao, userDao);
    }
    
    @After
    public void tearDown() {
        hikeDao.emptyHashMap();
    }
    
//    @Test
//    public void createHikeCreatesANewHikeAndAddsItToTheHashMap() {
//        assertTrue(hikeDao.hikes.isEmpty());
//        c.createNewHike("Kaldoaivi", 2019, false);
//        assertEquals(1, hikeDao.getSizeOfTheHashMap());
//        assertEquals("Kaldoaivi", hikeDao.hikes.get("Kaldoaivi").getName());
//        assertEquals(2019, hikeDao.hikes.get("Kaldoaivi").getYear());
//        assertFalse(hikeDao.hikes.get("Kaldoaivi").isUpcoming());
//    }
    
//    @Test
//    public void getHikeReturnsTheNameOfTheHikeIfHikeExistsOrNullIfItDoesNot() {
//        Hike h = new Hike("Kaldoaivi", 2019, false);
//        c.createNewHike("Kaldoaivi", 2019, false);
//        assertEquals(h, c.getHike("Kaldoaivi"));
//        assertEquals(null, c.getHike("Kevo"));
//    }
    
//    @Test
//    public void listPastHikesReturnsAListOfPastHikesInDescendingOrder() {
//        c.createNewHike("Kevo", 2013, false);
//        c.createNewHike("Kaldoaivi", 2019, false);
//        c.createNewHike("Lofootit", 2020, true);
//        assertEquals(3, hikeDao.getSizeOfTheHashMap());
//        assertEquals(2, c.listPastHikes().size());
//        assertEquals("Kaldoaivi", c.listPastHikes().get(0).getName());
//        assertEquals(2019, c.listPastHikes().get(0).getYear());
//        assertEquals("Kevo", c.listPastHikes().get(1).getName());
//        assertEquals(2013, c.listPastHikes().get(1).getYear());
//    }
    
//    @Test
//    public void listUpcomingHikesReturnsAListOfUpcomingHikesInAscendingOrder() {
//        c.createNewHike("Kevo", 2013, false);
//        c.createNewHike("Halti", 2021, true);
//        c.createNewHike("Lofootit", 2020, true);
//        assertEquals(3, hikeDao.getSizeOfTheHashMap());
//        assertEquals("Lofootit", c.listUpcomingHikes().get(0).getName());
//        assertEquals(2020, c.listUpcomingHikes().get(0).getYear());
//        assertEquals("Halti", c.listUpcomingHikes().get(1).getName());
//        assertEquals(2021, c.listUpcomingHikes().get(1).getYear());
//    }

    @Test
    public void hello() {}
}
