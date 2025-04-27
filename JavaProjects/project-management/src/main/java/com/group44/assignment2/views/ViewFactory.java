package com.group44.assignment2.views;

import com.group44.assignment2.App;
import com.group44.assignment2.controllers.LoginController;
import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Payroll;
import com.group44.assignment2.models.Shift;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    private static AnchorPane dashboard;
    private static AnchorPane departments;
    private static AnchorPane employees;
    private static AnchorPane payrolls;
    private static AnchorPane shifts;

    public static AnchorPane getDashboard() {
        if (dashboard == null) {
            try {
                dashboard = new FXMLLoader(App.class.getResource("dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboard;

    }

    public static AnchorPane getDepartments() {
        if (departments == null) {
            try {
                departments = new FXMLLoader(App.class.getResource("department/list.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return departments;
    }

    public static void showDepartmentAdd() {
        var loader = new FXMLLoader(App.class.getResource("department/view.fxml"));
        try {
            Parent p = loader.load();
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDepartmentEdit(Department department) {
        var loader = new FXMLLoader(App.class.getResource("department/view.fxml"));
        try {
            Parent p = loader.load();
            com.group44.assignment2.controllers.department.ViewController c = loader.getController();
            c.setDepartment(department);
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AnchorPane getEmployees() {
        if (employees == null) {
            try {
                employees = new FXMLLoader(App.class.getResource("employee/list.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return employees;

    }

    public static void showEmployeeAdd() {
        var loader = new FXMLLoader(App.class.getResource("employee/view.fxml"));
        try {
            Parent p = loader.load();
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEmployeeEdit(Employee employee) {
        var loader = new FXMLLoader(App.class.getResource("employee/view.fxml"));
        try {
            Parent p = loader.load();
            com.group44.assignment2.controllers.employee.ViewController c = loader.getController();
            c.setEmployee(employee);
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AnchorPane getPayrolls() {
        if (payrolls == null) {
            try {
                payrolls = new FXMLLoader(App.class.getResource("payroll/list.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return payrolls;
    }

    public static void showPayrollAdd() {
        var loader = new FXMLLoader(App.class.getResource("payroll/view.fxml"));
        try {
            Parent p = loader.load();
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPayrollEdit(Payroll payroll) {
        var loader = new FXMLLoader(App.class.getResource("payroll/view.fxml"));
        try {
            Parent p = loader.load();
            com.group44.assignment2.controllers.payroll.ViewController c = loader.getController();
            c.setPayroll(payroll);
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AnchorPane getShifts(){
        if (shifts == null) {
            try {
                shifts = new FXMLLoader(App.class.getResource("shift/list.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shifts;
    }

    public static void showShiftAdd() {
        var loader = new FXMLLoader(App.class.getResource("shift/view.fxml"));
        try {
            Parent p = loader.load();
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showShiftEdit(Shift shift) {
        var loader = new FXMLLoader(App.class.getResource("shift/view.fxml"));
        try {
            Parent p = loader.load();
            com.group44.assignment2.controllers.shift.ViewController c = loader.getController();
            c.setShift(shift);
            createStage(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(ViewFactory.class.getResource("/com/group44/assignment2/login.fxml"));
        try {
            Parent root = loader.load();
            LoginController controller = loader.getController();

            // ✅ Ensure error message is cleared every time login screen is opened
            if (controller.errorText != null) {
                controller.errorText.setVisible(false);
                controller.errorText.setText("");
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("main.fxml"));
        createStage(loader);
    }

    public static void createStage(FXMLLoader loader) {
        try {
            var scene = new Scene(loader.load());
            scene.getStylesheets().add(App.class.getResource("style/global.css").toExternalForm());
            var stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Group 44 management system");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createStage(Parent loaded) {
        try {
            var scene = new Scene(loaded);
            scene.getStylesheets().add(App.class.getResource("style/global.css").toExternalForm());
            var stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Group 44 management system");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeStage(Stage stage) {
        stage.close();
    }
}
