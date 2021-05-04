package view;

import java.util.Scanner;

public abstract class Page
{
    protected String username;
    protected static int functionCounter;
    protected static boolean isCommandValid;
    protected static Scanner scanner = new Scanner(System.in);
    protected String[] commandPatterns;		
    private boolean validCommand = false;		
    private int commandNumber;		

    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) 		
    {
        
    }		
    
    public void setCommandPatterns(String commandPatterns) 		
    {
        
    }		
    
    public void getCommandMatcher(String command, String commandPattern) 		
    {
        
    }		
    
    public void enterMenu() 		
    {
        
    }		
    
    public void exitMenu() 		
    {
        
    }		
    
    public void showCurrentMenu() 		
    {
        
    }		
    
    public void run() 		
    {
        
    }		
}
