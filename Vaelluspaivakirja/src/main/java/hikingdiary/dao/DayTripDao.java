/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.DayTrip;
import java.sql.*;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface DayTripDao<T, K> {
    void create(int id, DayTrip dt);
    T read(K key) throws SQLException;
    T update(T object) throws SQLException;
    void delete(K key) throws SQLException;
    List<T> list() throws SQLException;
    
}
