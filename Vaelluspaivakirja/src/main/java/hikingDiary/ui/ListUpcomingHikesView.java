/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Hike;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author veeralupunen
 */
public class ListUpcomingHikesView {
    
    Controller c;
    GraphicalUserInterface ui;
    
    public ListUpcomingHikesView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label lName = new Label("Upcoming Hikes");
        gp.add(lName, 0, 1);
        
        ArrayList<Button> buttons = new ArrayList<>();
        int i = 2;
        for (Hike hike : c.listUpcomingHikes()) {
            Button b = new Button(hike.toString());
            b.setUserData(hike.getName());
            
            b.setOnMouseClicked((event) -> {
                String hikeName = (String) b.getUserData();
                ui.bp.setCenter(new HikeView(c.getHike(hikeName)).getView());
            });
            
            buttons.add(b);
            gp.add(b, 0, i);
            i++;
        }
        
//        int i = 2;
//        for (Hike hike: c.listUpcomingHikes()) {
//            Button b = new Button(hike.toString());
//            gp.add(b, 0, i);
//            i++;
//        }
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
}
