/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Item;
import hikingdiary.domain.Meal;
import java.sql.*;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface HikeDao {
    int createHike(Hike hike);
    int createCompanion(Hike hike, Companion comp);
    int createItem(Hike hike, Item item);
    int createMeal(Hike hike, Meal meal);
    Hike readHike(String name);
    int updateHike(Hike hike);
    int deleteHike(String name);
    int deleteCompanion(Hike hike, String name);
    int deleteMeal(Hike hike, String name);
    int deleteItem(Hike hike, String name);
    List<Hike> list(boolean upcoming);
}
