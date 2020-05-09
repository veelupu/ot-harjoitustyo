/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.User;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author veeralupunen
 */
public class DBUserDaoTest {
    
    private DBUserDao userDao;
    private User user;
    
    public DBUserDaoTest() {
    }
    
    @Before
    public void setUp() {
        userDao = new DBUserDao("hikesTest.db");
        user = new User("Veera");
        userDao.create(user);
    }
    
    @After
    public void tearDown() {
        File file = new File(userDao.getDBAdress());
        file.delete();
    }

    @Test
    public void createCreatesUserAndSavesItToTheDB() {
        assertEquals("Veera", userDao.read().getName());
    }
    
    @Test
    public void updateUserChangesUsernameToTheGivenName() {
        userDao.update(user, "Pekko");
        assertEquals("Pekko", userDao.read().getName());
    }
    
    @Test
    public void deleteRemovesUserFromDB() {
        userDao.delete(user);
        assertEquals(null, userDao.read());
    }
    
     @Test
     public void hello() {}
}
