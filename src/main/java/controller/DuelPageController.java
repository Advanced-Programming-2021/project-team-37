package controller;

import model.*;
import view.Phase;

class DuelPageController extends Controller {
    static private String currentTurnUsername;
    static private String opponentUsername; // this is the player who is not his turn
    static private String firstPlayerUsername;
    static private String secondPlayerUsername;
    private Phase currentPhase;
    public String gameMode;


    public void updateAndShowGameBoard() {

    }

    public Monster selectMonsterCard(String command) {

    }

    public SpellAndTrap selectSpellAndTrapCard(String command) {

    }

    public void resetSelectedCard() {

    }

    public void startGame() {

    }

    public void drawCard() {

    }

    public void setCard() {

    }

    public void summonCard() {

    }

    public void changePhase() {

    }

    public void attack(String command) {

    }

    public void deffend() {

    }

    public void selectCard(String command) {

    }

    public void setPossition(String command) {

    }

    public void flipSummon() {

    }

    public void ritualSummon() {

    }

    public void makeTribute() {

    }

    public void dealDamage() {

    }

    public void directAttack(MonsterCard card) {

    }

    public viod activateSpellCard(SpellAndTrapCard card) {

    }

    public void attackMonster(MonsterCard card1, MonsterCard card2) {

    }

    public void setMonsterCard(MonsterCard card, String possition) {

    }

    public void setSpellCard(MonsterCard card, String possition) {

    }

    public void setTrapCard() {

    }

    public void specialSummon() {

    }

    public void showGraveyard() {

    }

    public void showOpponentGraveYard() {

    }

    public void showCard() {

    }

    public void finishGame() {

    }

    public void deselectCard() {

    }

    public void surrender() {

    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void getCommandMatcher(String command, String commandPattern) {

    }

    public void enterMenu() {

    }

    public void exitMenu() {

    }

    public void showCurrentMenu() {

    }

    public void run() {

    }

    public static String getCurrentTurnUsername() {
        return currentTurnUsername;
    }

    public static void setCurrentTurnUsername(String currentTurnUsername) {
        DuelPageController.currentTurnUsername = currentTurnUsername;
    }

    public static String getOpponentUsername() {
        return opponentUsername;
    }

    public static void setOpponentUsername(String opponentUsername) {
        DuelPageController.opponentUsername = opponentUsername;
    }

    public static String getFirstPlayerUsername() {
        return firstPlayerUsername;
    }

    public static void setFirstPlayerUsername(String firstPlayerUsername) {
        DuelPageController.firstPlayerUsername = firstPlayerUsername;
    }

    public static String getSecondPlayerUsername() {
        return secondPlayerUsername;
    }

    public static void setSecondPlayerUsername(String secondPlayerUsername) {
        DuelPageController.secondPlayerUsername = secondPlayerUsername;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    private void showGameBoard() {
        showOpponentGameBoard();
        showCurrentPlayerGameBoard();
    }

    private void showCurrentPlayerGameBoard() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard() == null) System.out.print("E");
        else System.out.print("O");
        for (int i = 0; i < 6; i++) {
            System.out.print("  ");
        }
        System.out.print(User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().size());

        for (int i = 5; i >= 0; i--) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null)
                System.out.print("E");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.ATTACK)
                System.out.print("OO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.FACE_UP_DEFENCE)
                System.out.print("DO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.FACE_DOWN)
                System.out.print("DH");
            System.out.println("    ");
        }

        for (int i = 5; i >= 0; i--) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null)
                System.out.print("E");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].getSpellOrTrapCardState() == SpellOrTrapCardState.FACE_UP)
                System.out.print("O");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].getSpellOrTrapCardState() == SpellOrTrapCardState.FACE_DOWN)
                System.out.print("H");
        }

        for (int i = 0; i < 6; i++) {
            System.out.print("  ");
        }

        System.out.println(User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().size());

        for (int i = 0; i < User.getUserByUsername().getBoard().getInHandCards().size(); i++) {
            System.out.print("c "); //TODO after c is a tab solve this if is not correct
        }

        System.out.println("<" + User.getUserByUsername(currentTurnUsername).getNickname() + ">:<"
                + User.getUserByUsername(currentTurnUsername).getLifePoints() + ">");
    }

    private void showOpponentGameBoard() {
        System.out.println("<" + User.getUserByUsername(opponentUsername).getNickname() + ">:<"
                + User.getUserByUsername(opponentUsername).getLifePoints() + ">");
        for (int i = 0; i < User.getUserByUsername().getBoard().getInHandCards().size(); i++) {
            System.out.print("    c");
        }
        System.out.println(User.getUserByUsername(opponentUsername).getBoard().getMainDeckCards().size());

        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i] == null) System.out.print("E");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getSpellOrTrapCardState() == SpellOrTrapCardState.FACE_UP)
                System.out.print("O");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getSpellOrTrapCardState() == SpellOrTrapCardState.FACE_DOWN)
                System.out.print("H");
        }

        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i] == null) System.out.print("E");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.ATTACK)
                System.out.print("OO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.FACE_UP_DEFENCE)
                System.out.print("DO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getMonsterCardState() == MonsterCardState.FACE_DOWN)
                System.out.print("DH");
            System.out.println("    ");
        }

        System.out.print(User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().size());
        for (int i = 0; i < 6; i++) {
            System.out.print("  ");
        }
        if (User.getUserByUsername(opponentUsername).getBoard().getFieldCard() == null) System.out.print("E");
        else System.out.print("O");
    }
}
