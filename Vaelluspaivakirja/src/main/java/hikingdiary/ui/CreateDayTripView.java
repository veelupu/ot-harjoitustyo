/*
 * /*
 * Copyright (c) Veera Lupunen 2020. All rights reserved.
 */
package hikingdiary.ui;

import hikingdiary.domain.Controller;
import hikingdiary.domain.DayTrip;
import hikingdiary.domain.Hike;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author veeralupunen
 */
public class CreateDayTripView {

    Controller c;

    public CreateDayTripView(Controller c) {
        this.c = c;
    }

    public Parent getCreationView(Hike hike) {
        return formatContent(hike, null, false, "", "", 0, 0, "");
    }
    
    public Parent getModifyView(Hike hike, DayTrip td) {
        return formatContent(hike, td.getDate(), true, td.getStartingPoint(), td.getEndingPoint(), td.getWalkDist(), td.getWalkTime(), td.getWeather());
    }
    
    private GridPane formatContent(Hike hike, LocalDate date, boolean disable, String start, String end, double dist, double hours, String weather) {
        GridPane gp = new GridPane();

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);

        Label lDate = new Label("Add a new day trip for " + hike.toString() + ":");
        DatePicker dateP = new DatePicker(date);
        dateP.setDisable(disable);

        Label lStart = new Label("My day trip starts here:");
        TextField tfStart = new TextField(start);

        Label lEnd = new Label("My day trip ends here:");
        TextField tfEnd = new TextField(end);

        Label lDist = new Label("Kilometres of the day:");
        TextField tfDist = new TextField("" + dist);

        Label lTime = new Label("Walking hours of the day:");
        TextField tfHours = new TextField("" + hours);

        Label lWeather = new Label("The weather was like:");
        TextField tfWeather = new TextField(weather);

        Button ready = new Button("Ready\nto add!");

        ready.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;"
        );

        box.getChildren().addAll(lDate, dateP, lStart, tfStart, lEnd, tfEnd, lDist, tfDist, lTime, tfHours, lWeather, tfWeather, ready);

        Label done = new Label("Day trip added!");
        Label exists = new Label("This hike has this day trip already.");
        Label error = new Label("Oops, something went wrong. Did you fill all the textfields correctly?");

        ready.setOnAction((event) -> {
            try {
                LocalDate d = dateP.getValue();
                String startV = tfStart.getText();
                String endV = tfEnd.getText();
                double distV = Double.valueOf(tfDist.getText());
                double hoursV = Double.valueOf(tfHours.getText());
                String weatherV = tfWeather.getText();

                DayTrip dt = new DayTrip(d, startV, endV, distV, hoursV, weatherV);

                if (c.addDayTrip(hike, dt)) {
                    box.getChildren().add(done);
                    tfStart.clear();
                    tfEnd.clear();
                    tfDist.clear();
                    tfHours.clear();
                    tfWeather.clear();
                } else {
                    box.getChildren().add(exists);
                }

            } catch (Exception e) {
                box.getChildren().add(error);
            }
        });
        
        tfStart.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, error);
        });

        tfEnd.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, error);
        });
        
        tfDist.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, error);
        });
        
        tfHours.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, error);
        });
        
        tfWeather.setOnMouseClicked((event) -> {
            box.getChildren().removeAll(done, exists, error);
        });

        gp.add(box, 0, 0);

        gp.setAlignment(Pos.CENTER);
        gp.setVgap(5);
        gp.setHgap(5);
        gp.setPadding(new Insets(5, 5, 5, 5));

        return gp;
    }
}
