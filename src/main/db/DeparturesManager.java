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
                "departureId", time, "departure_time");
    }

    public Data getDepartureTimesForRoute(String departure, String destination) throws SQLException {
        return manager.selectWhereColumnEqualsJoinTables("routes",
                "departure", "destination", "route_id", "departures",
                departure, destination);
    }


}
