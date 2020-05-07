/*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        
        Label lNewName = new Label("To change your username,\nplease type new username here.");
        TextField newName = new TextField();
        Button bReady = new Button("Change\nmy\nusername!");
        
        lNewName.setPadding(new Insets(5, 5, 5, 5));
        newName.setPadding(new Insets(5, 5, 5, 5));
        bReady.setPadding(new Insets(15, 15, 15, 15));
        
        VBox box = new VBox();
        box.getChildren().addAll(bReady);
        
        box.setAlignment(Pos.CENTER);
        
        bReady.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 100px; "
                + "-fx-min-height: 100px; "
                + "-fx-max-width: 100px; "
                + "-fx-max-height: 100px;");
        
        gp.add(lNewName, 0, 0);
        gp.add(newName, 0, 1);
//        gp.add(bReady, 0, 2);
        
        gp.add(box, 0, 2);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        
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
