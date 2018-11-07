package main;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private Connection connection;
    private Statement statement;
    private Driver driver;

    public boolean connect(String database, String login, String password) throws SQLException {
        if (connection == null){
            try {
                driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection =
                        DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/" + database +"?user=" + login + "&password=" + password);
                statement = connection.createStatement();
                return true;
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                return false;
            }
        }
        else
            return true;
    }

    public void insertIntoTable(String tableName, Object... values) throws SQLException {
        StringBuilder builder=new StringBuilder();
        builder.append("INSERT INTO " + tableName + " VALUES (");
        if (values.length > 0){
            builder.append(values[0]);
            for (int i=1; i< values.length; ++i){
                Object v = values[i];
                builder.append(',');
                if (v instanceof String) {
                    builder.append('\'');
                    builder.append(values[i]);
                    builder.append('\'');
                }
                else{
                    builder.append(values[i]);
                }
            }
        }
        builder.append(")");
        statement.executeUpdate(builder.toString());
    }

    boolean isConnected(){
        return connection != null;
    }
}
