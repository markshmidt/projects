package com.group44.assignment2.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "departments")
public class Department {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int managerId;
    @DatabaseField
    private int baseHours;
    @DatabaseField
    private double taxRate;

    public Department() {}

    public Department(String name, int managerId, int baseHours, double taxRate) {
        this.name = name;
        this.managerId = managerId;
        this.baseHours = baseHours;
        this.taxRate = taxRate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getBaseHours() {
        return baseHours;
    }

    public void setBaseHours(int baseHours) {
        this.baseHours = baseHours;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

}

