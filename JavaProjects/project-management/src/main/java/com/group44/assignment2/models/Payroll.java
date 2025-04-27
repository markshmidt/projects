package com.group44.assignment2.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "payrolls")
public class Payroll {

    final public static int OVERTIME_COEFFICIENT = 2;

    public enum PayrollStatus {
        NEED_APPROVAL, APPROVED, REJECTED
    }

    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField
    private int employeeId;
    @DatabaseField
    private double baseSalary;
    @DatabaseField
    private double taxRate;
    @DatabaseField
    private double regularHours;
    @DatabaseField
    private double hoursOvertime;
    @DatabaseField
    private double total;
    @DatabaseField
    private double tax;
    @DatabaseField
    private double gross;
    @DatabaseField
    private double totalRegular;
    @DatabaseField
    private double totalOvertime;
    @DatabaseField
    private PayrollStatus status;

    public Payroll() {
    }

    public Payroll(Integer employeeId, double baseSalary, double taxRate, double hoursWorked, double hoursOvertime, double totalRegular, double totalOvertime) {
        this.employeeId = employeeId;
        this.baseSalary = baseSalary;
        this.taxRate = taxRate;
        this.regularHours = hoursWorked;
        this.hoursOvertime = hoursOvertime;
        this.status = PayrollStatus.NEED_APPROVAL;
        this.totalRegular = totalRegular;
        this.totalOvertime = totalOvertime;
        this.calculateTotal();
    }

    private void calculateTotal() {
        double gross = this.totalRegular + this.totalOvertime;
        double tax = gross * taxRate;
        this.total = gross - tax;
        this.gross = gross;
        this.tax = tax;
    }


    public Integer getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public double getBaseSalary() {
        return this.baseSalary;
    }

    public double getTaxRate() {
        return this.taxRate;
    }

    public double getRegularHours() {
        return this.regularHours;
    }

    public double getHoursOvertime() {
        return this.hoursOvertime;
    }

    public double getTotal() {
        return this.total;
    }

    public double getTax() {
        return this.tax;
    }

    public double gross() {
        return this.gross;
    }

    public double getTotalRegular() {
        return this.totalRegular;
    }

    public double getTotalOvertime() {
        return this.totalOvertime;
    }

    public PayrollStatus getStatus() {
        return status;
    }

    public void setStatus(PayrollStatus status) {
        this.status = status;
    }

}
