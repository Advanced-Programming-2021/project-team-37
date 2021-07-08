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

    public void buyCard(String cardName) {
        int userMoney = User.getUserByUsername(username).getMoney();
        if (!Card.isCardWithThisNameExists(cardName)) ShopPage.setMessage("there is no card with this name");
        else if (Card.getCardByName(cardName).getPrice() > userMoney) {
            ShopPage.setMessage("not enough money");
            Page.playNotEnoughCoin();
        } else {
            int cardPrice = Card.getCardByName(cardName).getPrice();
            User.getUserByUsername(username).setMoney(userMoney - cardPrice);
            User.getUserByUsername(username).getCards().add(Card.getCardByName(cardName));
            User.getUserByUsername(username).getAllBoughtCards().add(Card.getCardByName(cardName));
            ShopPage.setMessage("Card added successfully");
            Page.playCoinSound();
        }
    }

    public void showAllCards() {
        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + ":" + card.getPrice());
        }
    }

    public void increaseMoney(int amount) {
        User.getUserByUsername(username).setMoney(User.getUserByUsername(username).getMoney() + amount);
    }
}
