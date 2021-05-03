package model;

public class User
{
    private ArrayList<User> users;		
    private ArrayList<Card> cards;		
    private ArrayList<Deck> decks;		
    private String username;		
    private String nickname;		
    private String password;		
    private double money;		
    private int lifePoints;		
    private Board board;		

    
    private boolean isUsernameAlreadyExists(String username) 		
    {
        
        return true;
    
    }		
    
    private boolean isNicknameAlreadyExists(String username) 		
    {
        
        return true;
    
    }		
    
    private boolean isPasswordCorrect(String username) 		
    {
        
        return true;
    
    }		
    
    private boolean getPassword(String username) 		
    {
        
        return true;
    
    }		
    
    private void setPassword(String password) 		
    {
        
        this.password = password;
    
    }		
    
    private String[] getNicknamePlusScoreOfAllUsers() 		
    {
        
        return new String[]{"", " "};
    
    }		
    
    private boolean isDeckAlreadyExists(String deckName) 		
    {
        
        return true;
    
    }		
    
    public void addCardToDeck(String deckName, String cardName) 		
    {
        

    
    }		
    
    public void deleteCardFromDeck(String deckName, String cardName) 		
    {
        

    
    }		
    
    public User getUserByUsername() 		
    {
        
    }		
    
    public void setGameBoard() 		
    {
        
    }		
}
