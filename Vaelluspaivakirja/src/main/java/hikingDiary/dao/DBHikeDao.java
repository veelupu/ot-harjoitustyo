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
public class DBHikeDao implements HikeDao<Hike, Integer> {

    private String homeAddress;
    private Connection connection;

    public DBHikeDao() {
        homeAddress = System.getProperty("user.home");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + homeAddress + "/.hikes.db");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            System.err.println(e.getMessage());
        }
        try {
            createHikeTable();
        } catch (Exception e) {
            System.err.println("Table creation failed.");
            System.err.println(e.getMessage());
        }
    }

    public void createHikeTable() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("BEGIN TRANSACTION");
        s.execute("PRAGMA foreign_keys = ON");
        s.execute("CREATE TABLE IF NOT EXISTS Hikes (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, year INTEGER NOT NULL, upcoming INTEGER NOT NULL, rucksacBeg INTEGER, rucksacEnd INTEGER)");
        s.execute("COMMIT");
    }

    @Override
    public void create(Hike hike) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hikes (name, year, upcoming) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, hike.getName());
            ps.setInt(2, hike.getYear());
            ps.setBoolean(3, hike.isUpcoming());

            int executeUpdate = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            hike.setId(id);

            System.out.println("Create hike: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Hike creation failed.");
            System.err.println(e.getMessage());
        }
        //connection.close(); //tee tälle oma metodi ja kutsu sitä kun lopetetaan
    }

    @Override
    public Hike read(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            boolean upcoming = rs.getBoolean("upcoming");
            Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), upcoming, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));

            ps.close();
            rs.close();
            return hike;
        } catch (SQLException e) {
            System.err.println("Hike get failed.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Hike hike) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Hikes SET name=?, year=?, upcoming=?, rucksacBeg=?, rucksacEnd=? WHERE id=?");
            ps.setString(1, hike.getName());
            ps.setInt(2, hike.getYear());
            ps.setBoolean(3, hike.isUpcoming());
            ps.setDouble(4, hike.getRucksackWeightBeg());
            ps.setDouble(5, hike.getRucksackWeightEnd());
            ps.setInt(6, hike.getId());
            
            int executeUpdate = ps.executeUpdate();
            System.out.println("Update hike: " + executeUpdate);
            ps.close();
            
        } catch (SQLException e) {
            System.err.println("Hike update failed.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Hike> list() {
        ArrayList<Hike> hikes = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name, year, upcoming FROM Hikes");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //boolean upcoming = rs.getBoolean("upcoming");
                Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), false);
                hikes.add(hike);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Listing all hikes failed.");
            System.err.println(e.getMessage());
        }
        return hikes;
    }

    @Override
    public List<Hike> listPastHikes() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE upcoming = 0");
        ResultSet rs = ps.executeQuery();

        ArrayList<Hike> hikes = new ArrayList<>();

        while (rs.next()) {
            //boolean upcoming = rs.getBoolean("upcoming");
            Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), false, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
            hikes.add(hike);
        }

        ps.close();
        rs.close();

        return hikes;
    }

    @Override
    public List<Hike> listUpcomingHikes() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE upcoming = 1");
        ResultSet rs = ps.executeQuery();

        ArrayList<Hike> hikes = new ArrayList<>();

        while (rs.next()) {
            //boolean upcoming = rs.getBoolean("upcoming");
            Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), true, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
            hikes.add(hike);
        }

        ps.close();
        rs.close();

        return hikes;
    }
    
//    @Override
//    public boolean addRucksacWeightBeg(double weight, String hikeName) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hikes (rucksacBeg) VALUES (?) WHERE name=?");
//            ps.setDouble(1, weight);
//            ps.setString(2, hikeName);
//            
//            int executeUpdate = ps.executeUpdate();
//            System.out.println("Add rucksac weight hike: " + executeUpdate);
//            ps.close();
//            return true;
//        } catch (SQLException e) {
//            System.err.println("Adding rucksac weight in the beginning failed.");
//            System.err.println(e.getMessage());
//        }
//        return false;
//    }
//    
//    @Override
//    public boolean addRucksacWeightEnd(double weight, String hikeName) {
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hikes (rucksacEnd) VALUES (?) WHERE name=?");
//            ps.setDouble(1, weight);
//            ps.setString(2, hikeName);
//            
//            int executeUpdate = ps.executeUpdate();
//            System.out.println("Add rucksac weight hike: " + executeUpdate);
//            ps.close();
//            return true;
//        } catch (SQLException e) {
//            System.err.println("Adding rucksac weight in the end failed.");
//            System.err.println(e.getMessage());
//        }
//        return false;
//    }
//    
//    @Override
//    public double getRucksacWeightBeg(String hikeName) {
//        double weight = 0;
//        try {
//            PreparedStatement ps = connection.prepareStatement("SELECT rucksacBeg FROM Hikes WHERE name=?");
//            ps.setString(1, hikeName);
//            
//            ResultSet rs = ps.executeQuery();
//            
//            while (rs.next()) {
//                weight = rs.getInt("rucksacBeg");
//            }
//            
//            ps.close();
//            rs.close();
//            
//        } catch (SQLException e) {
//            System.err.println("Getting rucksac weight in the beginning failed.");
//            System.err.println(e.getMessage());
//        }
//        return weight;
//    }
//    
//    @Override
//    public double getRucksacWeightEnd(String hikeName) {
//        double weight = 0;
//        try {
//            PreparedStatement ps = connection.prepareStatement("SELECT rucksacEnd FROM Hikes WHERE name=?");
//            ps.setString(1, hikeName);
//            
//            ResultSet rs = ps.executeQuery();
//            
//            while (rs.next()) {
//                weight = rs.getInt("rucksacEnd");
//            }
//            
//            ps.close();
//            rs.close();
//            
//        } catch (SQLException e) {
//            System.err.println("Getting rucksac weight in the end failed.");
//            System.err.println(e.getMessage());
//        }
//        return weight;
//    }
    
}
