package controller;

 class MultiPlayer extends DuelPageController implements SaveData
{
    private User currrentPalyer;		
    private User opponentPlayer;		
    private User gameField;		

    
    public void changeTurn() 		
    {
        
    }		
    
    public void flipGameBoard() 		
    {
        
    }		
    
    public void setPlayers(Player player1, Player player2) 		
    {
        
    }		
    
    public void opponentActivateSpellOrTrapCard() 		
    {
        
    }

    public User getCurrrentPalyer() {
        return currrentPalyer;
    }

    public void setCurrrentPalyer(User currrentPalyer) {
        this.currrentPalyer = currrentPalyer;
    }

    public User getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(User opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public User getGameField() {
        return gameField;
    }

    public void setGameField(User gameField) {
        this.gameField = gameField;
    }
}
