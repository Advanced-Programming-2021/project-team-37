package view;

import controller.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPage extends Application {
    private static String message;
    public Button duelButton;
    public Button importOrExportButton;
    public Button shopButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Text makeDuelError;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        MainPage.message = message;
    }


    public void exitMenu() throws Exception {
        User.updateUsers();
        new Page().start(Page.getStage());
    }

    public void openDuelPage() throws Exception {
        if (MainPageController.getInstance().newGameWithAI(1)) {
            new RockPaperScissorMenu().start(Page.getStage());
        }
        else makeDuelError.setText(message);
    }

    public void openDeckPage() throws Exception {
        new DeckPage().start(Page.getStage());
    }

    public void openScoreboardPage() throws Exception {
        new ScoreboardPage().start(Page.getStage());
    }

    public void openProfilePage() throws Exception {
        new ProfilePage().start(Page.getStage());
    }

    public void openShopPage() throws Exception {
        new ShopPage().start(Page.getStage());
    }

    public void openImportOrExportPage() throws Exception {
        new ImportOrExportPage().start(Page.getStage());
    }

    public void logout() throws Exception {
        new Page().start(Page.getStage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
