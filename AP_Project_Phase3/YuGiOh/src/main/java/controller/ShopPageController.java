package controller;

import model.Card;
import model.User;
import view.Page;
import view.ShopPage;

public class ShopPageController extends Controller {
    private static ShopPageController instance;

    private ShopPageController() {

    }

    public static ShopPageController getInstance() {
        if (instance == null)
            instance = new ShopPageController();
        return instance;
    }


    private void buyCardByCardName(String cardName) {


    }

    public void showAllCards() {
        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + ":" + card.getPrice());
        }
    }

    public void increaseMoney(int amount) {
        currentUser.setMoney(currentUser.getMoney() + amount);
    }
}
