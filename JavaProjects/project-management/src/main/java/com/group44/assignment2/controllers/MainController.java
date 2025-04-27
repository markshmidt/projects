package com.group44.assignment2.controllers;

import com.group44.assignment2.util.PermissionHelper;
import com.group44.assignment2.util.PermissionLevel;
import com.group44.assignment2.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public BorderPane main_parent;
    public Text welcomeText;
    public Button dashboardButton;
    public Button departmentButton;
    public Button employeeButton;
    public Button payrollButton;
    public Button shiftButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Welcome, "+ PermissionHelper.getName());
        var deptAccess = PermissionHelper.getLevel() == PermissionLevel.FULL;
        departmentButton.setVisible(deptAccess);
        departmentButton.setManaged(deptAccess);

        var employeeAccess = PermissionHelper.getLevel() == PermissionLevel.FULL || PermissionHelper.getLevel() == PermissionLevel.DEPARTMENT;
        employeeButton.setVisible(employeeAccess);
        employeeButton.setManaged(employeeAccess);
    }

    @FXML
    private void openDashboard() {
        main_parent.setCenter(ViewFactory.getDashboard());
    }

    @FXML
    private void openDepartments() {
        main_parent.setCenter(ViewFactory.getDepartments());
    }

    @FXML
    private void openEmployees() {
        main_parent.setCenter(ViewFactory.getEmployees());
    }

    @FXML
    private void openPayrolls() {
        main_parent.setCenter(ViewFactory.getPayrolls());
    }

    @FXML
    private void openShifts() {
        main_parent.setCenter(ViewFactory.getShifts());
    }

    @FXML
    private void logout() {
        ViewFactory.showLoginWindow();
        var stage = (Stage)this.welcomeText.getScene().getWindow();
        ViewFactory.closeStage(stage);
    }

}
