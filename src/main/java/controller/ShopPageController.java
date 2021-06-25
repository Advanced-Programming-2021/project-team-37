package controller;

import model.Card;
import model.User;
import view.ShopPage;

public class ShopPageController extends Controller
{
    private static ShopPageController instance;

    private ShopPageController()
    {

    }

    public static ShopPageController getInstance() {
        if (instance == null)
            instance = new ShopPageController();
        return instance;
    }


    private void buyCardByCardName(String cardName)
    {


    }

    private void showAllCardsInShop()
    {


    }

    @Override
    public void exit() {

    }

    @Override
    public void showCurrentMenu() {

    }

    public void buyCard(String cardName) {
        int userMoney = User.getUserByUsername(username).getMoney();
        if (!Card.isCardWithThisNameExists(cardName)) ShopPage.setMessage("there is no card with this name");
        else if (Card.getCardByName(cardName).getPrice() > userMoney)
            ShopPage.setMessage("not enough money");
        else {
            int cardPrice = Card.getCardByName(cardName).getPrice();
            User.getUserByUsername(username).setMoney(userMoney - cardPrice);
            User.getUserByUsername(username).getCards().add(Card.getCardByName(cardName));
            ShopPage.setMessage("");
        }
    }

    public void showAllCards() {
        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + ":" + card.getPrice());
        }
    }
}
