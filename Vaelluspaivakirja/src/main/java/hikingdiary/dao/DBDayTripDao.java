/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.DayTrip;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author veeralupunen
 */
public class DBDayTripDao implements DayTripDao {
    
    private String dbAddress;
    private Connection connection;
    
    public DBDayTripDao() {
        dbAddress = System.getProperty("user.home");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbAddress + "/.hikes.db");
        } catch (SQLException e) {
            System.err.println("Connection failed." + e.getMessage());
        }
        try {
            createTables();
        } catch (Exception e) {
            System.err.println("Table creation failed." + e.getMessage());
        }
    }
    
    /**
     * Constructor for the test classes
     * 
     * @param address address where to temporarily place a test database
     */
    public DBDayTripDao(String address) {
        try {
            Path path = Files.createTempDirectory("hikingDiary-");
            path.toFile().deleteOnExit();
            dbAddress = path.toAbsolutePath().toString() + "/" + address;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbAddress);
        } catch (SQLException e) {
            System.err.println("Connection failed." + e.getMessage());
        }
        try {
            createTables();
        } catch (Exception e) {
            System.err.println("Table creation failed." + e.getMessage());
        }
    }
    
    public String getDBAdress() {
        return dbAddress;
    }
    
    private void createTables() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("PRAGMA foreign_keys = ON");
        s.execute("BEGIN TRANSACTION");
        s.execute("CREATE TABLE IF NOT EXISTS DayTrips (id INTEGER PRIMARY KEY, hikeId INTEGER NOT NULL, date TEXT UNIQUE NOT NULL, start TEXT NOT NULL, end TEXT, dist REAL, hours REAL, weather TEXT)");
        s.execute("COMMIT");
    }

    @Override
    public void create(int hikeId, DayTrip dt) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DayTrips (hikeId, date, start, end, dist, hours, weather) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, hikeId);
            ps.setString(2, dt.getDate().toString());
            ps.setString(3, dt.getStartingPoint());
            ps.setString(4, dt.getEndingPoint());
            ps.setDouble(5, dt.getWalkDist());
            ps.setDouble(6, dt.getWalkTime());
            ps.setString(7, dt.getWeather());

            int executeUpdate = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            dt.setId(id);

            System.out.println("Create day trip: " + executeUpdate);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Day trip creation failed." + e.getMessage());
        }
    }

    @Override
    public Object read(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object update(Object object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List list() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
