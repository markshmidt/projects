package com.group44.assignment2.models;

import com.group44.assignment2.managers.PayrollManager;
import com.group44.assignment2.managers.ShiftManager;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "managers")
public class Manager extends Permanent {


    public Manager() {}

    public Manager(String name, String email, String phone, double baseSalary, Integer departmentId, String jobTitle, String password) {
        super(name, email, phone, baseSalary, departmentId, jobTitle, password);
    }

}
