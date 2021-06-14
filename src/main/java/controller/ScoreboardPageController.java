package controller;

public class ScoreboardPageController extends Controller
{
    private ScoreboardPageController instance;		

    
    private void showScoreboard() 		
    {
        
    
    }		
    
    private void sort() 		
    {
        
    
    }		
    
    private ScoreboardPageController()
    {
        
    }		
    
    private ScoreboardPageController getInstance() 		
    {
        if (instance == null)
            instance = new ScoreboardPageController();
        return instance;
    }

    @Override
    public void exit() {

    }

    @Override
    public void showCurrentMenu() {

    }
}
