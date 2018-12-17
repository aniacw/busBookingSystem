package main;

public class User {

    private int id;
    private String login;
    private String password;
    private String access;

    public User(int id, String login, String password, String access) {
        this.id=id;
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAccess() {
        return access;
    }

    @Override
    public String toString() {
        return "login=" + login;
    }
}
