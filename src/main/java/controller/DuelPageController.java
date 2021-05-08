﻿package controller;

import model.*;
import view.DuelPage;
import view.Phase;

import java.util.regex.Matcher;

public class DuelPageController extends Controller {
    public static String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase", "main phase 2", "end phase"};
    public int phaseNumber;
    public static int[] cardsOrders = {5, 3, 1, 2, 4};
    static private String currentTurnUsername;
    static private DuelPageController instance;
    static private String opponentUsername; // this is the player who is not his turn
    static private String firstPlayerUsername;
    static private String secondPlayerUsername;
    //private Phase currentPhase;         // I think there is no need to this in my code
    public String gameMode;

    public static String[] getPhaseNames() {
        return phaseNames;
    }

    public static void setPhaseNames(String[] phaseNames) {
        DuelPageController.phaseNames = phaseNames;
    }

    public int getPhaseNumber() {
        return phaseNumber;
    }

    public void setPhaseNumber(int phaseNumber) {
        this.phaseNumber = phaseNumber;
    }

    public static int[] getCardsOrders() {
        return cardsOrders;
    }

    public static void setCardsOrders(int[] cardsOrders) {
        DuelPageController.cardsOrders = cardsOrders;
    }

    /**
     * following function called from selectCard in duelPage
     */

    public void selectMyMonsterCard(Matcher matcher) {
        int monsterNumber = Integer.parseInt(matcher.group(1));
        if (monsterNumber <= 5 && monsterNumber >= 1) {
            if (!(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[monsterNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[monsterNumber].setCardSelected(true);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMyMonsterCardNumber(monsterNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_MONSTER);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectMySpellCard(Matcher matcher) {
        int spellNumber = Integer.parseInt(matcher.group(1));
        if (spellNumber <= 5 && spellNumber >= 1) {
            if (!(User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[spellNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[spellNumber].setCardSelected(true);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMySpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMySpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_SPELL);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectMyFieldCard() {
        if (!(User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard() == null)) {
            User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard().setCardSelected(true);
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_FIELD_CARD);
        } else System.out.println("no card found in the given position");
    }

    public void selectOpponentMonsterCard(Matcher matcher) {
        int monsterNumber = Integer.parseInt(matcher.group(2));
        if (monsterNumber <= 5 && monsterNumber >= 0) {
            if (!(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[monsterNumber] == null)) {
                User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[monsterNumber].setCardSelected(true);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedOpponentMonsterCardNumber(monsterNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_MONSTER);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectOpponentSpellCard(Matcher matcher) {
        int spellNumber = Integer.parseInt(matcher.group(2));
        if (spellNumber <= 5 && spellNumber >= 0) {
            if (!(User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[spellNumber] == null)) {
                User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[spellNumber].setCardSelected(true);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedOpponentSpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_SPELL);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectOpponentFieldCard() {
        if (!(User.getUserByUsername(opponentUsername).getBoard().getFieldCard() == null)) {
            User.getUserByUsername(opponentUsername).getBoard().getFieldCard().setCardSelected(true);
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_FIELD_CARD);
        } else System.out.println("no card found in the given position");
    }

    public void selectInHandCard(Matcher matcher) {
        int inHandCardNumber = Integer.parseInt(matcher.group(1));
        if (inHandCardNumber > User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().size())
            System.out.println("invalid selection");
        else {
            User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(inHandCardNumber - 1).setCardSelected(true);
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardNumberInHand(inHandCardNumber - 1);
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.IN_HAND);
        }
    }

    /**
     * till here
     */

    public void updateAndShowGameBoard() {

    }

    public void nextPhase() {
        phaseNumber++;
        System.out.println(phaseNames[phaseNumber]);
        if (phaseNumber == 5) {
            System.out.println("its " + opponentUsername + "’s turn");
            changeTurn();
        }
    }

    private void changeTurn() {
        phaseNumber = 0;
        String tempUsername;
        tempUsername = currentTurnUsername;
        currentTurnUsername = opponentUsername;
        opponentUsername = tempUsername;
        User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(false);
        User.getUserByUsername(opponentUsername).setCardSummonedOrSetInThisTurn(false);
        //TODO reset User data before change turn ( cardPosition )
    }

    public void resetSelectedCard() {

    }

    public void startGame() {

    }

    public void drawCard() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().size() > 0) {
            User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().get(0));
            System.out.println("new card added to the hand : " + User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().get(0).getCardName());
            User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().remove(0);
        } else System.out.println(); //TODO this player is loser
    }

    public void setPosition(Matcher matcher) {
        String toBeChangedPosition = matcher.group(1);
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if ((User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].getCardState() == CardState.DO &&
                            toBeChangedPosition.equals("defence")) || (toBeChangedPosition.equals("attack") &&
                            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].getCardState() == CardState.OO)) {
                        if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].isCardSetPositionInThisTurn()) {
                            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].setCardSetPositionInThisTurn(true);
                            System.out.println("monster card position changed successfully");
                            if (toBeChangedPosition.equals("defence"))
                                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].setCardState(CardState.DO);
                            else
                                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].setCardState(CardState.OO);
                        } else System.out.println("you already changed this card position in this turn");
                    } else System.out.println("this card is already in the wanted position");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t change this card position");
        } else System.out.println("no card is selected yet");
    }

