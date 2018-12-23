package main.db;

import java.sql.SQLException;

public class SeatsManager {
    private DataBaseManager manager;

    public SeatsManager(DataBaseManager manager) {
        this.manager = manager;
    }


    public Data selectSeatsForDeparture(int departureId) throws SQLException {
        return manager.selectFromJoinedTablesWhereColumnEquals("bookings", "seats", "departure_id", departureId);
    }

    public void insertSeatForBooking(int bookingId, String seat) throws SQLException {
        manager.insertObjectsIntoTable("seats", bookingId, seat);
    }

    public Data getSeatsForBooking(int bookingId) throws SQLException {
        return manager.selectWhereColumnEqualsValueFromOtherColumn("seats", "booking_id", bookingId, "seat_id");
    }
}
