package com.group44.assignment2.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.group44.assignment2.util.PermissionHelper;
import com.group44.assignment2.views.ViewFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    public Text errorText;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        errorText.setFill(Paint.valueOf("red"));
        errorText.setVisible(false);

        var resetFocusListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                errorText.setVisible(!newValue);
            }
        };
        usernameInput.focusedProperty().addListener(resetFocusListener);
        passwordInput.focusedProperty().addListener(resetFocusListener);
    }

    @FXML
    private void login() {
        var stage = (Stage) usernameInput.getScene().getWindow();
        errorText.setVisible(false);
        errorText.setText("");
        if (PermissionHelper.authenticate(usernameInput.getText(), passwordInput.getText())) {
            ViewFactory.showMainWindow();
            ViewFactory.closeStage(stage);

            Platform.runLater(() -> {
                errorText.setVisible(false);
                errorText.setText("");
            });
        } else {
            Platform.runLater(() -> {
                errorText.setText("Invalid username or password");
                errorText.setVisible(true);
            });
        }

    }
}
