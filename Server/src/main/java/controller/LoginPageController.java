package controller;

import model.User;

public class LoginPageController extends Controller {
    private static LoginPageController instance;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void loginUser(String username, String password) {
        if (!User.isUsernameAlreadyExists(username))
            setMessage("Username and password didn't match!");
        else if (!User.getUserByUsername(username).getPassword().equals(password))
            setMessage("Username and password didn't match!");
        else {
            setMessage("user logged in successfully!");
        }
    }

    private LoginPageController() {

    }

    public static LoginPageController getInstance() {
        if (instance == null)
            instance = new LoginPageController();
        return instance;
    }
}
