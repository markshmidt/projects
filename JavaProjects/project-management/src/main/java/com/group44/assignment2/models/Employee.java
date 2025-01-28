package com.group44.assignment2.models;

import com.j256.ormlite.field.DatabaseField;
import java.io.Console;

public abstract class Employee {

    static private int idCounter = 1;

    @DatabaseField(id = true)
    protected Integer id;
    @DatabaseField
    protected String name;
    @DatabaseField
    protected String email;
    @DatabaseField
    protected String phone;
    @DatabaseField
    protected double baseSalary;
    @DatabaseField
    protected int departmentId;
    @DatabaseField
    protected String jobTitle;
    @DatabaseField
    protected String password;


    public Employee() {}

    public void postLoad(){
        idCounter = Math.max(idCounter, this.id + 1);
    }

    public Employee(String name, String email, String phone, double baseSalary, Integer departmentId, String jobTitle, String password) {
        this.id = idCounter++;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.baseSalary = baseSalary;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
