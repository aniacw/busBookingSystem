package main;

public class LoginManager {
    private DataBaseManager manager;

    public boolean userExists(String username){
        return false;
    }

    public LoginManager(DataBaseManager manager) {
        this.manager = manager;
    }

//    boolean login(String login, String password){
//        if (manager.isConnected()){
//
//        }
//        else{
//            return false;
//        }
//    }
}
