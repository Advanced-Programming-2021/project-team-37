
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.Controller;
import controller.DuelPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void initializeProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
            ArrayList<User> users = new Gson().fromJson(data, new TypeToken<ArrayList<User>>() {
            }.getType());
            User.setUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Card.setCards();
        Card.updateCardsDatabase();
        //initializeProgram();
        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + " " + card);
        }
//        test();
        new Page().run();
    }

    public static void test() {
        DuelPageController.getInstance().setCurrentTurnUsername("moein");
        DuelPageController.getInstance().setOpponentUsername("ali");
        User.getUserByUsername("moein").setLifePoints(8000);
        User.getUserByUsername("ali").setLifePoints(5000);
        new User("moein", "moein7", "m");
        User.getUserByUsername("moein").getDecks().add(new Deck("d1"));
        User.getUserByUsername("moein").setActivatedDeck(User.getUserByUsername("moein").getDeckByDeckName("d1"));
        addCardToDeckByUsername("moein");
        addCardToDeckByUsername("ali");
    }

    private static void addCardToDeckByUsername(String username) {
        for (int i = 0; i < 40; i++)
            User.getUserByUsername(username).getDeckByDeckName("d1").getMainDeckCards().add(Card.getCards().get(i));
    }
}
