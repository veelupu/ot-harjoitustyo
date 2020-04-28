/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.User;
import java.sql.*;
import java.util.*;

/**
 *
 * @author veeralupunen
 */
public interface UserDao<T> {
    void create(T user);
    User read();
    boolean update(T user, String newName);
    boolean delete(T user);

}
