package com.group44.assignment2.managers;

import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepartmentManager {
    private final ObservableList<Department> departments;
    int maxCount = 0;
    int currentCount = 0;

    public DepartmentManager(int maxCount) {
        this.maxCount = maxCount;
        departments = FXCollections.observableArrayList();
    }

    public void addDepartment(Department department) {
        addEntity(department);
    }

    public void addDepartment(String name, int managerId, int baseHours, double taxRate) {
        addEntity(new Department(name, managerId, baseHours, taxRate));
    }

    public ObservableList<Department> getAllDepartments() {
        return FXCollections.unmodifiableObservableList(departments);
    }

    public Department getDepartmentById(int id) {
        for (Department department : getAllDepartments()) {
            if (department.getId() == id) {
                return department;
            }
        }
        return null;
    }

    public void removeDepartment(Department department) {
        removeEntity(department);
    }

    private void addEntity(Department department) {
        if (currentCount >= maxCount) {
            throw new RuntimeException("Maximum employee count reached.");
        }
        departments.add(department);
    }

    public void editDepartment(Department oldDepartment, Department newDepartment) {
        int index = departments.indexOf(oldDepartment);
        departments.set(index, newDepartment);
    }

    private void removeEntity(Department department) {
        if (departments.contains(department)) {
            departments.remove(department);
            currentCount--;
        }
        ;
    }
}
