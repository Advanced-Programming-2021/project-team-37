package model;

import controller.CardState;
import controller.SpellOrTrapCardState;

import java.util.ArrayList;

public class Card {
    protected String cardName;
    protected String description;
    protected boolean HaveCardPositionChangedInThisTurn = false;

    public void setCardState(controller.CardState cardState) {
        CardState = cardState;
    }

    public CardState CardState;
    private SpellOrTrapCardState spellOrTrapCardState; // maybe it is better to send this to SpellAndTrapCard
    public String statusOnField;
    public ArrayList<Card> cards;
    protected String cardType;
    protected String deckName;
    protected boolean isCardSelected = false;
    protected boolean isCardSetPositionInThisTurn = false;
    protected boolean cardAlreadyAttackedInThisTurn = false;

    public boolean isCardAlreadyAttackedInThisTurn() {
        return cardAlreadyAttackedInThisTurn;
    }

    public void setCardAlreadyAttackedInThisTurn(boolean cardAlreadyAttackedInThisTurn) {
        this.cardAlreadyAttackedInThisTurn = cardAlreadyAttackedInThisTurn;
    }


    public boolean isCardSetInThisTurn() {
        return isCardSetInThisTurn;
    }

    public void setCardSetInThisTurn(boolean cardSetInThisTurn) {
        isCardSetInThisTurn = cardSetInThisTurn;
    }

    protected boolean isCardSetInThisTurn = false;

    public boolean isCardSetPositionInThisTurn() {
        return isCardSetPositionInThisTurn;
    }

    public void setCardSetPositionInThisTurn(boolean cardSetPositionInThisTurn) {
        isCardSetPositionInThisTurn = cardSetPositionInThisTurn;
    }


    public boolean isCardSelected() {
        return isCardSelected;
    }

    public void setCardSelected(boolean cardSelected) {
        isCardSelected = cardSelected;
    }

    public boolean isHaveCardPositionChangedInThisTurn() {
        return HaveCardPositionChangedInThisTurn;
    }

    public void setHaveCardPositionChangedInThisTurn(boolean haveCardPositionChangedInThisTurn) {
        HaveCardPositionChangedInThisTurn = haveCardPositionChangedInThisTurn;
    }

    public controller.CardState getCardState() {
        return CardState;
    }

    public SpellOrTrapCardState getSpellOrTrapCardState() {
        return spellOrTrapCardState;
    }

    public void setSpellOrTrapCardState(SpellOrTrapCardState spellOrTrapCardState) {
        this.spellOrTrapCardState = spellOrTrapCardState;
    }

    public void createCard(String cardType, String cardName) {

    }

    public String getCardType() {
        return this.cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusOnField() {
        return statusOnField;
    }

    public void setStatusOnField(String statusOnField) {
        this.statusOnField = statusOnField;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
