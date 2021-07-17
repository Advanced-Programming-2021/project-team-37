package controller;

public abstract class Controller {
    protected static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Controller.username = username;
    }
}
