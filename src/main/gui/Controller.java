package main.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import main.Admin;
import main.User;
import main.db.Data;
import main.db.DataBaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    @FXML
    Tab
            reservation,
            busManagement,
            fareCalculator,
            adminPanel,
            tickets;

    @FXML
    ComboBox<Object>
            departureList,
            destinationList,
            preselectedBusesList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField
            departureId,
            routeIdTextField,
            date,
            dateTextField,
            departureTimeTextField,
            destinationTextField,
            departureTextField,
            busNoTextField,
            accessTextField,
            tempPassTextField,
            loginDisplayDataTextField,
            accessTextFieldDisplayData,
            userIdTextField,
            loginToRemoveTextField,
            loginTextField;

    @FXML
    Button
            getBusDetailsButton,
            resetButton,
            addBusButton,
            removeBusButton,
            fetchDataButton,
            updateDataButton,
            bookBusButton,
            removeUserButton,
            displayUserDataButton,
            addNewUserButton;

    @FXML
    GridPane seatPlan;

    @FXML
    TableView busManagementTable;

    @FXML
    ListView<Object> ordersList;

   // private User admin;


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
        int timeColIdx = result.getColumnIndex("departure_time");

        for (Data.Row row : result) {
            String rowString = row.get(dateColIdx).toString() + " " + row.get(timeColIdx).toString();
            preselectedBusesList.getItems().add(rowString);
        }
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

        String newDeparture = departureTextField.getText();
        String newDestination = destinationTextField.getText();
        Integer newBusNo = Integer.parseInt(busNoTextField.getText());

        try {
            Main.getInstance().getRoutesManager().addNewRoute(newDeparture, newDestination, newBusNo);

            AlertDialog.show("New route created!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonRemoveBusClicked() {

        String departure = departureTextField.getText();
        String destination = destinationTextField.getText();
        String routeId = routeIdTextField.getText();

        if(departureTextField.getText() != null && destinationTextField.getText() != null){
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                Main.getInstance().getRoutesManager().removeRoute(departure, destination);
                AlertDialog.show("Bus deleted!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (routeIdTextField.getText() != null) {
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                Main.getInstance().getRoutesManager().removeRouteById(routeId);
                AlertDialog.show("Bus deleted!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onButtonAddNewUserButtonClicked() {
//        String login = loginTextField.getText();
//        String tempPassword = tempPassTextField.getText();
//        String access = accessTextField.getText();
//
//        admin = new Admin();
//        admin.createNewUser(login, tempPassword, access);
//
//        try {
//            Main.getInstance().getUsersManager().addNewUser(login, tempPassword, access);
//            AlertDialog.show("User added", Alert.AlertType.INFORMATION);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public void onButtonRemoveUserClicked() {
        String login = loginToRemoveTextField.getText();

        try {
            Main.getInstance().getUsersManager().removeUser(login);
            AlertDialog.show("User deleted", Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonDisplayUsersDataClicked() {
        String login = loginDisplayDataTextField.getText();
        try {
            Data result = Main.getInstance().getDataBaseManager().selectWhereColumnEquals("users", "login", login);

            int accessColIdx = result.getColumnIndex("access");
            int userIdColIdx = result.getColumnIndex("user_id");

            accessTextFieldDisplayData.setText(result.get(1, accessColIdx).toString());
            userIdTextField.setText(result.get(1, userIdColIdx).toString());

            Data ordersResult = Main.getInstance().getUsersManager().getOrdersForUser(login);

            for (Data.Row order : ordersResult) {
                String orderResult = order.toString();
                ordersList.getItems().add(orderResult);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}