package main.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.DateTimeStringConverter;
import main.db.BookingsManager;
import main.db.Data;
import main.db.SeatsManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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
            routeIdComboBox,
            preselectedBusesList;

    @FXML
    TabPane mainTabPanel;

    @FXML
    TextField
            currentPasswordUserTextField,
            newPasswordUserTextField,
            confirmPasswordUserTextField,
            timeTextField,
            priceTextField,
            destinationTextField,
            departureTextField,
            busNoTextField,
            loginAddTextField,
            passwordTextField,
            loginRemoveTextField,
            loginDisplayTextField,
            accessDisplayTextField,
            userIdDisplayTextField,
            nameTextField;

    @FXML
    Button
            addBusButton,
            bookBusButton,
            removeUserButton,
            displayUserDataButton,
            myOrdersButton,
            changePasswordButton,
            addDepartureButton,
            deleteRouteButton,
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
    GridPane seatsContainer;

    @FXML
    ListView<Object>
            ordersHistoryList;
    @FXML
    ListView<Data.Row>
            routesListManagement;

    @FXML
    Label
            statusBarAdminMenu,
            statusBarBusManagement,
            statusBarReservation;

    @FXML
    DatePicker datePicker;

    private Data departuresData;
    private Data routeToRemove;
    private ToggleGroup seats;
    private HashMap<String, ToggleButton> seatsMap;


    private void initSeatsMap(){
        seatsMap=new HashMap<>();
        for (Node n : seatsContainer.getChildren()){
            if (n instanceof ToggleButton){
                ToggleButton b = (ToggleButton)n;
                seatsMap.put(b.getText(), b);
            }
        }
    }

    @FXML
    public void initialize() throws SQLException {

        accessTabs();

        initSeatsMap();

        Data data = Main.getInstance().getDataBaseManager().getColumnFromTable("departure", "routes");
        for (Data.Row row : data) {
            String rowString = row.get(1).toString();
            departureList.getItems().add(rowString);
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

        routeToRemove = Main.getInstance().getDataBaseManager().getTable("routes");
        for (Data.Row row : routeToRemove) {
           // String rowString = row.get(1).toString() + " " + row.get(2).toString() + " " + row.get(3).toString() + " " +
            //        row.get(4).toString() + " " + row.get(5).toString();
            routesListManagement.getItems().add(row);
        }

        Data data1 = Main.getInstance().getRoutesManager().getRouteId();
        for (Data.Row row : data1) {
            int routeId = Integer.parseInt(row.get(1).toString());
            routeIdComboBox.getItems().add(routeId);
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            timeTextField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00:00")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        seatsContainer.getChildren().addAll(seat1A, seat2A, seat3A, seat4A, seat5A, seat6A, seat7A, seat8A, seat9A,
//                seat10A, seat1B, seat2B, seat3B, seat4B, seat5B, seat6B, seat7B, seat8B, seat9B, seat10B, seat1C,
//                seat2C, seat3C, seat4C, seat5C, seat6C, seat7C, seat8C, seat9C, seat10C);
}

    //dziala
    public void onDeparturesSelected() {
        destinationList.getItems().clear();

        try {
            Data data = Main.getInstance().getDataBaseManager().selectWhereColumnEqualsValueFromOtherColumn("routes",
                    "departure", departureList.getValue().toString(), "destination");

            for (Data.Row row : data) {
                String rowString = row.get(1).toString();
                destinationList.getItems().add(rowString);
            }
        } catch (SQLException e) {
            statusBarReservation.setText("Ooops, something went wrong:(");
            e.printStackTrace();
        }
    }

    public void accessTabs() {
        if (Main.getInstance().getLoginManager().getDbAccess().equals("client"))
            mainTabPanel.getTabs().removeAll(busManagement, adminPanel);
    }

    //dziala
    public void onDestinationSelected() {
        String departureSelected = departureList.getValue().toString();
        String destinationSelected = destinationList.getValue().toString();
        try {
            departuresData = Main.getInstance().getDeparturesManager().getDepartureTimesForRoute(
                    departureSelected, destinationSelected);

            int dateColIdx = departuresData.getColumnIndex("departure_date");
            int timeColIdx = departuresData.getColumnIndex("departure_time");

            preselectedBusesList.getItems().clear();
            for (Data.Row row : departuresData) {
                String rowString = row.get(dateColIdx).toString() + " " + row.get(timeColIdx).toString();
                preselectedBusesList.getItems().add(rowString);
            }
        } catch (SQLException e) {
            statusBarReservation.setText("Ooops, something went wrong:(");
            e.printStackTrace();
        }
    }

    public void onButtonAddBusClicked() {

        String newDeparture = departureTextField.getText();
        String newDestination = destinationTextField.getText();
        Integer newBusNo = Integer.parseInt(busNoTextField.getText());

        try {
            Main.getInstance().getRoutesManager().addNewRoute(newDeparture, newDestination, newBusNo);
            statusBarBusManagement.setText("New route created!");
        } catch (SQLException e) {
            statusBarBusManagement.setText("Ooops, something went wrong:(");
            e.printStackTrace();
        }
    }

    //dziala ale brzydko
    public void onButtonRemoveBusClicked() {

        ObservableList<Data.Row> selectedRoute = routesListManagement.getSelectionModel().getSelectedItems();
        Data.Row line = selectedRoute.get(0);
        //String idLine = selectedRoute.get(0).toString();
        //String[] array = idLine.split(" ");
        //String id = array[0];
        int id = (Integer)line.get(1);
        try {
            Main.getInstance().getRoutesManager().removeRouteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }


//        String departure = departureTextField.getText().trim();
//        String destination = destinationTextField.getText().trim();
//        String routeId = routeIdTextField.getText().trim();

//        if (!departure.isEmpty() && !destination.isEmpty()) {
//            try {
//                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
//                if (Main.getInstance().getRoutesManager().removeRoute(departure, destination)) {
//                    statusBar.setText("Route removed successfully");
//                } else {
//                    statusBar.setText("Route with given parameters does not exist");
//                }
//                //AlertDialog.show("Bus deleted!");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else if (!routeId.isEmpty()) {
//            try {
//                AlertDialog.show("Are you sure?", Alert.AlertType.CONFIRMATION);
//                if (Main.getInstance().getRoutesManager().removeRouteById(routeId)) {
//                    statusBar.setText("Route removed successfully");
//                } else {
//                    statusBar.setText("Route with given parameters does not exist");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

    }

    public void onButtonCreateNewUserButtonClicked() {
        String login = loginAddTextField.getText();
        String tempPassword = passwordTextField.getText();

        accessSelect.getItems().addAll("client", "admin");
        String access = accessSelect.getSelectionModel().toString();

        if (Main.getInstance().getLoginManager().getLoggedUser().getAccess().equals("admin")) {
            try {
                Main.getInstance().getUsersManager().addNewUser(login, tempPassword, access);
                statusBarAdminMenu.setText("User added");
            } catch (SQLException e) {
                AlertDialog.show(e.getMessage());
                statusBarAdminMenu.setText(e.getMessage());
            }
        }
    }

    public void onButtonRemoveUserClicked() {
        String login = loginRemoveTextField.getText();
        if (Main.getInstance().getLoginManager().getLoggedUser().getAccess().equals("admin")) {
            try {
                Main.getInstance().getUsersManager().removeUser(login);
                statusBarAdminMenu.setText("User deleted");
            } catch (SQLException e) {
                statusBarAdminMenu.setText(e.getMessage());
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

    private void reserveSelectedSeats(int bookingId) throws SQLException {
        SeatsManager seatsManager=Main.getInstance().getSeatsManager();
        for (Node n : seatsContainer.getChildren()){
            if (n instanceof ToggleButton){
                ToggleButton b = (ToggleButton)n;
                if (b.isSelected())
                    seatsManager.insertSeatForBooking(bookingId, b.getText());
            }
        }
    }

    public void onButtonBookBusClicked() {
        String name = nameTextField.getText();

        if (name.isEmpty()) {
            AlertDialog.show("Please type your name", Alert.AlertType.ERROR);
            return;
        }
        try {
            if (departuresData == null) {
                AlertDialog.show("No data selected!", Alert.AlertType.ERROR);
                return;
            }
            int departudeIdColumn = departuresData.getColumnIndex("departure_id");

            int selectedDepartureIndex = preselectedBusesList.getSelectionModel().getSelectedIndex();
            int departureId = departuresData.get(selectedDepartureIndex+1, departudeIdColumn);

            double fare = Double.parseDouble(priceTextField.getText());

            BookingsManager bookingsManager=Main.getInstance().getBookingsManager();

            bookingsManager.addOrder(name, departureId, fare,
                    Main.getInstance().getLoginManager().getLoggedUser().getId());
            int lastBookingId = bookingsManager.getLastBookingId();
            reserveSelectedSeats(lastBookingId);
            statusBarReservation.setText("Reservation successfully completed!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //dziala
    public void onButtonChangePasswordClicked() throws SQLException {

        String currenltyLoggedIn = Main.getInstance().getLoginManager().getLoggedUser().getLogin();
        Data currentPassData = Main.getInstance().getUsersManager().getPassword(currenltyLoggedIn);
        String currentPass = currentPassData.get(1, 1).toString();

        if (currentPass.equals(currentPasswordUserTextField.getText())) {
            if (newPasswordUserTextField.getText().equals(confirmPasswordUserTextField.getText())) {
                try {
                    Main.getInstance().getUsersManager().changePassword(currenltyLoggedIn, newPasswordUserTextField.getText());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else
                AlertDialog.show("Passwords are not the same!", Alert.AlertType.WARNING);

        } else
            AlertDialog.show("Incorrect password", Alert.AlertType.ERROR);
    }


    private void lockSeat(String seat){
        ToggleButton button = seatsMap.get(seat);
        if (button != null){
            button.setDisable(true);
            //button.setStyle("-fx-background-color: C04040");
        }
    }


    private void unlockAllSeats(){
        for (Node n : seatsContainer.getChildren()){
            if (n instanceof ToggleButton){
                ToggleButton b = (ToggleButton)n;
                b.setDisable(false);
                //b.setStyle("-fx-background-color: B3CEE9");
            }
        }
    }


    public void onPreselectedBusesListSelected(ActionEvent actionEvent) {
        preselectedBusesList.getSelectionModel();
        int selectedDepartureIndex = preselectedBusesList.getSelectionModel().getSelectedIndex();
        int departudeIdColumn = departuresData.getColumnIndex("departure_id");
        int departureId = departuresData.get(selectedDepartureIndex + 1, departudeIdColumn);
        try {
            Data seatsTaken = Main.getInstance().getSeatsManager().selectSeatsForDeparture(departureId);
            unlockAllSeats();
            int seatIdColumn = seatsTaken.getColumnIndex("seat_id");
            for (Data.Row row : seatsTaken){
                lockSeat(row.get(seatIdColumn).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int distanceColumn = departuresData.getColumnIndex("distance");
        int distance = departuresData.get(selectedDepartureIndex + 1, distanceColumn);
        priceTextField.setText(Double.toString(distance * 0.9));
    }


    public void onSeatButtonSelected(ActionEvent actionEvent) throws SQLException {

    }
}