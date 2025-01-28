package com.group44.assignment2.models;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "permanent")
public class Permanent extends Employee {

    public Permanent() {}

    public Permanent(String name, String email, String phone, double baseSalary, Integer departmentId, String jobTitle, String password) {
        super(name, email, phone, baseSalary, departmentId, jobTitle, password);
    }
}
