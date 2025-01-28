package com.group44.assignment2.views.cells;

import com.group44.assignment2.App;
import com.group44.assignment2.controllers.payroll.CellController;
import com.group44.assignment2.models.Payroll;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class PayrollCell extends ListCell<Payroll> {

    private AnchorPane content;
    private CellController controller;

    public PayrollCell() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("payroll/cell.fxml"));
            content = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Payroll item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            controller.setItem(item);
            setGraphic(content);
        }
    }
}
