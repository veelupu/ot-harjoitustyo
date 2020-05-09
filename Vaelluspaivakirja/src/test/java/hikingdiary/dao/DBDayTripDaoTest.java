/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.DayTrip;
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
public class DBDayTripDaoTest {
    
    private DBDayTripDao dtDao;
    private DayTrip dt;
    private LocalDate date;
    
    public DBDayTripDaoTest() {
    }
    
    @Before
    public void setUp() {
        dtDao = new DBDayTripDao("hikesTest.db");
        date = LocalDate.of(2019, 7, 19);
        dt = new DayTrip(date, "Pulmankijärvi", "se pieni lampi", 12.6, 4, "pilvistä");
        dtDao.create(1, dt);
    }
    
    @After
    public void tearDown() {
        File file = new File(dtDao.getDBAdress());
        file.delete();
    }
    
    @Test
    public void createDayTripAddsDayTripToTheDatabase() {
        assertEquals(date, dtDao.read(date).getDate());
        assertEquals("Pulmankijärvi", dtDao.read(date).getStartingPoint());
        assertEquals("se pieni lampi", dtDao.read(date).getEndingPoint());
        assertTrue(12.6 == dtDao.read(date).getWalkDist());
        assertTrue(4 == dtDao.read(date).getWalkTime());
        assertEquals("pilvistä", dtDao.read(date).getWeather());
    }
    
    @Test
    public void updateUpdatesAllTheElementsOfTheDayTripExceptDate() {
        dt.setStartingPoint("Pulmankijärvi (parkkis)");
        dt.setEndingPoint("pikkupuron ranta");
        dt.setWalkDist(15.4);
        dt.setWalkTime(5.2);
        dt.setWeather("pilvistä ja tuulista");
        dtDao.update(dt);
        assertEquals(date, dtDao.read(date).getDate());
        assertEquals("Pulmankijärvi (parkkis)", dtDao.read(date).getStartingPoint());
        assertEquals("pikkupuron ranta", dtDao.read(date).getEndingPoint());
        assertTrue(15.4 == dtDao.read(date).getWalkDist());
        assertTrue(5.2 == dtDao.read(date).getWalkTime());
        assertEquals("pilvistä ja tuulista", dtDao.read(date).getWeather());
    }

    @Test
    public void hello() {}
}
