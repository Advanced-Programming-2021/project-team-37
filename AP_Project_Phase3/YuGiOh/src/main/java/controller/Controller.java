package controller;

import model.User;

public abstract class Controller {

    public static User currentUser;
    public static User currentOpponent;
    public static String token;
    protected static String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Controller.username = username;
    }
}
