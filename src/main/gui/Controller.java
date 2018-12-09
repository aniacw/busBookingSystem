package main.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import main.FareCalculator;
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
            adminPanel;

    @FXML
    ComboBox<Object>
            departureList,
            destinationList,
            accessSelect,
            preselectedBusesList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField
            currentPasswordUserTextField,
            newPasswordUserTextField,
            confirmPasswordUserTextField,
            departureId,
            priceTextField,
            routeIdTextField,
            date,
            dateTextField,
            departureTimeTextField,
            destinationTextField,
            departureTextField,
            busNoTextField,
            loginAddTextField,
            passwordTextField,
            loginRemoveTextField,
            loginDisplayTextField,
            accessDisplayTextField,
            userIdDisplayTextField,
            nameTextField,
            loginTextField;

    @FXML
    Button
            getBusDetailsButton,
            resetButton,
            addBusButton,
            removeBusButton,
            bookBusButton,
            removeUserButton,
            displayUserDataButton,
            myOrdersButton,
            createNewUserButton;

    @FXML
    ToggleButton
            seat1A,
            seat2A,
            seat3A,
            seat4A,
            seat5A,
            seat6A,
            seat7A,
            seat8A,
            seat9A,
            seat10A,
            seat1B,
            seat2B,
            seat3B,
            seat4B,
            seat5B,
            seat6B,
            seat7B,
            seat8B,
            seat9B,
            seat10B,
            seat1C,
            seat2C,
            seat3C,
            seat4C,
            seat5C,
            seat6C,
            seat7C,
            seat8C,
            seat9C,
            seat10C;

    @FXML
    ListView<Object> ordersHistoryList;

    @FXML
    Label statusBar;

    @FXML
    DatePicker datePicker;


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

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
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
        } catch (SQLException e) {
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

        if (!departure.isEmpty() && !destination.isEmpty()) {
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                if (Main.getInstance().getRoutesManager().removeRoute(departure, destination)) {
                    statusBar.setText("Route removed successfully");
                } else {
                    statusBar.setText("Route with given parameters does not exist");
                }
                //AlertDialog.show("Bus deleted!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (!routeId.isEmpty()) {
            try {
                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
                if (Main.getInstance().getRoutesManager().removeRouteById(routeId)) {
                    statusBar.setText("Route removed successfully");
                } else {
                    statusBar.setText("Route with given parameters does not exist");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onButtonCreateNewUserButtonClicked() {
        String login = loginTextField.getText();
        String tempPassword = passwordTextField.getText();

        accessSelect.getItems().addAll("client", "admin");
        String access = accessSelect.getSelectionModel().toString();

        if (Main.getInstance().getLoginManager().getLoggedUser().getAccess().equals("admin")) {
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
        String login = loginRemoveTextField.getText();
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
        String login = loginDisplayTextField.getText();
        try {
            Data result = Main.getInstance().getDataBaseManager().selectWhereColumnEquals("users", "login", login);

            int accessColIdx = result.getColumnIndex("access");
            int userIdColIdx = result.getColumnIndex("user_id");

            accessDisplayTextField.setText(result.get(1, accessColIdx).toString());
            userIdDisplayTextField.setText(result.get(1, userIdColIdx).toString());

            Data ordersResult = Main.getInstance().getUsersManager().getOrdersForUser(login);

            //ordersResult.process((o, row, column) -> System.out.println(o + " " + row + " " + column));

            for (Data.Row order : ordersResult) {
                String orderResult = order.toString();
                ordersHistoryList.getItems().add(orderResult);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonBookBusClicked() {
        String name = nameTextField.getText();
        String departureSelected = departureList.getValue().toString();
        String destinationSelected = destinationList.getValue().toString();
        String date = "";
        String time = "";
        String routeIdSelected = "";
        String departureIdSelected = "";
        String distanceSelected = "";

        if (name == null)
            AlertDialog.show("Please type your name", Alert.AlertType.ERROR);


        try {
            Data result = Main.getInstance().getDeparturesManager().getDepartureTimesForRoute(
                    departureSelected, destinationSelected);

            int dateColIdx = result.getColumnIndex("departure_date");
            int timeColIdx = result.getColumnIndex("departure_time");

            for (Data.Row row : result) {
                date = row.get(dateColIdx).toString();
                time = row.get(timeColIdx).toString();
            }

            Data departuteIdResult = Main.getInstance().getDeparturesManager().getDeparturesIdOnDateTime(
                    date, time);

            for (Data.Row row : departuteIdResult)
                departureIdSelected = row.toString();

            Data route = Main.getInstance().getRoutesManager().getRoute(departureSelected, destinationSelected);

            for (Data.Row row : route)
                routeIdSelected = row.toString();

            Data distanceRsult = Main.getInstance().getRoutesManager().getDistance(departureSelected, destinationSelected);

            for (Data.Row row : distanceRsult)
                distanceSelected = row.toString();

            FareCalculator.setOneKmCharge(0.80);
            double fare = FareCalculator.calculateFare(FareCalculator.getOneKmCharge(), 100);

            Main.getInstance().getBookingsManager().addOrder(name, routeIdSelected, departureIdSelected, fare,
                    Main.getInstance().getLoginManager().getLoggedUser().toString());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onButtonChangePasswordClicked() {
        String currentPass = "";

        try {
            currentPass = Main.getInstance().getUsersManager().getPassword(Main.getInstance().getLoginManager().getLoggedUser().toString()).toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (currentPass.equals(currentPasswordUserTextField.getText())) {
            if (newPasswordUserTextField.getText().equals(confirmPasswordUserTextField.getText())) {
            } else {
                AlertDialog.show("Passwords are not the same!", Alert.AlertType.WARNING);
            }

        }


    }

}