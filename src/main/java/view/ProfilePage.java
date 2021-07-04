package view;

import controller.Controller;
import controller.ProfilePageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfilePage extends Application {
    private static String message;
    public Label username;
    public Label nickname;
    public Button changePasswordButton;
    public TextField newNicknameInput;
    public Button changeNicknameButton;
    public PasswordField currentPasswordInput;
    public PasswordField newPasswordInput;
    public ImageView userImageView;
    public Label changeNicknameOrPasswordMessage;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        ProfilePage.message = message;
    }

    public void setUsername(String username) {

    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    private void changePassword(String currentPassword, String newPassword) {
        ProfilePageController.getInstance().changePassword(currentPassword, newPassword);
        showChangePasswordMessage();
    }

    private void showChangePasswordMessage() {
        changeNicknameOrPasswordMessage.setText(message);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/profilePage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {

        String currentUsername = ProfilePageController.getInstance().getUsername();
        username.setText(currentUsername);
        nickname.setText(User.getUserByUsername(currentUsername).getNickname());
        Image image = new Image(getClass().getResource(User.getUserByUsername(currentUsername).getProfileImageAddress())
                .toExternalForm());
        userImageView.setImage(image);

        changePasswordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changePassword(currentPasswordInput.getText(), newPasswordInput.getText());
                currentPasswordInput.clear();
                newPasswordInput.clear();
            }
        });

        changeNicknameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                changeNickname(newNicknameInput.getText());
                newNicknameInput.clear();
            }
        });
    }

    private void changeNickname(String newNickname) {
        ProfilePageController.getInstance().changeNickname(newNickname);
        if (message.equals("nickname changed successfully!")) nickname.setText(newNickname);
        showChangeNicknameMessage();
    }

    private void showChangeNicknameMessage() {
        changeNicknameOrPasswordMessage.setText(message);
    }
}
