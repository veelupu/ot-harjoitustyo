/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 *
 * @author veeralupunen
 */
public class MainMenuView {
    
    GraphicalUserInterface ui;
    GridPane gp;
    Controller c;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    
    public MainMenuView(GraphicalUserInterface ui, Controller c) {
        this.ui = ui;
        this.c = c;
        gp = new GridPane();
    }
    
    public Parent getView() {
        
        //createButtons();
        
//        b1.setOnAction((event) -> ui.bp.setCenter(new CreateHikeView(c).getView()));
//            //avaa näkymä, jossa voi luoda uuden Hike-olion           
//        b2.setOnAction((event) -> ui.bp.setCenter(new ListPastHikesView(c, ui, gp).getView()));
//            //avaa näkymä, jossa on lista menneistä vaelluksista            
//        b3.setOnAction((event) -> ui.bp.setCenter(new ListUpcomingHikesView(c).getView()));
//            //avaa näkymä, jossa on lista tulevista vaelluksista
//        b4.setOnAction((event) -> ui.bp.setCenter(new SettingsView(c).getView()));
//            //avaa näkymä, jossa voi muuttaa käyttäjänimeä
//        b5.setOnAction((event) -> {
//            //sulkee sovelluksen
//        });
        
        return gp;
    }
    
    private void createButtons() {
        b1 = new Button("Create\na new hike\nfor me");
        b2 = new Button("List\nmy past\nhikes");
        b3 = new Button("List\nmy upcoming\nhikes");
        b4 = new Button("Settings");
        b5 = new Button("Quit");
        
        Button[] buttons = new Button[]{b1, b2, b3, b4};
        for (Button b : buttons) {
            b.setStyle(
                    "-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 120px; "
                    + "-fx-min-height: 120px; "
                    + "-fx-max-width: 120px; "
                    + "-fx-max-height: 120px;"
            );
        }
//        b5.setStyle("-fx-text-alignment: center;" +
//                "-fx-background-radius: 5em; " +
//                "-fx-min-width: 70px; " +
//                "-fx-min-height: 70px; " +
//                "-fx-max-width: 70px; " +
//                "-fx-max-height: 70px;");

        gp.add(buttons[0], 1, 1);
        gp.add(buttons[1], 2, 1);
        gp.add(buttons[2], 1, 2);
        gp.add(buttons[3], 2, 2);
        //gp.add(buttons[4], 3, 2);
    }
    
}
