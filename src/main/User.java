package main;

public class User {

    private String login;
    private String password;
    private String access;
    //access i wszystko z tym zwiazane

    public User(String login, String password, String access) {
        this.login = login;
        this.password = password;
        this.access = access;
    }

    public User() {
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

    public void createNewUser(String login, String tempPassword, String access) {
    }
}
