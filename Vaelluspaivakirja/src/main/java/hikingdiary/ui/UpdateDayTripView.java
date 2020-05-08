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
public class UpdateDayTripView {
    
    Controller c;
    
    public UpdateDayTripView(Controller c) {
        this.c = c;
    }
    
    public Parent getView(Hike hike, DayTrip dt) {
        GridPane gp = new GridPane();
        
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        
        Label lDate = new Label("Modify your day trip!");
        DatePicker date = new DatePicker(dt.getDate());
        date.setEditable(false);
        
        Label lStart = new Label("My day trip starts here:");
        TextField tfStart = new TextField(dt.getStartingPoint());
        
        Label lEnd = new Label("My day trip ends here:");
        TextField tfEnd = new TextField(dt.getEndingPoint());
        
        Label lDist = new Label("Kilometres of the day:");
        TextField tfDist = new TextField("" + dt.getWalkDist());
        
        Label lTime = new Label("Walking hours of the day:");
        TextField tfHours = new TextField("" + dt.getWalkTime());
        
        Label lWeather = new Label("The weather was like:");
        TextField tfWeather = new TextField(dt.getWeather());
        
        Button ready = new Button("Ready\nto\nmodify!");
        
        ready.setStyle(
                "-fx-text-alignment: center;"
                + "-fx-background-radius: 5em; "
                + "-fx-min-width: 80px; "
                + "-fx-min-height: 80px; "
                + "-fx-max-width: 80px; "
                + "-fx-max-height: 80px;"
        );
        
        box.getChildren().addAll(lDate, date, lStart, tfStart, lEnd, tfEnd, lDist, tfDist, lTime, tfHours, lWeather, tfWeather, ready);
        
        Label done = new Label("Day trip modified!");
        Label exists = new Label("This hike has this day trip already.");
        Label error = new Label("Oops, something went wrong. Did you fill all the textfields correctly?");
        
        ready.setOnAction((event) -> {
            try {
                LocalDate d = date.getValue();
                String start = tfStart.getText();
                String end = tfEnd.getText();
                double dist = Double.valueOf(tfDist.getText());
                double hours = Double.valueOf(tfHours.getText()); 
                String weather = tfWeather.getText();
                
                DayTrip newDT = new DayTrip(d, start, end, dist, hours, weather);
                
                c.updateDayTrip(hike, newDT);
                box.getChildren().add(done);
                
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
