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

    public Data getDeparturesOnDate(String date) throws SQLException{
        return manager.selectWhereColumnEquals("departures", "departure_date", date);
    }

    public Data getDepartureTimesForRoute(String departure, String destination) throws SQLException {
        return manager.selectWhereColumnEqualsJoinTables("routes",
                "departure", "destination", "route_id", "departures",
                departure, destination);
    }
}
