package hikingdiary.ui;

/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */

import hikingdiary.domain.User;
import hikingdiary.ui.UserInterface;
import hikingdiary.domain.Controller;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;
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
public class UserInterfaceTest {
    
    User u;
    String quit;
    String pastHike1;
    String pastHike2;
    String upcomingHike1;
    String upcomingHike2;
    
    public UserInterfaceTest() {
    }
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    
    @Before
    public void setUp() {
        u = new User("Tuukka");
        quit = "5\nYes\n";
        pastHike1 = "1\nKaldoaivi\n2019\nA\n";
        pastHike2 = "1\nKevo\n2013\nA\n";
        upcomingHike1 = "1\nLofootit\n2020\nB\n";
        upcomingHike2 = "1\nHalti\n2021\nB\n";
    }
    
//    @After
//    public void tearDown() {
//    }

    @Test
    public void createdUserInterfaceExists() {
        UserInterface ui = new UserInterface(u);
        assertTrue(ui != null);
    }
    
//    @Test
//    public void hikesListIsFirstEmpty() {
//        UserInterface ui = new UserInterface(u);
//        assertEquals(0, ui.c.listPastHikes().size());
//    }
    
    @Test
    public void userHasTheGivenName() {
        UserInterface ui = new UserInterface(u);
        assertEquals("Tuukka", ui.user.getName());
    }
    
    //Option 1: Create a new hike
//    @Test
//    public void mainMenuOption1WorksForAPastHike() {
//        InputStream testInput = IOUtils.toInputStream(pastHike1 + quit, java.nio.charset.StandardCharsets.UTF_8);
//        UserInterface ui = new UserInterface(testInput, u);
//        ui.start();
//        assertEquals(1, ui.c.hikes.size());
//        assertEquals("Kaldoaivi", ui.c.hikes.get("Kaldoaivi").getName());
//        assertEquals(2019, ui.c.hikes.get("Kaldoaivi").getYear());
//        assertEquals(false, ui.c.hikes.get("Kaldoaivi").isUpcoming());
//    }
//    
//    @Test
//    public void mainMenuOption1WorksForAnUpcomingtHike() {
//        InputStream testInput = IOUtils.toInputStream(upcomingHike1 + quit, java.nio.charset.StandardCharsets.UTF_8);
//        UserInterface ui = new UserInterface(testInput, u);
//        ui.start();
//        assertEquals(1, ui.c.hikes.size());
//        assertEquals("Lofootit", ui.c.hikes.get("Lofootit").getName());
//        assertEquals(2020, ui.c.hikes.get("Lofootit").getYear());
//        assertEquals(true, ui.c.hikes.get("Lofootit").isUpcoming());
//    }
    
    //Option 2: Past hikes
//    @Test
//    public void mainMenuOption2Works() {
//        InputStream testInput = IOUtils.toInputStream(pastHike1 + pastHike2 + "2\n" + quit, java.nio.charset.StandardCharsets.UTF_8);
//        UserInterface ui = new UserInterface(testInput, u);
//        ui.start();
//        assertEquals("Kaldoaivi", ui.c.listPastHikes().get(0).getName());
//        assertEquals(2019, ui.c.listPastHikes().get(0).getYear());
//        assertEquals("Kevo", ui.c.listPastHikes().get(1).getName());
//        assertEquals(2013, ui.c.listPastHikes().get(1).getYear());
//    }
    
    //Option 3: Upcoming hikes
    public void mainMenuOption3Works() {
        InputStream testInput = IOUtils.toInputStream(upcomingHike1 + upcomingHike2 + "3\n" + quit, java.nio.charset.StandardCharsets.UTF_8);
        UserInterface ui = new UserInterface(testInput, u);
        ui.start();
        assertEquals("Lofootit", ui.c.listPastHikes().get(0).getName());
        assertEquals(2020, ui.c.listPastHikes().get(0).getYear());
        assertEquals("Halti", ui.c.listPastHikes().get(1).getName());
        assertEquals(2021, ui.c.listPastHikes().get(1).getYear());
    }
    
    //Option 4: Settings
    @Test
    public void mainMenuOption4WorksWhenNameIsChanged() {
        InputStream testInput = IOUtils.toInputStream("4\nYes\nVeera\n5\nYes\n", java.nio.charset.StandardCharsets.UTF_8);
        UserInterface ui = new UserInterface(testInput, u);
        ui.start();
        assertEquals("Veera", ui.user.getName());
    }
    
    @Test
    public void mainMenuOption4WorksWhenNameIsNotChanged() {
        InputStream testInput = IOUtils.toInputStream("4\nNo\n5\nYes\n", java.nio.charset.StandardCharsets.UTF_8);
        UserInterface ui = new UserInterface(testInput, u);
        ui.start();
        assertEquals("Tuukka", ui.user.getName());
    }
    
     @Test
     public void hello() {}
}
