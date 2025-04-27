package com.group44.assignment2.controllers.shift;

import com.group44.assignment2.models.Employee;
import com.group44.assignment2.models.Shift;
import com.group44.assignment2.views.ViewFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CellController implements Initializable {
    public Button view;
    public Text userText;
    public Text startText;
    public Text endText;

    private Shift currentItem;

    public void setItem(Shift item) {
        this.currentItem = item;
        this.userText.setText(String.valueOf(item.getEmployeeId()));
        this.startText.setText(item.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        if (item.getFinish() != null){
            this.endText.setText(item.getFinish().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view.setOnAction(e->{
            ViewFactory.showShiftEdit(currentItem);
        });
    }
}
