package controller;

import model.User;

public class RegisterPageController {
    private static RegisterPageController instance;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private RegisterPageController() {

    }

    public static RegisterPageController getInstance() {
        if (instance == null) instance = new RegisterPageController();
        return instance;
    }

    public void registerUser(String username, String nickname, String password) {
        if (User.isUsernameAlreadyExists(username))
            setMessage("user with username " + username + " already exists");
        else if (User.isNicknameAlreadyExists(nickname))
            setMessage("user with nickname " + nickname + " already exists");
        else if (username.equals("AI")) setMessage("this username is invalid");
        else {
            setMessage("user created successfully!");
            new User(username, nickname, password);
        }
    }
}
