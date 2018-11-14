package main.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.DataBaseManager;
import main.LoginManager;
import main.User;
import main.db.CSVLoader;

import java.util.Optional;

public class Main extends Application {

    private User loggedUser;
    private static Main instance = null;

    public static Main getInstance(){return instance;}

    public User getLoggedUser() {
        return loggedUser;
    }

    private Pair<String, String> loginDialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");


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

//// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        loginButton.setDisable(true);
//
//// Do some validation (using the Java 8 lambda syntax).
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
                                new Pair<String,String>(username.getText(), password.getText())
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
       Pair<String,String> loginData = loginDialog();
       if (loginData == null)
           System.out.printf("canceled");
       else
           System.out.println("Username=" + loginData.getKey() + ", Password=" + loginData.getValue());

       DataBaseManager manager = new DataBaseManager();
       manager.connect("busschedule", "root", "123456");
       LoginManager loginManager = new LoginManager(manager);
       CSVLoader csvLoader = new CSVLoader(manager);
       csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\BUS.csv","USERS");

      // manager.insertIntoTable("routes", 1, "W", "L", 3344, 45);

//
//        Statement stmt = null;
//        ResultSet rs = null;
//
//        try {
//            stmt = connection.createStatement();
//
////            rs = stmt.executeQuery("SELECT * FROM routes");
//////            while (rs.next()) {
//////                for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i)
//////                    System.out.println(rs.getObject(i));
//////            }
//
//            stmt.executeUpdate("INSERT INTO routes(departure, destination, distance, bus_no) VALUES ('Wrocaw', 'Rzeszów', 400, 33)");
//
//            rs = stmt.executeQuery("SELECT * FROM routes");
//            while (rs.next()) {
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i)
//                    System.out.println(rs.getObject(i));
//            }
//                    // or alternatively, if you don't know ahead of time that
//                    // the query will be a SELECT...
//
//            if (stmt.execute("SELECT foo FROM bar")) {
//                rs = stmt.getResultSet();
//            }
//
//            // Now do something with the ResultSet ....
//        } catch (SQLException ex) {
//            // handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
//        } finally {
//            // it is a good idea to release
//            // resources in a finally{} block
//            // in reverse-order of their creation
//            // if they are no-longer needed
//
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException sqlEx) {
//                } // ignore
//
//                rs = null;
//            }
//
//            if (stmt != null) {
//                try {
//                    stmt.close();
//                } catch (SQLException sqlEx) {
//                } // ignore
//
//                stmt = null;
//            }
//        }

//        Pane root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 900, 500));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
