package main.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Controller {

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
    Label busIdLabel;

    @FXML
    Label busNameLabel;

    @FXML
    TextField busId;

    @FXML
    TextField busName;

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



    public void onButtonAddBusClicked(){
        //Main.getInstance().getLoggedUser()
    }

}
