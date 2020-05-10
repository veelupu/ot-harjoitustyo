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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for database connection for the day trip related data
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

    /**
     * Method adds a new day trip into the DayTrips table in the database.
     * 
     * @param hikeId the id of the hike this day trip belongs to
     * @param dt the day trip to add into the database
     * @return 1 if creation succeeded and 0 if not and -1 if an exception occurred
     */
    @Override
    public int create(int hikeId, DayTrip dt) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO DayTrips (hikeId, date, start, end, dist, hours, weather) VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            fillPreparedStatementForCreate(ps, hikeId, dt);
            
            int executeUpdate = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            dt.setId(rs.getInt(1));

            ps.close();
            rs.close();
            return executeUpdate;
        } catch (SQLException e) { 
            return -1; 
        }
    }
    
    private void fillPreparedStatementForCreate(PreparedStatement ps, int hikeId, DayTrip dt) throws SQLException {
        ps.setInt(1, hikeId);
        ps.setString(2, dt.getDate().toString());
        ps.setString(3, dt.getStartingPoint());
        ps.setString(4, dt.getEndingPoint());
        ps.setDouble(5, dt.getWalkDist());
        ps.setDouble(6, dt.getWalkTime());
        ps.setString(7, dt.getWeather());
    }

    /**
     * Method gets the day trip with the given date from the database.
     * 
     * @param date date of the day trip
     * @return day trip with the given date or null if there is no day trips for this date or an exception occurred
     */
    @Override
    public DayTrip read(LocalDate date) {
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
            return null;
        }
    }

    /**
     * Method updates the given day trip in the database.
     * 
     * @param dt the day trip to update
     * @return 1 if update succeeded and 0 if not and -1 if an exception occurred
     */
    @Override
    public int update(DayTrip dt) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE DayTrips SET start=?, end=?, dist=?, hours=?, weather=? WHERE date=?");
            ps.setString(1, dt.getStartingPoint());
            ps.setString(2, dt.getEndingPoint());
            ps.setDouble(3, dt.getWalkDist());
            ps.setDouble(4, dt.getWalkTime());
            ps.setString(5, dt.getWeather());
            ps.setString(6, dt.getDate().toString());

            int executeUpdate = ps.executeUpdate();
            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method removes the given day trip from the database.
     * 
     * @param dt the day trip to remove
     * @return 1 if deletion succeeded and 0 if not and -1 if an exception occurred
     */
    @Override
    public int delete(DayTrip dt) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM DayTrips WHERE id = ?");
            ps.setInt(1, dt.getId());
            
            int executeUpdate = ps.executeUpdate();
            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method forms a list of day trips belonging to the given hike.
     * 
     * @param hikeId id of the hike day trips are asked for
     * @return list of day trips or null if there is no day trips for this date or an exception occurred
     */
    @Override
    public List<DayTrip> list(int hikeId) {
        ArrayList<DayTrip> dayTrips = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM DayTrips WHERE hikeId = ?");
            ps.setInt(1, hikeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {                
                DayTrip dt = new DayTrip(LocalDate.parse(rs.getString("date")), rs.getString("start"), rs.getString("end"), rs.getDouble("dist"), rs.getDouble("hours"), rs.getString("weather"));
                dt.setId(rs.getInt("id"));
                dayTrips.add(dt);
            }
            ps.close();
            rs.close();
            return dayTrips;
        } catch (SQLException e) {
            return null;
        }
    }
}
