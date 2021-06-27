package view;

import controller.BetweenDuelPageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetweenDuelPage extends Page {


    public void runBetweenDuelPage(String command) {
        String[] commandPatterns = {"menu exit", "menu enter (\\S+)",
                "main deck card (.+) exchange main deck card (.+)", "skip"
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
            else if (functionNumber == 2)
                BetweenDuelPageController.getInstance().exchangeCard(matcher.group(1), matcher.group(2));
            else if (functionNumber == 3) BetweenDuelPageController.getInstance().skip();
            isCommandValid = true;
        }
    }

    public static void printHelp() {
        System.out.println("It's " + BetweenDuelPageController.getInstance().getCurrentUsername() + " turn");
        System.out.println("Here you can exchange a card between your main and side deck with this command");
        System.out.println("main deck card <card name> exchange main deck card <card name>");
        System.out.println("if you don't want to exchange card just type \"skip\"");
    }

}
