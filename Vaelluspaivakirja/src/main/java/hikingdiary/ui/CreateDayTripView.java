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
    
    public Parent getView(Hike hike) {
        GridPane gp = new GridPane();
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        
        Label lDate = new Label("Add a new day trip!");
        DatePicker date = new DatePicker();
        
        Label lStart = new Label("My day trip starts here:");
        TextField tfStart = new TextField();
        
        Label lEnd = new Label("My day trip ends here:");
        TextField tfEnd = new TextField();
        
        Label lDist = new Label("Kilometres of the day:");
        TextField tfDist = new TextField();
        
        Label lTime = new Label("Walking hours of the day:");
        TextField tfHours = new TextField();
        
        Label lWeather = new Label("The weather was like:");
        TextField tfWeather = new TextField();
        
        Button ready = new Button("Ready\nto add!");
        
        ready.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 70px; "
                + "-fx-min-height: 70px; "
                + "-fx-max-width: 70px; "
                + "-fx-max-height: 70px;"
        );
        
        box.getChildren().addAll(lDate, date, lStart, tfStart, lEnd, tfEnd, lDist, tfDist, lTime, tfHours, lWeather, tfWeather, ready);
        
        Label done = new Label("Day trip added!");
        Label exists = new Label("This hike has this day trip already.");
        Label error = new Label("Oops, something went wrong. Did you fill all the textfields?");
        
        ready.setOnAction((event) -> {
            try {
                LocalDate d = date.getValue();
                String start = tfStart.getText();
                String end = tfEnd.getText();
                double dist = Double.valueOf(tfDist.getText());
                double hours = Double.valueOf(tfHours.getText()); 
                String weather = tfWeather.getText();
                
                DayTrip dt = new DayTrip(d, start, end, dist, hours, weather);
                
                if (c.addDayTrip(hike, dt)) {
                    box.getChildren().add(done);
                } else {
                    box.getChildren().add(exists);
                }
                
                tfStart.clear();
                tfEnd.clear();
                tfDist.clear();
                tfHours.clear();
                tfWeather.clear();
            } catch (Exception e) {
                box.getChildren().add(error);
            }
        });
        
        tfStart.setOnAction((event) -> {
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
