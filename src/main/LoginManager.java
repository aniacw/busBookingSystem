package main;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.db.DataBaseManager;
import main.gui.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class LoginManager {
    private DataBaseManager manager;
    private Statement statement;

    public boolean userExists(String username) {
        return false;
    }

    public LoginManager(DataBaseManager manager) {
        this.manager = manager;
    }

    private User loggedUser;
    private static Main instance = null;

    public static Main getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public Pair<String, String> loginDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("System login");
        dialog.setHeaderText("Please type in your login and password");


// Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

//sprawdzanie danych usera
        verifyCredentials(username.getText(), password.getText());


        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(
                buttonType ->
                        buttonType == ButtonType.OK ?
                                new Pair<String, String>(username.getText(), password.getText())
                                : null);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        Optional<Pair<String, String>> result = dialog.showAndWait();
        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    public boolean verifyCredentials(String userName, String password) {

        StringBuilder userPasswordCheck = new StringBuilder();
        userPasswordCheck
                .append("SELECT user_password FROM users WHERE login IN ('")
                .append(userName)
                .append("')");

        try {
            // statement = manager.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery(userNameCheck.toString());
//            if (resultSet.absolute(1)) {
//                StringBuilder passwordCheck = new StringBuilder();
//                passwordCheck
//                        .append("SELECT user_password FROM users WHERE login = ")
//                        .append(password);

            statement = manager.getConnection().createStatement();
            ResultSet resultSet1 = statement.executeQuery(userPasswordCheck.toString());
            if (resultSet1.equals(password)) {
                StringBuilder accessType = new StringBuilder();
                accessType
                        .append("SELECT access FROM users WHERE login IN ('")
                        .append(userName)
                        .append("')");
                ResultSet resultSet2 = statement.executeQuery(accessType.toString());

                if (resultSet2.equals("admin"))
                    loggedUser = new Admin();
                else if (resultSet2.equals("client"))
                    loggedUser = new Customer();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLoggedIn() {
        Pair<String, String> loginData = loginDialog();

        if (loginData == null) {
            System.out.printf("canceled");
            return false;
        } else {
            System.out.println("Username=" + loginData.getKey() + ", Password=" + loginData.getValue());
            return true;
        }
    }


//    boolean login(String login, String password){
//        if (manager.isConnected()){
//
//        }
//        else{
//            return false;
//        }
//    }
}
