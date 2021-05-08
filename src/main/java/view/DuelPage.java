﻿package view;

import controller.DuelPageController;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static view.Page.scanner;

public class DuelPage extends Page {
    public static DuelPageController duelPageController;

    public DuelPage() {
        duelPageController = DuelPageController.getInstance();
    }

    public static void run() {
        String command;
        String[] commandPatterns = {"select -d", "select .*", "next phase", "summon", "set", "set -- position (attack|defense)",
                "flip-summon", "attack (1|2|3|4|5)", "attack direct", "activate effect"};
        while (true) {
            command = scanner.nextLine();
            isCommandValid = false;
            for (functionNumber = 0; functionNumber < commandPatterns.length && !isCommandValid; functionNumber++) {
                getCommandMatcher(command, commandPatterns[functionNumber]);
            }

            if (!isCommandValid) System.out.println("invalid command");
        }
    }



    public static void getCommandMatcher(String command, String commandPattern) {
        Pattern pattern = Pattern.compile(commandPattern);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            if (functionNumber == 0) duelPageController.deselectCard();
            else if (functionNumber == 1) selectCard(command);
            else if (functionNumber == 2) duelPageController.nextPhase();
            else if (functionNumber == 3) duelPageController.summonCard();
            else if (functionNumber == 4) duelPageController.set();
            else if (functionNumber == 5) duelPageController.setPosition(matcher);
            else if (functionNumber == 6) duelPageController.flipSummon();
            else if (functionNumber == 7) duelPageController.attack(matcher);
            else if (functionNumber == 8) duelPageController.directAttack();
            else if (functionNumber == 9) duelPageController.activateEffect();
            isCommandValid = true;
        }
    }

    public static int getTributeMonsterNumberFromPlayer() {
        return scanner.nextInt();
    }

    public void setUsernameId(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu() {

    }

    public void exitMenu() {

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
                User.getUserByUsername(DuelPageController.getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
                if (functionNumber == 0) duelPageController.selectMyMonsterCard(matcher);
                else if (functionNumber == 1) duelPageController.selectMySpellCard(matcher);
                else if (functionNumber == 2) duelPageController.selectOpponentMonsterCard(matcher);
                else if (functionNumber == 3) duelPageController.selectOpponentMonsterCard(matcher);
                else if (functionNumber == 4) duelPageController.selectMyFieldCard();
                else if (functionNumber == 5) duelPageController.selectOpponentFieldCard();
                else if (functionNumber == 6) duelPageController.selectInHandCard(matcher);
                break;
            }
        }
        if (!isAddressValid) System.out.println("invalid selection");
    }

    private void addCardToDeck(Matcher matcher)
    {

    }

    private void set ()
    {

    }

    private void setPosition (Matcher matcher)
    {

    }

    private void flipSummon (Matcher matcher)
    {

    }

    private void attack (Matcher matcher)
    {

    }

    private void directAttack (Matcher matcher)
    {

    }

    private void activateEffect ()
    {

    }

    private void setSpell (Matcher matcher)
    {

    }

    private void setTrap (Matcher matcher)
    {

    }

    private void showGraveYard (Matcher matcher)
    {

    }

    private void back ()
    {

    }

    private void showSelectedCard (Matcher matcher)
    {

    }

    private void surrender ()
    {

    }
}
