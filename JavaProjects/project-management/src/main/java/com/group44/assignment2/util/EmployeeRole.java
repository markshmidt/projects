package com.group44.assignment2.util;

public enum EmployeeRole {
    MANAGER("Manager"), PERMANENT("Permanent Employee"), CONTRACTOR("Contractor");

    public final String displayValue;

    private EmployeeRole(String displayValue) {
        this.displayValue = displayValue;
    }
}
