package main.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.db.Data;
import main.db.DataBaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller {

    Data data;

    @FXML
    Tab reservation;

    @FXML
    Tab busManagement;

    @FXML
    Tab fareCalculator;

    @FXML
    Tab adminPanel;

    @FXML
    Tab tickets;

    @FXML
    Label departureLabel;

    @FXML
    Label destiantion;

    @FXML
    ComboBox departureList;

    private String routes;
    private String departure;
//    public ComboBox getDepartureList(ComboBox comboBox) {
//        try {
//            statement = manager.getConnection().createStatement();
//            StringBuilder getDepartureListQuery = new StringBuilder();
//            getDepartureListQuery
//                    .append("SELECT departure FROM routes");
//            ResultSet resultSet = statement.executeQuery(getDepartureListQuery.toString());
//
//            int index = 0;
//            while (resultSet.next()) {
//                comboBox.getItems().add(resultSet.getString(index));
//                index++;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return comboBox;
//
//        //Main.getInstance().getLoginManager().getLoggedUser()
//    }


    @FXML
    TabPane mainTabPanel;

    @FXML
    ComboBox destinationList;

    @FXML
    Label dateLabel;

    @FXML
    Label ddLabel;

    @FXML
    Label mmLabel;

    @FXML
    Label yyLabel;

    @FXML
    TextField ddTextField;

    @FXML
    TextField mmTextField;

    @FXML
    TextField yyTextField;

    @FXML
    Button getBusDetails;

    @FXML
    Button reset;

    @FXML
    GridPane seatPlan;

    @FXML
    Button loadBus;

    @FXML
    ComboBox busSelectList;

    @FXML
    Label selectBusLabel;

    @FXML
    Label originLabel;

    @FXML
    Label timingLabel;

    @FXML
    TextField hh;

    @FXML
    TextField mm;

    @FXML
    TableView busManagementTable;

    @FXML
    Button addBusButton;

    @FXML
    Button removeBusButton;

    @FXML
    Button fetchDataButton;

    @FXML
    Button updateDataButton;

    @FXML
    TextField departureId;

    @FXML
    TextField routeId;

    @FXML
    TextField date;

    @FXML
    public void initialize() throws SQLException {

        departureList.getItems().addAll(manager.getColumnFromTable("departure", "routes"));
        destinationList.getItems().addAll(manager.getColumnFromTable("destination", "routes"));


        //dopasowac gui do accesu usera
        //getDepartureList(departureList);

    }


    private Statement statement;
    private DataBaseManager manager;

    public void onButtonAddBusClicked() {
        try {
            statement = manager.getConnection().createStatement();
            StringBuilder addBusQuery = new StringBuilder();
            addBusQuery
                    .append("INSERT INTO departures (departure_id, route_id, departure_time) VALUES (")
                    .append(departureId.getText())
                    .append(",")
                    .append(routeId.getText())
                    .append(",")
                    .append(date.getText())
                    .append(")");

            ResultSet resultSet = statement.executeQuery(addBusQuery.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonRemoveBusClicked() {
        try {
            statement = manager.getConnection().createStatement();

            StringBuilder removeBusQuery = new StringBuilder();
            removeBusQuery
                    .append("DELETE FROM departures WHERE departure_id = ")
                    .append(departureId.getText())
                    .append("AND WHERE route_id = ")
                    .append(routeId.getText())
                    .append("AND WHERE departure_time = ")
                    .append(date.getText());

            ResultSet resultSet = statement.executeQuery(removeBusQuery.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
