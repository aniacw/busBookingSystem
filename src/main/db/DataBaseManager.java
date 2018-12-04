package main.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DataBaseManager {
    private Connection connection;
    private Statement statement;
    private Driver driver;

    public boolean connect(String database, String login, String password) throws SQLException {
        if (connection == null) {
            try {
                driver = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection =
                        DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/" + database + "?user=" + login + "&password=" + password);
                statement = connection.createStatement();
                return true;
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                return false;
            }
        } else
            return true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void insertObjectsIntoTable(String tableName, Object... values) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO " + tableName + " VALUES (");

        if (values.length > 0) {
            builder.append(values[0]);
            for (int i = 1; i < values.length; ++i) {
                Object v = values[i];
                builder.append(',');
                builder.append(objectToString(values[i]));
            }
        }

        builder.append(")");
        statement.executeUpdate(builder.toString());
    }

    public void insertValuesIntoNewTable(String tableName, String columnOrder, String row) throws SQLException {
        StringBuilder builder = new StringBuilder(25 + tableName.length() + columnOrder.length() + row.length());
        builder
                .append("INSERT INTO ")
                .append(tableName)
                .append('(')
                .append(columnOrder)
                .append(')')
                .append(" VALUES(")
                .append(row)
                .append(')');
        statement.executeUpdate(builder.toString());
    }

    public void insertValuesIntoExistingTable(String tableName, String row) throws SQLException {
        StringBuilder builder = new StringBuilder();
        builder
                .append("INSERT INTO ")
                .append(tableName)
                .append(" VALUES(")
                .append(row)
                .append(')');
        statement.executeUpdate(builder.toString());
    }

    public Data getColumnFromTable(String column, String table) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT ")
                .append(column)
                .append(" FROM ")
                .append(table);
        return new Data(statement.executeQuery(getData.toString()));
    }

    public Data selectWhereColumnEquals(String table, String column, Object value) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column)
                .append(" = ")
                .append(objectToString(value));
        return new Data(statement.executeQuery(getData.toString()));
    }

    public void removeWhereColumnEquals(String table, String column, Object value) throws SQLException {
        StringBuilder deleteData = new StringBuilder();
        deleteData
                .append("DELETE FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column)
                .append(" = ")
                .append(objectToString(value));
       statement.executeUpdate(deleteData.toString());
    }

    public void removeWhereTwoColumnEquals(String table, String column1, String column2, Object value1, Object value2) throws SQLException {
        StringBuilder deleteData = new StringBuilder();
        deleteData
                .append("DELETE FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column1)
                .append(" = ")
                .append(objectToString(value1))
                .append(" AND ")
                .append(column2)
                .append(" = ")
                .append(objectToString(value2));
        statement.executeUpdate(deleteData.toString());
    }

    public Data selectWhereColumnEqualsJoinTables(String table1, String column1, String column2,
                                                  Object primaryKey, String table2,
                                                  Object selectFromColumn1, Object selectFromColumn2) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table1)
                .append(" INNER JOIN ")
                .append(table2)
                .append(" ON ")
                .append(table1)
                .append(".")
                .append(primaryKey)
                .append(" = ")
                .append(table2)
                .append(".")
                .append(primaryKey)
                .append(" WHERE ")
                .append(table1)
                .append(".")
                .append(column1)
                .append(" = ")
                .append(objectToString(selectFromColumn1))
                .append(" AND ")
                .append(table1)
                .append(".")
                .append(column2)
                .append(" = ")
                .append(objectToString(selectFromColumn2));
        return new Data((statement.executeQuery(getData.toString())));

    }

    public Data selectWhereColumnEqualsJoinTables2(String table1, String column1,
                                                   Object primaryKey, String table2,
                                                   Object selectFromColumn1) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table1)
                .append(" INNER JOIN ")
                .append(table2)
                .append(" ON ")
                .append(table1)
                .append(".")
                .append(primaryKey)
                .append(" = ")
                .append(table2)
                .append(".")
                .append(primaryKey)
                .append(" WHERE ")
                .append(table1)
                .append(".")
                .append(column1)
                .append(" = ")
                .append(objectToString(selectFromColumn1));
        return new Data((statement.executeQuery(getData.toString())));

    }

    public Data getTable(String table) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table);
        return new Data(statement.executeQuery(getData.toString()));
    }

    public boolean isConnected() {
        return connection != null;
    }

    private static String objectToString(Object o) {
        if (o instanceof String || o instanceof Date)
            return '\'' + o.toString() + '\'';
        else
            return o.toString();
    }

}