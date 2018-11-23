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
                if (v instanceof String) {
                    builder.append('\'');
                    builder.append(values[i]); //*******
                    builder.append('\'');
                } else {
                    builder.append(values[i]);
                }
            }
        }

        builder.append(")");
        statement.executeUpdate(builder.toString());
    }

    public void insertValuesIntoTable(String tableName, String columnOrder, String row) throws SQLException {
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

    public Data getColumnFromTable(String column, String table) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT ")
                .append(column)
                .append(" FROM ")
                .append(table);
        return new Data(statement.executeQuery(getData.toString()));
    }

    public Data selectWhereColumnEquals(String table, String column, String value) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table)
                .append(" WHERE ")
                .append(column)
                .append(" = ")
                .append(value);
        return new Data(statement.executeQuery(getData.toString()));
    }

    public Data selectWhereColumnEqualsString(String table, String column, String value) throws SQLException {
        return selectWhereColumnEquals(table, column, "'"+value+"'");
    }

    public Data getTable(String table) throws SQLException {
        StringBuilder getData = new StringBuilder();
        getData
                .append("SELECT * FROM ")
                .append(table);
        return new Data(statement.executeQuery(getData.toString()));
    }


    //"select * from users where login = '...' "

    boolean isConnected() {
        return connection != null;
    }

}