package view;

import controller.ShopPageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopPage extends Page
{
    
    public void setUsername(String username) 		
    {

    }		
    
    public void setCommandPatterns(String commandPatterns) 		
    {

    }

    public void enterMenu(String menuName)
    {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }		
    
    public void exitMenu() 		
    {
        currentMenu = Menu.MAIN;
    }		
    
    public void showCurrentMenu() 		
    {

    }		
    
    private void buyCardByCardName()
    {

    }		
    
    private void showAllCardsInShop() 		
    {

    }
    
    public void run() 		
    {
        
    }

    public void runShopPage(String command) {
        String[] commandPatterns = {
                "shop buy (\\S+)",
                "shop show --all",
                "menu enter (\\S+)",
                "menu exit"
        };

        isCommandValid = false;
        for (functionNumber = 0; functionNumber < commandPatterns.length && !isCommandValid; functionNumber++) {
            getCommandMatcher(command, commandPatterns[functionNumber]);
        }
        if (!isCommandValid) System.out.println("invalid command");
    }

    private void getCommandMatcher(String command, String commandPattern) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (functionNumber == 0) ShopPageController.getInstance().buyCard(matcher.group(1));
            else if (functionNumber == 1) ShopPageController.getInstance().showAllCards();
            else if (functionNumber == 2) enterMenu(matcher.group(1));
            else if (functionNumber == 3) exitMenu();
            isCommandValid = true;
        }
    }
}
