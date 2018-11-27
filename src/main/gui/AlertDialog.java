package main.gui;

import javafx.scene.control.Alert;

public class AlertDialog {

    private Alert alert;

    public AlertDialog(String message) {
       this.alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setContentText(message);
       alert.showAndWait();
    }
}
