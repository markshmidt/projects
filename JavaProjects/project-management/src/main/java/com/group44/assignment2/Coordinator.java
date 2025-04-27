package com.group44.assignment2;

import com.group44.assignment2.managers.DepartmentManager;
import com.group44.assignment2.managers.PayrollManager;
import com.group44.assignment2.managers.ShiftManager;
import com.group44.assignment2.models.*;
import com.group44.assignment2.managers.EmployeeManager;
import com.group44.assignment2.storage.Storage;
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import com.group44.assignment2.models.*;
import com.group44.assignment2.managers.EmployeeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Coordinator {
    private EmployeeManager employeeManager;
    private ShiftManager shiftManager;
    private PayrollManager payrollManager;
    private DepartmentManager departmentManager;

    private Storage storage;

    public Coordinator() {
        this.employeeManager = new EmployeeManager(100);
        this.shiftManager = new ShiftManager(100);
        this.payrollManager = new PayrollManager(100);
        this.departmentManager = new DepartmentManager(100);

        try {
            this.storage = new Storage("jdbc:sqlite:test.db");

            this.storage.getEmployees().forEach(emp -> this.employeeManager.addEmployee(emp));
            this.storage.getShifts().forEach(shift -> this.shiftManager.addShift(shift));
            this.storage.getDepartments().forEach(department -> this.departmentManager.addDepartment(department));
            this.storage.getPayrolls().forEach(payroll -> this.payrollManager.addPayroll(payroll));
                    } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Coordinator(EmployeeManager managerEmployee, ShiftManager shiftManager, PayrollManager payrollManager, DepartmentManager departmentManager) {
        this.employeeManager = managerEmployee;
        this.shiftManager = shiftManager;
        this.payrollManager = payrollManager;
        this.departmentManager = departmentManager;
    }


    // Departments
    public void addDepartment(Department department) {
        departmentManager.addDepartment(department);
        this.upsertData();
    }

    public void addDepartment(String name, int managerId, int baseHours, double taxRate) {
        departmentManager.addDepartment(new Department(name, managerId, baseHours, taxRate));
        this.upsertData();
    }

    public void editDepartment(Department oldDepartment, Department newDepartment) {
        this.departmentManager.editDepartment(oldDepartment, newDepartment);
        this.upsertData();
    }

    public ObservableList<Department> getDepartmentsSubscription() {
        return departmentManager.getAllDepartments();
    }

    // Employee
    public void addEmployee(Employee employee) {
        employeeManager.addEmployee(employee);
        this.upsertData();
    }

    public void editEmployee(Employee oldEmployee, Employee newEmployee){
        employeeManager.editEmployee(oldEmployee, newEmployee);
        this.upsertData();
    }

    public void removeEmployee(Employee employee) {
        try {
            storage.deleteEmployee(employee);
            employeeManager.removeEmployee(employee);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addManager(String name, String email, String phone, double baseSalary, int departmentId, String jobTitle, String password) {
        employeeManager.addManager(name, email, phone, baseSalary, departmentId, jobTitle, password);
        this.upsertData();
    }

    public void addContractor(String name, String email, String phone, double hourlyRate, int departmentId, String jobTitle, String password) {
        employeeManager.addContractor(name, email, phone, hourlyRate, departmentId, jobTitle, password);
        this.upsertData();
    }

    public void addPermanent(String name, String email, String phone, double baseSalary, int departmentId, String
            jobTitle, String password) {
        employeeManager.addPermanent(name, email, phone, baseSalary, departmentId, jobTitle, password);
        this.upsertData();
    }

    public ObservableList<Employee> getEmployeesSubscription() {
        return employeeManager.getAllEmployees();
    }

    public ObservableList<Manager> getManagersSubscription() {
        ObservableList<Manager> managers = getEmployeesSubscription().stream()
                .filter(Manager.class::isInstance)
                .map(Manager.class::cast)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return managers;
    }

    public Employee authenticateEmployee(String email, String password) {
        return employeeManager.getAllEmployees()
                .stream()
                .filter(employee -> employee.getEmail().equals(email) && employee.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    // Payrolls
    public void generatePayroll(Employee employee) {
        var department = departmentManager.getDepartmentById(employee.getDepartmentId());
        var shifts = shiftManager.getShiftsByEmployeeId(employee.getId()).stream().filter(shift -> (shift.getPayrollId() == null || shift.getPayrollId() == 0) && shift.getFinish() != null).collect(Collectors.toList());
        Payroll newPayroll = payrollManager.addPayroll(employee, department, shifts);
        shifts.forEach((shift) -> {
            shift.setPayrollId(newPayroll.getId());
        });
        this.upsertData();
    }

    public void approvePayroll(Payroll payroll) {
        payrollManager.setPayrollStatus(payroll, Payroll.PayrollStatus.APPROVED);
        this.upsertData();
    }

    public void declinePayroll(Payroll payroll) {
        payrollManager.setPayrollStatus(payroll, Payroll.PayrollStatus.REJECTED);
        this.upsertData();
    }

    public ObservableList<Payroll> getPayrollsSubscription() {
        return payrollManager.getPayrolls();
    }

    //Shifts
    public void addShift(Shift shift) {
        this.shiftManager.addShift(shift);
        this.upsertData();        
    }

    public ObservableList<Shift> getShiftsSubscription() {
        return shiftManager.getAllShifts();
    }

    public Optional<Shift> getActiveShift(Employee employee) {
        return shiftManager.getShiftsByEmployeeId(employee.getId()).stream().filter(s -> s.getFinish() == null).findFirst();
    }


    public void startShift(Employee employee) {
        this.shiftManager.addShift(employee);
        this.upsertData();
    }

    public void endShift(Employee employee) {
        var shift = this.getActiveShift(employee);
        if (shift.isEmpty()) {
            return;
        }
        shiftManager.finishShift(shift.get());
        this.upsertData();
    }



    public void upsertData(){
        try{
            System.out.println("UPSERT DATA");
            this.storage.upsertEmployees(new ArrayList<Employee>(employeeManager.getAllEmployees()));
            this.storage.upsertDepartments(new ArrayList<Department> (departmentManager.getAllDepartments()));
            this.storage.upsertPayrolls(new ArrayList<Payroll>(payrollManager.getPayrolls()));
            this.storage.upsertShifts(new ArrayList<Shift>(this.shiftManager.getAllShifts()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
