package main.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.PasswordEncryptor;
import main.db.*;
import main.LoginManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;

public class Main extends Application {
    private static Main instance = null;

    public static Main getInstance() {
        return instance;
    }

    private DataBaseManager dataBaseManager;
    private LoginManager loginManager;
    private DeparturesManager departuresManager;
    private UsersManager usersManager;
    private RoutesManager routesManager;
    private BookingsManager bookingsManager;
    private SeatsManager seatsManager;

    public BookingsManager getBookingsManager() {
        return bookingsManager;
    }

    public RoutesManager getRoutesManager() {
        return routesManager;
    }

    public DeparturesManager getDeparturesManager() {
        return departuresManager;
    }

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public LoginManager getLoginManager() {
        return loginManager;
    }

    public UsersManager getUsersManager() {
        return usersManager;
    }

    public SeatsManager getSeatsManager() {return seatsManager;}

    @Override
    public void start(Stage primaryStage) throws Exception {
        dataBaseManager = new DataBaseManager();
        loginManager = new LoginManager(dataBaseManager);
        routesManager = new RoutesManager(dataBaseManager);
        departuresManager = new DeparturesManager(dataBaseManager);
        usersManager = new UsersManager(dataBaseManager);
        bookingsManager = new BookingsManager(dataBaseManager);
        seatsManager=new SeatsManager(dataBaseManager);
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

        loginManager.addToLoginHistory();
        if (loginManager.isLoggedIn()) {
            Pane root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
            primaryStage.setTitle("Book a Dream Bus!");
            primaryStage.setScene(new Scene(root, 900, 680));
            primaryStage.show();
        }
    }


    public static void main(String[] args) {
   //     try {
//            Document document = new Document();
//            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Ania\\Desktop\\ticket.pdf"));
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
        //System.out.println(PasswordEncryptor.encrypt("alamakota"));
    }

}