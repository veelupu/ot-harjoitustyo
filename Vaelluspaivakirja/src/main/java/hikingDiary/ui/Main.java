/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingDiary.ui;

import hikingDiary.domain.User;
import java.util.Scanner;
import javafx.application.Application;

/**
 *
 * @author veeralupunen
 */
public class Main {
    public static void main(String[] args) {
//        Application.launch(GraphicalUserInterface.class);
        Scanner reader = new Scanner(System.in);
        System.out.println("Hello there! What is your name?");
        String name = reader.nextLine();
        User u = new User(name);
        UserInterface ui = new UserInterface(reader, u);
        ui.start();
        
    }
}
