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

/**
 *
 * @author veeralupunen
 */
public class SettingsView {
    
    Controller c;
    
    public SettingsView(Controller c) {
        this.c = c;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label lNewName = new Label("If you want to change your username,\nplease type your new username here.");
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
                if (newName != null) {
                    //ui.user.setName(newName.toString());
                    c.changeUsername(newName.toString());
                    gp.add(new Label("Username changed successfully!"), 0, 4);
                }
            } catch (Exception e) {
                gp.add(new Label("That was not a correct username.\nTry again!"), 0, 4);
            }
        });
        
        return gp;
    }
    
}
