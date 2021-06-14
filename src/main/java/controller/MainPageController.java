package controller;

import model.User;
import view.DuelPage;
import view.Menu;
import view.Page;

public class MainPageController extends Controller {

    protected String username;
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
        if (!User.isUsernameAlreadyExists(username)) System.out.println("there is no player with this username");
        else if (User.getUserByUsername(username).getActivatedDeck() == null)
            System.out.println(username + "has no active deck");
        else if (User.getUserByUsername(secondPlayerUsername).getActivatedDeck() == null)
            System.out.println(secondPlayerUsername + "has no active deck");
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

        Page.setCurrentMenu(Menu.DUEL);
    }

    public void newGameWithAI(int numberOfRounds) {
        if (User.getUserByUsername(username).getActivatedDeck() == null)
            System.out.println(username + "has no active deck");
        else if (!User.getUserByUsername(username).getActivatedDeck().isDeckValid())
            System.out.println(username + "’s deck is invalid");
        else {
            // todo start a new game with AI
        }
    }
}
