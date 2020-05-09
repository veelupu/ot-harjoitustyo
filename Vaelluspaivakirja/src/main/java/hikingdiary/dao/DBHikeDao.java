/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.dao;

import hikingdiary.domain.Companion;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Item;
import hikingdiary.domain.Meal;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.sql.*;

/**
 * Class responsible for database connection for the hike related data
 *
 * @author veeralupunen
 */
public class DBHikeDao implements HikeDao {

    private String dbAddress;
    private Connection connection;

    public DBHikeDao() {
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
    public DBHikeDao(String address) {
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
        s.execute("CREATE TABLE IF NOT EXISTS Hikes (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, year INTEGER NOT NULL, upcoming INTEGER NOT NULL, rucksacBeg INTEGER, rucksacEnd INTEGER, locationStart TEXT, locationEnd TEXT)");
        s.execute("CREATE TABLE IF NOT EXISTS Companion (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_Co (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, c_id INTEGER REFERENCES Companion ON UPDATE CASCADE ON DELETE CASCADE, PRIMARY KEY (h_id, c_id))");
        s.execute("CREATE TABLE IF NOT EXISTS Item (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, weight INTEGER)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_It (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, i_id INTEGER REFERENCES Item ON UPDATE CASCADE ON DELETE CASCADE, count INTEGER NOT NULL, PRIMARY KEY (h_id, i_id))");
        s.execute("CREATE TABLE IF NOT EXISTS Meal (id INTEGER PRIMARY KEY, name TEXT NOT NULL, category INTEGER NOT NULL, ingr TEXT)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_Me (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, m_id INTEGER REFERENCES Meal ON UPDATE CASCADE ON DELETE CASCADE, PRIMARY KEY (h_id, m_id))");
        s.execute("COMMIT");
    }

    /**
     * Method adds a new hike into the Hikes table in the database.
     *
     * @param hike hike to be added
     * @return 1 if creation succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int createHike(Hike hike) {
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

            ps.close();
            rs.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method checks if the given companion already exists in the database.If
     * not, it adds the companion into the Companion table. Lastly method adds a
     * connection between the given hike and companion into the Hi_Co table.
     *
     * @param hike hike to add the companion for
     * @param comp companion to be added in the database and/or for the hike
     * @return 1 or 2 if creation succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int createCompanion(Hike hike, Companion comp) {
        try {
            int executeUpdate = 0;
            int id = fetchCompanionId(comp.getName());

            if (id == -1) {
                return -1;
            } else if (id == 0) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Companion (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, comp.getName());

                executeUpdate += ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                comp.setId(id);

                ps.close();
                rs.close();
            }
            executeUpdate += addHikeCompanion(hike, id);
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    private int addHikeCompanion(Hike hike, int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_Co (h_id, c_id) VALUES (?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, id);
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method checks if the given item already exists in the database.If not, it
     * adds the item into the Item table. Finally method adds a connection
     * between the given hike and item into the Hi_It table.
     *
     * @param hike hike to add the item for
     * @param item item to be added in the database and/or for the hike
     * @return 1 or 2 if creation succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int createItem(Hike hike, Item item) {
        try {
            int id = fetchItemId(item.getName());
            item.setId(id);
            int executeUpdate = 0;

            if (id == -1) {
                return -1;
            } else if (id == 0) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Item (name, weight) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getName());
                ps.setDouble(2, item.getWeight());

                executeUpdate += ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                item.setId(id);

                ps.close();
                rs.close();

            }
            executeUpdate += addHikeItem(hike, item);
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    private int addHikeItem(Hike hike, Item item) {
        if (fetchEquipment(hike).containsKey(item.getName())) {
            int executeUpdate = addCountHikeItem(hike, item);
            return executeUpdate;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_It (h_id, i_id, count) VALUES (?, ?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, item.getId());
            ps.setInt(3, item.getCount());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    private int addCountHikeItem(Hike hike, Item item) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Hi_It SET count = ? WHERE h_id = ? AND i_id = ?");
            ps.setInt(1, item.getCount());
            ps.setInt(2, hike.getId());
            ps.setInt(3, item.getId());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method checks if the given meal already exists in the database.If not, it
     * adds the meal into the Meal table. Finally method adds a connection
     * between the given hike and meal into the Hi_Co table.
     *
     * @param hike hike to add the meal for
     * @param meal meal to be added in the database and/or for the hike
     * @return 1 or 2 if creation succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int createMeal(Hike hike, Meal meal) {
        try {
            ArrayList<Meal> meals = fetchAllMeals();
            if (meals == null) {
                return -1;
            }
            int id = 0;
            if (meals.contains(meal)) {
                for (Meal m : meals) {
                    if (m.equals(meal)) {
                        id = m.getId();
                    }
                }
                return addHikeMeal(hike, id);
            }

            PreparedStatement ps = connection.prepareStatement("INSERT INTO Meal (name, category, ingr) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, meal.getName());
            ps.setInt(2, meal.getCategory());
            ps.setString(3, String.join(", ", meal.getIngredients()));

            int executeUpdate = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
            meal.setId(id);

            ps.close();
            rs.close();
            executeUpdate += addHikeMeal(hike, id);
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    private int addHikeMeal(Hike hike, int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_Me (h_id, m_id) VALUES (?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, id);
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method gets the hike with the given name and all its companions, items
     * and meals from the database.
     *
     * @param name name of the hike
     * @return hike with its companion, items and meals or null if an error
     * occurred
     */
    @Override
    public Hike readHike(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Hikes WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            boolean upcoming = rs.getBoolean("upcoming");
            Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), upcoming, rs.getDouble("rucksacBeg"), rs.getDouble("rucksacEnd"));
            hike.setId(rs.getInt("id"));
            hike.setLocationStart(rs.getString("locationStart"));
            hike.setLocationEnd(rs.getString("locationEnd"));

