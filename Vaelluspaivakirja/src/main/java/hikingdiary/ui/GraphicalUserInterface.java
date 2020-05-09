/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.dao.DBDayTripDao;
import hikingdiary.domain.Controller;
import hikingdiary.dao.DBHikeDao;
import hikingdiary.dao.DBUserDao;
import hikingdiary.dao.DayTripDao;
import hikingdiary.dao.HikeDao;
import hikingdiary.dao.UserDao;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class GraphicalUserInterface extends Application {

    Controller c;
    GridPane gp;
    BorderPane bp;
    HikeDao hikeDao;
    UserDao userDao;
    DayTripDao dtDao;

    public GraphicalUserInterface() {
        this.hikeDao = new DBHikeDao();
        this.userDao = new DBUserDao();
        this.dtDao = new DBDayTripDao();
        this.c = new Controller(hikeDao, userDao, dtDao);
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

        ready.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;"
        );

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(intro, username, ready);

        gp.setPrefSize(400, 250);
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setPadding(new Insets(20, 20, 20, 20));

        ready.setOnAction((event) -> {
            c.createUser(username.getText());
            openMainMenuView(window);
        });

        gp.add(box, 0, 0);

        Scene firstView = new Scene(gp);

        window.setScene(firstView);
        window.show();
    }

    private void openMainMenuView(Stage window) {
        String username = userDao.read().getName();
        window.setTitle(username + "’s Hiking Diary");

        ArrayList<Button> buttons = new ArrayList<>();

        Button createHike = new Button("Create\nnew hike");
        buttons.add(createHike);

        createHike.setOnAction((event) -> {
            bp.setCenter(new CreateHikeView(c).getView());
        });

        Button listPast = new Button("List\npast\nhikes");
        buttons.add(listPast);

        listPast.setOnAction((event) -> {
            bp.setCenter(new ListPastHikesView(c, this).getView());
        });

        Button listUpcoming = new Button("List\nupcoming\nhikes");
        buttons.add(listUpcoming);

        listUpcoming.setOnAction((event) -> {
            bp.setCenter(new ListUpcomingHikesView(c, this).getView());
        });

        Button settings = new Button("Settings");
        buttons.add(settings);

        settings.setOnAction((event) -> {
            bp.setCenter(new SettingsView(c, this, window).getView());
        });

        HBox box = new HBox();

        for (Button b : buttons) {
            box.getChildren().add(b);
            b.setStyle("-fx-text-alignment: center;"
                    + "-fx-background-radius: 5em;"
                    + "-fx-min-width: 80px;"
                    + "-fx-min-height: 80px;"
                    + "-fx-max-width: 80px;"
                    + "-fx-max-height: 80px;");
        }

        Image image = new Image("file:backgroudImage.png", 780, 780, false, true);
        BackgroundImage bgImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        bp.setBackground(new Background(bgImage));
        bp.setTop(box);
        bp.setPrefSize(800, 900);

        Label welcome = new Label("Welcome, " + username + "!\nGreat to see you!");

        VBox bBox = new VBox();
        bBox.getChildren().addAll(welcome);
        bBox.setAlignment(Pos.CENTER);
        bp.setCenter(bBox);

        Scene view = new Scene(bp);

        window.setScene(view);
        window.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

}
