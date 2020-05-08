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
public class DBHikeDao implements HikeDao<Hike, Integer> {

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
        s.execute("CREATE TABLE IF NOT EXISTS Hikes (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, year INTEGER NOT NULL, upcoming INTEGER NOT NULL, rucksacBeg INTEGER, rucksacEnd INTEGER)");
        s.execute("CREATE TABLE IF NOT EXISTS Companion (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_Co (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, c_id INTEGER REFERENCES Companion ON UPDATE CASCADE ON DELETE CASCADE, PRIMARY KEY (h_id, c_id))");
        s.execute("CREATE TABLE IF NOT EXISTS Item (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, weight INTEGER)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_It (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, i_id INTEGER REFERENCES Item ON UPDATE CASCADE ON DELETE CASCADE, count INTEGER NOT NULL, PRIMARY KEY (h_id, i_id))");
        s.execute("CREATE TABLE IF NOT EXISTS Meal (id INTEGER PRIMARY KEY, name TEXT UNIQUE NOT NULL, category INTEGER NOT NULL, ingr TEXT)");
        s.execute("CREATE TABLE IF NOT EXISTS Hi_Me (h_id INTEGER REFERENCES Hikes ON UPDATE CASCADE ON DELETE CASCADE, m_id INTEGER REFERENCES Meal ON UPDATE CASCADE ON DELETE CASCADE, PRIMARY KEY (h_id, m_id))");
        s.execute("COMMIT");
    }

    /**
     * Method adds a new hike into the Hike table in the database.
     * 
     * @param hike hike to be created
     */
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
            rs.close();
        } catch (SQLException e) {
            System.err.println("Hike creation failed." + e.getMessage());
        }
        //connection.close(); //tee tälle oma metodi ja kutsu sitä kun lopetetaan
    }

    /**
     * Method checks if the given companion already exists in the database. 
     * If not, it adds the companion into the Companion table.
     * Finally method adds a connection between the given hike and companion 
     * into the Hi_Co table.
     * 
     * @param hike hike to add the companion for
     * @param comp companion to be added in the database and/or for the hike
     */
    @Override
    public void createCompanion(Hike hike, Companion comp) {
        try {
            int id = fetchCompanionId(comp.getName());
            if (id == 0) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Companion (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, comp.getName());

                int executeUpdate = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                comp.setId(id);
                System.out.println("Create companion: " + executeUpdate);
                ps.close();
                rs.close();
            }
            addHikeCompanion(hike, id);
        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Companion creation failed." + e.getMessage());
        }
    }

    private void addHikeCompanion(Hike hike, int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_Co (h_id, c_id) VALUES (?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, id);
            int executeUpdate = ps.executeUpdate();
            System.out.println("Adding Hike_Companion: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Adding Hike_Companion failed." + e.getMessage());
        }
    }
    
    /**
     * Method checks if the given item already exists in the database. 
     * If not, it adds the item into the Item table.
     * Finally method adds a connection between the given hike and item 
     * into the Hi_It table.
     * 
     * @param hike hike to add the item for
     * @param item item to be added in the database and/or for the hike
     */
    @Override
    public void createItem(Hike hike, Item item) {
        try {
            int id = fetchItemId(item.getName());
            item.setId(id);

            if (id == 0) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Item (name, weight) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getName());
                ps.setDouble(2, item.getWeight());

                int executeUpdate1 = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                item.setId(id);
                System.out.println("Create item: " + executeUpdate1);
                ps.close();
                rs.close();
            }
            addHikeItem(hike, item);
        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Item creation failed." + e.getMessage());
        }
    }

    private void addHikeItem(Hike hike, Item item) {
        if (fetchEquipment(hike).containsKey(item.getName())) {
            addCountHikeItem(hike, item);
            return;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_It (h_id, i_id, count) VALUES (?, ?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, item.getId());
            ps.setInt(3, item.getCount());
            int executeUpdate = ps.executeUpdate();
            System.out.println("Add in Hikes_Item: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Hikes_Item add failed." + e.getMessage());
        }
    }
    
    private void addCountHikeItem(Hike hike, Item item) {
        try {
                PreparedStatement ps = connection.prepareStatement("UPDATE Hi_It SET count = ? WHERE h_id = ? AND i_id = ?");
                ps.setInt(1, item.getCount());
                ps.setInt(2, hike.getId());
                ps.setInt(3, item.getId());
                int executeUpdate = ps.executeUpdate();
                System.out.println("Update in Hikes_Item: " + executeUpdate);
                ps.close();
            } catch (SQLException e) {
                //poista tämä ennen lopullista palautusta / muuta muuksi
                System.err.println("Hikes_Item update failed." + e.getMessage());
            }
    }

    /**
     * Method checks if the given meal already exists in the database.
     * If not, it adds the meal into the Meal table.
     * Finally method adds a connection between the given hike and meal 
     * into the Hi_Co table.
     * 
     * @param hike hike to add the meal for
     * @param meal meal to be added in the database and/or for the hike
     */
    @Override
    public void createMeal(Hike hike, Meal meal) {
        try {
            int id = fetchMealId(meal.getName());

            if (id == 0) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO Meal (name, category, ingr) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, meal.getName());
                ps.setInt(2, meal.getCategory());
                ps.setString(3, String.join(",", meal.getIngredients()));

                int executeUpdate = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                id = rs.getInt(1);
                meal.setId(id);
                System.out.println("Create meal: " + executeUpdate);
                ps.close();
                rs.close();
            }

            addHikeMeal(hike, id);

        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Meal creation failed." + e.getMessage());
        }
    }

    private void addHikeMeal(Hike hike, int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Hi_Me (h_id, m_id) VALUES (?, ?)");
            ps.setInt(1, hike.getId());
            ps.setInt(2, id);
            int executeUpdate = ps.executeUpdate();
            System.out.println("Add in Hikes_Meal: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            //poista tämä ennen lopullista palautusta / muuta muuksi
            System.err.println("Hikes_Meal add failed." + e.getMessage());
        }
    }

    /**
     * Method gets hike with given name and all its companions, items and meals
     * from the database.
     * 
     * @param name name of the hike
     * @return hike with its companion, items and meals
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

            hike.setCompanions(fetchCompanions(hike));
            hike.setEquipment(fetchEquipment(hike));
            hike.setMeals(fetchMeals(hike));

            ps.close();
            rs.close();
            return hike;
        } catch (SQLException e) {
            System.err.println("Hike get failed.");
            System.err.println(e.getMessage());
        }
        return null;
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
            //poista tai muuta nämä ennen lopullista palautusta
            System.err.println("Hike id get failed." + e.getMessage());
        }
        return 0;
    }
    
    private int fetchCompanionId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Companion WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            //poista tai muuta nämä ennen lopullista palautusta
            System.err.println("Companion id get failed." + e.getMessage());
        }
        return 0;
    }

    private int fetchItemId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Item WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Item id get failed." + e.getMessage());
        }
        return 0;
    }
    
    private int fetchMealId(String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT id FROM Meal WHERE name = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("Meal id get failed." + e.getMessage());
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
            //muuta tämä ennen lopullista palautusta
            System.err.println("Companions get failed." + e.getMessage());
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
            //muuta tämä ennen lopullista palautusta
            System.err.println("Equipment get failed." + e.getMessage());
        }
        return equipment;
    }

    private HashMap<String, Meal> fetchMeals(Hike hike) {
        HashMap<String, Meal> meals = new HashMap<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT M.id, M.name, category, ingr FROM Hikes H JOIN Hi_Me ON H.id = h_id JOIN Meal M ON m_id = M.id WHERE h_id = ?");
            ps.setInt(1, hike.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Meal meal = formatMeal(rs);
                meals.put(meal.getName(), meal);
            }
            
            ps.close();
            rs.close();
        } catch (SQLException e) {
            //muuta tämä ennen lopullista palautusta
            System.err.println("Meals get failed." + e.getMessage());
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
     */
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
            System.err.println("Hike update failed." + e.getMessage());
        }
    }

    /**
     * Method removes the given hike from the database.
     * 
     * @param name the name for the hike to be removed
     * @throws SQLException insert description here
     */
    @Override
    public void deleteHike(String name) {
        try {
            int id = fetchHikeId(name);

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hikes WHERE id = ?");
            ps.setInt(1, id);
            
            int executeUpdate = ps.executeUpdate();
            System.out.println("Delete hike: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Deleting hike failed." + e.getMessage());
        }
    }
    
    @Override
    public void deleteCompanion(Hike hike, String name) {
        try {
            int idC = fetchCompanionId(name);

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_Co WHERE c_id = ? AND h_id = ?");
            ps.setInt(1, idC);
            ps.setInt(2, hike.getId());
            
            int executeUpdate = ps.executeUpdate();
            System.out.println("Delete companion: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Deleting companion failed." + e.getMessage());
        }
    }
    
    @Override
    public void deleteMeal(Hike hike, String name) {
        try {
            int idM = fetchMealId(name);

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_Me WHERE m_id = ? AND h_id = ?");
            ps.setInt(1, idM);
            ps.setInt(2, hike.getId());
            
            int executeUpdate = ps.executeUpdate();
            System.out.println("Delete meal: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Deleting meal failed." + e.getMessage());
        }
    }
    
    @Override
    public void deleteItem(Hike hike, String name) {
        try {
            int idI = fetchItemId(name);

            PreparedStatement ps = connection.prepareStatement("DELETE FROM Hi_It WHERE i_id = ? AND h_id = ?");
            ps.setInt(1, idI);
            ps.setInt(2, hike.getId());
            
            int executeUpdate = ps.executeUpdate();
            System.out.println("Delete item: " + executeUpdate);
            ps.close();
        } catch (SQLException e) {
            System.err.println("Deleting item failed." + e.getMessage());
        }
    }

    /**
     * Method lists past or upcoming hikes in the database with their basic data.
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
        } catch (SQLException e) {
            System.err.println("Listing hikes failed." + e.getMessage());
        }
        return hikes;
    }
}
