/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.sql.*;

/**
 *
 * @author veeralupunen
 */
public class DBHikeDao implements HikeDao<Hike, Integer> {

    private String dbAddress;
    private Connection connection;

    public DBHikeDao() {
        dbAddress = System.getProperty("user.home");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbAddress + "/.hikes.db");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            System.err.println(e.getMessage());
        }
        try {
            createTables();
        } catch (Exception e) {
            System.err.println("Table creation failed.");
            System.err.println(e.getMessage());
        }
    }

    public DBHikeDao(String address) { //for testing;
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
            System.err.println("Connection failed.");
            System.err.println(e.getMessage());
        }
        try {
            createTables();
        } catch (Exception e) {
            System.err.println("Table creation failed.");
            System.err.println(e.getMessage());
        }
    }

    public String getDBAdress() {
        return dbAddress;
    }

    public void createTables() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("BEGIN TRANSACTION");
        s.execute("PRAGMA foreign_keys = ON");
        s.execute("CREATE TABLE IF NOT EXISTS Hikes (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, year INTEGER NOT NULL, upcoming INTEGER NOT NULL, rucksacBeg INTEGER, rucksacEnd INTEGER)");
        s.execute("CREATE TABLE IF NOT EXISTS Companion (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_Co (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE, c_id INTEGER REFERENCES Companion ON UPDATE CASCADE, PRIMARY KEY (h_id, c_id))");
        s.execute("COMMIT");
    }

    @Override
    public void createHike(Hike hike) {
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
    public void createCompanion(Hike hike, Companion comp) {
        try {
            int id = fetchCompanionId(comp.getName());
            if (id == 0) {
                PreparedStatement ps1 = connection.prepareStatement("INSERT INTO Companion (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, comp.getName());

                int executeUpdate1 = ps1.executeUpdate();
                ResultSet rs1 = ps1.getGeneratedKeys();
                rs1.next();
                id = rs1.getInt(1);
                comp.setId(id);
                System.out.println("Create companion 1/2: " + executeUpdate1);
                ps1.close();
            }
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO Hi_Co (h_id, c_id) VALUES (?, ?)");
            ps2.setInt(1, hike.getId());
            ps2.setInt(2, id);
            int executeUpdate2 = ps2.executeUpdate();
            System.out.println("Create companion 2/2: " + executeUpdate2);
            ps2.close();
        } catch (SQLException e) {
            System.err.println("Companion creation failed." + e.getMessage());
        }
    }

    @Override
    public Hike read(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            boolean upcoming = rs.getBoolean("upcoming");
            Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), upcoming, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
            hike.setId(rs.getInt("id"));

            hike.setCompanions(fetchCompanions(hike));

            ps.close();
            rs.close();
            return hike;
        } catch (SQLException e) {
            System.err.println("Hike get failed.");
            System.err.println(e.getMessage());
        }
        return null;
    }

    private int fetchCompanionId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Companion WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Companion get failed.");
            System.err.println(e.getMessage());
        }
        return 0;
    }

    private ArrayList<String> fetchCompanions(Hike hike) {
        ArrayList<String> companion = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT C.name FROM Hikes H JOIN Hi_Co ON H.id = h_id JOIN Companion C ON c_id = C.id WHERE h_id = ?");
            ps.setInt(1, hike.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String comp = rs.getString("name");
                companion.add(comp);
            }

        } catch (SQLException e) {
            System.err.println("Companions get failed.");
            System.err.println(e.getMessage());
        }

        Collections.sort(companion);
        return companion;
    }

    //Häääh tarvinko tätä todella?
    @Override
    public void updateHike(Hike hike) {
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

//    @Override
//    public void updateCompanion(Hike hike, Companion comp) {
//        try {
//            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO Companion (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
//            ps1.setString(1, comp.getName());
//
//            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO Hi_Co (h_id, c_id) VALUES (?, ?)");
//            ps2.setInt(1, hike.getId());
//            ps2.setInt(2, comp.getId());
//
//            int executeUpdate = ps2.executeUpdate();
//            System.out.println("Update hike: " + executeUpdate);
//            ps1.close();
//            ps2.close();
//        } catch (SQLException e) {
//            System.err.println("Companion update failed.");
//            System.err.println(e.getMessage());
//        }
//    }
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
    public List<Hike> listPastHikes() {
        ArrayList<Hike> hikes = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE upcoming = 0");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //boolean upcoming = rs.getBoolean("upcoming");
                Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), false, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
                hikes.add(hike);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Listing past hikes failed.");
            System.err.println(e.getMessage());
        }
        return hikes;
    }

    @Override
    public List<Hike> listUpcomingHikes() {
        ArrayList<Hike> hikes = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE upcoming = 1");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //boolean upcoming = rs.getBoolean("upcoming");
                Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), true, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
                hikes.add(hike);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Listing upcoming hikes failed.");
            System.err.println(e.getMessage());
        }
        return hikes;
    }
}
