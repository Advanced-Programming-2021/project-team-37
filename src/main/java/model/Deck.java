package model;

public class Deck
{
     ArrayList<Card> mainDeckCardsPlusSideDeckCards;		
     ArrayList<Card> mainDeckCards;		
     ArrayList<Card> sideDeckCards;		
    private String deckName;		
    private boolean isActivated = false;		
    public ArrayList<Card> cards;		

    
    public boolean isActivated() 		
    {
        
        return isActivated;
    
    }		
    
    public void setActivated(boolean activated) 		
    {
        
        isActivated = activated;
    
    }		
}
