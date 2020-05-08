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
public interface DayTripDao {
    int create(int hikeId, DayTrip dt);
    DayTrip read(LocalDate date);
    int update(DayTrip dt);
    int delete(DayTrip dt);
    List<DayTrip> list(int hikeId);
    
}
