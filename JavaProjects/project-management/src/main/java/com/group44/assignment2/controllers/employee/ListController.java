package com.group44.assignment2.controllers.employee;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.views.ViewFactory;
import com.group44.assignment2.views.cells.EmployeeCell;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public ListView<Employee> employeeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeList.setItems(App.getCoordinator().getEmployeesSubscription());
        employeeList.setCellFactory(list-> new EmployeeCell());
    }

    public void add() {
        ViewFactory.showEmployeeAdd();
    }
}
