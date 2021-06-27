package controller;

public abstract class Controller {
    protected static String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Controller.username = username;
    }
}
