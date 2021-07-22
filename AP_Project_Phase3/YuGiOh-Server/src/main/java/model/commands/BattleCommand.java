package model.commands;

import controller.Controller;
import controller.DuelPageController;
import model.Board;
import model.User;

public class BattleCommand extends CommandClass {

    private BattleCommandsType battleCommandType;
    private User user;
    private User opponent;
    private User applicatorUser;
    private String currentTurnUsername;
    private int selectedMyMonsterCardNumber;
    private int selectedOpponentMonsterCardNumber;
    private int selectedMySpellOrTrapCardNumber;
    private int SelectedOpponentSpellOrTrapCardNumber;
    private int toBeAttackedCardNumber;
    private int selectedCardNumberInHand;
    private int numberOfRounds;
    private boolean firstPlayer;
    private String usernameToRequest;
    private Board userBoard;
    private Board opponentBoard;


    public BattleCommand() {
        super(CommandType.BATTLE);
    }


    public void set(int selectedCardNumberInHand, User user) {
        this.battleCommandType = BattleCommandsType.SET;
        this.selectedCardNumberInHand = selectedCardNumberInHand;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedCardNumberInHand: " + selectedCardNumberInHand);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }


    public void activate(int selectedCardNumberInHand, int selectedMySpellOrTrapCardNumber, User user) {
        this.battleCommandType = BattleCommandsType.ACTIVATE;
        this.selectedCardNumberInHand = selectedCardNumberInHand;
        this.selectedMySpellOrTrapCardNumber = selectedMySpellOrTrapCardNumber;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedCardNumberInHand: " + selectedCardNumberInHand);
        System.out.println("selectedMySpellOrTrapCardNumber: " + selectedMySpellOrTrapCardNumber);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }


    public void endTurn(User user) {
        this.battleCommandType = BattleCommandsType.END_TURN;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }


    public void nextPhase(User user) {
        this.battleCommandType = BattleCommandsType.NEXT_PHASE;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }


    public void directAttack(int selectedMyMonsterCardNumber, User user) {
        this.battleCommandType = BattleCommandsType.DIRECT_ATTACK;
        this.selectedMyMonsterCardNumber =selectedMyMonsterCardNumber;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedMyMonsterCardNumber: " + selectedMyMonsterCardNumber);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }

    public void attack(int selectedMyMonsterCardNumber, int toBeAttackedCardNumber, User user) {
        this.battleCommandType = BattleCommandsType.ATTACK;
        this.selectedMyMonsterCardNumber =selectedMyMonsterCardNumber;
        this.toBeAttackedCardNumber = toBeAttackedCardNumber;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedCardNumberInHand: " + selectedCardNumberInHand);
        System.out.println("toBeAttackedCardNumber: " + toBeAttackedCardNumber);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }

    public void flipSummon(int selectedMyMonsterCardNumber, User user) {
        this.battleCommandType = BattleCommandsType.FLIP_SUMMON;
        this.selectedMyMonsterCardNumber = selectedMyMonsterCardNumber;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedCardNumberInHand: " + selectedCardNumberInHand);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();
    }


    public void changePosition(int selectedMyMonsterCardNumber, User user) {
        this.battleCommandType = BattleCommandsType.CHANGE_POSITION;
        this.selectedMyMonsterCardNumber = selectedMyMonsterCardNumber;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedMyMonsterCardNumber: " + selectedMyMonsterCardNumber);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();

    }

    public void summon(int selectedCardNumberInHand, User user) {
        this.battleCommandType = BattleCommandsType.SUMMON;
        this.selectedCardNumberInHand = selectedCardNumberInHand;
        this.user = user;
        this.opponent = Controller.currentOpponent;
        System.out.println("selectedCardNumberInHand: " + selectedCardNumberInHand);
        this.userBoard = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
        this.opponentBoard = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard();

    }


    public void startBattle(String usernameToRequest, User user) {
        this.usernameToRequest = usernameToRequest;
        this.battleCommandType = BattleCommandsType.START_BATTLE;
        this.user = user;
        this.applicatorUser = user;
    }

    public void cancelSentRequest(String usernameToRequest, User user) {
        this.usernameToRequest = usernameToRequest;
        this.battleCommandType = BattleCommandsType.CANCEL_SENT_REQUEST;
        this.user = user;
        this.applicatorUser = user;
    }

//    public void acceptRequest(User opponent, boolean firstPlayer) {
//        this.battleCommandType = BattleCommandsType.ACCEPT_REQUEST;
//        this.opponent = opponent;
//        this.firstPlayer = firstPlayer;
//    }


    public BattleCommandsType getBattleCommandType() {
        return battleCommandType;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponent() {
        return opponent;
    }

    public void setOpponent(User opponent) {
        this.opponent = opponent;
    }

    public int getSelectedMyMonsterCardNumber() {
        return selectedMyMonsterCardNumber;
    }

    public void setSelectedMyMonsterCardNumber(int selectedMyMonsterCardNumber) {
        this.selectedMyMonsterCardNumber = selectedMyMonsterCardNumber;
    }

    public int getSelectedOpponentMonsterCardNumber() {
        return selectedOpponentMonsterCardNumber;
    }

    public void setSelectedOpponentMonsterCardNumber(int selectedOpponentMonsterCardNumber) {
        this.selectedOpponentMonsterCardNumber = selectedOpponentMonsterCardNumber;
    }

    public int getSelectedMySpellOrTrapCardNumber() {
        return selectedMySpellOrTrapCardNumber;
    }

    public void setSelectedMySpellOrTrapCardNumber(int selectedMySpellOrTrapCardNumber) {
        this.selectedMySpellOrTrapCardNumber = selectedMySpellOrTrapCardNumber;
    }

    public int getSelectedOpponentSpellOrTrapCardNumber() {
        return SelectedOpponentSpellOrTrapCardNumber;
    }

    public void setSelectedOpponentSpellOrTrapCardNumber(int selectedOpponentSpellOrTrapCardNumber) {
        SelectedOpponentSpellOrTrapCardNumber = selectedOpponentSpellOrTrapCardNumber;
    }

    public int getToBeAttackedCardNumber() {
        return toBeAttackedCardNumber;
    }

    public void setToBeAttackedCardNumber(int toBeAttackedCardNumber) {
        this.toBeAttackedCardNumber = toBeAttackedCardNumber;
    }

    public int getSelectedCardNumberInHand() {
        return selectedCardNumberInHand;
    }

    public void setSelectedCardNumberInHand(int selectedCardNumberInHand) {
        this.selectedCardNumberInHand = selectedCardNumberInHand;
    }

    public String getCurrentTurnUsername() {
        return currentTurnUsername;
    }

    public void setCurrentTurnUsername(String currentTurnUsername) {
        this.currentTurnUsername = currentTurnUsername;
    }

    public String getUsernameToRequest() {
        return usernameToRequest;
    }

    public void setUsernameToRequest(String usernameToRequest) {
        this.usernameToRequest = usernameToRequest;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public User getApplicatorUser() {
        return applicatorUser;
    }

    public void setApplicatorUser(User applicatorUser) {
        this.applicatorUser = applicatorUser;
    }

    public void acceptRequest(User user) {
        this.battleCommandType = BattleCommandsType.ACCEPT_REQUEST;
        this.opponent = user;
    }

    public void cancelRequest() {
        this.battleCommandType = BattleCommandsType.CANCEL_REQUEST;
    }

    public Board getUserBoard() {
        return userBoard;
    }

    public void setUserBoard(Board userBoard) {
        this.userBoard = userBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }
}
