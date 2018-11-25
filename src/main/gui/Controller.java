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
    TextField departureId, routeId, date, dateTextField, departureTimeTextField;

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
//        try {
//            statement = manager.getConnection().createStatement();
//            StringBuilder addBusQuery = new StringBuilder();
//            addBusQuery
//                    .append("INSERT INTO departures (departure_id, route_id, departure_time) VALUES (")
//                    .append(departureId.getText())
//                    .append(",")
//                    .append(routeId.getText())
//                    .append(",")
//                    .append(date.getText())
//                    .append(")");
//
//            ResultSet resultSet = statement.executeQuery(addBusQuery.toString());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void onButtonRemoveBusClicked() {
//        try {
//            statement = manager.getConnection().createStatement();
//
//            StringBuilder removeBusQuery = new StringBuilder();
//            removeBusQuery
//                    .append("DELETE FROM departures WHERE departure_id = ")
//                    .append(departureId.getText())
//                    .append("AND WHERE route_id = ")
//                    .append(routeId.getText())
//                    .append("AND WHERE departure_time = ")
//                    .append(date.getText());
//
//            ResultSet resultSet = statement.executeQuery(removeBusQuery.toString());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

}