package main.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import main.db.Data;
import main.db.DataProcessor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @FXML
    Label statusBar;

    @FXML
    DatePicker datePicker;

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

        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }

    public void accessTabs() {
        if (Main.getInstance().getLoginManager().getDbAccess().equals("client"))
            mainTabPanel.getTabs().removeAll(busManagement, adminPanel);
    }

    public void onGetBusDetailsButtonClicked() {
        String departureSelected = departureList.getValue().toString();
        String destinationSelected = destinationList.getValue().toString();
        try {
            Data result = Main.getInstance().getDeparturesManager().getDepartureTimesForRoute(
                    departureSelected, destinationSelected);

            int dateColIdx = result.getColumnIndex("departure_date");
            int timeColIdx = result.getColumnIndex("departure_time");

            for (Data.Row row : result) {
                String rowString = row.get(dateColIdx).toString() + " " + row.get(timeColIdx).toString();
                preselectedBusesList.getItems().add(rowString);
            }
        }
        catch (SQLException e){
            statusBar.setText(e.getMessage());
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

        String departure = departureTextField.getText().trim();
        String destination = destinationTextField.getText().trim();
        String routeId = routeIdTextField.getText().trim();

        if(!departure.isEmpty() && !destination.isEmpty()){
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                if (Main.getInstance().getRoutesManager().removeRoute(departure, destination)){
                    statusBar.setText("Route removed successfully");
                }else{
                    statusBar.setText("Route with given parameters does not exist");
                }
                //AlertDialog.show("Bus deleted!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (!routeId.isEmpty()) {
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                if (Main.getInstance().getRoutesManager().removeRouteById(routeId)){
                    statusBar.setText("Route removed successfully");
                }else{
                    statusBar.setText("Route with given parameters does not exist");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onButtonAddNewUserButtonClicked() {
        String login = loginTextField.getText();
        String tempPassword = tempPassTextField.getText();
        String access = accessTextField.getText();

        if (Main.getInstance().getLoginManager().getLoggedUser().getAccess().equals("admin")){
            try {
                Main.getInstance().getUsersManager().addNewUser(login, tempPassword, access);
                statusBar.setText("User added");
            } catch (SQLException e) {
                AlertDialog.show(e.getMessage());
                statusBar.setText(e.getMessage());
            }

        }
    }

    public void onButtonRemoveUserClicked() {
        String login = loginToRemoveTextField.getText();
        if (Main.getInstance().getLoginManager().getLoggedUser().getAccess().equals("admin")) {
            try {
                Main.getInstance().getUsersManager().removeUser(login);
                statusBar.setText("User deleted");
            } catch (SQLException e) {
                statusBar.setText(e.getMessage());
            }
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

            //ordersResult.process((o, row, column) -> System.out.println(o + " " + row + " " + column));

            for (Data.Row order : ordersResult) {
                String orderResult = order.toString();
                ordersList.getItems().add(orderResult);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}