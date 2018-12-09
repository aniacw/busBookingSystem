package main.db;

import java.sql.SQLException;

public class BookingsManager {

    private DataBaseManager manager;

    public BookingsManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public void addOrder(String name, int routeId, int departureId, double price, String login) throws SQLException {
        manager.insertObjectsIntoTable("bookings", name, routeId, departureId, price, login);
    }

    public void addOrder(String name, Integer routeIdSelected, Integer departureIdSelected, double fare,
                         String login) throws SQLException {
        manager.insertObjectsIntoTable("bookings", name, routeIdSelected, departureIdSelected, fare, login);
    }
}
