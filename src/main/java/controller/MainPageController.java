package controller;

import model.GameMode;
import model.User;
import view.DuelPage;
import view.Menu;
import view.Page;

import java.util.Collections;

public class MainPageController extends Controller {

    private static MainPageController instance;

    void enterMenu() {


    }

    public static MainPageController getInstance() {
        if (instance == null)
            instance = new MainPageController();
        return instance;
    }

    private MainPageController() {

    }

    private boolean isMenuNavigationPossible() {

        return true;

    }

    private String[] sortLexicographically(String[] s) {

        return new String[]{"", " "};

    }

    private void logout() {


    }

    private void newGameWithAnotherPlayer(String rivalName, int number) {


    }

    private boolean isRivalNameExists(String rivalName) {

        return true;

    }

    private void havePlayerActiveDeck(String playerName) {


    }

    private boolean isNumberOfRoundsValid(int number) {

        return true;

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void exit() {

    }

    @Override
    public void showCurrentMenu() {

    }

    public void newGameWithAnotherUser(String secondPlayerUsername, int numberOfRounds) {
        if (username.equals(secondPlayerUsername)) System.out.println("please enter another player username");
        else if (!User.isUsernameAlreadyExists(secondPlayerUsername)) System.out.println("there is no player with this username");
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

        User.getUserByUsername(username).setLifePoints(8000);
        User.getUserByUsername(secondPlayerUsername).setLifePoints(8000);

        User.getUserByUsername(username).getBoard().setMainDeckCards(User.getUserByUsername(username).
                getActivatedDeck().getMainDeckCards());
        User.getUserByUsername(secondPlayerUsername).getBoard().setMainDeckCards
                (User.getUserByUsername(secondPlayerUsername).getActivatedDeck().getMainDeckCards());

        shuffleCards(username);
        shuffleCards(secondPlayerUsername);

        DuelPageController.getInstance().setGameMode(GameMode.MULTI_PLAYER);

        Page.setCurrentMenu(Menu.DUEL);
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
            DuelPageController.getInstance().setGameMode(GameMode.SINGLE_PLAYER);
            // todo start a new game with AI
        }
    }
}
