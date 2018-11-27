package main;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import main.db.Data;
import main.db.DataBaseManager;
import main.gui.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class LoginManager {
    private DataBaseManager manager;
    private String dbAccess;
    private SimpleDateFormat currentTime;

    public boolean userExists(String username) {
        return false;
    }

    public LoginManager(DataBaseManager manager) {
        this.manager = manager;
    }

    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean loginDialog() {
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
        if (result.isPresent()) {
            Pair<String, String> resultData = result.get();
            loggedUser = null;
            return verifyCredentials(resultData.getKey(), resultData.getValue());
           // currentTime = new SimpleDateFormat();
        } else
            return true;
    }

    public void addToLoginHistory(String logged){
        if (loggedUser != null){
            StringBuilder rowToInsert = new StringBuilder();
            rowToInsert
                    .append(loggedUser.getLogin())
                    .append(", ");
                    //.append(timestamp);
            try {
                Main.getInstance().getDataBaseManager().insertValuesIntoExistingTable(
                        "login_history", rowToInsert.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verifyCredentials(String userName, String password) {

        try {
            Data data = manager.selectWhereColumnEqualsString("users", "login", userName);
            if (data.isEmpty())
                return false;
            String dbPassword = data.getFromTopRow(3);
            dbAccess = data.getFromTopRow(4);
            if (dbPassword.equals(password)) {
                if (dbAccess.equals("admin")) {
                    loggedUser = new Admin(userName, password, dbAccess);
                    return true;
                } else if (dbAccess.equals("client")) {
                    loggedUser = new Client(userName, password, dbAccess);
                    return true;
                } else
                    return false; //wyrzucic jakis sensowny wyjatek (?)
            } else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

//        StringBuilder userPasswordCheck = new StringBuilder();
//        userPasswordCheck
//                .append("SELECT user_password FROM users WHERE login = '")
//                .append(userName)
//                .append("'");
//
//        try {
//            // statement = manager.getConnection().createStatement();
////            ResultSet resultSet = statement.executeQuery(userNameCheck.toString());
////            if (resultSet.absolute(1)) {
////                StringBuilder passwordCheck = new StringBuilder();
////                passwordCheck
////                        .append("SELECT user_password FROM users WHERE login = ")
////                        .append(password);
//
//            statement = manager.getConnection().createStatement();
//            ResultSet resultSet1 = statement.executeQuery(userPasswordCheck.toString());
//            if (resultSet1.equals(password)) {
//                StringBuilder accessType = new StringBuilder();
//                accessType
//                        .append("SELECT access FROM users WHERE login IN ('")
//                        .append(userName)
//                        .append("')");
//                ResultSet resultSet2 = statement.executeQuery(accessType.toString());
//
//                if (resultSet2.equals("admin"))
//                    loggedUser = new Admin();
//                else if (resultSet2.equals("client"))
//                    loggedUser = new Client();
//                return true;
//            }
//            else
//                return false;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
    }

    public String getDbAccess() {
        return dbAccess;
    }

    public boolean isLoggedIn() {
        return loggedUser != null;
    }

}
