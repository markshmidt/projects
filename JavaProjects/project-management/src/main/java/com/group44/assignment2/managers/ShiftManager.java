package com.group44.assignment2.managers;


import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Shift;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShiftManager {
    private final ObservableList<Shift> shifts;
    int maxCount = 0;
    int currentCount = 0;

    public ShiftManager(int maxCount) {
        this.maxCount = maxCount;
        shifts = FXCollections.observableArrayList();
    }

    public void addShift(Shift shift) {
        this.addEntity(shift);
    }

    public void addShift(Employee employee) {
        this.addEntity(new Shift(employee.getId(), LocalDateTime.now(), null, employee.getBaseSalary()));
    }

    public void finishShift(Shift shift) {
        shift.setFinish(LocalDateTime.now());
    }

    public Shift getShiftById(int id) {
        for (Shift shift : getAllShifts()) {
            if (shift.getId() == id) {
                return shift;
            }
        }
        return null;
    }

    public List<Shift> getShiftsByEmployeeId(int employeeId) {
        List<Shift> shifts = new ArrayList<>();
        for (Shift shift : getAllShifts()) {
            if (shift.getEmployeeId() == employeeId) {
                shifts.add(shift);
            }
        }
        return shifts;
    }

    public ObservableList<Shift> getAllShifts() {
        return FXCollections.unmodifiableObservableList(shifts);
    }

    private void addEntity(Shift shift) {
        if (currentCount >= maxCount) {
            throw new RuntimeException("Maximum employee count reached.");
        }
        shifts.add(shift);
        currentCount++;
    }

    private void removeEntity(Shift shift) {
        if (shifts.contains(shift)) {
            shifts.remove(shift);
            currentCount--;
        }

    }

}
