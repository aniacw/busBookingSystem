package main.db;

import java.sql.SQLException;

public class BookingsManager {

    private DataBaseManager manager;

    public BookingsManager(DataBaseManager manager) {
        this.manager = manager;
    }

//    public void addOrder(String name, int routeId, int departureId, double price, String login) throws SQLException {
//        manager.insertObjectsIntoTable("bookings", name, routeId, departureId, price, login);
//    }

    public void addOrder(String name, int departureIdSelected, double fare,
                         int userId) throws SQLException {
        manager.insertObjectsIntoTable("bookings", null, name, departureIdSelected, fare, userId);
    }

    public int getLastBookingId() throws SQLException {
        return (Integer)manager.getMaximum("bookings", "booking_id");
    }
}
