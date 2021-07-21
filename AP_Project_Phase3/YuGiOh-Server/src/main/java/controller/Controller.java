package controller;

import model.User;

public class Controller {
    public static User currentOpponent;
    protected static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Controller.username = username;
    }
}
