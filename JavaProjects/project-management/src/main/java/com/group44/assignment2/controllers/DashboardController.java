package com.group44.assignment2.controllers;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Shift;
import com.group44.assignment2.util.PermissionHelper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text shiftStatusText;
    public Button shiftButton;
    private final ObjectProperty<Shift> activeShift = new SimpleObjectProperty<>(new Shift());
    private long secondsOnShift = 0;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        activeShift.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                secondsOnShift = Duration.between(newValue.getStart(), LocalDateTime.now()).getSeconds();
                timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
                    secondsOnShift++;
                    shiftStatusText.setText("On shift: " + formatTime((int)secondsOnShift));
                }));
                timeline.setCycleCount(Timeline.INDEFINITE);

                // Start the timer
                timeline.play();
                shiftButton.setText("End shift");
                shiftButton.setOnAction(event -> {
                    endShift();
                });
            } else {
                shiftStatusText.setText("Not on shift");
                if (timeline != null) {
                    timeline.stop();
                }
                shiftButton.setText("Start shift");
                shiftButton.setOnAction(event -> {
                    startShift();
                });
            }
        });

        var shift = App.getCoordinator().getActiveShift(PermissionHelper.getAuthenticatedEmployee());
        shift.ifPresentOrElse(activeShift::setValue, () -> {
            activeShift.setValue(null);
        });
    }

    private void startShift() {
        if (activeShift.getValue() != null) {
            return;
        }
        App.getCoordinator().startShift(PermissionHelper.getAuthenticatedEmployee());
        var shift = App.getCoordinator().getActiveShift(PermissionHelper.getAuthenticatedEmployee());
        shift.ifPresent(value -> activeShift.setValue(value));
    }

    private void endShift() {
        if (activeShift.getValue() == null) {
            return;
        }
        App.getCoordinator().endShift(PermissionHelper.getAuthenticatedEmployee());
        activeShift.setValue(null);
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void restoreTimer(int startSeconds) {
        secondsOnShift = startSeconds;
        if (timeline != null) {
            timeline.playFromStart(); // Restart the timeline
        }
    }
}
