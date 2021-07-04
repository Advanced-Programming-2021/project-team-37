package view;

import controller.BetweenDuelPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetweenDuelPage extends Application {
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        BetweenDuelPage.message = message;
    }

    public static void printHelp() {
        System.out.println("It's " + BetweenDuelPageController.getInstance().getCurrentUsername() + " turn");
        System.out.println("Here you can exchange a card between your main and side deck with this command");
        System.out.println("main deck card <card name> exchange main deck card <card name>");
        System.out.println("if you don't want to exchange card just type \"skip\"");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/betweenDuelPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
