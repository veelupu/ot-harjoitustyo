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
public interface UserDao<T, K> {
    void create(T object) throws SQLException;
    T read(K key) throws SQLException;
    T update(T object) throws SQLException;
    void delete(K key) throws SQLException;
    List<T> list() throws SQLException;
}
