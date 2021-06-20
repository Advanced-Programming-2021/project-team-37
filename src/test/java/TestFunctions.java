import controller.DuelPageController;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static view.Page.currentMenu;
import static view.Page.scanner;

public class TestFunctions {
    @BeforeEach
    public void init() {
        User.newUsers();
        Card.setCards();
    }

    @Test
    public void testRegister() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        Assertions.assertEquals("user created successfully!\r\n", outContent.toString());
    }

    @Test
    public void testRegister2() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");

        ArrayList<User> users = User.getUsers();
        String password = users.get(0).getPassword();
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
        Assertions.assertEquals("Username and password didn't match!\r\n", outContent.toString());
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
        Assertions.assertEquals("deck created successfully!\r\ndeck with name d1 already exists\r\n", outContent.toString());
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
                "deck deleted successfully\r\ndeck created successfully!\r\n", outContent.toString());
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
                "deck activated successfully\r\n", outContent.toString());
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
                "there are already three cards with name c1 in deck d1\r\n", outContent.toString());
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
                "main deck is full\r\nside deck is full\r\n", outContent.toString());
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
                "d1: main deck 40, side deck 0, valid\r\nd2: main deck 36, side deck 5, invalid\r\n", outContent.toString());
    }

    @Test
    public void testShowDeckWithDeckName() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");

        for (int i = 0; i < 75; i++) {
            User.getUserByUsername("moein").getCards().add(Card.getCards().get(i));
        }

        for (int i = 40; i < 45; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --card " + Card.getCards().get(i).getCardName());
        }

        for (int i = 6; i < 8; i++) {
            deckPage.runDeckPage("deck add-card --side --deck d1 --card " + Card.getCards().get(i).getCardName());
        }

        outContent.reset();

        System.out.println("Deck: d1\r\nMain deck:\r\nMonsters:");

        Deck deck = User.getUserByUsername("moein").getDeckByDeckName("d1");

        for (int i = 0; i < 5; i++) {
            if (deck.getMainDeckCards().get(i) instanceof Monster)
                System.out.println(deck.getMainDeckCards().get(i).getCardName() + ": " + deck.getMainDeckCards().get(i).getDescription());
        }

        System.out.println("Spell and Traps:");

        for (int i = 0; i < 5; i++) {
            if (deck.getMainDeckCards().get(i) instanceof Spell || deck.getCards().get(i) instanceof Trap)
                System.out.println(deck.getMainDeckCards().get(i).getCardName() + ": " + deck.getMainDeckCards().get(i).getDescription());
        }

        String expected = outContent.toString();
        outContent.reset();

        deckPage.runDeckPage("deck show --deck-name d1");

        Assertions.assertEquals(expected, outContent.toString());

        outContent.reset();


        System.out.println("Deck: d1\r\nSide deck:\r\nMonsters:");

        for (int i = 0; i < 2; i++) {
            if (deck.getSideDeckCards().get(i) instanceof Monster)
                System.out.println(deck.getSideDeckCards().get(i).getCardName() + ": " + deck.getSideDeckCards().get(i).getDescription());
        }

        System.out.println("Spell and Traps:");

        for (int i = 0; i < 2; i++) {
            if (deck.getSideDeckCards().get(i) instanceof Spell || deck.getCards().get(i) instanceof Trap)
                System.out.println(deck.getSideDeckCards().get(i).getCardName() + ": " + deck.getSideDeckCards().get(i).getDescription());
        }

        expected = outContent.toString();
        outContent.reset();

        deckPage.runDeckPage("deck show --deck-name d1 --side");
        Assertions.assertEquals(expected, outContent.toString());
    }

    @Test
    public void testShowUserCards() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");

        for (int i = 0; i < 75; i++) {
            User.getUserByUsername("moein").getCards().add(Card.getCards().get(i));
        }

        for (int i = 40; i < 45; i++) {
            deckPage.runDeckPage("deck add-card --deck d1 --card " + Card.getCards().get(i).getCardName());
        }

        for (int i = 6; i < 8; i++) {
            deckPage.runDeckPage("deck add-card --side --deck d1 --card " + Card.getCards().get(i).getCardName());
        }

        outContent.reset();

        for (Card card : User.getUserByUsername("moein").getCards()) {
            System.out.println(card.getCardName() + ":" + card.getDescription());
        }

        String expected = outContent.toString();
        outContent.reset();

        deckPage.runDeckPage("deck show --cards");

        Assertions.assertEquals(expected, outContent.toString());
    }

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
                "6- user2009: 1000\r\n", outContent.toString());
    }

    @Test
    public void testShop() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user login --password m --username moein");

        outContent.reset();

        ShopPage shopPage = new ShopPage();
        shopPage.runShopPage("shop buy card1");

        Assertions.assertEquals("there is no card with this name\r\n", outContent.toString());
        outContent.reset();

        User.getUserByUsername("moein").setMoney(19000);

        shopPage.runShopPage("shop buy Gate Guardian");

        Assertions.assertEquals("not enough money\r\n", outContent.toString());
        outContent.reset();

        User.getUserByUsername("moein").setMoney(20000);

        shopPage.runShopPage("shop buy Gate Guardian");

        Assertions.assertEquals("Gate Guardian", User.getUserByUsername("moein").getCards().get(0).getCardName());
        outContent.reset();

        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + ":" + card.getPrice());
        }
        String expected = outContent.toString();
        outContent.reset();

        shopPage.runShopPage("shop show --all");
        Assertions.assertEquals(expected, outContent.toString());
    }

    @Test
    public void testDuelPage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        LoginPage loginPage = new LoginPage();
        loginPage.runLoginPage("user create --username moein --nickname moein7 --password m");
        loginPage.runLoginPage("user create --username ali --nickname ali2 --password a");
        loginPage.runLoginPage("user login --password m --username moein");

        MainPage mainPage = new MainPage();
        outContent.reset();
        mainPage.runMainPage("duel --new --second-player a --rounds 1");

        Assertions.assertEquals("there is no player with this username\r\n", outContent.toString());
        outContent.reset();

        mainPage.runMainPage("duel --new --second-player ali --rounds 1");

        Assertions.assertEquals("moein has no active deck\r\n", outContent.toString());
        outContent.reset();

        DeckPage deckPage = new DeckPage();
        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");
        deckPage.runDeckPage("deck set-activate d1");

        loginPage.runLoginPage("user login --password a --username ali");

        deckPage.runDeckPage("deck create d1");
        deckPage.runDeckPage("deck create d2");
        deckPage.runDeckPage("deck set-activate d1");

        outContent.reset();
        mainPage.runMainPage("duel --new --second-player moein --rounds 1");

        Assertions.assertEquals("ali’s deck is invalid\r\n", outContent.toString());
        outContent.reset();

        loginPage.runLoginPage("user login --password m --username moein");
        addCardToDeckByUsername(deckPage, "moein");

        loginPage.runLoginPage("user login --password a --username ali");
        addCardToDeckByUsername(deckPage, "ali");

        outContent.reset();
        mainPage.runMainPage("duel --new --second-player moein --rounds 2");
        Assertions.assertEquals("number of rounds is not supported\r\n", outContent.toString());

        mainPage.runMainPage("duel --new --second-player moein --rounds 1");

        Assertions.assertEquals("DUEL", Page.getCurrentMenu().toString());
    }

    private void addCardToDeckByUsername(DeckPage deckPage, String username) {
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

    @Test
    public void testShowGameBoard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

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

        outContent.reset();
        loginPage.runLoginPage("user login --password m --username moein");
        addCardToDeckByUsername(deckPage, "moein");

        loginPage.runLoginPage("user login --password a --username ali");
        addCardToDeckByUsername(deckPage, "ali");

        mainPage.runMainPage("duel --new --second-player moein --rounds 1");

        Assertions.assertEquals("DUEL", Page.getCurrentMenu().toString());

        DuelPage duelPage = new DuelPage();
        outContent.reset();

        // game board is empty here
        DuelPageController.getInstance().showGameBoard();
        Assertions.assertEquals("<moein7>:<8000>\r\n" +
                "\tc\tc\tc\tc\tc\r\n" +
                "35\r\n" +
                "\tE\tE\tE\tE\tE\r\n" +
                "\tE\tE\tE\tE\tE\r\n" +
                "0\t\t\t\t\t\tE\r\n" +
                "E\t\t\t\t\t\t0\r\n" +
                "\tE\tE\tE\tE\tE\r\n" +
                "\tE\tE\tE\tE\tE\r\n" +
                "\t\t\t\t\t\t35\r\n" +
                "c\tc\tc\tc\tc\t\r\n" +
                "<ali2>:<8000>\r\n", outContent.toString());

//        User.getUserByUsername("moein").getBoard().getMonsterCards()[2] = Monster.getMonsters().get(3);
//        User.getUserByUsername("moein").getBoard().getMonsterCards()[2].setCardState(CardState.OO);
//        User.getUserByUsername("ali").getBoard().getMonsterCards()[2] = Monster.getMonsters().get(3);
//        User.getUserByUsername("ali").getBoard().getMonsterCards()[2].setCardState(CardState.OO);
//
//        outContent.reset();
//        duelPage.runDuelPage("select --hand 8");
//        Assertions.assertEquals("invalid selection\r\n", outContent.toString());
//        outContent.reset();
//
//        duelPage.runDuelPage("select -d");
//        Assertions.assertEquals("no card is selected yet\r\n", outContent.toString());
//        outContent.reset();
//
//        duelPage.runDuelPage("select --hand 2");
//        duelPage.runDuelPage("summon");
//        Assertions.assertEquals("action not allowed in this phase\r\n", outContent.toString());
//        outContent.reset();
//
//        DuelPageController.getInstance().setPhaseNumber(2);
//        duelPage.runDuelPage("select --hand 2");
//        duelPage.runDuelPage("summon");
//        Assertions.assertEquals("action not allowed in this phase\r\n", outContent.toString());
    }
}