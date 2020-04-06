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
    GraphicalUserInterface ui;
    GridPane gp;

    public ListPastHikesView(Controller c, GraphicalUserInterface ui, GridPane gp) {
        this.c = c;
        this.ui = ui;
        this.gp = gp;
    }

    public Parent getView() {
        GridPane gp = new GridPane();

        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 1);

        ArrayList<Button> buttons = new ArrayList<>();
        int i = 2;
        for (Hike hike : c.listPastHikes()) {
            Button b = new Button(hike.toString());
            b.setUserData(hike.getName());
            buttons.add(b);
            gp.add(b, 0, i);
            i++;
        }

        for (Button b: buttons) {
            b.setOnMouseClicked((event) -> {
                String hike = (String) b.getUserData();
                ui.bp.setCenter(new HikeView(c.hikes.get(hike)).getView());
            });
        }

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));

        return gp;
    }

}
