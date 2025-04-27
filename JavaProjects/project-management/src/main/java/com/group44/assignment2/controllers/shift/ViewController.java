package com.group44.assignment2.controllers.shift;

import com.group44.assignment2.App;
import com.group44.assignment2.models.*;
import com.group44.assignment2.util.PermissionHelper;
import com.group44.assignment2.util.PermissionLevel;
import com.group44.assignment2.views.ViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ViewController implements Initializable {


    public Text titleText;
    public Button saveButton;
    public ChoiceBox<Employee> employeeChoiceBox;
    public TextField rateField;
    public DatePicker startDatePicker;
    public TextField startTimeField;
    public DatePicker endDatePicker;
    public TextField endTimeField;
    private Shift shift;

    private ObservableList<Employee> possibleEmployees;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.titleText.setText("New Shift");

        this.possibleEmployees = App.getCoordinator().getEmployeesSubscription();
        this.employeeChoiceBox.setItems(possibleEmployees);
        employeeChoiceBox.setConverter(new StringConverter<Employee>() {
            @Override
            public String toString(Employee employee) {
                return employee == null ? "" : employee.getName();
            }

            @Override
            public Employee fromString(String string) {
                return null;
            }
        });
    }

    public void setShift(Shift shift) {
        this.shift = shift;

        this.titleText.setText("Edit shift");

        saveButton.setVisible(false);
        var employee = possibleEmployees.stream().filter(m -> m.getId() != null && m.getId().equals(shift.getEmployeeId())).findFirst().orElse(null);
        this.employeeChoiceBox.getSelectionModel().select(employee);
        this.employeeChoiceBox.setDisable(true);

        this.startDatePicker.setValue(shift.getStart().toLocalDate());
        this.startDatePicker.setDisable(true);

        this.startTimeField.setText(shift.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        this.startTimeField.setDisable(true);
        if (shift.getFinish() != null) {
            this.endDatePicker.setValue(shift.getFinish().toLocalDate());
        } else {
            System.out.println("Finish time is NULL for shift ID: " + shift.getId());
            this.endDatePicker.setValue(null); // Ensure the date picker is set correctly
        }

        this.endDatePicker.setDisable(true);

        this.endTimeField.setText(shift.getFinish().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        this.endTimeField.setDisable(true);

        this.rateField.setText(String.valueOf(shift.getBaseRate()));
        this.rateField.setDisable(true);
    }

    @FXML
    private void back() {
        var stage = (Stage) titleText.getScene().getWindow();
        ViewFactory.closeStage(stage);
    }

    @FXML
    private void save() {
        double rate;
        LocalTime startTime;
        LocalTime endTime;
        Employee employee = employeeChoiceBox.getValue();
        if (employee == null) {
            return;
        }
        try {
            rate = Double.parseDouble(rateField.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            startTime = LocalTime.parse(startTimeField.getText());
            endTime = LocalTime.parse(endTimeField.getText());
        } catch (Exception e) {
            return;
        }
        var startDate = startDatePicker.getValue();
        var endDate = endDatePicker.getValue();

        var shift = new Shift(employee.getId(), startDate.atTime(startTime), endDate.atTime(endTime), rate);
        App.getCoordinator().addShift(shift);

        back();
    }
}
