package main.gui;

import javafx.scene.control.Alert;

public class AlertDialog {
    public static void show(String message, Alert.AlertType type) {
       Alert alert = new Alert(type);
       alert.setContentText(message);
       alert.showAndWait();
    }

    public static void show(String message) {
        show(message, Alert.AlertType.INFORMATION);
    }
}
