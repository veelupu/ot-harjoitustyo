/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.dao.DBHikeDao;
import hikingdiary.dao.DBUserDao;
import hikingdiary.dao.HikeDao;
import hikingdiary.dao.UserDao;
import hikingdiary.domain.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class GraphicalUserInterface extends Application {

    //User user;
    Controller c;
    GridPane gp;
    BorderPane bp;
    HikeDao hikeDao;
    UserDao userDao;

    public GraphicalUserInterface() {
        //this.user = new User("Veera");
        this.hikeDao = new DBHikeDao();
        this.userDao = new DBUserDao();
        //this.userDao.create(user);
        this.c = new Controller(hikeDao, userDao);
        this.gp = new GridPane();
        this.bp = new BorderPane();
    }

    @Override
    public void start(Stage window) {
        if (userDao.read() == null) {
            openFirstUseView(window);
        } else {
            openMainMenuView(window);
        }
    }

    public void setTitle(Stage window) {
        window.setTitle(userDao.read().getName() + "’s Hiking Diary");
    }

    private void openFirstUseView(Stage window) {
        GridPane gp = new GridPane();

        Label intro = new Label("Hello there, new user!\nStart by entering a username of your choice.");
        TextField username = new TextField();
        Button ready = new Button("Ready!");

        VBox v = new VBox();
        v.getChildren().addAll(intro, username, ready);

        gp.setPrefSize(300, 180);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(20, 20, 20, 20));

        ready.setOnAction((event) -> {
            c.createUser(username.getText());
            openMainMenuView(window);
        });

        gp.add(v, 0, 0);

        Scene firstView = new Scene(gp);

        window.setScene(firstView);
        window.show();
    }

    private void openMainMenuView(Stage window) {
        window.setTitle(userDao.read().getName() + "’s Hiking Diary");

        Button bMainmenu = new Button("Main\nmenu");

        bMainmenu.setStyle("-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;");

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
