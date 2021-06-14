package controller;

public abstract class Controller
{
    protected String username;

    
    protected String getUsername() 		
    {
        return username;
    }		

    protected void setUsername(String username)
    {
        this.username = username;
    }

    public abstract void exit();

    public abstract void showCurrentMenu();
}
