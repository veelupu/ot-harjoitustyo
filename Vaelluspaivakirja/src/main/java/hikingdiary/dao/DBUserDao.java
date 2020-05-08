/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class responsible for database connection for the user related data
 * 
 * @author veeralupunen
 */
public class DBUserDao implements UserDao<User> {

    private String dbAddress;
    private Connection connection;

    public DBUserDao() {

        dbAddress = System.getProperty("user.home");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbAddress + "/.hikes.db");
        } catch (SQLException e) {
            System.err.println("Connection failed.");
            System.err.println(e.getMessage());
        }
        try {
            createUserTable();
        } catch (Exception e) {
            System.err.println("Table creation failed.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Constructor for the test classes
     * 
     * @param address address where to temporarily place a test database
     */
    public DBUserDao(String address) {
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
            createUserTable();
        } catch (Exception e) {
            System.err.println("Table creation failed.");
            System.err.println(e.getMessage());
        }
    }
    
    public String getDBAdress() {
        return dbAddress;
    }

    private void createUserTable() throws SQLException {
        Statement s = connection.createStatement();
        s.execute("BEGIN TRANSACTION");
        s.execute("PRAGMA foreign_keys = ON");
        s.execute("CREATE TABLE IF NOT EXISTS User (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL)");
        s.execute("COMMIT");
    }

    /**
     * Method adds a new user into the User table in the database.
     * 
     * @param user user to be added
     * @return 1 if creation succeeded and 0 if not and -1 if an exception occurred
     */
    @Override
    public int create(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO User (name) VALUES (?)");
            ps.setString(1, user.getName());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method replaces the given user's username with the given new username.
     * 
     * @param user user to have a new name
     * @param newName new name to give to the user
     * @return whether the adding succeeded or not
     */
    @Override
    public boolean update(User user, String newName) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE User SET name=? WHERE name=?");
            ps.setString(1, newName);
            ps.setString(2, user.getName());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Method removes all the user related data from the database.
     * 
     * @param user user to be deleted
     * @return whether the removing succeeded or not
     */
    @Override
    public boolean delete(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM User WHERE name=?");
            ps.setString(1, user.getName());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Method gets the user from the database.
     * 
     * @return user or null if an error occurred
     */
    @Override
    public User read() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT name FROM User");
            ResultSet rs = ps.executeQuery();
            String name = rs.getString("name");

            ps.close();
            rs.close();
            return new User(name);
        } catch (SQLException e) {
            return null;
        }
    }
}
