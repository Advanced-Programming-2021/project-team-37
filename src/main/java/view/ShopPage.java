package view;

import controller.ShopPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopPage extends Application {
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        ShopPage.message = message;
    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    private void buyCardByCardName(String cardName) {
        ShopPageController.getInstance().buyCard(cardName);
        if (!message.equals(""))
            System.out.println(message);
    }

    public void run() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/shopPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
