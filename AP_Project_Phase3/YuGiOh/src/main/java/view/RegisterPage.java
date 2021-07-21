package view;

import client.ReadMessage;
import client.SendMessage;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.commands.RegisterCommand;

import java.awt.*;
import java.io.IOException;

public class RegisterPage extends Application {
    private static String message;
    public AnchorPane anchorPane;
    public TextField usernameInput;
    public TextField nicknameInput;
    public PasswordField passwordInput;
    public Button registerButton;
    public Label registerError = new Label();


    public RegisterPage() {
        ReadMessage.setCurrentRegisterPage(this);

    }


    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        RegisterPage.message = message;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/RegisterPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        registerButton.setOnMouseClicked(mouseEvent -> {
            Page.playButtonClickSound();
            register();
            usernameInput.clear();
            nicknameInput.clear();
            passwordInput.clear();
        });
    }

    private void register() {
        String username = usernameInput.getText();
        String nickname = nicknameInput.getText();
        String password = passwordInput.getText();

        RegisterCommand registerCommand = new RegisterCommand();
        registerCommand.setUsername(username);
        registerCommand.setNickname(nickname);
        registerCommand.setPassword(password);
        SendMessage.getSendMessage().sendMessage(registerCommand);
        while (message == null) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        showRegisterError(message);
    }

    public void showRegisterError(String messageText) {
        registerError.setText(messageText);
        registerError.setStyle("-fx-background-color: #ff0000");
        message = null;
    }

    public void exitMenu() throws Exception {
        new Page().start(Page.getStage());
    }
}
