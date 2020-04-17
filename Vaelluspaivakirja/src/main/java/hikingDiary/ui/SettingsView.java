/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author veeralupunen
 */
public class SettingsView {
    
    Controller c;
    GraphicalUserInterface ui;
    Stage window;
    
    public SettingsView(Controller c, GraphicalUserInterface ui, Stage window) {
        this.c = c;
        this.ui = ui;
        this.window = window;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label lNewName = new Label("To change your username,\nplease type your new username here.");
        TextField newName = new TextField();
        Button bReady = new Button("Ready to change my username!");
        
        gp.add(lNewName, 0, 1);
        gp.add(newName, 0, 2);
        gp.add(bReady, 0, 3);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        bReady.setOnMouseClicked((event) -> {
            try {
                if (newName.getText() != null) {
                    //ui.user.setName(newName.toString());
                    c.changeUsername(newName.getText());
                    ui.setTitle(window);
                    newName.clear();
                    gp.add(new Label("Username changed successfully!"), 0, 4);
                }
            } catch (Exception e) {
                gp.add(new Label("Sorry, changing username failed.\nTry again!"), 0, 4);
                System.out.println("Error when trying to change username: " + e.getMessage());
            }
        });
        
        return gp;
    }
    
}
