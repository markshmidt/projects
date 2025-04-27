package com.group44.assignment2.models;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contractors")
public class Contractor extends Employee {
    public Contractor() {}

    public Contractor(String name, String email, String phone, double hourlyRate, Integer departmentId, String jobTitle, String password) {
        super(name, email, phone, hourlyRate, departmentId, jobTitle, password);
    }
}
