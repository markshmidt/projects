package com.group44.assignment2.controllers.department;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Manager;
import com.group44.assignment2.views.ViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    public Text titleText;
    public TextField nameInput;
    public ChoiceBox<Manager> managerInput;
    public TextField baseHoursInput;
    public TextField taxRateInput;
    private Department department;
    private ObservableList<Manager> possibleManagers = App.getCoordinator().getManagersSubscription();
    public ViewController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.managerInput.setItems(possibleManagers);
        managerInput.setConverter(new javafx.util.StringConverter<Manager>() {
            @Override
            public String toString(Manager manager) {
                return manager == null ? "" : manager.getName();
            }

            @Override
            public Manager fromString(String string) {
                return null;
            }
        });
    }

    public void setDepartment(Department department) {
        this.department = department;
        this.nameInput.setText(department.getName());
        this.baseHoursInput.setText(String.valueOf(department.getBaseHours()));
        this.taxRateInput.setText(String.valueOf(department.getTaxRate()));
        var manager = possibleManagers.stream().filter(m->m.getId() == department.getManagerId()).findFirst().orElse(null);
        this.managerInput.getSelectionModel().select(manager);
    }

    @FXML
    private void back() {
        var stage = (Stage) titleText.getScene().getWindow();
        ViewFactory.closeStage(stage);
    }

    @FXML
    private void save() {
        int hourlyRate;
        double taxRate;
        int managerId = managerInput.getValue() == null ? 0 : managerInput.getValue().getId();
        try {
            hourlyRate = Integer.parseInt(baseHoursInput.getText());
        } catch (NumberFormatException e) {
            return;
        }
        try {
            taxRate = Double.parseDouble(taxRateInput.getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (department == null) {
            var department = new Department(nameInput.getText(), managerId, hourlyRate, taxRate);
            App.getCoordinator().addDepartment(department);
        } else {
            var newDepartment = new Department(nameInput.getText(), managerId, hourlyRate, taxRate);
            App.getCoordinator().editDepartment(department, newDepartment);
        }

        back();
    }
}
