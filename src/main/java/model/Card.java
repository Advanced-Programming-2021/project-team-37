package model;

import java.util.ArrayList;

abstract class Card {
    protected String cardName;
    protected String description;
    public String status;
    public String statusOnField;		
    public ArrayList<Card> cards;
    protected String cardType;
    protected String deckName;

    
    public void createCard(String cardType, String cardName) {
        
    }		
    
    public String getCardType() {
        return this.cardType;
    }		
}
