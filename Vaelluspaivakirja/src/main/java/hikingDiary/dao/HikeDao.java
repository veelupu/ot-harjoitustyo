/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import java.sql.*;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface HikeDao<T, K> {
    void createHike(T object);
    void createCompanion(Hike hike, Companion comp);
    Hike read(String name);
    void updateHike(Hike hike);
    void updateCompanion(Hike hike, Companion comp);
    void delete(K key) throws SQLException;
    List<T> list();
    List<T> listPastHikes() throws SQLException;
    List<T> listUpcomingHikes() throws SQLException;
//    double getRucksacWeightBeg(String hikeName);
//    double getRucksacWeightEnd(String hikeName);
//    boolean addRucksacWeightBeg(double weight, String hikeName);
//    boolean addRucksacWeightEnd(double weight, String hikeName);
}
