package model;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> mainDeckCardsPlusSideDeckCards;
    ArrayList<Card> mainDeckCards;
    ArrayList<Card> sideDeckCards;
    private String deckName;
    private boolean isActivated = false;
    public ArrayList<Card> cards;

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
}
