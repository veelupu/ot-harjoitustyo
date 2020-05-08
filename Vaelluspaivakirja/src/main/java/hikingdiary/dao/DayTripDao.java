/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.DayTrip;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface DayTripDao<T, K> {
    void create(int hikeId, DayTrip dt);
    T read(LocalDate date) throws SQLException;
    void update(int hikeId, DayTrip dt);
    void delete(K key) throws SQLException;
    List<T> list(int hikeId);
    
}
