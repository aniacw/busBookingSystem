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

    public void removeRoute(String departure, String destination) throws SQLException {
        manager.removeWhereTwoColumnEquals("routes", "departure", "destination", departure, destination);
    }

    public void removeRouteById(String id) throws SQLException {
        manager.removeWhereColumnEquals("routes", "route_id", id);
    }
}
