/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.time.LocalDate;
import java.time.Month;
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
public class DayTripTest {
    
    private DayTrip dt;
    
    public DayTripTest() {
    }
    
    @Before
    public void setUp() {
        dt = new DayTrip(LocalDate.of(2016, 5, 4), "Hetta", "Hannukuru", 15.8, 4, "pilvistä ja sateista");
    }
    
    @Test
    public void getWeekdayReturnsTheCorrecNameOfTheWeekDay() {
        assertEquals("Wednesday", dt.getWeekday());
        dt.setDate(LocalDate.of(2020, 5, 10));
        assertEquals("Sunday", dt.getWeekday());
        dt.setDate(LocalDate.of(2019, 7, 22));
        assertEquals("Monday", dt.getWeekday());
        dt.setDate(LocalDate.of(2019, 7, 23));
        assertEquals("Tuesday", dt.getWeekday());
        dt.setDate(LocalDate.of(2019, 7, 25));
        assertEquals("Thursday", dt.getWeekday());
        dt.setDate(LocalDate.of(2019, 7, 26));
        assertEquals("Friday", dt.getWeekday());
        dt.setDate(LocalDate.of(2019, 7, 27));
        assertEquals("Saturday", dt.getWeekday());
    }
    
    @Test
    public void compareToWorksAsWanted() {
        DayTrip dt2 = new DayTrip(LocalDate.of(2017, 6, 8), "Liesjärvi", "laavu");
        assertEquals(1, dt.compareTo(dt2));
        assertEquals(-1, dt2.compareTo(dt));
        assertEquals(0, dt.compareTo(dt));
    }
    
    @Test
    public void toStringFormsAStringAsWanted() {
        assertEquals("Wednesday 4.5.2016 15.8 km", dt.toString());
    }

    @Test
    public void hello() {}
}
