package main;

import java.util.ArrayList;
import java.util.List;

public class Bus {

    private int busNumber;
    private List<Seat> seatList;

    public Bus(int busNumber) {
        this.busNumber = busNumber;
        this.seatList = new ArrayList<>();
    }

    public int getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }


}
