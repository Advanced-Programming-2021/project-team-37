package controller;

import model.User;
import view.Menu;
import view.Page;

public class LoginPageController extends Controller {
    private static LoginPageController instance;

    public void registerUser(String username, String nickname, String password) {
        if (User.isUsernameAlreadyExists(username))
            Page.setMessage("user with username " + username + " already exists");
        else if (User.isNicknameAlreadyExists(nickname))
            Page.setMessage("user with nickname " + nickname + " already exists");
        else if (username.equals("AI")) Page.setMessage("this username is invalid");
        else {
            Page.setMessage("user created successfully!");
            new User(username, nickname, password);
        }
    }

    public void loginUser(String username, String password) {
        if (!User.isUsernameAlreadyExists(username)) Page.setMessage("Username and password didn't match!");
        else if (!User.getUserByUsername(username).getPassword().equals(password))
            Page.setMessage("Username and password didn't match!");
        else {
            Page.setMessage("user logged in successfully!");
            Page.setCurrentMenu(Menu.MAIN);
            Controller.username = username;
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