    public void flipSummon() {
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].getCardState() == CardState.DH &&
                            !User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].isCardSetInThisTurn()) {
                        User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].setCardState(CardState.OO);
                        System.out.println("flip summoned successfully");
                    } else System.out.println("you can’t flip summon this card");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t change this card position");
        } else System.out.println("no card is selected yet");
    }

    public void activateEffect() {
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        int selectedMySpellOrTrapCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMySpellOrTrapCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_SPELL ||
                    (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND &&
                            User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof SpellAndTrap)) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_SPELL &&
                            User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[selectedMySpellOrTrapCardNumber].getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                        System.out.println("you have already activated this card");
                    } else {
                        if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND &&
                                howManySpellFieldAreOccupied(currentTurnUsername) == 5) { // TODO see picture TODO1 in notes
                            System.out.println("spell card zone is full");
                        } else {
                            if (true) { // TODO see picture TODO2 in notes
                                System.out.println("preparations of this spell are not done yet");
                            } else {
                                System.out.println("spellActivated");
                                // TODO put spell on board
                            }
                        }
                    }
                } else System.out.println("you can’t activate an effect on this turn"); // todo I think in this message turn should replace with phase
            } else System.out.println("activate effect is only for spell cards.");
        } else System.out.println("no card is selected yet");
    }

    public void directAttack() {
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER) {
                if (phaseNames[phaseNumber].equals("battle phase")) {
                    if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].isCardAlreadyAttackedInThisTurn()) {
                        int occupiedMonsterFieldNumber = howManyMonsterFieldAreOccupied(opponentUsername);
                        if (occupiedMonsterFieldNumber == 0) {
                            int damage = ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]).getAttack();
                            System.out.println("your opponent receives " + damage + " battle damage"); // todo this output is wrong in pdf
                        } else System.out.println("you can’t attack the opponent directly");
                    } else System.out.println("this card already attacked");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t attack with this card");
        } else System.out.println("no card is selected yet");

    }

    /**
     * from here
     */

    public void attack(Matcher matcher) {
        int toBeAttackedCardNumber = Integer.parseInt(matcher.group(1));
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER &&
                    User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].getCardState() == CardState.OO) { // todo this condition is not in phase 1 pdf but I think it is necessary here
                if (phaseNames[phaseNumber].equals("battle phase")) {
                    if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].isCardAlreadyAttackedInThisTurn()) {
                        if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] != null) {
                            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].getCardState() == CardState.OO) {
                                attackOOCard(toBeAttackedCardNumber, selectedMonsterCardNumber);
                            } else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].getCardState() == CardState.DO) {
                                attackDOOrDHCard(toBeAttackedCardNumber, selectedMonsterCardNumber);
                            } else {
                                System.out.println("opponent’s monster card was " + User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].getCardName() + " and ");
                                attackDOOrDHCard(toBeAttackedCardNumber, selectedMonsterCardNumber);
                            }
                        } else System.out.println("there is no card to attack here");
                    } else System.out.println("this card already attacked");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t attack with this card");
        } else System.out.println("no card is selected yet");
    }

    private void attackOOCard(int toBeAttackedCardNumber, int selectedMonsterCardNumber) {
        int damage = ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]).getAttack() -
                ((Monster) User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]).getAttack();
        if (damage > 0) {
            System.out.println("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
            User.getUserByUsername(opponentUsername).setLifePoints(User.getUserByUsername(opponentUsername).getLifePoints() - damage);
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
        } else if (damage == 0) {
            System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
            User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber] = null;
        } else { // damage < 0
            System.out.println("Your monster card is destroyed and you received " + ((-1) * damage) + " battle damage");
            User.getUserByUsername(currentTurnUsername).setLifePoints(User.getUserByUsername(currentTurnUsername).getLifePoints() + damage);
            User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber] = null;
        }
    }

    private void attackDOOrDHCard(int toBeAttackedCardNumber, int selectedMonsterCardNumber) {
        int attackPower = ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]).getAttack() -
                ((Monster) User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]).getAttack();
        if (attackPower > 0) {
            System.out.println("the defense position monster is destroyed");
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
        } else if (attackPower == 0) {
            System.out.println("no card is destroyed");
        } else { // attackPower < 0
            System.out.println("no card is destroyed and you received " + attackPower + " battle damage");
            User.getUserByUsername(currentTurnUsername).setLifePoints(User.getUserByUsername(currentTurnUsername).getLifePoints() + attackPower);
        }
    }

    /**
     * till here functions are related to attack function
     */

    /**
     * from here
     */

    public void summonCard() {
        int occupiedMonsterFieldNumber = howManyMonsterFieldAreOccupied(currentTurnUsername);
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Monster &&
                    User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (occupiedMonsterFieldNumber < 5) {
                        if (!User.getUserByUsername(currentTurnUsername).isCardSummonedOrSetInThisTurn()) {
                            if (((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand)).getLevel() <= 4) {
                                System.out.println("summoned successfully");
                                User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                            } else {
                                if (((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand)).getLevel() == 5 ||
                                        ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand)).getLevel() == 6) {
                                    summonLevelFiveOrSixMonster(occupiedMonsterFieldNumber);
                                } else {
                                    summonLevelSevenOrMoreMonster(occupiedMonsterFieldNumber);
                                }
                            }
                        } else System.out.println("you already summoned/set on this turn");
                    } else System.out.println("monster card zone is full");
                } else System.out.println("action not allowed in this phase");
            } else System.out.println("you can’t summon this card");
        } else System.out.println("no card is selected yet");
    }

    private void summonLevelSevenOrMoreMonster(int occupiedMonsterFieldNumber) {
        if (occupiedMonsterFieldNumber >= 1) {
            int firstTributeNumber = DuelPage.getTributeMonsterNumberFromPlayer();
            int secondTributeNumber = DuelPage.getTributeMonsterNumberFromPlayer();
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[firstTributeNumber] == null ||
                    User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[secondTributeNumber] == null) {
                System.out.println("there is no monster on one of these addresses");
            } else {
                System.out.println("summoned successfully");
                User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                int selectedCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
                User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[firstTributeNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[secondTributeNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[firstTributeNumber] = null;
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[secondTributeNumber] = null;
                putSummonedCardOnBoard(selectedCardNumber);
            }
        } else System.out.println("there are not enough cards for tribute");
    }

    private void putSummonedCardOnBoard(int selectedCardNumber) {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null) {
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] = User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardState(CardState.OO); //todo check if it is true
                break;
            }
        }
    }

    private void summonLevelFiveOrSixMonster(int occupiedMonsterFieldNumber) {
        if (occupiedMonsterFieldNumber >= 1) {
            int tributeNumber = DuelPage.getTributeMonsterNumberFromPlayer();
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[tributeNumber] == null) {
                System.out.println("there no monsters one this address"); //todo I think this message is wrong in pdf
            } else {
                System.out.println("summoned successfully");
                User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                int selectedCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
                User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[tributeNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[tributeNumber] = null;
                putSummonedCardOnBoard(selectedCardNumber);
            }
        } else System.out.println("there are not enough cards for tribute");
    }

    /**
     * till here functions are related to summonCard function
     */

    public void set() {
        int occupiedMonsterFieldNumber = howManyMonsterFieldAreOccupied(currentTurnUsername);
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (occupiedMonsterFieldNumber < 5) {
                        if (!User.getUserByUsername(currentTurnUsername).isCardSummonedOrSetInThisTurn()) {
                            System.out.println("set successfully");
                            User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                            putSetCardOnBoard(selectedCardNumberInHand);
                        } else System.out.println("you already summoned/set on this turn");
                    } else System.out.println("monster card zone is full");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t set this card");
        } else System.out.println("no card is selected yet");
    }

    private void putSetCardOnBoard(int selectedCardNumber) {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null) {
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] = User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardState(CardState.DH);
                break;
            }
        }
    }

    private int getFirstFreeMonsterFieldNumber() {
        int i;
        for (i = 0; i < 5; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null) break;
        }
        return i;
    }

    private int howManyMonsterFieldAreOccupied(String username) {
        int numberOfOccupiedMonsterField = 0;
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(username).getBoard().getMonsterCards()[i] != null)
                numberOfOccupiedMonsterField++;
        }
        return numberOfOccupiedMonsterField;
    }

    private int howManySpellFieldAreOccupied(String username) {
        int numberOfOccupiedSpellField = 0;
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(username).getBoard().getSpellOrTrapCards()[i] != null)
                numberOfOccupiedSpellField++;
        }
        return numberOfOccupiedSpellField;
    }

    public void changePhase() {

    }

    public static void winChecker() {
    }

    public void defend() {

    }

    public void ritualSummon() {

    }

    public void makeTribute() {

    }

    public void dealDamage() {

    }

    public void attackMonster(Monster card1, Monster card2) {

    }

    public void setMonsterCard(Monster card, String position) {

    }

    public void setSpellCard(Monster card, String position) {

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
        int selectedCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            User.getUserByUsername(currentTurnUsername).getBoard().setAnyCardSelected(false);
            //next line deselect inHandCard
            User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().
                    get(User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand()).setCardSelected(false);
            // next loop will deselect card that is not in hand
            for (int i = 0; i < 6; i++) {
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardSelected(false);
                User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].setCardSelected(false);
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i].setCardSelected(false);
                User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[i].setCardSelected(false);
            }
            System.out.println("card deselected");
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardNumberInHand(-1);
        } else System.out.println("no card is selected yet");
    }

    public void surrender() {

    }

    public static DuelPageController getInstance() {
        if (instance == null)
            instance = new DuelPageController();
        return instance;
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

//    public Phase getCurrentPhase() {
//        return currentPhase;
//    }

//    public void setCurrentPhase(Phase currentPhase) {
//        this.currentPhase = currentPhase;
//    }

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
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("E");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.OO)
                System.out.print("OO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DO)
                System.out.print("DO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DH)
                System.out.print("DH");
            System.out.println("    ");
        }

        for (int i = 5; i >= 0; i--) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("E");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED)
                System.out.print("O");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.SET)
                System.out.print("H");
        }

        for (int i = 0; i < 6; i++) {
            System.out.print("  ");
        }

        System.out.println(User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().size());

        for (int i = 0; i < User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().size(); i++) {
            System.out.print("c "); //TODO after c is a tab solve this if is not correct
        }

        System.out.println("<" + User.getUserByUsername(currentTurnUsername).getNickname() + ">:<"
                + User.getUserByUsername(currentTurnUsername).getLifePoints() + ">");
    }

    private void showOpponentGameBoard() {
        System.out.println("<" + User.getUserByUsername(opponentUsername).getNickname() + ">:<"
                + User.getUserByUsername(opponentUsername).getLifePoints() + ">");
        for (int i = 0; i < User.getUserByUsername(opponentUsername).getBoard().getInHandCards().size(); i++) {
            System.out.print("    c");
        }
        System.out.println(User.getUserByUsername(opponentUsername).getBoard().getMainDeckCards().size());

        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("E");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED)
                System.out.print("O");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.SET)
                System.out.print("H");
        }

        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("E");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.OO)
                System.out.print("OO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DO)
                System.out.print("DO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DH)
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