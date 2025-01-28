package com.group44.assignment2.views.cells;

import com.group44.assignment2.App;
import com.group44.assignment2.controllers.department.CellController;
import com.group44.assignment2.models.Department;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class DepartmentCell extends ListCell<Department> {

    private AnchorPane content;
    private CellController controller;

    public DepartmentCell() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("department/cell.fxml"));
            content = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Department item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.setItem(item);
            setGraphic(content);
        }
    }
}
