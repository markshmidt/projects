module com.group44.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;
    requires java.sql;
    requires java.desktop;
    requires annotations;

    opens com.group44.assignment2 to javafx.fxml;
    exports com.group44.assignment2;
    exports com.group44.assignment2.models;
    opens com.group44.assignment2.models to javafx.fxml, ormlite.jdbc;
    exports com.group44.assignment2.managers;
    opens com.group44.assignment2.managers to javafx.fxml;
    exports com.group44.assignment2.controllers;
    opens com.group44.assignment2.controllers to javafx.fxml;
    exports com.group44.assignment2.util;
    exports com.group44.assignment2.controllers.department;
    opens com.group44.assignment2.controllers.department to javafx.fxml;
    exports com.group44.assignment2.controllers.employee;
    opens com.group44.assignment2.controllers.employee to javafx.fxml;
    exports com.group44.assignment2.controllers.payroll;
    opens com.group44.assignment2.controllers.payroll to javafx.fxml;
    exports com.group44.assignment2.controllers.shift;
    opens com.group44.assignment2.controllers.shift to javafx.fxml;
}
