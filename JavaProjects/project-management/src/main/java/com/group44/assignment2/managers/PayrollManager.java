package com.group44.assignment2.managers;

import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Payroll;
import com.group44.assignment2.models.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class PayrollManager {
    private ObservableList<Payroll> payrolls;
    int maxCount = 0;
    int currentCount = 0;

    public PayrollManager(int maxCpunt) {
        this.maxCount = maxCpunt;
        payrolls = FXCollections.observableArrayList();
    }

    public void addPayroll(Payroll payroll) {
        addEntity(payroll);
    }

    public ObservableList<Payroll> getPayrolls() {
        return FXCollections.unmodifiableObservableList(payrolls);
    }

    public Payroll addPayroll(Employee employee, Department department, List<Shift> shifts) {
        var res = new Object() {
            double totalBaseHours = 0;
            double totalOvertimeHours = 0;
            double totalBasePay = 0;
            double totalOvertimePay = 0;
        };
        var deptBaseHours = department == null ? 0 : department.getBaseHours();
        var deptTaxRate = department == null ? 0 : department.getTaxRate();
        shifts.forEach(shift -> {
            if (shift.getHoursWorked() > deptBaseHours) {
                res.totalBaseHours += deptBaseHours;
                res.totalBasePay += deptBaseHours * shift.getBaseRate();
                res.totalOvertimeHours += shift.getHoursWorked() - deptBaseHours;
                res.totalOvertimePay += (shift.getHoursWorked() - deptBaseHours) * shift.getBaseRate() * Payroll.OVERTIME_COEFFICIENT;
            } else {
                res.totalBaseHours += shift.getHoursWorked();
                res.totalBasePay += deptBaseHours * shift.getBaseRate();
            }
        });
        Payroll newPayroll = new Payroll(employee.getId(), employee.getBaseSalary(), deptTaxRate, res.totalBaseHours, res.totalOvertimeHours, res.totalBasePay, res.totalOvertimePay);
        addEntity(newPayroll);
        return newPayroll;
    }

    public void setPayrollStatus(Payroll payroll, Payroll.PayrollStatus status) {
        payroll.setStatus(status);
    }

    private void addEntity(Payroll payroll) {
        if (currentCount >= maxCount) {
            throw new RuntimeException("Maximum employee count reached.");
        }
        payrolls.add(payroll);
        currentCount++;
    }
}
