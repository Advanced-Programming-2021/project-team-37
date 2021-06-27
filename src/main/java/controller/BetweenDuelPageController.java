package controller;

import model.Card;
import model.User;
import view.BetweenDuelPage;
import view.Menu;
import view.Page;

public class BetweenDuelPageController extends DuelPageController {
    private static BetweenDuelPageController instance;
    private String currentUsername;

    private BetweenDuelPageController() {

    }

    public static BetweenDuelPageController getInstance() {
        if (instance == null)
            instance = new BetweenDuelPageController();
        return instance;
    }

    public void exchangeCard(String mainDeckCardName, String sideDeckCardName) {
        if (!User.getUserByUsername(currentUsername).getActivatedDeck().isCardWithThisNameAlreadyExistsInMainDeckCards(mainDeckCardName))
            System.out.println("card with name " + mainDeckCardName + " doesn't exist in you main deck cards");
        else if (!User.getUserByUsername(currentUsername).getActivatedDeck().isCardWithThisNameAlreadyExistsInSideDeckCards(sideDeckCardName))
            System.out.println("card with name " + sideDeckCardName + " doesn't exist in you side deck cards");
        else {
            Card sideDeckCard = User.getUserByUsername(currentUsername).getActivatedDeck().getCardByCardNameFromSideDeck(sideDeckCardName);
            Card mainDeckCard = User.getUserByUsername(currentUsername).getActivatedDeck().getCardByCardNameFromMainDeck(sideDeckCardName);
            User.getUserByUsername(currentUsername).getActivatedDeck().getSideDeckCards().add(mainDeckCard);
            User.getUserByUsername(currentUsername).getActivatedDeck().getMainDeckCards().add(sideDeckCard);
            User.getUserByUsername(currentUsername).getActivatedDeck().getSideDeckCards().remove(sideDeckCard);
            User.getUserByUsername(currentUsername).getActivatedDeck().getMainDeckCards().remove(mainDeckCard);

            skip();
        }
    }


    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void skip() {
        if (currentUsername.equals(DuelPageController.getInstance().getFirstPlayerUsername()))
            currentUsername = DuelPageController.getInstance().getSecondPlayerUsername();
        else if (currentUsername.equals(DuelPageController.getInstance().getSecondPlayerUsername())) {
            User.getUserByUsername(firstPlayerUsername).getBoard().setMainDeckCards
                    (User.getUserByUsername(firstPlayerUsername).getActivatedDeck().getMainDeckCards());
            User.getUserByUsername(secondPlayerUsername).getBoard().setMainDeckCards
                    (User.getUserByUsername(secondPlayerUsername).getActivatedDeck().getMainDeckCards());
            Page.setCurrentMenu(Menu.DUEL);
        }
        BetweenDuelPage.printHelp();

        if (currentUsername.equals("AI")) Page.setCurrentMenu(Menu.DUEL);
    }
}