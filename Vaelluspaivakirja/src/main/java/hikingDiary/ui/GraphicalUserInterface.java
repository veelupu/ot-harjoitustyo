/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.User;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author veeralupunen
 */
public class GraphicalUserInterface extends Application {
    
    User user;
    Controller c;
    GridPane gp;
    BorderPane bp;

    
    public GraphicalUserInterface() {
        this.user = new User("Veera");
        this.c = new Controller(user);
        this.gp = new GridPane();
        this.bp = new BorderPane();
    }
    
    @Override
    public void start(Stage window) {
        window.setTitle(user.getName() + "’s Hiking Diary");
        
        Button bMainmenu = new Button("Main menu");
        bMainmenu.setOnAction((event) -> {
            bp.setCenter(new MainMenuView(this, c).getView());
        });
        
        bp.setCenter(new MainMenuView(this, c).getView());
        //bp.setBottom(bMainmenu);
        bp.setRight(bMainmenu);
        Scene view = new Scene(bp);

        window.setScene(view);
        window.show();
    }  
}
