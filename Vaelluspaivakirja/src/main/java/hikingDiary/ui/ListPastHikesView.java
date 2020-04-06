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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author veeralupunen
 */
public class ListPastHikesView {
    
    Controller c;
    
    public ListPastHikesView(Controller c) {
        this.c = c;
    }
    
    public Parent getView() {
        GridPane gp = new GridPane();
        
        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 1);
        
        int i = 2;
        for (Hike hike: c.listPastHikes()) {
            Button b = new Button(hike.toString());
            gp.add(b, 0, i);
            i++;
        }
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));
        
        return gp;
    }
    
}
