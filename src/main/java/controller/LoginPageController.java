package controller;

import model.User;
import view.LoginPage;
import view.MainPage;
import view.Page;
import view.RegisterPage;

public class LoginPageController extends Controller {
    private static LoginPageController instance;

    public void registerUser(String username, String nickname, String password) {
        if (User.isUsernameAlreadyExists(username))
            RegisterPage.setMessage("user with username " + username + " already exists");
        else if (User.isNicknameAlreadyExists(nickname))
            RegisterPage.setMessage("user with nickname " + nickname + " already exists");
        else if (username.equals("AI")) RegisterPage.setMessage("this username is invalid");
        else {
            RegisterPage.setMessage("user created successfully!");
            new User(username, nickname, password);
        }
    }

    public void loginUser(String username, String password) {
        if (!User.isUsernameAlreadyExists(username))
            LoginPage.setMessage("Username and password didn't match!");
        else if (!User.getUserByUsername(username).getPassword().equals(password))
            LoginPage.setMessage("Username and password didn't match!");
        else {
            LoginPage.setMessage("user logged in successfully!");
            Controller.username = username;
            try {
                new MainPage().start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
