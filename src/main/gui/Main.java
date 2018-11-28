package main.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.db.DataBaseManager;
import main.LoginManager;
import main.db.CSVLoader;
import main.db.DeparturesManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main extends Application {
    private static Main instance = null;

    public static Main getInstance() {
        return instance;
    }

    private DataBaseManager dataBaseManager;
    private LoginManager loginManager;
    private DeparturesManager departuresManager;

    public DeparturesManager getDeparturesManager() {
        return departuresManager;
    }


    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        dataBaseManager = new DataBaseManager();
        loginManager = new LoginManager(dataBaseManager);
        instance = this;

        dataBaseManager.connect("busschedule", "root", "123456");
        CSVLoader csvLoader = new CSVLoader(dataBaseManager);
        //csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\BUS.csv", "USERS");
        //csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\Routes.csv", "routes");
        //csvLoader.loadIntoTable("C:\\Users\\Ania\\Desktop\\Departures.csv", "departures");
        boolean loginSuccess = false;
        do {
            loginSuccess = loginManager.loginDialog();
        } while (!loginSuccess);

        if (loginManager.isLoggedIn()) {
            Pane root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
            primaryStage.setTitle("Book a Dream Bus!");
            primaryStage.setScene(new Scene(root, 900, 600));
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
//        try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Ania\\Desktop\\iTextHelloWorld.pdf"));
//
//            document.open();
//            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
//            Chunk chunk = new Chunk("Hello World", font);
//
//            document.add(chunk);
//            document.close();
//        } catch (DocumentException | FileNotFoundException e) {
//            e.printStackTrace();
//        }
        launch(args);
    }
}