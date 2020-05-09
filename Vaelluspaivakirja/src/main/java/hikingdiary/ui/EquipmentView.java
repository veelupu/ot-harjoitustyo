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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author veeralupunen
 */
public class EquipmentView {

    Controller c;
    Hike hike;
    GridPane gp;
    VBox equipmentList;

    public EquipmentView(Controller c) {
        this.c = c;
    }

    public Parent getView(Hike hike) {
        gp = new GridPane();
        this.hike = hike;

        Label title = new Label("Your equipment during " + hike.toString() + ":");
        gp.add(title, 0, 0);

        formatItemsBox();

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
        Label exception = new Label("Oops, weight and count should be numbers.\nTry again!");

        BackgroundFill bgF = new BackgroundFill(Color.WHITE, new CornerRadii(1), null);
        done.setBackground(new Background(bgF));
        exists.setBackground(new Background(bgF));
        exception.setBackground(new Background(bgF));
        
        ready.setOnAction((event) -> {
            try {
                Item item = new Item(tfName.getText(), Integer.valueOf(tfCount.getText()));

                if (tfWeight.getLength() != 0) {
                    item.setWeight(Double.valueOf(tfWeight.getText()));
                }
                if (!hike.addItem(item)) {
                    box.getChildren().add(exists);
                    return;
                }
                if (c.addItem(hike, item)) {
                    formatItemsBox();
                    tfName.clear();
                    tfWeight.clear();
                    tfCount.clear();
                    box.getChildren().add(done);
                } else {
                    box.getChildren().add(exception);
                }
            } catch (Exception e) {
                box.getChildren().add(exception);
            }
        });

        tfName.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, exception);
        });

        tfWeight.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, exception);
        });

        tfCount.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, exception);
        });

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
                c.removeItem(hike, i.getName());
            } else {
                i.setCount(count);
                c.addItem(hike, i);
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
