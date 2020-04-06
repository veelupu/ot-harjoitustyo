/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.domain;

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
        Hike w1 = new Hike("Lofootit", 2019, false);
        Hike w2 = new Hike("Kaldoaivi", 2020, false);
        Hike w3 = new Hike("Kaldoaivi", 2019, true);
        User u = new User("Antti");
        assertTrue(hike.equals(hike));
        assertTrue(hike.equals(r));
        assertFalse(hike.equals(w1));
        assertFalse(hike.equals(w2));
        assertFalse(hike.equals(w3));
        assertFalse(hike.equals(u));
    }

    @Test
    public void hello() {}
}
