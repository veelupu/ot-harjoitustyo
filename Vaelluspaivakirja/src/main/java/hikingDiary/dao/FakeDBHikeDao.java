/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Hike;
import java.util.*;
import java.sql.*;

/**
 *
 * @author veeralupunen
 */
public class FakeDBHikeDao implements HikeDao<Hike, Integer> {

    private String homeAddress;
    private Connection connection;
    public HashMap<String, Hike> hikes;

    public FakeDBHikeDao() {
        hikes = new HashMap<>();
    }
    
    public int getSizeOfTheHashMap() {
        return hikes.size();
    }
    
    public void emptyHashMap() {
        hikes.clear();
    }

    @Override
    public void create(Hike hike) {
        hikes.put(hike.getName(), hike);
    }

    @Override
    public Hike read(String name) {
        if (hikes.containsKey(name)) {
            return hikes.get(name);
        }
        return null;
    }

    @Override
    public Hike update(Hike object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Hike> list() {
        List<Hike> hikesToReturn = new ArrayList<>();
        for (String hike: hikes.keySet()) {
            hikesToReturn.add(hikes.get(hike));
        }
        return hikesToReturn;
    }

    @Override
    public List<Hike> listPastHikes() throws SQLException {
        List<Hike> hikesToReturn = new ArrayList<>();
        for (String hike: hikes.keySet()) {
            if (!hikes.get(hike).isUpcoming()) {
                hikesToReturn.add(hikes.get(hike));
            }
        }
        Collections.sort(hikesToReturn);
        Collections.reverse(hikesToReturn);
        return hikesToReturn;
    }

    @Override
    public List<Hike> listUpcomingHikes() throws SQLException {
        List<Hike> hikesToReturn = new ArrayList<>();
        for (String hike: hikes.keySet()) {
            if (hikes.get(hike).isUpcoming()) {
                hikesToReturn.add(hikes.get(hike));
            }
        }
        Collections.sort(hikesToReturn);
        return hikesToReturn;
    }
}