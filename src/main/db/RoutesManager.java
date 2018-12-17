package main.db;

import java.sql.SQLException;

public class RoutesManager {

    private DataBaseManager manager;

    public RoutesManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public Data getAllRoutes() throws SQLException {
        return manager.getTable("routes");
    }

    public Data getAllRoutesFrom(String departure) throws SQLException {
        return manager.selectWhereColumnEquals("routes", "departure", departure);
    }

    public Data getAllRoutesTo(String destination) throws SQLException {
        return manager.selectWhereColumnEquals("routes", "destination", destination);
    }

    public void addNewRoute(String departure, String destination, Integer busNo) throws SQLException {
        manager.insertObjectsIntoTable("routes", null, departure, destination, 1, busNo);
    }

    public boolean removeRoute(String departure, String destination) throws SQLException {
        return manager.removeWhereTwoColumnEquals("routes", "departure", "destination",
                departure, destination) > 0;
    }

    public boolean removeRouteById(int id) throws SQLException {
        return manager.removeWhereColumnEquals("routes", "route_id", id) > 0;
    }

    public Data getRoute(String selectedDeparture, String selectedDestination) throws SQLException {
        return manager.selectWhereColumnEquals2("routes", "departure", selectedDeparture,
                "route_id", selectedDestination, "destination");
    }

    public Data getDistance(String selectedDeparture, String selectedDestination) throws SQLException {
        return manager.selectWhereColumnEquals2("routes", "departure", selectedDeparture,
                "distance", selectedDestination, "destination");
    }


}
