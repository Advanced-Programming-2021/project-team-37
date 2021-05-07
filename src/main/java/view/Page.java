package view;

import java.util.Scanner;

public abstract class Page
{
    protected static Scanner scanner = new Scanner(System.in);
    protected String username;
    protected static int functionNumber;
    protected static boolean isCommandValid = false;
    protected String[] commandPatterns;
    private int commandNumber;		

    
    public String getUsername() 		
    {
        
        return username;
    
    }		
    
    public void setUsername(String username) 		
    {
        
    }		

    public void setCommandPatterns(String commandPatterns) 		
    {
        
    }		
    
    public static void getCommandMatcher(String command, String commandPattern)
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
    
    public static void run()
    {
        
    }

    pu
}
