package view;

import controller.DeckPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckPage extends Application {
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        DeckPage.message = message;
    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {


    }

    private void createDeck(String name) {
        DeckPageController.getInstance().createDeck(name);
        System.out.println(message);
    }

    private void deleteDeck(String name) {
        DeckPageController.getInstance().deleteDeck(name);
        System.out.println(message);
    }

    private void setActiveDeck(String name) {
        DeckPageController.getInstance().setActiveDeck(name);
        System.out.println(message);
    }

    private void addCardToDeck(String cardName, String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().addCardToDeck(cardName, deckName, isSide);
        System.out.println(message);
    }

    private void showDeck(String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().showMainOrSideDeckByName(deckName, isSide);
    }

    private void removeCardFromDeck(String cardName, String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().removeCardFromDeck(cardName, deckName, isSide);
        System.out.println(message);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/deckPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
