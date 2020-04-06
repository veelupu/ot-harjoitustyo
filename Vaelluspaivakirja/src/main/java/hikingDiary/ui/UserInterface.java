/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Hike;
import hikingdiary.domain.User;
import java.io.InputStream;
import java.util.Scanner;

/**
 *
 * @author veeralupunen
 */
public class UserInterface {

    Scanner reader;
    User user;
    Controller c;

    public UserInterface(Scanner reader, User user) {
        this.reader = reader;
        this.user = user;
        c = new Controller(this.user);
    }

    public UserInterface(InputStream ips, User user) {
        this.user = user;
        c = new Controller(this.user);
        this.reader = new Scanner(ips);
    }

    public UserInterface(User user) {
        this.user = user;
        c = new Controller(this.user);
    }

    public void start() {
        System.out.println("Welcome to Hiking Diary, " + user.getName() + "!");
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
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
                pastHikes();
            } else if (option.equals("3")) {
                upcomingHikes();
            } else if (option.equals("4")) {
                changeSettings();
            } else if (option.equals("5")) {
                System.out.println("Do you really want to leave?");
                String answer = reader.nextLine();
                if (answer.equals("Yes")) {
                    System.out.println("Ok! See you soon, " + user.getName() + "!");
                    return;
                }
            } else {
                System.out.println("Sorry, this command is not available.");
            }
        }

    }

    //Option 1: Creating a new hike
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
        System.out.println("Is this a past hike or an upcoming hike?");
        System.out.println("[A] A past hike.");
        System.out.println("[B] An upcoming hike.");
        String answer = reader.nextLine();
        boolean upcoming = true;
        if (answer.equals("A")) {
            upcoming = false;
        } else if (answer.equals("B")) {
            upcoming = true;
        } else {
            System.out.println("That was not a correct answer. Please try again!");
        }
        boolean s = c.createNewHike(name, year, upcoming);
        if (s) {
            System.out.println("A new hike created succesfully!");
        } else {
            System.out.println("Oops, something went wrong!");
        }
    }
    
    //Option 2: Past hikes
    private void pastHikes() {
        for (Hike hike: c.listPastHikes()) {
            System.out.println(hike.toString());
        }
    }
    
    //Option 3: Upcoming hikes
    private void upcomingHikes() {
        for (Hike hike: c.listUpcomingHikes()) {
            System.out.println(hike.toString());
        }
    }

    //Option 4: Settings
    private void changeSettings() {
        System.out.println("Do you want to change your username?");
        System.out.println("[Yes]");
        System.out.println("[Noup, please go back to Menu.]");
        String answer = reader.nextLine();
        if (answer.equals("Yes")) {
            System.out.println("What is your new name?");
            String newName = reader.nextLine();
            user.setName(newName);
            System.out.println("Username changed to " + newName + "succesfully!");
        }

    }

}
