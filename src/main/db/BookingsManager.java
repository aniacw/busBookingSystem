package main.db;

import java.sql.SQLException;

public class BookingsManager {

    private DataBaseManager manager;

    public BookingsManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public void addOrder(String name, int departureIdSelected, double fare, int userId) throws SQLException {
        manager.insertObjectsIntoTable("bookings", null, name, departureIdSelected, fare, userId);
    }

    public int getLastBookingId() throws SQLException {
        return (Integer)manager.getMaximum("bookings", "booking_id");
    }

    public Data displayUserOrders(int id) throws SQLException {
        return manager.selectWhereColumnEquals("bookings", "user_id", id);
    }

    public Data getName(int bookingId) throws SQLException {
        return manager.selectWhereColumnEqualsValueFromOtherColumn("bookings", "booking_id", bookingId, "name_on_ticket");

    }
}
