package main.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.db.Data;
import main.db.DataBaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    @FXML
    Tab reservation, busManagement, fareCalculator, adminPanel, tickets;

    @FXML
    Label departureLabel, destiantion;

    @FXML
    ComboBox<Object> departureList, destinationList, busSelectList, preselectedBusesList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField departureId, routeId, date, dateTextField, departureTimeTextField, destinationTextField,
            departureTextField, busNoTextField;

    @FXML
    Button getBusDetailsButton, resetButton, loadBus, addBusButton, removeBusButton, fetchDataButton, updateDataButton, bookBusButton;

    @FXML
    GridPane seatPlan;

    @FXML
    TableView busManagementTable;


    @FXML
    public void initialize() throws SQLException {

        //    accessTabs();


        Data data = Main.getInstance().getDataBaseManager().getColumnFromTable("departure", "routes");
        for (Object[] row : data) {
            departureList.getItems().add(row.toString());
        }

        Data data1 = Main.getInstance().getDataBaseManager().getColumnFromTable("destination", "routes");
        for (Object[] row : data) {
            destinationList.getItems().add(row.toString());
        }

    }


    public void accessTabs() {
        if (Main.getInstance().getLoginManager().getDbAccess().equals("client")) {
            mainTabPanel.getTabs().removeAll(busManagement, adminPanel);
        }
    }

    public void onGetBusDetailsButtonClicked() throws SQLException {
        String departureSelected = departureList.getValue().toString();
        String destinationSelected = destinationList.getValue().toString();

//        Data routesPreselected = Main.getInstance().getDataBaseManager().
//                selectWhereColumnEquals("routes", "departure", departureSelected);
//
//        Data routesPreselected2 = Main.getInstance().getDataBaseManager().
//                selectWhereColumnEquals("routes", "destination", destinationSelected);

        Data result = Main.getInstance().getDataBaseManager().selectWhereColumnEqualsJoinTables("routes",
                departureSelected, destinationSelected, "route_id", "departures");

        for (Object[] row : result) {
            preselectedBusesList.getItems().add(row.toString());
        }

        //SELECT * FROM routes
        //INNER JOIN departures
        //ON routes.route_id = departures.route_id
        //WHERE routes.departure = city1
        //AND routes.destination = city2;

    }

    public void onButtonResetClicked() {
        departureList.getSelectionModel().clearSelection();
        destinationList.getSelectionModel().clearSelection();
        dateTextField.clear();
        preselectedBusesList.getSelectionModel().clearSelection();
        departureTimeTextField.clear();
    }

    public void onBookBusButtonClicked() {
    }

    public void onButtonAddBusClicked() {

        StringBuilder rowToInsert = new StringBuilder();
        rowToInsert
                .append(departureTextField.getText())
                .append(", ")
                .append(destinationTextField.getText())
                .append(", ")
                .append(" ")
                .append(", ")
                .append(busNoTextField.getText());

        try {
            Main.getInstance().getDataBaseManager().insertValuesIntoExistingTable("routes", rowToInsert.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonRemoveBusClicked() {

    }

}