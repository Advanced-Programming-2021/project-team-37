package controller;

import model.User;
import view.Page;
import view.ProfilePage;

public class ProfilePageController extends Controller {
    private static ProfilePageController instance;


    public void changeNickname(String nickname) {
        if (User.isNicknameAlreadyExists(nickname))
            Page.setMessage("user with nickname " + nickname + " already exists");
        else {
            User.getUserByUsername(username).setNickname(nickname);
            Page.setMessage("nickname changed successfully!");
        }
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!User.getUserByUsername(username).getPassword().equals(currentPassword))
            ProfilePage.setMessage("current password is invalid");
        else if (currentPassword.equals(newPassword)) ProfilePage.setMessage("please enter a new password");
        else {
            User.getUserByUsername(username).setPassword(newPassword);
            ProfilePage.setMessage("password changed successfully!");
        }

    }

    public static ProfilePageController getInstance() {
        if (instance == null)
            instance = new ProfilePageController();
        return instance;
    }

    private ProfilePageController() {

    }

}
