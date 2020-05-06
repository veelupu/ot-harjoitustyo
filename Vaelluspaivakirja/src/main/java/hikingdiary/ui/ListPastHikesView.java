/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class ListPastHikesView {

    Controller c;
    GraphicalUserInterface ui;
    GridPane gp;
    ArrayList<Button> buttons;
    VBox hikeButtons;

    public ListPastHikesView(Controller c, GraphicalUserInterface ui) {
        this.c = c;
        this.ui = ui;
        this.buttons = new ArrayList<>();
    }

    public Parent getView() {
        this.gp = new GridPane();

        Label lName = new Label("Past Hikes");
        gp.add(lName, 0, 1);

        gp.add(formatHikeButtons(), 0, 2);

        //poistaminen
        Button deleteButton = new Button("Remove hike");
        VBox boxRm = new VBox();
        
        deleteButton.setOnAction((event) -> {
            gp.getChildren().remove(deleteButton);
            gp.add(boxRm, 0, 3);
            delete(boxRm);
        });

        gp.add(deleteButton, 0, 3);
        
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(5, 5, 5, 5));

        return gp;
    }
    
    private void delete(VBox boxRm) {
        Label lHikeToRm = new Label("Which hike do you want to remove from this hike?");
        TextField hikeToRm = new TextField();
        Button remove = new Button("Remove this hike permanently");
        boxRm.getChildren().addAll(lHikeToRm, hikeToRm, remove);       
        
        Label succ = new Label("Hike removed succesfully!");
        Label unsucc = new Label("Couldn't remove hike. Make sure you wrote the name correctly.");
        
        remove.setOnAction((event) -> {
            String name = hikeToRm.getText();
            Hike h = new Hike(name);
            if (c.removeHike(h)) {
                boxRm.getChildren().add(succ);
                hikeToRm.clear();
                gp.add(formatHikeButtons(), 0, 2);
            } else {
                boxRm.getChildren().add(unsucc);
            }
        });
    }
    
    private VBox formatHikeButtons() {
        buttons.clear();
        gp.getChildren().remove(hikeButtons);
        hikeButtons = new VBox();
        
        for (Hike hike : c.listPastHikes()) {
            Button b = new Button(hike.toString());
            b.setUserData(hike.getName());
            
            b.setOnMouseClicked((event) -> {
                String hikeName = (String) b.getUserData();
                ui.bp.setCenter(new HikeView(c.getHike(hikeName), c, ui).getView());
            });
            
            buttons.add(b);
            hikeButtons.getChildren().add(b);
        }
        
        return hikeButtons;
    }

}
