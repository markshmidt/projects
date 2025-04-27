package com.group44.assignment2.controllers.employee;

import com.group44.assignment2.App;
import com.group44.assignment2.models.*;
import com.group44.assignment2.util.EmployeeRole;
import com.group44.assignment2.views.ViewFactory;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    public Text titleText;
    public ChoiceBox<EmployeeRole> roleInput;
    public ChoiceBox<Department> departmentInput;
    public TextField nameInput;
    public TextField emailInput;
    public PasswordField passwordInput;
    public TextField phoneInput;
    public TextField rateInput;

    private Employee employee;
    private ObservableList<Department> possibleDepartments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.roleInput.getItems().addAll(EmployeeRole.values());
        roleInput.setConverter(new javafx.util.StringConverter<EmployeeRole>() {
            @Override
            public String toString(EmployeeRole role) {
                return role == null ? "" : role.displayValue;
            }

            @Override
            public EmployeeRole fromString(String string) {
                return null;
            }
        });

        this.possibleDepartments = App.getCoordinator().getDepartmentsSubscription();
        this.departmentInput.setItems(possibleDepartments);
        this.departmentInput.setConverter(new StringConverter<Department>() {
            @Override
            public String toString(Department department) {
                return department == null ? "" : department.getName();
            }

            @Override
            public Department fromString(String string) {
                return null;
            }
        });
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        EmployeeRole empRole;
        if (employee instanceof Manager) {
            empRole = EmployeeRole.MANAGER;
        } else if (employee instanceof Contractor) {
            empRole = EmployeeRole.CONTRACTOR;
        } else {
            empRole = EmployeeRole.PERMANENT;
        }

        this.roleInput.getSelectionModel().select(empRole);

        var department = possibleDepartments.stream().filter(m -> m.getId() == employee.getDepartmentId()).findFirst().orElse(null);
        this.departmentInput.getSelectionModel().select(department);

        this.nameInput.setText(employee.getName());
        this.emailInput.setText(employee.getEmail());
        this.phoneInput.setText(employee.getPhone());
        this.rateInput.setText(String.valueOf(employee.getBaseSalary()));

    }

    @FXML
    private void back() {
        var stage = (Stage) titleText.getScene().getWindow();
        ViewFactory.closeStage(stage);
    }

    @FXML
    private void save() {
        double salary;
        Employee employee = null;
        try {
            salary = Double.parseDouble(rateInput.getText());
        } catch (NumberFormatException e) {
            return;
        }
        switch (roleInput.getValue()) {
            case MANAGER:
                employee = new Manager(nameInput.getText(), emailInput.getText(), phoneInput.getText(), salary, this.departmentInput.getValue().getId(), "JobTitle", passwordInput.getText());
                break;
            case PERMANENT:
                employee = new Permanent(nameInput.getText(), emailInput.getText(), phoneInput.getText(), salary, this.departmentInput.getValue().getId(), "JobTitle", passwordInput.getText());
                break;
            case CONTRACTOR:
                employee = new Contractor(nameInput.getText(), emailInput.getText(), phoneInput.getText(), salary, this.departmentInput.getValue().getId(), "JobTitle", passwordInput.getText());
                break;
        }
        if (employee != null) {
            if (this.employee == null) {
                App.getCoordinator().addEmployee(employee);
            } else {
                App.getCoordinator().editEmployee(this.employee, employee);
            }
        }

        back();
    }
}
