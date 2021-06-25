
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Card;
import model.User;
import view.DeckPage;
import view.LoginPage;
import view.MainPage;
import view.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void initializeProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
            ArrayList<User> users = new Gson().fromJson(data, new TypeToken<ArrayList<User>>(){}.getType());
            User.setUsers(users);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        Card.setCards();
        initializeProgram();
        test();
        Page page = new Page();
        page.run();
    }

    public static void test() {
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user create --username ali --nickname ali2 --password a");
        loginPage.runLoginPage("user login --password m --username moein");

        MainPage mainPage = new MainPage();
        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");
        deckPage.runDeckPage("deck set-activate d1");

        loginPage.runLoginPage("user login --password a --username ali");
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");
        deckPage.runDeckPage("deck set-activate d1");

        loginPage.runLoginPage("user login --password m --username moein");
        addCardToDeckByUsername(deckPage, "moein");

        loginPage.runLoginPage("user login --password a --username ali");
        addCardToDeckByUsername(deckPage, "ali");

        mainPage.runMainPage("duel --new --second-player moein --rounds 1");
    }

    private static void addCardToDeckByUsername(DeckPage deckPage, String username) {
        for (int i = 0; i < 75; i++) {
            User.getUserByUsername(username).getCards().add(Card.getCards().get(i));
        }

        for (int i = 0; i < 40; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --card " + Card.getCards().get(i).getCardName());
        }

        for (int i = 43; i < 55; i++) {
            deckPage.runDeckPage("deck add-card --side --deck d1 --card " + Card.getCards().get(i).getCardName());
        }
    }

}
