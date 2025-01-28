package com.group44.assignment2.controllers.payroll;

import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Payroll;
import com.group44.assignment2.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CellController implements Initializable {
    public Button view;
    public Text userText;
    public Text amountText;

    private Payroll currentItem;

    public void setItem(Payroll item) {
        this.currentItem = item;
        userText.setText(String.valueOf(item.getEmployeeId()));
        amountText.setText(String.valueOf(item.getTotal()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.setOnAction(e->{
            ViewFactory.showPayrollEdit(currentItem);
        });
    }
}
