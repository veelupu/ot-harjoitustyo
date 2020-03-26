/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingDiary.ui;

import hikingDiary.domain.User;
import java.util.Scanner;

/**
 *
 * @author veeralupunen
 */
public class UserInterface {
    
    Scanner reader;
    User user;
    ControllerInterface c;
    
    public UserInterface(Scanner reader, User user) {
        c = new Controller();
        this.reader = reader;
        this.user = user;
    }
    
    public void start() {
        
        System.out.println("Welcome to Hiking Diary, " + user.getName() + "!");
        mainMenu();
    }
    
    private void mainMenu() {
        System.out.println("What would you like to do?");
        System.out.println("[1] Create a new hike.");
        System.out.println("[2] Show me my past hikes.");
        System.out.println("[3] Show me my upcoming hikes.");
        System.out.println("[4] Take me to Settings.");
        System.out.println("[5] I would like to quit, please.");
        String option = reader.nextLine();
        
        if (option.equals("1")) {
            createHike();
        } else if (option.equals("2")) {
            //Tee tätä
        } else if (option.equals("3")) {
            //Tee tätä
        } else if (option.equals("4")) {
            //Tee tätä
        } else if (option.equals("5")) {
            System.out.println("Do you really want to leave?");
            String answer = reader.nextLine();
            if (answer.equals("Yes")) {
                return;
            } else {
                mainMenu();
            }
        } else {
            System.out.println("Sorry, this instruction is not available.");
        }
    }
    
    private void createHike() {
        System.out.println("Great! Now please give your new hike a name.");
        String name = reader.nextLine();
        System.out.println("And in which year did this hike happen or is to take place?");
        int year = 0;
        try {
            year = Integer.parseInt(reader.nextLine());
        } catch (Exception e) {
            System.out.println("This does not seem to be a number. Perhaps you should try again?");
        }
        boolean s = c.createNewHike(name, year);
        if (s) {
            System.out.println("A new hike created succesfully!");
        } else {
            System.out.println("Oops, something went wrong!");
        }
        
    }
    
}
