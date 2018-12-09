package main;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class InitialPopUp {
    Stage stage;
    private Button login;
    private Button createAccount;
    private Button cancel;

    public InitialPopUp(Stage stage) {
        this.stage = stage;
        login = new Button("Login");
        createAccount = new Button("Create new account");
        cancel = new Button("Cancel");
    }

    public void start(Stage stage) {
        stage.setTitle("System login / User creation");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Label label = new Label();
        label.setText("Would you like to log in or to create new account?");


    }
}
