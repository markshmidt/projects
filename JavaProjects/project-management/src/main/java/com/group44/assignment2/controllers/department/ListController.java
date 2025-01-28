package com.group44.assignment2.controllers.department;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Department;
import com.group44.assignment2.views.ViewFactory;
import com.group44.assignment2.views.cells.DepartmentCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public ListView<Department> departmentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentList.setItems(App.getCoordinator().getDepartmentsSubscription());
        departmentList.setCellFactory(list-> new DepartmentCell());
    }

    @FXML
    private void add(){
        ViewFactory.showDepartmentAdd();
    }
}
