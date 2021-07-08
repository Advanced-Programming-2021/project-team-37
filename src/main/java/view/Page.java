package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Page extends Application {
    private static Stage stage;
    private static String message;
    public ImageView backGround;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Page.stage = stage;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Page.message = message;
    }

    protected static int functionNumber;
    protected static boolean isCommandValid = false;
    protected String[] commandPatterns;
    private int commandNumber;

    public static void playButtonClickSound() {
    }

    public static void playNotEnoughCoin() {
    }

    public static void playCoinSound() {
    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void exitMenu() throws Exception {

    }

    public void showCurrentMenu() {

    }

    public void openLoginPage() throws Exception {
        new LoginPage().start(stage);
    }

    public void openRegisterPage() throws IOException {
        new RegisterPage().start(stage);
    }

    public void exitProgram() {
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Page.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/View/firstPage.fxml"));
        stage.setTitle("YuGiOh");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Pictures/Icon/icon.png"))));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void run() {
        launch();
    }
}
