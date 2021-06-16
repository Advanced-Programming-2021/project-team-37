import model.Card;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.DeckPage;
import view.LoginPage;
import view.Page;
import view.ScoreboardPage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static view.Page.scanner;

public class TestFunctions {
    @BeforeEach
    public void init() {
        new User();
        new Card();
    }

    @Test
    public void testRegister() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        Assertions.assertEquals("user created successfully!\r\n", outContent);
    }

    @Test
    public void testRegister2() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");

        ArrayList<User> users = User.getUsers();
        String password =users.get(0).getPassword();
        String username = users.get(0).getUsername();
        String nickname = users.get(0).getNickname();
        Assertions.assertEquals("m", password);
        Assertions.assertEquals("moein", username);
        Assertions.assertEquals("moein7", nickname);
    }


    @Test
    public void testLogin() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user login --password m --username moein");
        Assertions.assertEquals("Username and password didn't match!\r\n", outContent);
    }

    @Test
    public void testCreateDeck() {
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d1");
        Assertions.assertEquals("d1", User.getUserByUsername("moein").getDecks().get(0).getDeckName());
        Assertions.assertEquals("deck created successfully!\r\ndeck with name d1 already exists\n", outContent);
    }

    @Test
    public void testDeleteDeck() {
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck delete d2");
        deckPage.runDeckPage("deck delete d1");
        deckPage.runDeckPage("deck create d1");
        Assertions.assertEquals(1, User.getUserByUsername("moein").getDecks().size());
        Assertions.assertEquals("d1", User.getUserByUsername("moein").getDecks().get(0).getDeckName());
        Assertions.assertEquals("deck created successfully!\r\ndeck with name d2 does not exist\r\n" +
                "deck deleted successfully\r\ndeck created successfully!\r\n", outContent);
    }

    @Test
    public void testSetActiveDeck() {
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck set-activate d2");
        deckPage.runDeckPage("deck set-activate d1");
        Assertions.assertEquals("d1", User.getUserByUsername("moein").getActivatedDeck().getDeckName());
        Assertions.assertEquals("deck created successfully!\r\ndeck with name d2 does not exist\r\n" +
                "deck activated successfully\r\n", outContent);
    }

    @Test
    public void testAddCardToDeck() {
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck add-card --card c2 --deck d1");
        Card.cards.add(new Card("c1"));
        User.getUserByUsername("moein").getCards().add(new Card("c1"));
        deckPage.runDeckPage("deck add-card --card c1 --deck d2");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1 --side");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1 --side");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1");

        Assertions.assertEquals("deck created successfully!\r\ncard with name c2 does not exist\r\n" +
                "deck with name d2 does not exist\r\ncard added to deck successfully\r\n" +
                "card added to deck successfully\r\ncard added to deck successfully\r\n" +
                "there are already three cards with name c1 in deck d1\r\n" +
                "there are already three cards with name c1 in deck d1\r\n", outContent);
    }

    @Test
    public void testAddCardToDeckFullMainOrSideDeckError() {
        LoginPage loginPage = new LoginPage();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");

        for (int i = 0; i < 100; i++) {
            Card.cards.add(new Card("c" + i));
            User.getUserByUsername("moein").getCards().add(new Card("c" + i));
        }

        for (int i = 0; i < 60; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --card c" + i);
        }

        for (int i = 0; i < 15; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --side --card c" + (i + 60));
        }

        outContent.reset();

        deckPage.runDeckPage("deck add-card --card c1 --deck d1");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1 --side");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1");
        deckPage.runDeckPage("deck add-card --card c1 --deck d1 --side");

        Assertions.assertEquals("main deck is full\r\nside deck is full\r\n" +
                "main deck is full\r\nside deck is full\r\n", outContent);
    }

    @Test
    public void testShowAllDecks() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");

        for (int i = 0; i < 100; i++) {
            Card.cards.add(new Card("c" + i));
            User.getUserByUsername("moein").getCards().add(new Card("c" + i));
        }

        for (int i = 0; i < 40; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --card c" + i);
        }

        for (int i = 0; i < 36; i++) {
            deckPage.runDeckPage("deck add-card --deck d2 --card c" + (i + 40));
        }

        for (int i = 0; i < 5; i++) {
            deckPage.runDeckPage("deck add-card --side --deck d2 --card c" + (i + 76));
        }

        outContent.reset();
        deckPage.runDeckPage("deck show --all");

        Assertions.assertEquals("Decks:\r\nActive deck:\r\nOther decks:\r\n" +
                "d1: main deck 40, side deck 0, valid\r\nd2: main deck 36, side deck 5, invalid\r\n", outContent);
    }

    // todo "deck show --deck-name <deck name> --side(Opt)"

    // todo "deck show --cards"

    @Test
    public void testShowScoreboard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        for (int i = 0; i < 10; i++) {
            loginPage.runLoginPage("user create --username user" + i + " --nickname user" + (i + 2000) + " --password p" + i);
        }

        for (int i = 0; i < 10; i++) {
            User.getUsers().get(i).setScore(1000);
        }

        for (int i = 0; i < 3; i++) {
            User.getUsers().get(2 * i).setScore(5000);
        }
        for (int i = 0; i < 2; i++) {
            User.getUsers().get(5 * i).setScore(3000);
        }

        loginPage.runLoginPage("user create --username ali --nickname ali2 --password a1");
        User.getUserByUsername("ali").setScore(1000);
        loginPage.runLoginPage("user create --username y --nickname t2 --password t1");
        User.getUserByUsername("y").setScore(3000);

        outContent.reset();
        ScoreboardPage scoreboardPage = new ScoreboardPage();
        scoreboardPage.runScoreboardPage("scoreboard show");

        Assertions.assertEquals("1- user2002: 5000\r\n" +
                "1- user2004: 5000\r\n" +
                "3- t2: 3000\r\n" +
                "3- user2000: 3000\r\n" +
                "3- user2005: 3000\r\n" +
                "6- ali2: 1000\r\n" +
                "6- user2001: 1000\r\n" +
                "6- user2003: 1000\r\n" +
                "6- user2006: 1000\r\n" +
                "6- user2007: 1000\r\n" +
                "6- user2008: 1000\r\n" +
                "6- user2009: 1000\r\n", outContent);
    }


}