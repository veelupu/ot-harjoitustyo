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
    public void readHikeReturnsTheHikeWithAllItsElementsIfExistingAndNullIfNot() {
        Companion comp = new Companion("Antti");
        Item item = new Item("trangia", 1);
        Meal meal = new Meal("chili con papu", 3);
        hikeDao.createCompanion(hike, comp);
        hikeDao.createItem(hike, item);
        hikeDao.createMeal(hike, meal);
        Hike h = hikeDao.readHike("Kaldoaivi");
        assertEquals(hike, h);
        assertTrue(h.formatCompanions().contains("Antti"));
        assertTrue(h.formatEquipment().contains("trangia"));
        assertTrue(h.formatMeals().contains("chili"));
        assertEquals(1, h.getMeals().size());
        assertEquals(null, hikeDao.readHike("Kevo"));
    }
    
    @Test
    public void createCompanionWorksAsWanted() {
        Companion comp = new Companion("Pekko");
        hikeDao.createCompanion(hike, comp);
        assertTrue(hikeDao.readHike("Kaldoaivi").formatCompanions().contains("Pekko"));
        Hike hike2 = new Hike("Halti", 2021, true);
        hikeDao.createHike(hike2);
        hikeDao.createCompanion(hike2, comp);
        assertTrue(hikeDao.readHike("Halti").formatCompanions().contains("Pekko"));
    }
    
    @Test
    public void deleteCompanionRemovesCompanionFromTheDatabase() {
        Companion comp = new Companion("Kaisu");
        hikeDao.createCompanion(hike, comp);
        assertTrue(hikeDao.readHike("Kaldoaivi").formatCompanions().contains("Kaisu"));
        hikeDao.deleteCompanion(hike, comp.getName());
        assertFalse(hikeDao.readHike("Kaldoaivi").formatCompanions().contains("Kaisu"));
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
    public void deleteHikeRemovesHikeFromDatabase() {
        assertTrue(hikeDao.list(false).contains(hike));
        hikeDao.deleteHike("Kaldoaivi");
        assertFalse(hikeDao.list(false).contains(hike));
    }

    @Test
    public void createMealWorksAsWantedWhenTheMealDoesNotExistYet() {
        assertTrue(hikeDao.readHike("Kaldoaivi").getMeals().isEmpty());
        Meal meal = new Meal("chili con carmé", 3);
        meal.addIngredient("papu");
        meal.addIngredient("riisi");
        meal.addIngredient("maustepussi");
        hikeDao.createMeal(hike, meal);
        assertTrue(hikeDao.readHike("Kaldoaivi").getMeals().contains(meal));
        assertEquals(3, hikeDao.readHike("Kaldoaivi").getMeals().get(0).getCategory());
        assertEquals(3, hikeDao.readHike("Kaldoaivi").getMeals().get(0).getIngredients().size());
        assertTrue(hikeDao.readHike("Kaldoaivi").getMeals().get(0).getIngredients().contains("papu"));
    }
    
    @Test
    public void deleteMealRemovesMealFromDatabase() {
        Meal meal = new Meal("suklaamousse", 4);
        hikeDao.createMeal(hike, meal);
        assertTrue(hikeDao.readHike("Kaldoaivi").getMeals().contains(meal));
        hikeDao.deleteMeal(hike, meal);
        assertFalse(hikeDao.readHike("Kaldoaivi").getMeals().contains(meal));
    }
    
    @Test
    public void createMealWorksAsWantedWhenTheMealExistsAlready() {
        Meal meal = new Meal("chili con carmé", 3);
        meal.addIngredient("papu");
        meal.addIngredient("riisi");
        meal.addIngredient("maustepussi");
        hikeDao.createMeal(hike, meal);
        Hike hike2 = new Hike("Halti", 2021, true);
        hikeDao.createHike(hike2);
        hikeDao.createMeal(hike2, meal);
        assertTrue(hikeDao.readHike("Halti").getMeals().contains(meal));
        assertEquals(3, hikeDao.readHike("Halti").getMeals().get(0).getCategory());
        assertEquals(3, hikeDao.readHike("Halti").getMeals().get(0).getIngredients().size());
        assertTrue(hikeDao.readHike("Halti").getMeals().get(0).getIngredients().contains("riisi"));
    }
    
    @Test
    public void createItemAddsItemToTheDatabaseOrAddsNewConnectionBetweenItemAndHike() {
        Item item = new Item("trangia", 1);
        hikeDao.createItem(hike, item);
        assertEquals("trangia", hikeDao.readHike("Kaldoaivi").getItems().get(0).getName());
        assertEquals(1, hikeDao.readHike("Kaldoaivi").getItems().get(0).getCount());
        Hike hike2 = new Hike("Halti", 2021, true);
        hikeDao.createHike(hike2);
        hikeDao.createItem(hike2, item);
        assertEquals("trangia", hikeDao.readHike("Halti").getItems().get(0).getName());
        assertEquals(1, hikeDao.readHike("Halti").getItems().get(0).getCount());
    }
    
    @Test
    public void createItemsAddsCountToTheItemIfTheHikeAlreadyHasIt() {
        Item item = new Item("trangia", 1);
        hikeDao.createItem(hike, item);
        item.setCount(5);
        hikeDao.createItem(hike, item);
        assertEquals(5, hikeDao.readHike("Kaldoaivi").getItems().get(0).getCount());
    }
    
    @Test
    public void deleteItemRemovesItemFromTheDatabase() {
        Item item = new Item("trangia", 1);
        hikeDao.createItem(hike, item);
        assertEquals(1, hikeDao.readHike("Kaldoaivi").getItems().size());
        hikeDao.deleteItem(hike, item.getName());
        assertEquals(0, hikeDao.readHike("Kaldoaivi").getItems().size());
    }

    @Test
    public void hello() {}
}
