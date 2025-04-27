package com.group44.assignment2.views.cells;

import com.group44.assignment2.App;
import com.group44.assignment2.controllers.shift.CellController;
import com.group44.assignment2.models.Shift;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ShiftCell extends ListCell<Shift> {

    private AnchorPane content;
    private CellController controller;

    public ShiftCell() {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("shift/cell.fxml"));
            content = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Shift item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.setItem(item);
            setGraphic(content);
        }
    }
}
