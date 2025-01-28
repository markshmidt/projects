package com.group44.assignment2.controllers.payroll;

import com.group44.assignment2.models.*;
import com.group44.assignment2.views.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

    public Text titleText;
    public ChoiceBox<Employee> employeeField;
    public TextField baseSalaryField;
    public TextField overtimeSalaryField;
    public TextField grossTotalField;
    public TextField taxField;
    public TextField netTotalField;
    private Payroll payroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
        this.titleText.setText("View payroll");
        this.baseSalaryField.setText(String.valueOf(payroll.getTotalRegular()));
        this.overtimeSalaryField.setText(String.valueOf(payroll.getTotalOvertime()));
        this.grossTotalField.setText(String.valueOf(payroll.gross()));
        this.taxField.setText(String.valueOf(payroll.getTax()));
        this.netTotalField.setText(String.valueOf(payroll.getTotal()));
    }

    @FXML
    private void back() {
        var stage = (Stage) titleText.getScene().getWindow();
        ViewFactory.closeStage(stage);
    }

    @FXML
    private void save() {
    }
}
