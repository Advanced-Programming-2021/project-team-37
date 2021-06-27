package view;

import controller.DeckPageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckPage extends Page {

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

    private void createDeck(String name) {
        DeckPageController.getInstance().createDeck(name);
        System.out.println(message);
    }

    private void deleteDeck(String name) {
        DeckPageController.getInstance().deleteDeck(name);
        System.out.println(message);
    }

    private void setActiveDeck(String name) {
        DeckPageController.getInstance().setActiveDeck(name);
        System.out.println(message);
    }

    private void addCardToDeck(String cardName, String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().addCardToDeck(cardName, deckName, isSide);
        System.out.println(message);
    }

    public void runDeckPage(String command) {
        String[] commandPatterns = {
                "deck create (\\S+)",
                "deck delete (\\S+)",
                "deck set-activate (\\S+)",
                "deck add-card --card (.+) --deck (\\S+)( --side)?",
                "deck add-card --card (.+)( --side)? --deck (\\S+)",
                "deck add-card --deck (\\S+) --card (.+)( --side)?",
                "deck add-card --deck (\\S+)( --side)? --card (.+)",
                "deck add-card( --side)? --card (.+) --deck (\\S+)",
                "deck add-card( --side)? --deck (\\S+) --card (.+)",
                "deck rm-card --card (.+) --deck (\\S+)( --side)?",
                "deck rm-card --card (.+)( --side)? --deck (\\S+)",
                "deck rm-card --deck (\\S+) --card (.+)( --side)?",
                "deck rm-card --deck (\\S+)( --side)? --card (.+)",
                "deck rm-card( --side)? --card (.+) --deck (\\S+)",
                "deck rm-card( --side)? --deck (\\S+) --card (.+)",
                "deck show --all",
                "deck show --deck-name (\\S+)( --side)?",
                "deck show( --side)? --deck-name (\\S+)",
                "deck show --cards",
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
            if (functionNumber == 0) createDeck(matcher.group(1));
            else if (functionNumber == 1) deleteDeck(matcher.group(1));
            else if (functionNumber == 2) setActiveDeck(matcher.group(1));
            else if (functionNumber == 3) addCardToDeck(matcher.group(1), matcher.group(2), matcher.group(3));
            else if (functionNumber == 4) addCardToDeck(matcher.group(1), matcher.group(3), matcher.group(2));
            else if (functionNumber == 5) addCardToDeck(matcher.group(2), matcher.group(1), matcher.group(3));
            else if (functionNumber == 6) addCardToDeck(matcher.group(3), matcher.group(1), matcher.group(2));
            else if (functionNumber == 7) addCardToDeck(matcher.group(2), matcher.group(3), matcher.group(1));
            else if (functionNumber == 8) addCardToDeck(matcher.group(3), matcher.group(2), matcher.group(1));
            else if (functionNumber == 9) removeCardFromDeck(matcher.group(1), matcher.group(2), matcher.group(3));
            else if (functionNumber == 10) removeCardFromDeck(matcher.group(1), matcher.group(3), matcher.group(2));
            else if (functionNumber == 11) removeCardFromDeck(matcher.group(2), matcher.group(1), matcher.group(3));
            else if (functionNumber == 12) removeCardFromDeck(matcher.group(3), matcher.group(1), matcher.group(2));
            else if (functionNumber == 13) removeCardFromDeck(matcher.group(2), matcher.group(3), matcher.group(1));
            else if (functionNumber == 14) removeCardFromDeck(matcher.group(3), matcher.group(2), matcher.group(1));
            else if (functionNumber == 15) DeckPageController.getInstance().showUserDecks();
            else if (functionNumber == 16) showDeck(matcher.group(1), matcher.group(2));
            else if (functionNumber == 17) showDeck(matcher.group(2), matcher.group(1));
            else if (functionNumber == 18) DeckPageController.getInstance().showAllCards();
            else if (functionNumber == 19) enterMenu(matcher.group(1));
            else if (functionNumber == 20) exitMenu();
            isCommandValid = true;
        }
    }

    private void showDeck(String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().showMainOrSideDeckByName(deckName, isSide);
    }

    private void removeCardFromDeck(String cardName, String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().removeCardFromDeck(cardName, deckName, isSide);
        System.out.println(message);
    }
}
