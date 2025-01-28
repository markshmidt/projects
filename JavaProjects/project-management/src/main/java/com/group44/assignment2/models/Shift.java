package com.group44.assignment2.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.Duration;
import java.time.LocalDateTime;

@DatabaseTable(tableName = "shifts")
public class Shift {

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField
    private int employeeId;
    // Not the best approach, because it serializes object to bytes
    // TODO: add a custom class presister, to convert data to timestamp
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDateTime start;
    // Not the best approach, because it serializes object to bytes
    // TODO: add a custom class presister, to convert data to timestamp
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDateTime finish;
    @DatabaseField
    private double baseRate;
    @DatabaseField
    private Integer payrollId;

    public Shift(){}

    public Shift(Integer employeeId, LocalDateTime start, LocalDateTime finish, double baseRate) {
        this.employeeId = employeeId;
        this.start = start;
        this.finish = finish;
        this.baseRate = baseRate;
    }

    public Shift(Integer employeeId, LocalDateTime start, double baseRate) {
        this.employeeId = employeeId;
        this.start = start;
        this.baseRate = baseRate;
    }

    public Shift(int employeeId, double baseRate) {
        this.employeeId = employeeId;
        this.start = LocalDateTime.now();
        this.baseRate = baseRate;
    }
    
    public Integer getId() {
        return id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public double getHoursWorked() {
        return Duration.between(start, finish).toHours();
    }

    public Integer getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Integer payrollId) {
        this.payrollId = payrollId;
    }
}
