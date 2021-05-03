package controller;

 class SinglePlayer extends DuelPageController implements SaveData
{
    private User player;		
    private AI AI;		
    private String currentTurn;		

    
    public void ComputerMakeMove() 		
    {
        
    }		
    
    public void createGameForComputer() 		
    {
        
    }		
    
    public void createGameForPlayer(User player) 		
    {
        
    }		
    
    public void changeTurn() 		
    {
        
    }		
    
    public void playerActivateSpellOrTrapCardInComputersTurn() 		
    {
        
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public AI getAI() {
        return AI;
    }

    public void setAI(AI AI) {
        this.AI = AI;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }
}
