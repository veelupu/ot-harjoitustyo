/*
 * /*
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

/**
 *
 * @author veeralupunen
 */
public class FirstUseView {

    Controller c;

    public FirstUseView(Controller c) {
        this.c = c;
    }

    public Parent getView() {

        GridPane gp = new GridPane();

        Label intro = new Label("Hello there, new user!\nStart by entering a username of your choice.");
        TextField username = new TextField();
        Button ready = new Button("Ready!");

        VBox v = new VBox();
        v.getChildren().addAll(intro, username, ready);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(20, 20, 20, 20));

        ready.setOnAction((event) -> {
            c.createUser(username.getText());
            return;
        });

        gp.add(v, 0, 0);
        return gp;
    }

}
