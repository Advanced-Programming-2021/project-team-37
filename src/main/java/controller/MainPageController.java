package controller;

import model.Card;
import model.Deck;
import model.GameMode;
import model.User;
import view.*;

import java.util.Collections;

public class MainPageController extends Controller {

    private static MainPageController instance;


    public static MainPageController getInstance() {
        if (instance == null)
            instance = new MainPageController();
        return instance;
    }

    private MainPageController() {

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public void newGameWithAnotherUser(String secondPlayerUsername, int numberOfRounds) {
        if (username.equals(secondPlayerUsername)) System.out.println("please enter another player username");
        else if (!User.isUsernameAlreadyExists(secondPlayerUsername))
            System.out.println("there is no player with this username");
        else if (User.getUserByUsername(username).getActivatedDeck() == null)
            System.out.println(username + " has no active deck");
        else if (User.getUserByUsername(secondPlayerUsername).getActivatedDeck() == null)
            System.out.println(secondPlayerUsername + " has no active deck");
        else if (!User.getUserByUsername(username).getActivatedDeck().isDeckValid())
            System.out.println(username + "’s deck is invalid");
        else if (!User.getUserByUsername(secondPlayerUsername).getActivatedDeck().isDeckValid())
            System.out.println(secondPlayerUsername + "’s deck is invalid");
        else if (!(numberOfRounds == 1 || numberOfRounds == 3)) System.out.println("number of rounds is not supported");
        else createNewGameWithAnotherUser(username, secondPlayerUsername, numberOfRounds);
    }

    private void createNewGameWithAnotherUser(String username, String secondPlayerUsername, int numberOfRounds) {
        DuelPageController.getInstance().setFirstPlayerUsername(username);
        DuelPageController.getInstance().setSecondPlayerUsername(secondPlayerUsername);
        DuelPageController.getInstance().setCurrentTurnUsername(username);
        DuelPageController.getInstance().setOpponentUsername(secondPlayerUsername);
        DuelPageController.getInstance().setNumberOfRounds(numberOfRounds);

        DuelPageController.getInstance().setCurrentRoundNumber(1);

        User.getUserByUsername(username).setLifePoints(8000);
        User.getUserByUsername(secondPlayerUsername).setLifePoints(8000);

        User.getUserByUsername(username).getBoard().setMainDeckCards(User.getUserByUsername(username).
                getActivatedDeck().getMainDeckCards());
        User.getUserByUsername(secondPlayerUsername).getBoard().setMainDeckCards
                (User.getUserByUsername(secondPlayerUsername).getActivatedDeck().getMainDeckCards());

        shuffleCards(username);
        shuffleCards(secondPlayerUsername);

        DuelPageController.getInstance().setGameMode(GameMode.MULTI_PLAYER);

        try {
            new DuelPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shuffleCards(String username) {
        Collections.shuffle(User.getUserByUsername(username).getBoard().getMainDeckCards());
        for (int i = 0; i < 5; i++) {
            User.getUserByUsername(username).getBoard().getInHandCards().
                    add(User.getUserByUsername(username).getBoard().getMainDeckCards().get(0));
            User.getUserByUsername(username).getBoard().getMainDeckCards().remove(0);
        }
    }

    public void newGameWithAI(int numberOfRounds) {
        if (User.getUserByUsername(username).getActivatedDeck() == null)
            System.out.println(username + "has no active deck");
        else if (!User.getUserByUsername(username).getActivatedDeck().isDeckValid())
            System.out.println(username + "’s deck is invalid");
        else {
            makeAI();

            DuelPageController.getInstance().setFirstPlayerUsername(username);
            DuelPageController.getInstance().setSecondPlayerUsername("AI");
            DuelPageController.getInstance().setCurrentTurnUsername(username);
            DuelPageController.getInstance().setOpponentUsername("AI");
            DuelPageController.getInstance().setNumberOfRounds(numberOfRounds);

            DuelPageController.getInstance().setCurrentRoundNumber(1);

            User.getUserByUsername(username).setLifePoints(8000);
            User.getUserByUsername("AI").setLifePoints(8000);
            User.getUserByUsername(username).setMaxLifePointInTheRounds(0);
            User.getUserByUsername("AI").setMaxLifePointInTheRounds(0);

            User.getUserByUsername(username).getBoard().setMainDeckCards(User.getUserByUsername(username).
                    getActivatedDeck().getMainDeckCards());
            User.getUserByUsername("AI").getBoard().setMainDeckCards
                    (User.getUserByUsername("AI").getActivatedDeck().getMainDeckCards());

            shuffleCards(username);
            shuffleCards("AI");

            DuelPageController.getInstance().setGameMode(GameMode.SINGLE_PLAYER);
            try {
                new DuelPage().start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void makeAI() {
        new User("AI", "AI", "AI");

        User.getUserByUsername("AI").getDecks().add(new Deck("d1"));
        User.getUserByUsername("AI").setActivatedDeck(User.getUserByUsername("AI").getDecks().get(0));

        addCardToDeckByUsername();
    }

    private static void addCardToDeckByUsername() {
        for (int i = 0; i < 75; i++) {
            User.getUserByUsername("AI").getCards().add(Card.getCards().get(i));
        }

        for (int i = 0; i < 40; i++) {
            User.getUserByUsername("AI").getActivatedDeck().getMainDeckCards().add(Card.getCards().get(i));
            User.getUserByUsername("AI").getActivatedDeck().getCards().add(Card.getCards().get(i));
        }

        for (int i = 43; i < 55; i++) {
            User.getUserByUsername("AI").getActivatedDeck().getSideDeckCards().add(Card.getCards().get(i));
            User.getUserByUsername("AI").getActivatedDeck().getCards().add(Card.getCards().get(i));
        }
    }
}
