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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class EquipmentView {

    Controller c;
    Hike hike;
    GridPane gp;
    VBox equipmentList;
    Label succ;
    Label error;

    public EquipmentView(Controller c) {
        this.c = c;
    }

    public Parent getView(Hike hike) {
        gp = new GridPane();
        this.hike = hike;
        
        Label title = new Label("Your equipment during " + hike.toString() + ":");
        gp.add(title, 0, 0);

        formatItemsBox();
        //Label equipmentList = new Label(hike.formatEquipment());
        //Label empty = new Label("");
        Label add = new Label("Add more items to the equipment list!");

        Label lName = new Label("Item name:");
        TextField tfName = new TextField();
        Label lWeight = new Label("How much does that item weight? (optional)");
        TextField tfWeight = new TextField();
        Label lCount = new Label("How many items of this kind you want to add?");
        TextField tfCount = new TextField();
        Button ready = new Button("Ready\nto add!");
        
        ready.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 75px; "
                + "-fx-min-height: 75px; "
                + "-fx-max-width: 75px; "
                + "-fx-max-height: 75px;");

        VBox box = new VBox();
        box.getChildren().addAll(add, lName, tfName, lWeight, tfWeight, lCount, tfCount, ready);
        box.setAlignment(Pos.CENTER);

        Label done = new Label("Item added!");
        Label exists = new Label("This hike already has this item.");
        succ = new Label();
        error = new Label();
        //muuta virhetekstiä kuvaamammaksi
        Label exception = new Label("Oops, weight and count should be numbers.\nTry again!");
        
        ready.setOnAction((event) -> {
            try {   
                Item item = new Item(tfName.getText(), Integer.valueOf(tfCount.getText()));
                
                if (tfWeight.getLength() != 0) {
                    item.setWeight(Double.valueOf(tfWeight.getText()));
                }
                
//                if (c.addItem(hike, item)) {
                c.addItem(hike, item);
                formatItemsBox();
                //equipmentList.setText(hike.formatEquipment());
                tfName.clear();
                tfWeight.clear();
                tfCount.clear();
                box.getChildren().add(done);
//                } else {
//                    box.getChildren().add(exists);
//                }
                
            } catch (Exception e) {
                box.getChildren().add(exception);
                //poista alla oleva ennen lopullista palautusta
                System.out.println("Adding item failed: " + e.getMessage());
            }
        });
        
        tfName.setOnMouseClicked((event) -> {
            box.getChildren().remove(done);
            box.getChildren().remove(exists);
            box.getChildren().remove(exception);
        });

        gp.setOnMouseClicked((event) -> {
            box.getChildren().remove(done);
            box.getChildren().remove(exists);
            box.getChildren().remove(exception);
            succ.setText("");
            error.setText("");
        });
        
        //poistaminen
//        Button delete = new Button("Remove item");
//        box.getChildren().add(delete);
//        VBox boxRm = new VBox();
//        Label lItemToRm = new Label("Which item do you want to remove from this hike?");
//        TextField itemToRm = new TextField();
//        Button remove = new Button("Delete");
//        boxRm.getChildren().addAll(lItemToRm, itemToRm, remove);
//        
//        delete.setOnAction((event) -> {
//            gp.add(boxRm, 0, 2);
//        });
//        
//        Label succ = new Label("Item removed succesfully!");
//        Label unsucc = new Label("Couldn't remove item. Make sure you wrote the name correctly.");
//        
//        remove.setOnAction((event) -> {
//            String name = itemToRm.getText();
//            if (c.removeItem(hike, name)) {
//                boxRm.getChildren().add(succ);
//                itemToRm.clear();
//                equipmentList.setText(hike.formatEquipment());
//            } else {
//                boxRm.getChildren().add(unsucc);
//            }
//        });

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        gp.add(box, 0, 2);

        return gp;
    }
    
    private void formatItemsBox() {
        gp.getChildren().remove(equipmentList);
        equipmentList = new VBox();
        
        for (Item i : hike.getItems()) {
            HBox boxH = new HBox();
            boxH.getChildren().addAll(new Label(i.toString()), minusButton(i), plusButton(i));
            equipmentList.getChildren().addAll(boxH);
        }
        gp.add(equipmentList, 0, 1);
    }

    private Button minusButton(Item i) {
        Button b = new Button("-");
        
        style(b);
        b.setOnAction((event) -> {
            int count = i.getCount() - 1;
            if (count < 1) {
                //hike.removeItem(i.getName());
                if (c.removeItem(hike, i.getName())) {
                    succ.setText("Subtraction succeeded!");
                } else {
                    error.setText("Subtraction failed.");
                }
            } else {
                //hike.updateItem(i.getName(), count);
                i.setCount(count);
                if (c.addItem(hike, i)) {
                    succ.setText("Subtraction succeeded!");
                } else {
                    error.setText("Subtraction failed.");
                }
            }
            formatItemsBox();
        });
        
        return b;
    }
    
    private Button plusButton(Item i) {
        Button b = new Button("+");
        
        style(b);
        b.setOnAction((event) -> {
            int count = i.getCount() + 1;
            //hike.updateItem(i.getName(), count);
            i.setCount(count);
            c.addItem(hike, i);
            formatItemsBox();
        });
        
        return b;
    }
    
    private void style(Button b) {
        b.setStyle(
                    "-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em; "
                    + "-fx-min-width: 27px; "
                    + "-fx-min-height: 27px; "
                    + "-fx-max-width: 27px; "
                    + "-fx-max-height: 27px;"
            );
    }
}
