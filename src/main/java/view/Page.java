package view;

import controller.DuelPageController;

import java.util.Scanner;

public class Page
{
    public static Menu currentMenu = Menu.LOGIN;
    public static Scanner scanner = new Scanner(System.in);
    public static String message;
    protected static String username;

    public void run() {
        //currentMenu = Menu.LOGIN; todo
        while (currentMenu != Menu.EXIT) {
            System.out.println(currentMenu);

            if (currentMenu == Menu.DUEL) {
                DuelPage duelPage = new DuelPage();
                duelPage.phaseWork();
            }

            String command = scanner.nextLine();
            if (currentMenu == Menu.LOGIN) {
                LoginPage loginPage = new LoginPage();
                loginPage.runLoginPage(command);
            } else if (currentMenu == Menu.MAIN) {
                MainPage mainPage = new MainPage();
                mainPage.runMainPage(command);
//                if (currentMenu == Menu.GAME_LAYOUT_MENU) {
//                    gameLayoutMenu = new GameLayoutMenu();
//                }
            } else if (currentMenu == Menu.DUEL) {
                DuelPage duelPage = new DuelPage();
                duelPage.runDuelPage(command);
//                if (currentMenu == Menu.GAME_MENU) {
//                    gameMenu = new GameMenu();
//                }
            } else if (currentMenu == Menu.DECK) {
                DeckPage deckPage = new DeckPage();
                deckPage.runDeckPage(command);
            } else if (currentMenu == Menu.SCOREBOARD) {
                ScoreboardPage scoreboardPage = new ScoreboardPage();
                scoreboardPage.runScoreboardPage(command);
            } else if (currentMenu == Menu.PROFILE) {
                ProfilePage profilePage = new ProfilePage();
                profilePage.runProfilePage(command);
            } else if (currentMenu == Menu.SHOP) {
                ShopPage shopPage = new ShopPage();
                shopPage.runShopPage(command);
            } else if (currentMenu == Menu.IMPORTOREXPORT) {
                ImportOrExportPage importOrExportPage = new ImportOrExportPage();
                importOrExportPage.runImportOrExportPage(command);
            }
        }
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Page.message = message;
    }

    protected static int functionNumber;
    protected static boolean isCommandValid = false;
    protected String[] commandPatterns;
    private int commandNumber;		

    
    public String getUsername() 		
    {
        return username;
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(Menu currentMenu) {
        Page.currentMenu = currentMenu;
    }

    public void setUsername(String username)
    {
        this.username = username;
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
        
    }		
    
    public void showCurrentMenu() 		
    {
        
    }
}
