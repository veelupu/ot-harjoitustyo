/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.Hike;
import hikingdiary.domain.Item;
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
public class EquipmentView {

    Controller c;
    Hike hike;

    public EquipmentView(Controller c) {
        this.c = c;
    }

    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();

        Label title = new Label("Your equipment during this hike:");
        Label equipmentList = new Label(hike.formatEquipment());
        Label empty = new Label("");
        Label add = new Label("Add more items to the equipment list!");

        Label lName = new Label("Item name:");
        TextField tfName = new TextField();
        Label lWeight = new Label("How much does that item weight? (optional)");
        TextField tfWeight = new TextField();
        Label lCount = new Label("How many items of this kind you want to add?");
        TextField tfCount = new TextField();
        Button ready = new Button("Ready to add an item to the equipment list!");

        VBox box = new VBox();
        box.getChildren().addAll(title, equipmentList, empty, add, lName, tfName, lWeight, tfWeight, lCount, tfCount, ready);

        Label done = new Label("Item added!");
        //muuta virhetekstiä kuvaamammaksi
        Label error = new Label("Oops, weight and count should be numbers.\nTry again!");
        
        ready.setOnAction((event) -> {
            try {   
                Item item = new Item(tfName.getText(), Integer.valueOf(tfCount.getText()));
                
                if (tfWeight.getLength() != 0) {
                    item.setWeight(Double.valueOf(tfWeight.getText()));
                }
                
                c.addItem(hike, item);
                //hike.addItem(item);
                equipmentList.setText(hike.formatEquipment());
                tfName.clear();
                tfWeight.clear();
                tfCount.clear();
                box.getChildren().add(done);
            } catch (Exception e) {
                box.getChildren().add(error);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Adding item failed: " + e.getMessage());
            }
            
        });

        tfName.setOnMouseClicked((event) -> {
            box.getChildren().remove(done);
            box.getChildren().remove(error);
        });

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        gp.add(box, 0, 0);

        return gp;
    }

}
