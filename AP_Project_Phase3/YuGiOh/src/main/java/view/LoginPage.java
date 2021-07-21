package view;

import client.ReadMessage;
import client.SendMessage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.commands.LoginCommand;
import model.commands.LoginCommandType;

import java.awt.*;


public class LoginPage extends Application {
    public javafx.scene.control.Button loginButton;
    public PasswordField passwordInput;
    public static String message;
    public javafx.scene.control.TextField usernameInput;
    public Label loginError;

    public LoginPage() {
        ReadMessage.setCurrentLoginPage(this);
    }

    public void loginUser() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        LoginCommand loginCommand = new LoginCommand(username, password);
        loginCommand.setLoginCommandType(LoginCommandType.LOGIN);
        SendMessage.getSendMessage().sendMessage(loginCommand);
        while (message == null) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        showLoginError(message);
    }

    public void showLoginError(String messageText) {
        loginError.setText(messageText);
        loginError.setStyle("-fx-background-color: #db2929");
        message = null;
    }

    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        new Page().start(Page.getStage());
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        LoginPage.message = message;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        loginButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                loginUser();
                usernameInput.clear();
                passwordInput.clear();
            }
        });
    }
}