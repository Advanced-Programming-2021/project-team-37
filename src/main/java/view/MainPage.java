package view;

import controller.MainPageController;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPage extends Page {

    public void enterMenu(String menuName) {
        if (menuName.equals("duel")) currentMenu = Menu.DUEL;
        else if (menuName.equals("deck")) currentMenu = Menu.DECK;
        else if (menuName.equals("scoreboard")) currentMenu = Menu.SCOREBOARD;
        else if (menuName.equals("profile")) currentMenu = Menu.PROFILE;
        else if (menuName.equals("shop")) currentMenu = Menu.SHOP;
        else if (menuName.equals("import/export")) currentMenu = Menu.IMPORTOREXPORT;
        else System.out.println("invalid menu name");
    }

    public void exitMenu() {
        User.updateUsers();
        currentMenu = Menu.LOGIN;
    }

    public void showCurrentMenu() {


    }

    private void logout() {
        System.out.println("user logged out successfully!");
        currentMenu = Menu.LOGIN;
    }

    private void newGameWithAnotherPlayer(Matcher matcher) {


    }

    private void newGameWithAI() {


    }

    public void runMainPage(String command) {
        String[] commandPatterns = {
                "user logout",
                "duel --new --second-player (\\S+) --rounds (\\d+)",
                "duel --new --rounds (\\d+) --second-player (\\S+)",
                "duel --second-player (\\S+) --new --rounds (\\d+)",
                "duel --second-player (\\S+) --rounds (\\d+) --new",
                "duel --rounds (\\d+) --new --second-player (\\S+)",
                "duel --rounds (\\d+) --second-player (\\S+) --new",
                "duel --new --ai --rounds (\\d+)",
                "duel --new --rounds (\\d+) --ai",
                "duel --ai --new --rounds (\\d+)",
                "duel --ai --rounds (\\d+) --new",
                "duel --rounds (\\d+) --new --ai",
                "duel --rounds (\\d+) --ai --new",
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
            if (functionNumber == 0) logout();
            else if (functionNumber == 1)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(1), Integer.parseInt(matcher.group(2)));
            else if (functionNumber == 2)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(2), Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 3)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(1), Integer.parseInt(matcher.group(2)));
            else if (functionNumber == 4)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(1), Integer.parseInt(matcher.group(2)));
            else if (functionNumber == 5)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(2), Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 6)
                MainPageController.getInstance().newGameWithAnotherUser(matcher.group(2), Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 7) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 8) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 9) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 10) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 11) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 12) MainPageController.getInstance().newGameWithAI(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 13) enterMenu(matcher.group(1));
            else if (functionNumber == 14) exitMenu();
            isCommandValid = true;
        }
    }
}
