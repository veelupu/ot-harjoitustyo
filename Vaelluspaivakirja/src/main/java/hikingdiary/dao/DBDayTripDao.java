/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.DayTrip;
import hikingdiary.domain.Hike;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
    public DayTrip read(LocalDate date) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DayTrips WHERE date = ?");
            ps.setString(1, date.toString());
            ResultSet rs = ps.executeQuery();

            DayTrip dt = new DayTrip(date, rs.getString("start"), rs.getString("end"), rs.getDouble("dist"), rs.getDouble("hours"), rs.getString("weather"));
            dt.setId(rs.getInt("id"));

            ps.close();
            rs.close();
            return dt;
        } catch (SQLException e) {
            System.err.println("DayTrip get failed.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(int hikeId, DayTrip dt) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DayTrips SET start=?, end=?, dist=?, hours=?, weather=? WHERE hikeId=? AND date=?");
            ps.setString(1, dt.getStartingPoint());
            ps.setString(2, dt.getEndingPoint());
            ps.setDouble(3, dt.getWalkDist());
            ps.setDouble(4, dt.getWalkTime());
            ps.setString(5, dt.getWeather());
            ps.setInt(6, hikeId);
            ps.setString(7, dt.getDate().toString());

            int executeUpdate = ps.executeUpdate();

            System.out.println("Update day trip: " + executeUpdate);
            ps.close();

        } catch (SQLException e) {
            System.err.println("Day trip update failed." + e.getMessage());
        }
    }

    @Override
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List list(int hikeId) {
        ArrayList<DayTrip> dayTrips = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DayTrips WHERE hikeId = ?");
            ps.setInt(1, hikeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//                ArrayList<String> date = new ArrayList<>(Arrays.asList(rs.getString("date").split("-")));
//                int year = Integer.valueOf(date.get(0));
//                int month = Integer.valueOf(date.get(1));
//                int day = Integer.valueOf(date.get(2));
//                LocalDate lDate = LocalDate.of(year, month, day);
//                LocalDate lDate = LocalDate.parse(rs.getString("date"));
                
                DayTrip dt = new DayTrip(LocalDate.parse(rs.getString("date")), rs.getString("start"), rs.getString("end"), rs.getDouble("dist"), rs.getDouble("hours"), rs.getString("weather"));
                dt.setId(rs.getInt("id"));
                dayTrips.add(dt);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Listing day trips failed." + e.getMessage());
        }
        return dayTrips;
    }
    
}
