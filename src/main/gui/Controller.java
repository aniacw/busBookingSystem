package main.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.LoginManager;
import main.db.Data;
import main.db.DataBaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller {

    private Statement statement;
    private DataBaseManager manager;

    @FXML
    Tab reservation, busManagment, fareCalculator, adminPanel, tickets;

    @FXML
    Label departureLabel, destiantion;

    @FXML
    ComboBox departureList, destinationList, busSelectList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField ddTextField, mmTextField, yyTextField, hh, mm, departureId, routeId, date;

    @FXML
    Button getBusDetails, reset, loadBus, addBusButton, removeBusButton, fetchDataButton, updateDataButton;

    @FXML
    GridPane seatPlan;

    @FXML
    TableView busManagementTable;


    @FXML
    public void initialize() throws SQLException {

        accessTabs();

        departureList.getItems().addAll(manager.getColumnFromTable("departure", "routes"));
        destinationList.getItems().addAll(manager.getColumnFromTable("destination", "routes"));
        departureList.getItems().add("to sie nie pojawia");

    }

    public void accessTabs() {
        if (Main.getInstance().getLoginManager().getDbAccess().equals("client")) {
            mainTabPanel.getTabs().remove(busManagment);
            mainTabPanel.getTabs().removeAll(busManagment, adminPanel);
        }
    }

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