package model;

import java.util.ArrayList;

public class Board {
    public Card[] getMonsterCards() {
        return monsterCards;
    }

    public void setMonsterCards(Card[] monsterCards) {
        this.monsterCards = monsterCards;
    }

    public Card[] getSpellOrTrapCards() {
        return spellOrTrapCards;
    }

    public void setSpellOrTrapCards(Card[] spellOrTrapCards) {
        this.spellOrTrapCards = spellOrTrapCards;
    }

    public ArrayList<Card> getGraveyardCards() {
        return graveyardCards;
    }

    public void setGraveyardCards(ArrayList<Card> graveyardCards) {
        this.graveyardCards = graveyardCards;
    }

    public ArrayList<Card> getInHandCards() {
        return inHandCards;
    }

    public void setInHandCards(ArrayList<Card> inHandCards) {
        this.inHandCards = inHandCards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public ArrayList<SpellAndTrap> getFieldZone() {
        return fieldZone;
    }

    public void setFieldZone(ArrayList<SpellAndTrap> fieldZone) {
        this.fieldZone = fieldZone;
    }

    public ArrayList<SpellAndTrap> getOpponentFieldZone() {
        return opponentFieldZone;
    }

    public void setOpponentFieldZone(ArrayList<SpellAndTrap> opponentFieldZone) {
        this.opponentFieldZone = opponentFieldZone;
    }

    public ArrayList<Card> getMainDeckCards() {
        return mainDeckCards;
    }

    public void setMainDeckCards(ArrayList<Card> mainDeckCards) {
        this.mainDeckCards = mainDeckCards;
    }

    private Card[] monsterCards; //TODO make new of this with size 5
    private Card[] spellOrTrapCards; //TODO make new of this with size 5
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> inHandCards;
    private ArrayList<Card> mainDeckCards;

    public Card getFieldCard() {
        return fieldCard;
    }

    public void setFieldCard(Card fieldCard) {
        this.fieldCard = fieldCard;
    }

    private Card fieldCard;
    private Card selectedCard;
    private ArrayList<SpellAndTrap> fieldZone;
    private ArrayList<SpellAndTrap> opponentFieldZone;
}
