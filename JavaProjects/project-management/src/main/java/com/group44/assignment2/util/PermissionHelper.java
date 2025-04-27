package com.group44.assignment2.util;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Manager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PermissionHelper {
    private static Employee authenticatedEmployee;
    private static PermissionLevel permissionLevel;

    public static boolean authenticate(String email, String password) {
        System.out.println("Authenticating user: " + email + " with password: " + password);
        var employee = App.getCoordinator().authenticateEmployee(email, password);
        if (employee == null) {
            System.out.println("Authentication failed: No such user.");
            return false;
        }
        authenticatedEmployee = employee;

        if (employee instanceof Manager) {
            if (employee.getDepartmentId() == 0) {
                permissionLevel = PermissionLevel.FULL;
            } else {
                permissionLevel = PermissionLevel.DEPARTMENT;
            }
        } else {
            permissionLevel = PermissionLevel.SELF;
        }

        System.out.println("Authentication successful for user: " + employee.getName());
        return true;
    }


    public static PermissionLevel getLevel() {
        return permissionLevel;
    }

    public static String getName() {
        return authenticatedEmployee.getName();
    }

    public static Employee getAuthenticatedEmployee() {
        return authenticatedEmployee;
    }

    public static void logout() {
        authenticatedEmployee = null;
    }
}
