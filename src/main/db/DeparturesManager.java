package main.db;

import java.sql.SQLException;

public class DeparturesManager {
    private DataBaseManager manager;

    public DeparturesManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public Data getAllDepartures() throws SQLException {
        return manager.getTable("departures");
    }

    public Data getDeparturesIdOnDateTime(String date, String time) throws SQLException {
        return manager.selectWhereColumnEquals2("departures", "departure_date", date,
                "departure_time", time, "departure_id");
    }

    public Data getDepartureTimesForRoute(String departure, String destination) throws SQLException {
        return manager.selectWhereColumnEqualsJoinTables("routes", "departure",
                "destination", "route_id", "departures", departure, destination);
    }

    public Data getDate(int departureId) throws SQLException {
        return manager.selectWhereColumnEqualsJoinTables2("departures", "departure_id", "bookings",
                "departure_date", departureId, "departure_id");

    }

    public Data getTime(int departureId) throws SQLException {
        return manager.selectWhereColumnEqualsJoinTables2("departures", "departure_id", "bookings",
                "departure_time", departureId, "departure_id");
    }
}
