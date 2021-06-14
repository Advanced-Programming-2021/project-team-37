package model;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> mainDeckCardsPlusSideDeckCards;
    ArrayList<Card> mainDeckCards;
    ArrayList<Card> sideDeckCards;
    private String deckName;
    private boolean isActivated = false;
    public ArrayList<Card> cards;

    public Deck(String name) {
        this.deckName = name;
    }

    public ArrayList<Card> getMainDeckCardsPlusSideDeckCards() {
        return mainDeckCardsPlusSideDeckCards;
    }

    public void setMainDeckCardsPlusSideDeckCards(ArrayList<Card> mainDeckCardsPlusSideDeckCards) {
        this.mainDeckCardsPlusSideDeckCards = mainDeckCardsPlusSideDeckCards;
    }

    public ArrayList<Card> getMainDeckCards() {
        return mainDeckCards;
    }

    public void setMainDeckCards(ArrayList<Card> mainDeckCards) {
        this.mainDeckCards = mainDeckCards;
    }

    public ArrayList<Card> getSideDeckCards() {
        return sideDeckCards;
    }

    public void setSideDeckCards(ArrayList<Card> sideDeckCards) {
        this.sideDeckCards = sideDeckCards;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public boolean isActivated() {

        return isActivated;

    }

    public void setActivated(boolean activated) {

        isActivated = activated;

    }

    public int howManyCardWithThisName(String name) {
        int counter = 0;
        for (Card card : cards)
            if (card.cardName.equals(name)) counter++;

        return counter;
    }

    public boolean isCardExistsInMainDeck(String cardName) {
        for (Card mainDeckCard : mainDeckCards) {
            if (mainDeckCard.getCardName().equals(cardName)) return true;
        }
        return false;
    }

    public boolean isCardExistsInSideDeck(String cardName) {
        for (Card sideDeckCard : sideDeckCards) {
            if (sideDeckCard.getCardName().equals(cardName)) return true;
        }
        return false;
    }

    public boolean isDeckValid () {
        return mainDeckCards.size() >= 40;
    }
}
