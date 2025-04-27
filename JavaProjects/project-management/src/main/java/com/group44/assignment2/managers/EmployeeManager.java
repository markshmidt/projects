package com.group44.assignment2.managers;

import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Contractor;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Manager;
import com.group44.assignment2.models.Permanent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private ObservableList<Employee> employees;
    int maxCount = 0;
    int currentCount = 0;

    public EmployeeManager(int maxCount) {
        this.maxCount = maxCount;
        employees = FXCollections.observableArrayList();
    }

    public void addEmployee(Employee employee) {
        this.addEntity(employee);
    }

    public void addManager(String name, String email, String phone, double baseSalary, int departmentId, String jobTitle, String password) {
        this.addEntity(new Manager(name, email, phone, baseSalary, departmentId, jobTitle, password));
    }

    public void addPermanent(String name, String email, String phone, double baseSalary, int departmentId, String jobTitle, String password) {
        this.addEntity(new Permanent(name, email, phone, baseSalary, departmentId, jobTitle, password));
    }

    public void addContractor(String name, String email, String phone, double hourlyRate, int departmentId, String jobTitle, String password) {
        this.addEntity(new Contractor(name, email, phone, hourlyRate, departmentId, jobTitle, password));
    }

    public Employee getEmployeeById(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        System.out.println("Employee not found.");
        return null;
    }


    public void removeEmployee(Employee employee) {
        this.removeEntity(employee);
    }

    public void editEmployee(Employee oldEmployee, Employee newEmployee) {
        oldEmployee.setBaseSalary(newEmployee.getBaseSalary());
        oldEmployee.setEmail(newEmployee.getEmail());
        oldEmployee.setJobTitle(newEmployee.getJobTitle());
        oldEmployee.setName(newEmployee.getName());
        oldEmployee.setPhone(newEmployee.getPhone());
        oldEmployee.setPassword(newEmployee.getPassword());
        oldEmployee.setDepartmentId(newEmployee.getDepartmentId());
    }

    public void removeEmployeeById(int employeeId) {
        Employee employee = getEmployeeById(employeeId);
        this.removeEntity(employee);
    }

    public ObservableList<Employee> getAllEmployees() {
        return FXCollections.unmodifiableObservableList(employees);
    }

    public List<Manager> getAllManagers() {
        return getAllEmployeesByType(Manager.class);
    }

    public List<Contractor> getAllContractors() {
        return getAllEmployeesByType(Contractor.class);
    }

    public List<Permanent> getAllPermanents() {
        return getAllEmployeesByType(Permanent.class);
    }

    public <T extends Employee> List<T> getAllEmployeesByType(Class<T> type) {
        List<T> filteredEmployees = new ArrayList<>();
        for (Employee employee : getAllEmployees()) {
            if (type.isInstance(employee)) {
                filteredEmployees.add(type.cast(employee));
            }
        }
        return filteredEmployees;
    }

    public List<Employee> getEmployeesByDepartment(int departmentId) {
        List<Employee> filteredEmployees = new ArrayList<>();
        for (Employee employee : getAllEmployees()) {
            if (employee.getDepartmentId() == departmentId) {
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }

    private void addEntity(Employee employee) {
        if (currentCount >= maxCount) {
            throw new RuntimeException("Maximum employee count reached.");
        }
        employees.add(employee);
        currentCount++;
    }

    private void removeEntity(Employee employee) {
        if (employees.contains(employee)) {
            employees.remove(employee);
            currentCount--;
        }
        ;
    }
}
