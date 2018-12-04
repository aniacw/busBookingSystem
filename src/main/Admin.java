package main;

public class Admin extends User {

    public Admin(String login, String password, String access) {
        super(login, password, access);
    }

    public Admin() {
    }

    public void createNewUser(String login, String tempPassword, String access) {
        switch (access) {
            case "client":
                User newClient = new Client(login, tempPassword, "client");
                break;
            case "admin":
                User newAdmin = new Admin(login, tempPassword, "admin");
                break;
        }
    }


}
