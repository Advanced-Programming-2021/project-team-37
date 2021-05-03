package model;

import java.util.ArrayList;

public abstract class Card {
    protected String cardName;
    protected String description;
    public String status;

    public MonsterCardState getCardState() {
        return monsterCardState;
    }

    public void setCardState(MonsterCardState monsterCardState) {
        this.monsterCardState = monsterCardState;
    }

    public String statusOnField;
    public ArrayList<Card> cards;
    protected String cardType;
    protected String deckName;
    private MonsterCardState monsterCardState;

    public MonsterCardState getMonsterCardState() {
        return monsterCardState;
    }

    public void setMonsterCardState(MonsterCardState monsterCardState) {
        this.monsterCardState = monsterCardState;
    }

    public SpellOrTrapCardState getSpellOrTrapCardState() {
        return spellOrTrapCardState;
    }

    public void setSpellOrTrapCardState(SpellOrTrapCardState spellOrTrapCardState) {
        this.spellOrTrapCardState = spellOrTrapCardState;
    }

    private SpellOrTrapCardState spellOrTrapCardState;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
