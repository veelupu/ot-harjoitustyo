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
public class MealTest {
    
    Meal meal0;
    Meal meal1;
    Meal meal2;
    Meal meal3;
    Meal meal4;
    Meal meal5;
    
    public MealTest() {
    }
    
    @Before
    public void setUp() {
        meal0 = new Meal("pähkinäsekoitus", 0);
        meal1 = new Meal("puuro", 1);
        meal2 = new Meal("pestopasta", 2);
        meal3 = new Meal("muussi ja falafel", 3);
        meal4 = new Meal("suklaamousse", 4);
        meal5 = new Meal("kaakao", 5);
    }
    

    @Test
    public void getCategoryNameReturnsCorrectName() {
        assertEquals("Snack", meal0.getCategoryName());
        assertEquals("Breakfast", meal1.getCategoryName());
        assertEquals("Lunch", meal2.getCategoryName());
        assertEquals("Dinner", meal3.getCategoryName());
        assertEquals("Dessert", meal4.getCategoryName());
        
    }
    
    @Test
    public void compareToReturnsCorrectNumber() {
        assertEquals(-1, meal0.compareTo(meal1));
        assertEquals(1, meal3.compareTo(meal2));
        assertEquals(1, meal4.compareTo(new Meal("panna cotta", 4)));
        assertEquals(0, meal3.compareTo(meal3));
    }

    @Test
    public void hello() {}
}
