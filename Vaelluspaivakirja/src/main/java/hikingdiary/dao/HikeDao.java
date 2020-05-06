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
public interface HikeDao<T, K> {
    void createHike(T object);
    void createCompanion(Hike hike, Companion comp);
    void createItem(Hike hike, Item item);
    void createMeal(Hike hike, Meal meal);
    Hike readHike(String name);
//    Item readItem(String name);
    void updateHike(Hike hike);
//    void updateHikeItem(Hike hike, Item item);
    void delete(K key) throws SQLException;
    void deleteCompanion(Hike hike, String name);
    List<T> list();
    List<T> listPastHikes();
    List<T> listUpcomingHikes();
    Map<String, Item> listItems();
}
