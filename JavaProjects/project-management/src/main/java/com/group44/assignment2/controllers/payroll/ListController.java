package com.group44.assignment2.controllers.payroll;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Payroll;
import com.group44.assignment2.models.Shift;
import com.group44.assignment2.views.ViewFactory;
import com.group44.assignment2.views.cells.PayrollCell;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class ListController implements Initializable {
    public ListView<Payroll> payrollList;
    public Button addButton;
    private Set<Integer> empMissingPayrolls;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        payrollList.setItems(App.getCoordinator().getPayrollsSubscription());
        payrollList.setCellFactory(list -> new PayrollCell());

        empMissingPayrolls = App.getCoordinator().getShiftsSubscription().stream()
                .filter(shift -> shift.getPayrollId() == null || shift.getPayrollId() == 0)
                .map(Shift::getEmployeeId).collect(Collectors.toSet());

        if (!empMissingPayrolls.isEmpty()) {
            addButton.setDisable(false);
            addButton.setManaged(true);
            addButton.setText("Generate for " + empMissingPayrolls.size());
        } else {
            addButton.setDisable(true);
            addButton.setManaged(false);
        }

        App.getCoordinator().getShiftsSubscription().addListener((ListChangeListener<Shift>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    empMissingPayrolls = App.getCoordinator().getShiftsSubscription().stream()
                            .filter(shift -> shift.getPayrollId() == null || shift.getPayrollId() == 0)
                            .map(Shift::getEmployeeId).collect(Collectors.toSet());

                    if (!empMissingPayrolls.isEmpty()) {
                        addButton.setDisable(false);
                        addButton.setManaged(true);
                        addButton.setText("Generate for " + empMissingPayrolls.size());
                    } else {
                        addButton.setDisable(true);
                        addButton.setManaged(false);
                    }
                }
            }
        });
    }

    public void add() {
        var employees = App.getCoordinator().getEmployeesSubscription()
                .stream()
                .filter(employee -> empMissingPayrolls.contains(employee.getId()))
                .collect(Collectors.toList());
        employees.forEach(employee -> {
            App.getCoordinator().generatePayroll(employee);
        });
    }
}
