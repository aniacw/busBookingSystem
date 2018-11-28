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

    AlertDialog alertDialog;

    @FXML
    Tab reservation, busManagement, fareCalculator, adminPanel, tickets;

    @FXML
    Label departureLabel, destiantion;

    @FXML
    ComboBox<Object> departureList, destinationList, busSelectList, preselectedBusesList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField
            departureId,
            routeId,
            date,
            dateTextField,
            departureTimeTextField,
            destinationTextField,
            departureTextField,
            busNoTextField;

    @FXML
    Button getBusDetailsButton, resetButton, loadBus, addBusButton, removeBusButton, fetchDataButton, updateDataButton, bookBusButton;

    @FXML
    GridPane seatPlan;

    @FXML
    TableView busManagementTable;


    @FXML
    public void initialize() throws SQLException {

          accessTabs();
        Data data = Main.getInstance().getDataBaseManager().getColumnFromTable("departure", "routes");
        for (Data.Row row : data) {
            String rowString = row.get(1).toString();
            departureList.getItems().add(rowString);
        }

        Data data1 = Main.getInstance().getDataBaseManager().getColumnFromTable("destination", "routes");
        for (Data.Row row : data1) {
            String rowString = row.get(1).toString();
            destinationList.getItems().add(rowString);
        }

    }


    public void accessTabs() {
        if (Main.getInstance().getLoginManager().getDbAccess().equals("client"))
            mainTabPanel.getTabs().removeAll(busManagement, adminPanel);
    }

    public void onGetBusDetailsButtonClicked() throws SQLException {
        String departureSelected = departureList.getValue().toString();
        String destinationSelected = destinationList.getValue().toString();

        Data result = Main.getInstance().getDeparturesManager().getDepartureTimesForRoute(
                departureSelected, destinationSelected);

        int dateColIdx = result.getColumnIndex("departure_date");
        int timeColIdx =result.getColumnIndex("departure_time");

        for (Data.Row row : result) {
            String rowString = row.get(dateColIdx).toString() + " " + row.get(timeColIdx).toString();
            preselectedBusesList.getItems().add(rowString);
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
        preselectedBusesList.getItems().clear();
        departureTimeTextField.clear();
    }

    public void onBookBusButtonClicked() {
        //czary-mary
    }

    public void onButtonAddBusClicked() {

        try {
            Main.getInstance().getDataBaseManager().insertObjectsIntoTable("routes", null,
                    departureTextField.getText(), destinationTextField.getText(), null,
                    Integer.parseInt(busNoTextField.getText()));

            AlertDialog.show("New route created!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonRemoveBusClicked() {

    }

}