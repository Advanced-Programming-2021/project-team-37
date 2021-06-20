
import com.google.gson.Gson;
import model.Card;
import model.Monster;
import model.User;
import view.DeckPage;
import view.LoginPage;
import view.MainPage;
import view.Page;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {

        Card.setCards();

        test();


        Page page = new Page();
        page.run();

        //        saveUserData();
//        readUserData();
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

    private static void saveUserData() {
        try {
            FileWriter writer = new FileWriter("src/main/resources/users.txt");
            writer.write(new Gson().toJson(User.getUsers()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void readUserData() {
        try {
            String text = new String(Files.readAllBytes(Paths.get("src/main/resources/users.txt")));
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
