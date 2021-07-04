package view;

import controller.LoginPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RegisterPage extends Application {
    private static String message;
    public AnchorPane anchorPane;
    public TextField usernameInput;
    public TextField nicknameInput;
    public PasswordField passwordInput;
    public Button registerButton;
    public Label registerError;

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
        registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                register();
                usernameInput.clear();
                nicknameInput.clear();
                passwordInput.clear();
            }
        });

    }

    private void register() {
        String username = usernameInput.getText();
        String nickname = nicknameInput.getText();
        String password = passwordInput.getText();
        LoginPageController.getInstance().registerUser(username, nickname, password);
        showRegisterError();
    }

    private void showRegisterError() {
        registerError.setText(message);
        registerError.setStyle("-fx-background-color: #ff0000");
    }

    public void exitMenu() throws Exception {
        new Page().start(Page.getStage());
    }
}
