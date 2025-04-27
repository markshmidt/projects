package com.group44.assignment2;

import com.group44.assignment2.models.Department;
import com.group44.assignment2.models.Manager;
import com.group44.assignment2.views.ViewFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final Coordinator coordinator = new Coordinator();

    @Override
    public void start(Stage stage) throws IOException {
       coordinator.addEmployee(new Manager("CEO", "ceo", "1234567890", 200, 0, "CEO", "ceo"));
//        coordinator.addDepartment("Management", 1, 4, 0.15);
//        coordinator.addDepartment("DEVOPS", 1, 8, 0.15);
        coordinator.addContractor("Contractor", "bomj", "12345", 10, 2, "bomj", "bomj");
        ViewFactory.showLoginWindow();
    }

    public static Coordinator getCoordinator(){
        return coordinator;
    }

    public static void main(String[] args) {
        launch();
    }

}