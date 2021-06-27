package view;

import controller.DuelPageController;
import controller.AI;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelPage extends Page {
    public static DuelPageController duelPageController;

    protected int numberOfRounds;
    protected String firstPlayerUsername;
    protected String secondPlayerUsername;

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public String getFirstPlayerUsername() {
        return firstPlayerUsername;
    }

    public void setFirstPlayerUsername(String firstPlayerUsername) {
        this.firstPlayerUsername = firstPlayerUsername;
    }

    public String getSecondPlayerUsername() {
        return secondPlayerUsername;
    }

    public void setSecondPlayerUsername(String secondPlayerUsername) {
        this.secondPlayerUsername = secondPlayerUsername;
    }

    public DuelPage() {
        duelPageController = DuelPageController.getInstance();
    }

    public void runDuelPage(String command) {
        String[] commandPatterns = {"^select -d$", "^select .*", "^next phase$", "^flip-summon$", "^set -- position (attack|defense)$", "^set$",
                "^summon$", "^attack (1|2|3|4|5)$", "^attack direct$", "^activate effect$", "^show graveyard( --opponent)?$", "^card show --selected$",
                "^menu enter (\\S+)$", "^menu exit$", "^increase --LP (\\d+)$", "^surrender$", "^duel set-winner (\\S+)$",
                "change turn", "activate action"};

        isCommandValid = false;
        for (functionNumber = 0; functionNumber < commandPatterns.length && !isCommandValid; functionNumber++) {
            getCommandMatcher(command, commandPatterns[functionNumber]);
        }

        if (!isCommandValid) System.out.println("invalid command");
        DuelPageController.getInstance().roundWinChecker();
    }

    public void phaseWork() {
        if (DuelPageController.getInstance().getPhaseNumber() == 0)
            for (int i = 0; i < 3; i++)
                work();
        else work();
    }

    private void work() {
        if (DuelPageController.getInstance().getPhaseNumber() == 0) DuelPageController.getInstance().drawPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 1)
            DuelPageController.getInstance().standbyPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 2 ||
                DuelPageController.getInstance().getPhaseNumber() == 4) DuelPageController.getInstance().mainPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 3) DuelPageController.getInstance().battlePhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 5) DuelPageController.getInstance().endPhase();
    }

    public void getCommandMatcher(String command, String commandPattern) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (functionNumber == 0) duelPageController.deselectCard();
            else if (functionNumber == 1) selectCard(command);
            else if (functionNumber == 2) duelPageController.nextPhase();
            else if (functionNumber == 3) duelPageController.flipSummon();
            else if (functionNumber == 4) duelPageController.setPosition(matcher);
            else if (functionNumber == 5) duelPageController.set();
            else if (functionNumber == 6) duelPageController.summonCard();
            else if (functionNumber == 7 && DuelPageController.getInstance().isCanAttack())
                duelPageController.attack(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 8 && DuelPageController.getInstance().isCanAttack())
                duelPageController.directAttack();
            else if (functionNumber == 9) duelPageController.activateEffect();
            else if (functionNumber == 10) duelPageController.showGraveyard(matcher.group(1));
            else if (functionNumber == 11) duelPageController.showSelectedCard();
            else if (functionNumber == 12) enterMenu(matcher.group(1));
            else if (functionNumber == 13) exitMenu();
            else if (functionNumber == 14) duelPageController.increaseLifePoint(Integer.parseInt(matcher.group(1)));
            else if (functionNumber == 15) duelPageController.surrender();
            else if (functionNumber == 16) duelPageController.setWinner(matcher.group(1));
            else if (functionNumber == 17) duelPageController.changeTurn();
            else if (functionNumber == 18) duelPageController.callMonsterActions();
            isCommandValid = true;
        }
    }

    public static int getTributeMonsterNumberFromPlayer() {
        if (DuelPageController.getInstance().getCurrentTurnUsername().equals("AI"))
            return AI.getTributeNumber();
        return scanner.nextInt();
    }

    public void setUsernameId(String username) {

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

    private static void selectCard(String selectCardCommand) {
        boolean isAddressValid = false;
        String[] selectCardCommandPatterns = {"^select --monster (\\d+)$",
                "^select --spell (\\d+)$",
                "^select (--monster --opponent|--opponent --monster) (\\d+)$",
                "^select (--spell --opponent|--opponent --spell) (\\d+)$",
                "^select --field$",
                "^select (--field --opponent|--opponent --field)$",
                "^select --hand (\\d+)$"};
        for (int i = 0; i < selectCardCommandPatterns.length; i++) {
            Pattern pattern = Pattern.compile(selectCardCommandPatterns[i]);
            Matcher matcher = pattern.matcher(selectCardCommand);

            if (matcher.find()) {
                isAddressValid = true;
                if (i == 0) duelPageController.selectMyMonsterCard(Integer.parseInt(matcher.group(1)));
                else if (i == 1) duelPageController.selectMySpellCard(Integer.parseInt(matcher.group(1)));
                else if (i == 2) duelPageController.selectOpponentMonsterCard(Integer.parseInt(matcher.group(2)));
                else if (i == 3) duelPageController.selectOpponentMonsterCard(Integer.parseInt(matcher.group(2)));
                else if (i == 4) duelPageController.selectMyFieldCard();
                else if (i == 5) duelPageController.selectOpponentFieldCard();
                else duelPageController.selectInHandCard(Integer.parseInt(matcher.group(1)));
                break;
            }
        }
        if (!isAddressValid) System.out.println("invalid selection");
    }
}
