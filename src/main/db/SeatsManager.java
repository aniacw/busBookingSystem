package main.db;

import java.sql.SQLException;
import java.util.List;

public class SeatsManager {
    private DataBaseManager manager;

    public SeatsManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public Data selectSeatsForBooking(int bookingId){
        return null;
    }

    public Data selectSeatsForDeparture(int departureId) throws SQLException {
        return manager.selectFromJoinedTablesWhereColumnEquals("bookings", "seats", "departure_id", departureId);
    }

    public void insertSeatForBooking(int bookingId, String seat) throws SQLException {
        manager.insertObjectsIntoTable("seats", bookingId, seat);
    }
}
