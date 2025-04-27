package com.group44.assignment2.controllers.shift;

import com.group44.assignment2.App;
import com.group44.assignment2.models.Shift;
import com.group44.assignment2.util.PermissionHelper;
import com.group44.assignment2.util.PermissionLevel;
import com.group44.assignment2.views.ViewFactory;
import com.group44.assignment2.views.cells.ShiftCell;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public ListView<Shift> shiftList;
    public Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        shiftList.setItems(App.getCoordinator().getShiftsSubscription());
        shiftList.setCellFactory(list -> new ShiftCell());

        if (PermissionHelper.getLevel() == PermissionLevel.SELF){
            this.addButton.setVisible(false);
        }
    }

    public void add() {
        if (PermissionHelper.getLevel() == PermissionLevel.SELF){
            return;
        }
        ViewFactory.showShiftAdd();
    }
}
