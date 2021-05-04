package view;

import controller.DuelPageController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelPage extends Page {
    private static boolean isFinished = false;

    @Override
    public void run() {
        String command;
        String[] commandPatterns = {"^\\s*select.*\\s*$"};

        while (!isFinished) {
            winChecker();
            command = Page.scanner.nextLine();
            isCommandValid = false;

            for (functionCounter = 0; functionCounter < commandPatterns.length && !isCommandValid; functionCounter++) {
                getCommandMatcher(command, commandPatterns[functionCounter]);
            }

            if (!isCommandValid) System.out.println("invalid command");
        }
    }

    private void winChecker() {
    }

    public void setUsernameId(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    @Override
    public void getCommandMatcher(String command, String commandPattern) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (functionCounter == 0) selectCard(command);
            else if (functionCounter == 1)
            isCommandValid = true;
        }
    }

    private void selectCard(String selectCardCommand) {
        String[] selectCardCommandPatterns = {"^select --(monster|spell) (\\d+)$",
                "^select (--(monster|spell) --opponent|--opponent --(monster|spell)) \\d+$",
                "^select --field$",
                "^select (--field --opponent|--opponent --field)$",
                "^select --hand \\d+$"};
        boolean isCardAddressValid = false;
        for (int i = 0; i < selectCardCommandPatterns.length; i++) {
            Pattern pattern = Pattern.compile(selectCardCommandPatterns[i]);
            Matcher matcher = pattern.matcher(selectCardCommand);
            DuelPageController duelPageController = DuelPageController.getInstance();
            if (matcher.find()) {
                if (functionCounter == 0) duelPageController.selectMyCard(matcher);
                else if (functionCounter == 1) duelPageController.selectMyFieldCard();
                else if (functionCounter == 2) duelPageController.selectOpponentCard(matcher);
                else if (functionCounter == 3) duelPageController.selectOpponentFieldCard();
                else if (functionCounter == 4) duelPageController.selectInHandCard(matcher);
            }
        }
    }


    public void enterMenu() {

    }

    public void exitMenu() {

    }

    public void showCurrentMenu() {

    }

    private void selectMyCard(Matcher matcher) {

    }


    private void deselectCard(Matcher matcher) {

    }

    private void addCardToDeck(Matcher matcher) {

    }

    private void summon() {

    }

    private void set() {

    }

    private void setPosition(Matcher matcher) {

    }

    private void flipSummon(Matcher matcher) {

    }

    private void attack(Matcher matcher) {

    }

    private void directAttack(Matcher matcher) {

    }

    private void activateEffect(Matcher matcher) {

    }

    private void setSpell(Matcher matcher) {

    }

    private void setTrap(Matcher matcher) {

    }

    private void showGraveYard(Matcher matcher) {

    }

    private void back() {

    }

    private void showSelectedCard(Matcher matcher) {

    }

    private void surrender() {

    }
}
