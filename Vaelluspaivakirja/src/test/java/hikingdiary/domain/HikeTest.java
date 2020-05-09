/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author veeralupunen
 */
public class HikeTest {

    private Hike hike;

    public HikeTest() {
    }

    @Before
    public void setUp() {
        hike = new Hike("Kaldoaivi", 2019, false);
    }

    @Test
    public void toStringReturnsAHikeInSpecifiedForm() {
        assertEquals("Kaldoaivi 2019", hike.toString());
    }

    @Test
    public void compareToWorksAsWanted() {
        Hike h1 = new Hike("Kevo", 2013, false);
        Hike h2 = new Hike("Lofootit", 2020, true);
        Hike h3 = new Hike("Kaldoaivi", 2019, false);
        assertEquals(1, hike.compareTo(h1));
        assertEquals(-1, hike.compareTo(h2));
        assertEquals(0, hike.compareTo(h3));
    }

    @Test
    public void equalsWorksAsWanted() {
        Hike r = new Hike("Kaldoaivi", 2019, false);
        Hike w = new Hike("Lofootit", 2019, false);
        User u = new User("Antti");
        assertTrue(hike.equals(hike));
        assertTrue(hike.equals(r));
        assertFalse(hike.equals(w));
        assertFalse(hike.equals(u));
    }
    
    @Test
    public void setCompanionsWorksAsWanted() {
        ArrayList<String> comp = new ArrayList<>();
        comp.add("Antti");
        comp.add("Pekko");
        comp.add("Kaisu");
        hike.setCompanions(comp);
        assertEquals("Antti\nKaisu\nPekko\n", hike.formatCompanions());
    }

    @Test
    public void addCompanionWorksAsWanted() {
        String name = "Antti";
        assertTrue(hike.addCompanion(name));
        assertTrue(hike.formatCompanions().contains(name));
        assertFalse(hike.addCompanion(name));
    }

    @Test
    public void formatCompanionsWorksAsWanted() {
        hike.addCompanion("Antti");
        hike.addCompanion("Pekko");
        hike.addCompanion("Kaisu");
        assertEquals("Antti\nKaisu\nPekko\n", hike.formatCompanions());
        assertFalse(hike.formatCompanions().contains("Miitta"));
    }

    @Test
    public void getKilometresWorksAsWanted() {
        LocalDate date1 = LocalDate.of(2019, 7, 19);
        LocalDate date2 = LocalDate.of(2019, 7, 20);
        hike.addDayTrip(new DayTrip(date1, "a", "b", 15, 5, "ok"));
        hike.addDayTrip(new DayTrip(date2, "a", "b", 22, 7, "not ok"));
        assertTrue(37 == hike.getKilometres());
    }

    @Test
    public void hello() {
    }
}
