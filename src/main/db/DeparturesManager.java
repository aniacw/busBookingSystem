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
        return manager.selectWhereColumnEqualsString("departures", "departure_date", date);
    }
}
