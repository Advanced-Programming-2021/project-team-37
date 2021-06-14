package model;

import controller.SelectedCardField;

import java.util.ArrayList;

public class Board {
    private Card fieldCard;
    private Card selectedCard;
    private Card[] monsterCards; //TODO make new of this with size 6 index 0 will be null
    private Card[] spellOrTrapCards; //TODO make new of this with size 6 index 0 will be null
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> inHandCards;
    private ArrayList<Card> mainDeckCards;
    private boolean isAnyCardSelected = false;
    private int selectedCardNumberInHand; // it starts from 0 and if it is -1 no card is selected // TODO if there is any problem with this change this to isCardSelected
    private int selectedMyMonsterCardNumber; // it starts from 0 and if it is -1 no card is selected
    private int selectedMySpellOrTrapCardNumber; // it starts from 0 and if it is -1 no card is selected

    public int getSelectedMyMonsterCardNumber() {
        return selectedMyMonsterCardNumber;
    }

    public void setSelectedMyMonsterCardNumber(int selectedMyMonsterCardNumber) {
        this.selectedMyMonsterCardNumber = selectedMyMonsterCardNumber;
    }

    public int getSelectedMySpellOrTrapCardNumber() {
        return selectedMySpellOrTrapCardNumber;
    }

    public void setSelectedMySpellOrTrapCardNumber(int selectedMySpellOrTrapCardNumber) {
        this.selectedMySpellOrTrapCardNumber = selectedMySpellOrTrapCardNumber;
    }

    public int getSelectedOpponentMonsterCardNumber() {
        return selectedOpponentMonsterCardNumber;
    }

    public void setSelectedOpponentMonsterCardNumber(int selectedOpponentMonsterCardNumber) {
        this.selectedOpponentMonsterCardNumber = selectedOpponentMonsterCardNumber;
    }

    public int getSelectedOpponentSpellOrTrapCardNumber() {
        return selectedOpponentSpellOrTrapCardNumber;
    }

    public void setSelectedOpponentSpellOrTrapCardNumber(int selectedOpponentSpellOrTrapCardNumber) {
        this.selectedOpponentSpellOrTrapCardNumber = selectedOpponentSpellOrTrapCardNumber;
    }

    private int selectedOpponentMonsterCardNumber; // it starts from 0 and if it is -1 no card is selected
    private int selectedOpponentSpellOrTrapCardNumber; // it starts from 0 and if it is -1 no card is selected
    private SelectedCardField selectedCardField;

    public boolean isAnyCardSelected() {
        return isAnyCardSelected;
    }

    public void setAnyCardSelected(boolean anyCardSelected) {
        isAnyCardSelected = anyCardSelected;
    }

    public SelectedCardField getSelectedCardField() {
        return selectedCardField;
    }

    public void setSelectedCardField(SelectedCardField selectedCardField) {
        this.selectedCardField = selectedCardField;
    }

    public int getSelectedCardNumberInHand() {
        return selectedCardNumberInHand;
    }

    public void setSelectedCardNumberInHand(int selectedCardNumberInHand) {
        this.selectedCardNumberInHand = selectedCardNumberInHand;
    }

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

    public ArrayList<Card> getMainDeckCards() {
        return mainDeckCards;
    }

    public void setMainDeckCards(ArrayList<Card> mainDeckCards) {
        this.mainDeckCards = mainDeckCards;
    }



    public Card getFieldCard() {
        return fieldCard;
    }

    public void setFieldCard(Card fieldCard) {
        this.fieldCard = fieldCard;
    }

}
