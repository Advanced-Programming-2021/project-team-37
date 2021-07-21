package model;

import java.util.ArrayList;

public class Board {
    private Card fieldCard;
    private Card selectedCard;
    private Card toBeAttackedCard;
    private Monster[] monsterCards;
    private SpellAndTrap[] spellOrTrapCards;
    private ArrayList<Card> graveyardCards;
    private ArrayList<Card> inHandCards;
    private ArrayList<Card> mainDeckCards;
    private boolean isAnyCardSelected = false;
    private int selectedCardNumberInHand; // it starts from 0 and if it is -1 no card is selected
    private int selectedMyMonsterCardNumber; // it starts from 0 and if it is -1 no card is selected
    private int selectedMySpellOrTrapCardNumber; // it starts from 0 and if it is -1 no card is selected

    public int getToBeAttackedCardNumber() {
        return toBeAttackedCardNumber;
    }

    public void setToBeAttackedCardNumber(int toBeAttackedCardNumber) {
        this.toBeAttackedCardNumber = toBeAttackedCardNumber;
    }

    private int toBeAttackedCardNumber; // it starts from 0 and if it is -1 no card is selected


    public Board() {
        graveyardCards = new ArrayList<>();
        inHandCards = new ArrayList<>();
        mainDeckCards = new ArrayList<>();
        monsterCards = new Monster[6];
        spellOrTrapCards = new SpellAndTrap[6];
    }

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

    public Monster[] getMonsterCards() {
        return monsterCards;
    }

    public void setMonsterCards(Monster[] monsterCards) {
        this.monsterCards = monsterCards;
    }

    public void setSpellOrTrapCards(SpellAndTrap[] spellOrTrapCards) {
        this.spellOrTrapCards = spellOrTrapCards;
    }

    public SpellAndTrap[] getSpellOrTrapCards() {
        return spellOrTrapCards;
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

    public Card getToBeAttackedCard() {
        return toBeAttackedCard;
    }

    public void setToBeAttackedCard(Card toBeAttackedCard) {
        this.toBeAttackedCard = toBeAttackedCard;
    }
}
