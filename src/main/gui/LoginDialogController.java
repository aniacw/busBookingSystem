package main.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginDialogController {
    @FXML
    Button OKButton;

    @FXML
    TextField loginTextBox;

    public void onLoginTextChanged(ActionEvent actionEvent) {
        OKButton.setDisable(loginTextBox.getText().trim().isEmpty());
    }
}
