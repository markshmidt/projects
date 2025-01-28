package com.group44.assignment2.test;

import com.group44.assignment2.models.*;
import com.group44.assignment2.storage.Storage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {

        try {
            Storage storage = new Storage("jdbc:sqlite:test.db");


            // -------- Employee Test ----------
            ArrayList<Employee> employees = storage.getEmployees();
            System.out.println("Number of employees: " + employees.size());

            // Print each employee
            for (Employee employee : employees) {
                System.out.println("Type: " + employee.getId() + " " + employee.getClass().getName());
            }

            employees.add(new Manager("Jhon Doe", "email@email.com", "123456789", 1000, 1, "Job titile", "qwerty"));
            employees.add(new Permanent("Jhon Doe", "email@email.com", "123456789", 1000, 1, "Job titile", "qwerty"));
            employees.add(new Contractor("Jhon Doe", "email@email.com", "123456789", 1000, 1, "Job titile", "qwerty"));

            System.out.println("Number of employees: " + employees.size());
            storage.upsertEmployees(employees);

//            // -------- Payroll Test ----------
//            ArrayList<Payroll> payrolls = storage.getPayrolls();
//            System.out.println(payrolls.size());
//
//            payrolls.add(new Payroll(1, 5000, 0.05, 0.5, 5000));
//
//            System.out.println(payrolls.size());
//            storage.upsertPayrolls(payrolls);

//            // -------- Shift Test ----------
//            ArrayList<Shift> shifts = storage.getShifts();
//            System.out.println(shifts.size());
//            for (Shift shift : shifts) {
//                System.out.println(shift.getStart());
//            }
//
//            shifts.add(new Shift(1, LocalDateTime.now(),LocalDateTime.now(), 4 ));
//
//            System.out.println(shifts.size());
//            storage.upsertShifts(shifts);

//            // -------- Shift Department ----------
//            ArrayList<Department> departments = storage.getDepartments();
//            System.out.println(departments.size());
//
//            departments.add(new Department("Name", 1, 40, 0.05));
//
//            System.out.println(departments.size());
//            storage.upsertDepartments(departments);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
