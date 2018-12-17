package main.db;

import java.sql.SQLException;

public class UsersManager {
    private DataBaseManager manager;

    public UsersManager(DataBaseManager manager) {
        this.manager = manager;
    }

    public Data getOrdersForUser(String login) throws SQLException {
        return manager.selectWhereColumnEquals("bookings", "login", login);
    }

    public void addNewUser(String login, String password, String access) throws SQLException {
        manager.insertObjectsIntoTable("users", null, login, password, access);
    }

    public void removeUser(String login) throws SQLException {
        manager.removeWhereColumnEquals("users", "login", login);
    }

    public Data getPassword(String login) throws SQLException {
        return manager.selectWhereColumnEqualsValueFromOtherColumn("users", "login", login, "user_password");
    }

    public void changePassword(String login, String newPassword ) throws SQLException {
        manager.updateWhereColumnEqualsValueFromOtherColumn("users", "login", login, "user_password", newPassword);
    }
}
