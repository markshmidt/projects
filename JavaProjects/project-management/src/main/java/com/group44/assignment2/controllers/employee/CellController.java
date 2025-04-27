package com.group44.assignment2.controllers.employee;

import com.group44.assignment2.models.Employee;
import com.group44.assignment2.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CellController implements Initializable {
    public Text text;
    public Button edit;

    private Employee currentItem;

    public void setItem(Employee item) {
        this.currentItem = item;
        text.setText(item.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        edit.setOnAction(e->{
            ViewFactory.showEmployeeEdit(currentItem);
        });
    }
}
