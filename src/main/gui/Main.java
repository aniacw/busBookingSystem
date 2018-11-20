package main.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.db.DataBaseManager;
import main.LoginManager;
import main.db.CSVLoader;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        DataBaseManager manager = new DataBaseManager();
        manager.connect("busschedule", "root", "123456");
        LoginManager loginManager = new LoginManager(manager);
        CSVLoader csvLoader = new CSVLoader(manager);
        csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\BUS.csv", "USERS");
        csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\Routes.csv", "routes");
        csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\Departures.csv", "departures");


        LoginManager.getInstance();

        if (loginManager.isLoggedIn()) {
            Pane root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 900, 500));
            primaryStage.show();
        }


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
