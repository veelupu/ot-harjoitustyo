/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import java.sql.*;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface HikeDao<T, K> {
    void create(T object) throws SQLException;
    T read(K key) throws SQLException;
    T update(T object) throws SQLException;
    void delete(K key) throws SQLException;
    List<T> list() throws SQLException;
    List<T> listPastHikes() throws SQLException;
    List<T> listUpcomingHikes() throws SQLException;
}
