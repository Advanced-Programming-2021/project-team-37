package view;

import controller.ImportOrExportPageController;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportOrExportPage extends Page {

    public void importCards(User user) {

    }

    public void exportCards(User user) {

    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }


    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public void exitMenu() {
        currentMenu = Menu.MAIN;
    }

    public void showCurrentMenu() {

    }

    public void runImportOrExportPage(String command) {
        String[] commandPatterns = {"menu exit", "menu enter (\\S+)",
                "import card (.+)",
                "export card (.+)"
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
            if (functionNumber == 0) exitMenu();
            else if (functionNumber == 1) enterMenu(matcher.group(1));
            else if (functionNumber == 2) ImportOrExportPageController.getInstance().importCard(matcher.group(1));
            else if (functionNumber == 3) ImportOrExportPageController.getInstance().exportCard(matcher.group(1));
            isCommandValid = true;
        }
    }

}