            hike.setCompanions(fetchCompanions(hike));
            hike.setEquipment(fetchEquipment(hike));
            hike.setMeals(fetchMeals(hike));

            ps.close();
            rs.close();
            return hike;
        } catch (SQLException e) {
            return null;
        }
    }

    private int fetchHikeId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Hikes WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

    private int fetchCompanionId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Companion WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                ps.close();
                rs.close();
                return id;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

    private int fetchItemId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Item WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                ps.close();
                rs.close();
                return id;
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }

    private int fetchMealId(Meal meal) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Meal WHERE name = ? AND category = ? AND ingr = ?");
            ps.setString(1, meal.getName());
            ps.setInt(2, meal.getCategory());
            ps.setString(3, String.join(",", meal.getIngredients()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                ps.close();
                rs.close();
                return id;
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return -1;
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

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        Collections.sort(companion);
        return companion;
    }

    private HashMap<String, Item> fetchEquipment(Hike hike) {
        HashMap<String, Item> equipment = new HashMap<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT I.id, I.name, weight, count FROM Hikes H JOIN Hi_It ON H.id = h_id JOIN Item I ON i_id = I.id WHERE h_id = ?");
            ps.setInt(1, hike.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Double weight = rs.getDouble("weight");
                int count = rs.getInt("count");
                Item item = new Item(name, weight, count);
                item.setId(id);
                equipment.put(name, item);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        return equipment;
    }

    private ArrayList<Meal> fetchMeals(Hike hike) {
        ArrayList<Meal> meals = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT M.id, M.name, category, ingr FROM Hikes H JOIN Hi_Me ON H.id = h_id JOIN Meal M ON m_id = M.id WHERE h_id = ?");
            ps.setInt(1, hike.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Meal meal = formatMeal(rs);
                meals.add(meal);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        return meals;
    }

    private ArrayList<Meal> fetchAllMeals() {
        ArrayList<Meal> meals = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Meal");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Meal meal = formatMeal(rs);
                meals.add(meal);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        return meals;
    }

    private Meal formatMeal(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int category = rs.getInt("category");
        String ingr = rs.getString("ingr");

        Meal meal = new Meal(name, category);
        meal.setId(id);
        meal.setIngredients(formatIngredients(ingr));
        return meal;
    }

    private ArrayList<String> formatIngredients(String ingr) {
        ArrayList<String> ingredients = new ArrayList<>();

        if (ingr.length() != 0) {
            String[] pcs = ingr.split(", ");
            for (int i = 0; i < pcs.length; i++) {
                ingredients.add(pcs[i]);
            }
        }
        return ingredients;
    }

    /**
     * Method updates the given hikes data into the database.
     *
     * @param hike hike to update
     * @return 1 if update succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int updateHike(Hike hike) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Hikes SET name=?, year=?, upcoming=?, rucksacBeg=?, rucksacEnd=?, locationStart=?, locationEnd=? WHERE id=?");
            ps.setString(1, hike.getName());
            ps.setInt(2, hike.getYear());
            ps.setBoolean(3, hike.isUpcoming());
            ps.setDouble(4, hike.getRucksackWeightBeg());
            ps.setDouble(5, hike.getRucksackWeightEnd());
            ps.setString(6, hike.getLocationStart());
            ps.setString(7, hike.getLocationEnd());
            ps.setInt(8, hike.getId());

            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method removes the given hike from the database.
     *
     * @param name the name for the hike to be removed
     * @return 1 if deletion succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int deleteHike(String name) {
        try {
            int id = fetchHikeId(name);
            if (id == 0) {
                return 0;
            }

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hikes WHERE id = ?");
            ps.setInt(1, id);
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method removes the given companion from the database.
     *
     * @param hike hike to remove the companion from
     * @param name name of the companion to be deleted
     * @return 1 if deletion succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int deleteCompanion(Hike hike, String name) {
        try {
            int id = fetchCompanionId(name);
            if (id == 0) {
                return -1;
            }

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_Co WHERE c_id = ? AND h_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, hike.getId());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method removes the given meal from the database.
     *
     * @param hike hike to remove the meal from
     * @param name name of the meal to be deleted
     * @return 1 if deletion succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int deleteMeal(Hike hike, Meal meal) {
        try {
            int id = fetchMealId(meal);
            if (id == 0) {
                return -1;
            }

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_Me WHERE m_id = ? AND h_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, hike.getId());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method removes the given item from the database.
     *
     * @param hike hike to remove the item from
     * @param name name of the item to be deleted
     * @return 1 if deletion succeeded and 0 if not and -1 if an exception
     * occurred
     */
    @Override
    public int deleteItem(Hike hike, String name) {
        try {
            int id = fetchItemId(name);
            if (id == 0) {
                return -1;
            }

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_It WHERE i_id = ? AND h_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, hike.getId());
            int executeUpdate = ps.executeUpdate();

            ps.close();
            return executeUpdate;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Method lists past or upcoming hikes in the database with their basic
     * data.
     *
     * @param upcoming whether past or upcoming hikes are to be listed
     * @return list of hikes
     */
    @Override
    public List<Hike> list(boolean upcoming) {
        ArrayList<Hike> hikes = new ArrayList<>();

        int upc = 0;
        if (upcoming) {
            upc = 1;
        }

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, year FROM Hikes WHERE upcoming = ?");
            ps.setInt(1, upc);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hike hike = new Hike(rs.getString("name"), rs.getInt("year"), false);
                if (upc == 1) {
                    hike.setUpcoming(true);
                }
                hike.setId(rs.getInt("id"));
                hikes.add(hike);
            }
            ps.close();
            rs.close();
            return hikes;
        } catch (SQLException e) {
            return null;
        }
    }
}
