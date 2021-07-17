package controller;

import model.Card;
import model.User;

public class ShopPageController extends Controller {
    private static ShopPageController instance;
    private String message;

    private ShopPageController() {

    }

    public static ShopPageController getInstance() {
        if (instance == null)
            instance = new ShopPageController();
        return instance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void buyCardByCardName(String cardName) {


    }

    public void buyCard(String cardName) {
        int userMoney = User.getUserByUsername(username).getMoney();
        if (!Card.isCardWithThisNameExists(cardName)) setMessage("there is no card with this name");
        else if (Card.getCardByName(cardName).getPrice() > userMoney) {
            setMessage("not enough money");
        } else {
            int cardPrice = Card.getCardByName(cardName).getPrice();
            User.getUserByUsername(username).setMoney(userMoney - cardPrice);
            User.getUserByUsername(username).getCards().add(Card.getCardByName(cardName));
            User.getUserByUsername(username).getAllBoughtCards().add(Card.getCardByName(cardName));
            Card.getCardByName(cardName).decreaseNumberOfAvailableCardsInShop();
            setMessage("Card added successfully");
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
