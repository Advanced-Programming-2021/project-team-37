package view;

import controller.LoginPageController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.beans.EventHandler;
import java.io.IOException;

public class LoginPage extends Application {
    public javafx.scene.control.Button loginButton;
    public PasswordField passwordInput;
    public static String message;
    public javafx.scene.control.TextField usernameInput;
    public Label loginError;

    public void loginUser() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        LoginPageController.getInstance().loginUser(username, password);
        showLoginError();
    }

    private void showLoginError() {
        loginError.setText(message);
        loginError.setStyle("-fx-background-color: #db2929");
    }

    public void exitMenu() throws Exception {
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
                loginUser();
                usernameInput.clear();
                passwordInput.clear();
            }
        });
    }
}
