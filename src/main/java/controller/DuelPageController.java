package controller;

import model.*;
import view.BetweenDuelPage;
import view.DuelPage;
import view.MainPage;
import view.Page;

import java.util.regex.Matcher;

public class DuelPageController extends Controller {
    private static String[] phaseNames = {"draw phase", "standby phase", "main phase 1", "battle phase", "main phase 2", "end phase"};
    private int phaseNumber;
    private static int[] cardsOrders = {5, 3, 1, 2, 4};
    protected String firstPlayerUsername;
    protected String secondPlayerUsername;
    private String currentTurnUsername;
    private String opponentUsername;
    private String gameWinnerUsername;
    private String gameLoserUsername;
    private String roundWinnerUsername;
    private String roundLoserUsername;
    private static DuelPageController instance;
    public GameMode gameMode;
    protected int numberOfRounds;
    protected int currentRoundNumber;
    private boolean canAttack; // the starter player can't attack

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public String getGameLoserUsername() {
        return gameLoserUsername;
    }

    public void setGameLoserUsername(String gameLoserUsername) {
        this.gameLoserUsername = gameLoserUsername;
    }

    public String getRoundLoserUsername() {
        return roundLoserUsername;
    }

    public void setRoundLoserUsername(String roundLoserUsername) {
        this.roundLoserUsername = roundLoserUsername;
    }

    public String getGameWinnerUsername() {
        return gameWinnerUsername;
    }

    public void setGameWinnerUsername(String gameWinnerUsername) {
        this.gameWinnerUsername = gameWinnerUsername;
    }

    public String getRoundWinnerUsername() {
        return roundWinnerUsername;
    }

    public void setRoundWinnerUsername(String roundWinnerUsername) {
        this.roundWinnerUsername = roundWinnerUsername;
    }

    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

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

    public static DuelPageController getInstance() {
        if (instance == null)
            instance = new DuelPageController();
        return instance;
    }

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

    public void selectMyMonsterCard(int monsterNumber) {
        if (monsterNumber <= 5 && monsterNumber >= 1) {
            if (!(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[monsterNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().
                        setSelectedCard(User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[monsterNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMyMonsterCardNumber(monsterNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_MONSTER);
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectMySpellCard(int spellNumber) {
        if (spellNumber <= 5 && spellNumber >= 1) {
            if (!(User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[spellNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                        (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[spellNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMySpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedMySpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_SPELL);
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectMyFieldCard() {
        if (!(User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard() == null)) {
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                    (User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard());
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.MY_FIELD_CARD);
            User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
        } else System.out.println("no card found in the given position");
    }

    public void selectOpponentMonsterCard(int monsterNumber) {
        if (monsterNumber <= 5 && monsterNumber >= 0) {
            if (!(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[monsterNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                        (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[monsterNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedOpponentMonsterCardNumber(monsterNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_MONSTER);
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectOpponentSpellCard(Matcher matcher) {
        int spellNumber = Integer.parseInt(matcher.group(2));
        if (spellNumber <= 5 && spellNumber >= 0) {
            if (!(User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[spellNumber] == null)) {
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                        (User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[spellNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedOpponentSpellOrTrapCardNumber(spellNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_SPELL);
            } else System.out.println("no card found in the given position");
        } else System.out.println("invalid selection");
    }

    public void selectOpponentFieldCard() {
        if (!(User.getUserByUsername(opponentUsername).getBoard().getFieldCard() == null)) {
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                    (User.getUserByUsername(opponentUsername).getBoard().getFieldCard());
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.OPPONENT_FIELD_CARD);
            User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
        } else System.out.println("no card found in the given position");
    }

    public void selectInHandCard(int inHandCardNumber) {
        if (inHandCardNumber > User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().size() || inHandCardNumber <= 0)
            System.out.println("invalid selection");
        else {
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard
                    (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(inHandCardNumber - 1));
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardNumberInHand(inHandCardNumber - 1);
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardField(SelectedCardField.IN_HAND);
            User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().setAnyCardSelected(true);
        }
    }

    /**
     * till here
     */

    public void updateAndShowGameBoard() {

    }

    public void nextPhase() {
        phaseNumber++;
        DuelPage duelPage = new DuelPage();
        if (DuelPageController.getInstance().getPhaseNumber() == 5) duelPage.phaseWork();
    }

    public void drawPhase() {
        System.out.println(phaseNames[phaseNumber]);
        drawCard(currentTurnUsername);
        phaseNumber++;
    }

    public void changeTurn() {
        if (User.getUserByUsername(currentTurnUsername) != null) {
            User.getUserByUsername(currentTurnUsername).setHasLostMonsters(false);
            User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(false);
            User.getUserByUsername(currentTurnUsername).reduceCanDrawCardInt();
        }
        if (User.getUserByUsername(opponentUsername) != null) {
            User.getUserByUsername(opponentUsername).setHasLostMonsters(false);
            User.getUserByUsername(opponentUsername).setCardSummonedOrSetInThisTurn(false);
            User.getUserByUsername(opponentUsername).reduceCanDrawCardInt();
        }
        deselectCard();
        resetCards();
        canAttack = true;
        phaseNumber = 0;
        String tempUsername;
        tempUsername = currentTurnUsername;
        currentTurnUsername = opponentUsername;
        opponentUsername = tempUsername;
        if (currentTurnUsername.equals("AI")) {
            DuelPage duelPage = new DuelPage();
            duelPage.phaseWork();
            AI.getInstance().AIPlay();
        }
        resetMonsters();
        applyFieldCardActions();
        activateSetTraps();
    }


    private void resetCards() {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(firstPlayerUsername).getBoard().getMonsterCards()[i] != null) {
                User.getUserByUsername(firstPlayerUsername).getBoard().getMonsterCards()[i].setCardAlreadyAttackedInThisTurn(false);
                User.getUserByUsername(firstPlayerUsername).getBoard().getMonsterCards()[i].setCardSetPositionInThisTurn(false);
                User.getUserByUsername(firstPlayerUsername).getBoard().getMonsterCards()[i].setCardSetInThisTurn(false);
            }
            if (User.getUserByUsername(secondPlayerUsername).getBoard().getMonsterCards()[i] != null) {
                User.getUserByUsername(secondPlayerUsername).getBoard().getMonsterCards()[i].setCardAlreadyAttackedInThisTurn(false);
                User.getUserByUsername(secondPlayerUsername).getBoard().getMonsterCards()[i].setCardSetPositionInThisTurn(false);
                User.getUserByUsername(secondPlayerUsername).getBoard().getMonsterCards()[i].setCardSetInThisTurn(false);
            }
        }
    }


    public void resetSelectedCard() {

    }

    public void startGame() {

    }


    public void drawCard(String username) {
        if (User.getUserByUsername(username).getBoard().getMainDeckCards().size() > 0) {
            if (User.getUserByUsername(username).getCanDrawCardInt() <= 0) {
                User.getUserByUsername(username).getBoard().getInHandCards().add(User.
                        getUserByUsername(username).getBoard().getMainDeckCards().get(0));
                System.out.println("new card added to the hand : " + User.getUserByUsername(username).
                        getBoard().getMainDeckCards().get(0).getCardName());
                User.getUserByUsername(username).getBoard().getMainDeckCards().remove(0);
            } else
                System.out.println("Can't Draw Card in this Turn (Time Seal effect)");
        } else
            loseRound();
    }

    private void loseRound() {
        if (numberOfRounds == 1) {
            User.getUserByUsername(opponentUsername).setNumberOfWonRoundInCurrentGame
                    (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() + 1);
            BetweenDuelPageController.getInstance().setCurrentUsername(firstPlayerUsername);
            BetweenDuelPage.printHelp();
            gameWinnerUsername = opponentUsername;
            gameLoserUsername = currentTurnUsername;
            gameGift();
            BetweenDuelPage.launch();
        } else if (numberOfRounds == 3) {
            User.getUserByUsername(opponentUsername).setNumberOfWonRoundInCurrentGame
                    (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() + 1);
            BetweenDuelPageController.getInstance().setCurrentUsername(firstPlayerUsername);
            BetweenDuelPage.printHelp();
            currentRoundNumber++;
            gameWinChecker();
            BetweenDuelPage.launch();
        }
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
                            deselectCard();
                        } else System.out.println("you already changed this card position in this turn");
                    } else System.out.println("this card is already in the wanted position");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t change this card position");
        } else System.out.println("no card is selected yet");
    }

    public void setSpellCard() {
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Spell &&
                        !(phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2"))) {
                    System.out.println("you can’t do this action in this phase");
                } else if (howManySpellFieldAreOccupied(currentTurnUsername) == 5 && !User.getUserByUsername
                        (currentTurnUsername).getBoard().getSelectedCard().getCardType().equals("Field"))
                    System.out.println("spell card zone is full");
                else {
                    System.out.println("set successfully");
                    setSpellOrTrapCardOnBoard(selectedCardNumberInHand);

                    deselectCard();
                    User.getUserByUsername(currentTurnUsername).getBoard().setAnyCardSelected(false);
                    User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard(null);
                }
            } else System.out.println("you can’t set this card");
        } else System.out.println("no card is selected yet");
    }

    public void setTrapCard() {
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                if (!(phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2"))) {
                    System.out.println("you can’t do this action in this phase");
                } else if (howManySpellFieldAreOccupied(currentTurnUsername) == 5)
                    System.out.println("spell card zone is full");
                else {
                    System.out.println("set successfully");
                    setSpellOrTrapCardOnBoard(selectedCardNumberInHand);

                    deselectCard();
                    User.getUserByUsername(currentTurnUsername).getBoard().setAnyCardSelected(false);
                    User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard(null);
                }
            } else System.out.println("you can’t set this card");
        } else System.out.println("no card is selected yet");
    }

    private void setSpellOrTrapCardOnBoard(int selectedCardNumber) {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i] == null) {
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i] =
                        (SpellAndTrap) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i].setCardState(CardState.H);
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i].setSpellOrTrapCardState(SpellOrTrapCardState.SET);

                break;
            }
        }
    }

    public void flipSummon() {
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]
                            .getCardState() == CardState.DH &&
                            !User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]
                                    .isCardSetInThisTurn()) {
                        User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]
                                .setCardState(CardState.OO);
                        System.out.println("flip summoned successfully");
                        User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].
                                actionWhenFlipped(selectedMonsterCardNumber);
                    } else System.out.println("you can’t flip summon this card");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t change this card position");
        } else System.out.println("no card is selected yet");
    }

    public void showSelectedCard() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard() instanceof Spell) {
                if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard().getCardState() == CardState.H &&
                        User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField()
                                == SelectedCardField.OPPONENT_SPELL)
                    System.out.println("card is not visible");
                else printSpellCard();
            } else if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard() instanceof Trap) {
                if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard().getCardState() == CardState.H &&
                        User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField()
                                == SelectedCardField.OPPONENT_SPELL)
                    System.out.println("card is not visible");
                else printTrapCard();
            } else {
                if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard().getCardState() == CardState.DH &&
                        User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField()
                                == SelectedCardField.OPPONENT_MONSTER)
                    System.out.println("card is not visible");
                else printMonsterCard();
            }

        } else System.out.println("no card is selected yet");
    }

    private void printMonsterCard() {
        System.out.println("Name: " + User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard().getCardName());
        System.out.println("Level: " + ((Monster) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getLevel());
        System.out.println("Type: " + (User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getCardType());
        System.out.println("ATK: " + ((Monster) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getAttack());
        System.out.println("DEF: " + ((Monster) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getDefense());
        System.out.println("Description: " + User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard().getDescription());
    }

    private void printSpellCard() {
        System.out.println("Name: " + User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard().getCardName());
        System.out.println("Spell");
        System.out.println("Type: " + ((Spell) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getCardType());
        System.out.println("Description: " + ((Spell) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getDescription());
    }

    private void printTrapCard() {
        System.out.println("Name: " + User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard().getCardName());
        System.out.println("Trap");
        System.out.println("Type: " + ((Trap) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getCardType());
        System.out.println("Description: " + ((Trap) User.getUserByUsername(currentTurnUsername)
                .getBoard().getSelectedCard()).getDescription());
    }

    public void activateEffect() {
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        int selectedMySpellOrTrapCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMySpellOrTrapCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                    [selectedMySpellOrTrapCardNumber] != null)
                if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                        [selectedMySpellOrTrapCardNumber] instanceof Trap && !User.getUserByUsername(currentTurnUsername).getBoard()
                        .getSpellOrTrapCards()[selectedMySpellOrTrapCardNumber].getCardName().equals("Call of The Haunted")) {
                    System.out.println("Selected card is a Trap");
                    return;
                }
            if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards()
                    .get(selectedCardNumberInHand) != null)
                if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards()
                        .get(selectedCardNumberInHand) instanceof Trap && !User.getUserByUsername(currentTurnUsername).getBoard()
                        .getSpellOrTrapCards()[selectedMySpellOrTrapCardNumber].getCardName().equals("Call of The Haunted")) {
                    System.out.println("Selected card is a Trap");
                    return;
                }
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_SPELL ||
                    (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND &&
                            User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards()
                                    .get(selectedCardNumberInHand) instanceof Spell || User.getUserByUsername
                            (currentTurnUsername).getBoard().getSpellOrTrapCards()[selectedMySpellOrTrapCardNumber]
                            .getCardName().equals("Call of The Haunted"))) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")
                        || User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                        [selectedMySpellOrTrapCardNumber].getCardName().equals("Call of The Haunted")) {
                    if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_SPELL &&
                            User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[selectedMySpellOrTrapCardNumber]
                                    .getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                        System.out.println("you have already activated this card");
                    } else {
                        if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND &&
                                howManySpellFieldAreOccupied(currentTurnUsername) == 5) {
                            System.out.println("spell card zone is full");
                        } else {
                            if (!User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                                    [selectedMySpellOrTrapCardNumber].getCanBeActivated()) {
                                System.out.println("preparations of this spell are not done yet");
                            } else {
                                System.out.println("spellActivated");
                                putSpellCardOnBoard(selectedCardNumberInHand);
                                SpellAndTrap card = User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                                        [selectedMySpellOrTrapCardNumber];
                                card.setSpellOrTrapCardState(SpellOrTrapCardState.ACTIVATED);
                                card.action();
                                callSpellOrTrapActionsTriggeredByAnother(User.getUserByUsername(currentTurnUsername));
                                callSpellOrTrapActionsTriggeredByAnother(User.getUserByUsername(opponentUsername));
                                if ((card.getIcon() == Icon.NORMAL || card.getIcon() == Icon.RITUAL || card.getIcon() ==
                                        Icon.QUICKPLAY) && card.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                                    User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(card);
                                    if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField()
                                            == SelectedCardField.MY_SPELL)
                                        User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()
                                                [selectedMySpellOrTrapCardNumber] = null;
                                    else if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField()
                                            == SelectedCardField.IN_HAND)
                                        User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards()
                                                .remove(selectedCardNumberInHand);
                                }

                            }
                        }
                    }
                } else
                    System.out.println("you can’t activate an effect on this turn");
            } else System.out.println("activate effect is only for spell cards.");
        } else System.out.println("no card is selected yet");
    }


    private void putSpellCardOnBoard(int selectedCardNumber) {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i] == null) {
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i] = (SpellAndTrap) User
                        .getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i].setCardState(CardState.O);
                User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[i]
                        .setSpellOrTrapCardState(SpellOrTrapCardState.ACTIVATED);
                break;
            }
        }
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
                            System.out.println("your opponent receives " + damage + " battle damage");
                        } else System.out.println("you can’t attack the opponent directly");
                    } else System.out.println("this card already attacked");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t attack with this card");
        } else System.out.println("no card is selected yet");

    }

    /**
     * from here
     */

    public void attack(int toBeAttackedCardNumber) {
        int selectedMonsterCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedMyMonsterCardNumber();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER &&
                    User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].getCardState() == CardState.OO) {
                if (phaseNames[phaseNumber].equals("battle phase")) {
                    if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].isCardAlreadyAttackedInThisTurn()) {
                        if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] != null) {
                            if (triggerTrapsAfterDeclaringAttack(selectedMonsterCardNumber))
                                return;
                            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]
                                    .getCardName().equals("The Calculator"))
                                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].action();
                            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]
                                    .getCardName().equals("The Calculator"))
                                User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].action();
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
        if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].
                getHasSetEffect()) {
            System.out.println("You have not set effect on you Monster");
            return;
        }
        User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].setBeingTargetedBy
                (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
        User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].actionWhenAttacked();
        int damage = ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber])
                .getAttack() - ((Monster) User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()
                [toBeAttackedCardNumber]).getAttack();

        if (damage > 0) {
            System.out.println("your opponent’s monster is destroyed and your opponent receives " + damage + " battle damage");
            User.getUserByUsername(opponentUsername).setLifePoints(User.getUserByUsername(opponentUsername).getLifePoints() - damage);
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername).
                    getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].
                    actionWhenDestroyed(selectedMonsterCardNumber, toBeAttackedCardNumber);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
            User.getUserByUsername(opponentUsername).setHasLostMonsters(true);
        } else if (damage == 0) {
            System.out.println("both you and your opponent monster cards are destroyed and no one receives damage");
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername)
                    .getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
            User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername
                    (currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber] = null;
            User.getUserByUsername(currentTurnUsername).setHasLostMonsters(true);
            User.getUserByUsername(opponentUsername).setHasLostMonsters(true);
        } else { // damage < 0
            System.out.println("Your monster card is destroyed and you received " + ((-1) * damage) + " battle damage");
            User.getUserByUsername(currentTurnUsername).setLifePoints(User.getUserByUsername(currentTurnUsername).getLifePoints() + damage);
            User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername
                    (currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
            User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber] = null;
            User.getUserByUsername(currentTurnUsername).setHasLostMonsters(true);
        }
    }

    private void attackDOOrDHCard(int toBeAttackedCardNumber, int selectedMonsterCardNumber) {
        if (!User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber].
                getHasSetEffect()) {
            System.out.println("You have not set effect on you Monster");
            return;
        }
        User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].setBeingTargetedBy
                (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
        User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].actionWhenAttacked();
        int attackPower = ((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]).getAttack() -
                ((Monster) User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]).getAttack();
        User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].setBeingTargetedBy
                (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[selectedMonsterCardNumber]);
        if (attackPower > 0) {
            System.out.println("the defense position monster is destroyed");
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber].
                    actionWhenDestroyed(selectedMonsterCardNumber, toBeAttackedCardNumber);
            User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().add(User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber]);
            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[toBeAttackedCardNumber] = null;
            User.getUserByUsername(opponentUsername).setHasLostMonsters(true);

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
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND &&
                    User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand)
                            instanceof Monster) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (occupiedMonsterFieldNumber < 5) {
                        if (!User.getUserByUsername(currentTurnUsername).isCardSummonedOrSetInThisTurn()) {
                            if (((Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand)).getLevel() <= 4) {
                                System.out.println("summoned successfully");
                                User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                                putSummonedCardOnBoard(selectedCardNumberInHand);
                                triggerTrapsAfterSummon();
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
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] = (Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardState(CardState.OO);
                deselectCard();
                break;
            }
        }
    }

    private void summonLevelFiveOrSixMonster(int occupiedMonsterFieldNumber) {
        if (occupiedMonsterFieldNumber >= 1) {
            int tributeNumber = DuelPage.getTributeMonsterNumberFromPlayer();
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[tributeNumber] == null) {
                System.out.println("there no monsters on this address");
            } else {
                System.out.println("summoned successfully");
                User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                int selectedCardNumber = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
                User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(User.getUserByUsername
                        (currentTurnUsername).getBoard().getMonsterCards()[tributeNumber]);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[tributeNumber] = null;
                putSummonedCardOnBoard(selectedCardNumber);
            }
        } else System.out.println("there are not enough cards for tribute");
    }

    /**
     * till here functions are related to summonCard function
     */

    public void set() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
                if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Monster)
                    setMonster();
                else if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Spell &&
                        User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand).getCardType().equals("Field")) {
                    setFieldCard();
                } else if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Spell)
                    setSpellCard();
                else if (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand) instanceof Trap)
                    setTrapCard();
            }
        } else System.out.println("no card is selected yet");
    }

    public void setMonster() {
        int occupiedMonsterFieldNumber = howManyMonsterFieldAreOccupied(currentTurnUsername);
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardField() == SelectedCardField.IN_HAND) {
                if (phaseNames[phaseNumber].equals("main phase 1") || phaseNames[phaseNumber].equals("main phase 2")) {
                    if (occupiedMonsterFieldNumber < 5) {
                        if (!User.getUserByUsername(currentTurnUsername).isCardSummonedOrSetInThisTurn()) {
                            System.out.println("set successfully");
                            User.getUserByUsername(currentTurnUsername).setCardSummonedOrSetInThisTurn(true);
                            putSetMonsterCardOnBoard(selectedCardNumberInHand);

                            deselectCard();
                            User.getUserByUsername(currentTurnUsername).getBoard().setAnyCardSelected(false);
                            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard(null);
                        } else System.out.println("you already summoned/set on this turn");
                    } else System.out.println("monster card zone is full");
                } else System.out.println("you can’t do this action in this phase");
            } else System.out.println("you can’t set this card");
        } else System.out.println("no card is selected yet");
    }

    private void putSetMonsterCardOnBoard(int selectedCardNumber) {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null) {
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] =
                        (Monster) User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().remove(selectedCardNumber);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardState(CardState.DH);
                User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i].setCardSetInThisTurn(true);
                break;
            }
        }
    }

    public int getFirstFreeMonsterFieldNumber() {
        int i;
        for (i = 1; i < 6; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[i] == null) return i;
            ;
        }
        return -1;
    }

    public int howManyMonsterFieldAreOccupied(String username) {
        int numberOfOccupiedMonsterField = 0;
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(username).getBoard().getMonsterCards()[i] != null)
                numberOfOccupiedMonsterField++;
        }
        return numberOfOccupiedMonsterField;
    }

    public int howManySpellFieldAreOccupied(String username) {
        int numberOfOccupiedSpellField = 0;
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(username).getBoard().getSpellOrTrapCards()[i] != null)
                numberOfOccupiedSpellField++;
        }
        return numberOfOccupiedSpellField;
    }


    public void roundWinChecker() {
        if (User.getUserByUsername(currentTurnUsername).getLifePoints() == 0) {
            User.getUserByUsername(opponentUsername).setNumberOfWonRoundInCurrentGame
                    (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() + 1);
            setMaxLifePoints();
            if (numberOfRounds == 3) {
                System.out.println(opponentUsername + " is the winner of this round");
                BetweenDuelPageController.getInstance().setCurrentUsername(firstPlayerUsername);
                BetweenDuelPage.printHelp();
                User.getUserByUsername(firstPlayerUsername).setLifePoints(8000);
                User.getUserByUsername(secondPlayerUsername).setLifePoints(8000);
                BetweenDuelPage.launch();
            }
        } else if (User.getUserByUsername(opponentUsername).getLifePoints() == 0) {
            User.getUserByUsername(currentTurnUsername).
                    setNumberOfWonRoundInCurrentGame(User.getUserByUsername(currentTurnUsername).getNumberOfWonRoundInCurrentGame() + 1);
            setMaxLifePoints();
            if (numberOfRounds == 3) {
                System.out.println(currentTurnUsername + " is the winner of this round");
                BetweenDuelPageController.getInstance().setCurrentUsername(firstPlayerUsername);
                BetweenDuelPage.printHelp();
                User.getUserByUsername(firstPlayerUsername).setLifePoints(8000);
                User.getUserByUsername(secondPlayerUsername).setLifePoints(8000);
                BetweenDuelPage.launch();
            }
        }
        gameWinChecker();
    }

    private void setMaxLifePoints() {
        User.getUserByUsername(firstPlayerUsername).setMaxLifePointInTheRounds(User.getUserByUsername(firstPlayerUsername).getLifePoints());
        User.getUserByUsername(secondPlayerUsername).setMaxLifePointInTheRounds(User.getUserByUsername(secondPlayerUsername).getLifePoints());
    }

    public void gameWinChecker() {
        if (numberOfRounds == 1) {
            if (User.getUserByUsername(currentTurnUsername).getNumberOfWonRoundInCurrentGame() == 1) {
                gameWinnerUsername = currentTurnUsername;
                gameLoserUsername = opponentUsername;
                gameGift();
            }
            if (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() == 1) {
                gameWinnerUsername = opponentUsername;
                gameLoserUsername = currentTurnUsername;
                gameGift();
            }
        }

        if (numberOfRounds == 3) {
            currentRoundNumber++;
            if (User.getUserByUsername(currentTurnUsername).getNumberOfWonRoundInCurrentGame() == 2) {
                gameWinnerUsername = currentTurnUsername;
                gameLoserUsername = opponentUsername;
                gameGift();
            } else if (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() == 2) {
                gameWinnerUsername = opponentUsername;
                gameLoserUsername = currentTurnUsername;
                gameGift();
            }
        }

    }

    public void showGraveyard(String s) {
        String username;
        if (s.equals(" --opponent")) username = opponentUsername;
        else username = currentTurnUsername;
        if (User.getUserByUsername(username).getBoard().getGraveyardCards().size() == 0)
            System.out.println("graveyard empty");
        else for (int i = 0; i < User.getUserByUsername(username).getBoard().getGraveyardCards().size(); i++) {
            System.out.println((i + 1) + ". " + User.getUserByUsername(username).getBoard().getGraveyardCards().get(i).getCardName()
                    + ":" + User.getUserByUsername(username).getBoard().getGraveyardCards().get(i).getDescription());
        }

        // todo make back button
    }

    public void showOpponentGraveYard() {

    }

    public void showCardByName(String name) {
        Card card = Card.getCardByName(name);
        if (card instanceof Monster) showMonsterCard(card);
        else if (card instanceof Spell) showSpellCard(card);
        else if (card instanceof Trap) showTrapCard(card);
    }

    private void showMonsterCard(Card card) {
        System.out.println("Card: " + card.getCardName());
        System.out.println("Level: " + ((Monster) card).getLevel());
        System.out.println("Type: Warrior");
        System.out.println("ATK: " + ((Monster) card).getAttack());
        System.out.println("DEF: " + ((Monster) card).getDefense());
        System.out.println("Description: " + card.getDescription());
    }

    private void showSpellCard(Card card) {
        System.out.println("Card: " + card.getCardName());
        System.out.println("Spell");
        System.out.println("Type: " + card.getCardType());
        System.out.println("Description: " + card.getDescription());
    }

    private void showTrapCard(Card card) {
        System.out.println("Card: " + card.getCardName());
        System.out.println("Trap");
        System.out.println("Type: " + card.getCardType());
        System.out.println("Description: " + card.getDescription());
    }

    public void finishGame() {

    }

    public void deselectCard() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().isAnyCardSelected()) {
            User.getUserByUsername(currentTurnUsername).getBoard().setAnyCardSelected(false);
            // next loop will deselect card that is not in hand
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCard(null);
            System.out.println("card deselected");
            User.getUserByUsername(currentTurnUsername).getBoard().setSelectedCardNumberInHand(-1);
        } else System.out.println("no card is selected yet");
    }

    public void surrender() {
        int winnerScore = User.getUserByUsername(opponentUsername).getScore() - User.getUserByUsername(currentTurnUsername).getScore();
        if (numberOfRounds == 3) {
            if (User.getUserByUsername(opponentUsername).getNumberOfWonRoundInCurrentGame() == 1)
                System.out.println(opponentUsername + " won the whole match with score:  " + winnerScore);
            else System.out.println(opponentUsername + " won the game and the score is: " + winnerScore);
        } else if (numberOfRounds == 1) {
            System.out.println(opponentUsername + " won the whole match with score:  " + winnerScore);
        }
        loseRound();
    }

    private void gameGift() {
        int winnerMoneyGift = 1000;
        int winnerScoreGift = 1000 + User.getUserByUsername(gameWinnerUsername).getMaxLifePointInTheRounds();
        int loserMoneyGift = 100;
        if (numberOfRounds == 1) {
            User.getUserByUsername(gameWinnerUsername).setScore(User.getUserByUsername(gameWinnerUsername).getScore()
                    + winnerScoreGift);
            User.getUserByUsername(gameWinnerUsername).setMoney(User.getUserByUsername(gameWinnerUsername).getMoney()
                    + winnerMoneyGift);
            User.getUserByUsername(gameLoserUsername).setMoney(User.getUserByUsername(gameLoserUsername).getMoney()
                    + loserMoneyGift);
        } else if (numberOfRounds == 3) {
            User.getUserByUsername(gameWinnerUsername).setScore(User.getUserByUsername(gameWinnerUsername).getScore()
                    + (winnerScoreGift) * 3);
            User.getUserByUsername(gameWinnerUsername).setMoney(User.getUserByUsername(gameWinnerUsername).getMoney()
                    + (winnerMoneyGift * 3));
            User.getUserByUsername(gameLoserUsername).setMoney(User.getUserByUsername(gameLoserUsername).getMoney()
                    + (loserMoneyGift * 3));
        }

        User.getUsers().remove(User.getUserByUsername("AI"));

        // exit duel menu and go to main menu
        try {
            new MainPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String getCurrentTurnUsername() {
        return currentTurnUsername;
    }

    public void setCurrentTurnUsername(String currentTurnUsername) {
        this.currentTurnUsername = currentTurnUsername;
    }

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public void setOpponentUsername(String opponentUsername) {
        this.opponentUsername = opponentUsername;
    }


    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void showGameBoard() {
        showOpponentGameBoard();
        showCurrentPlayerGameBoard();
    }

    private void showOpponentGameBoard() {
        System.out.println("<" + User.getUserByUsername(opponentUsername).getNickname() + ">:<"
                + User.getUserByUsername(opponentUsername).getLifePoints() + ">");
        for (int i = 0; i < User.getUserByUsername(opponentUsername).getBoard().getInHandCards().size(); i++) {
            System.out.print("\tc");
        }
        System.out.println();
        System.out.println(User.getUserByUsername(opponentUsername).getBoard().getMainDeckCards().size());

        showOpponentPlayerMonsterAndSpellOrTraps();

        System.out.print(User.getUserByUsername(opponentUsername).getBoard().getGraveyardCards().size());
        for (int i = 0; i < 6; i++) {
            System.out.print("\t");
        }
        if (User.getUserByUsername(opponentUsername).getBoard().getFieldCard() == null) System.out.print("E");
        else System.out.print("O");
        System.out.println();
    }

    private void showOpponentPlayerMonsterAndSpellOrTraps() {
        for (int i = 4; i >= 0; i--) {
            if (User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()[cardsOrders[i]] == null)
                System.out.print("\tE");
            else if (User.getUserByUsername(opponentUsername).getBoard().
                    getSpellOrTrapCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED)
                System.out.print("\tO");
            else if (User.getUserByUsername(opponentUsername).getBoard().
                    getSpellOrTrapCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.SET)
                System.out.print("\tH");
        }
        System.out.println();

        for (int i = 4; i >= 0; i--) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("\tE");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.OO)
                System.out.print("\tOO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DO)
                System.out.print("\tDO");
            else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DH)
                System.out.print("\tDH");
        }
        System.out.println();
    }

    private void showCurrentPlayerGameBoard() {
        if (User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard() == null) System.out.print("E");
        else System.out.print("O");
        for (int i = 0; i < 6; i++) {
            System.out.print("\t");
        }
        System.out.println(User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().size());

        showCurrentPlayerMonsterAndSpellOrTraps();

        for (int i = 0; i < 6; i++) {
            System.out.print("\t");
        }

        System.out.println(User.getUserByUsername(currentTurnUsername).getBoard().getMainDeckCards().size());

        for (int i = 0; i < User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().size(); i++) {
            System.out.print("c\t");
        }
        System.out.println();

        System.out.println("<" + User.getUserByUsername(currentTurnUsername).getNickname() + ">:<"
                + User.getUserByUsername(currentTurnUsername).getLifePoints() + ">");
    }

    private void showCurrentPlayerMonsterAndSpellOrTraps() {
        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]] == null)
                System.out.print("\tE");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.OO)
                System.out.print("\tOO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DO)
                System.out.print("\tDO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getMonsterCards()[cardsOrders[i]].getCardState() == CardState.DH)
                System.out.print("\tDH");
        }
        System.out.println();

        for (int i = 0; i < 5; i++) {
            if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[cardsOrders[i]] == null)
                System.out.print("\tE");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED)
                System.out.print("\tO");
            else if (User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()[cardsOrders[i]].getSpellOrTrapCardState() == SpellOrTrapCardState.SET)
                System.out.print("\tH");
        }
        System.out.println();
    }

    public void standbyPhase() {
        System.out.println(phaseNames[phaseNumber]);
        phaseNumber++;
    }

    public void mainPhase() {
        System.out.println(phaseNames[phaseNumber]);
        showGameBoard();
    }

    public void endPhase() {
        System.out.println(phaseNames[phaseNumber]);
        System.out.println("its " + opponentUsername + "’s turn");
        callActionsForContinuousSpells();
        changeTurn();
    }

    public void battlePhase() {
        System.out.println(phaseNames[phaseNumber]);
        showGameBoard();
    }


    public void increaseLifePoint(int amount) {
        User.getUserByUsername(username).setLifePoints(User.getUserByUsername(username).getLifePoints() + amount);
    }

    public void setWinner(String username) {
        gameWinnerUsername = username;
        if (username.equals(firstPlayerUsername)) gameLoserUsername = secondPlayerUsername;
        else if (username.equals(secondPlayerUsername)) gameLoserUsername = firstPlayerUsername;
        gameGift();
    }


    public void resetMonsters() {
        reset(currentTurnUsername);
        reset(opponentUsername);
    }

    private void reset(String opponentUsername) {
        for (Monster monsterCard : User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()) {
            if (monsterCard != null) {
                if (monsterCard.getRoundsToResetAttack() == 1)
                    monsterCard.setAttack(monsterCard.getOriginalAttack());
                if (monsterCard.getRoundsToResetDefense() == 1)
                    monsterCard.setDefense(monsterCard.getOriginalDefense());
                monsterCard.setUsedEffectsInThisTurn(false);
                if (monsterCard.getCardName().equals("Scanner")) {
                    monsterCard.revertToOrigin();
                    monsterCard.setHasSetEffect(false);
                }
            }
        }
    }


    public void callMonsterActions() {
        Monster monster = (Monster) User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCard();
        monster.action();
    }


    public void callActionsForContinuousSpells() {
        for (SpellAndTrap spell : User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()) {
            if (spell != null)
                if (spell.getIcon() == Icon.CONTINUOUS && spell.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                    spell.action();
                    spell.action(User.getUserByUsername(currentTurnUsername));
                }
        }
        for (SpellAndTrap spell : User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()) {
            if (spell != null)
                if (spell.getIcon() == Icon.CONTINUOUS && spell.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                    spell.action();
                    spell.action(User.getUserByUsername(currentTurnUsername));
                }
        }
    }


    public void callSpellOrTrapActionsTriggeredByAnother(User user) {
        for (SpellAndTrap spellOrTrapCard : user.getBoard().getSpellOrTrapCards()) {
            if (spellOrTrapCard != null)
                if (spellOrTrapCard instanceof Spell)
                    if (spellOrTrapCard.getCardName().equals("Spell Absorption") &&
                            spellOrTrapCard.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                        spellOrTrapCard.action(user);
                        spellOrTrapCard.action();
                    }
        }
    }


    public void applyFieldCardActions() {
        Spell filedCard1 = (Spell) User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard();
        Spell filedCard2 = (Spell) User.getUserByUsername(opponentUsername).getBoard().getFieldCard();
        if (filedCard1 != null)
            filedCard1.action(true);
        if (filedCard2 != null)
            filedCard2.action(true);
    }

    private void setFieldCard() {
        Spell card = (Spell) User.getUserByUsername(currentTurnUsername).getBoard().getFieldCard();
        if (card != null)
            User.getUserByUsername(currentTurnUsername).getBoard().getGraveyardCards().add(card);
        int selectedCardNumberInHand = User.getUserByUsername(currentTurnUsername).getBoard().getSelectedCardNumberInHand();
        User.getUserByUsername(currentTurnUsername).getBoard().setFieldCard
                (User.getUserByUsername(currentTurnUsername).getBoard().getInHandCards().get(selectedCardNumberInHand));
        deselectCard();
    }

    public void destroyTrapAfterAction(User user, SpellAndTrap trap) {
        int index = 1;
        for (SpellAndTrap spellOrTrapCard : user.getBoard().getSpellOrTrapCards()) {
            if (spellOrTrapCard.id.equals(trap.id))
                break;
            index++;
        }
        user.getBoard().getGraveyardCards().add(trap);
        user.getBoard().getSpellOrTrapCards()[index] = null;

    }

    public boolean canTrapBeActivated() {
        boolean canActivate;
        canActivate = isCanActivate(currentTurnUsername);
        if (canActivate)
            canActivate = isCanActivate(opponentUsername);
        return canActivate;
    }

    private boolean isCanActivate(String username) {
        for (Monster monsterCard : User.getUserByUsername(username).getBoard().getMonsterCards()) {
            if (monsterCard != null) {
                if (monsterCard.getCardName().equals("Mirage Dragon"))
                    if (monsterCard.cardState == CardState.O || monsterCard.cardState == CardState.DO
                            || monsterCard.cardState == CardState.OO) {
                        return false;
                    }
            }
        }
        return true;
    }

    public boolean triggerTrapsAfterDeclaringAttack(int selected) {
        boolean hasDestroyedAttacker = false;
        if (canTrapBeActivated()) {
            for (SpellAndTrap trap : User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards())
                if (trap != null)
                    if ((trap.getCardName().equals("Magic Cylinder") || trap.getCardName().equals("Mirror Force"))
                            && trap.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                        trap.action(selected);
                        trap.action();
                        destroyTrapAfterAction(User.getUserByUsername(opponentUsername), trap);
                        hasDestroyedAttacker = true;
                    }
        } else
            System.out.println("No trap can be activated (Mirage Dragon effect)");
        return hasDestroyedAttacker;
    }

    public void triggerTrapsAfterSummon() {
        if (canTrapBeActivated()) {
            for (SpellAndTrap trap : User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards())
                if (trap != null)
                    if ((trap.getCardName().equals("Torrential Tribute"))
                            && trap.getSpellOrTrapCardState() == SpellOrTrapCardState.ACTIVATED) {
                        trap.action();
                        destroyTrapAfterAction(User.getUserByUsername(opponentUsername), trap);
                    }
        } else
            System.out.println("No trap can be activated (Mirage Dragon effect)");
    }

    public void activateSetTraps() {
        for (SpellAndTrap spellOrTrapCard : User.getUserByUsername(currentTurnUsername).getBoard().getSpellOrTrapCards()) {
            if (spellOrTrapCard != null)
                if (spellOrTrapCard instanceof Trap)
                    spellOrTrapCard.setSpellOrTrapCardState(SpellOrTrapCardState.ACTIVATED);
        }
        for (SpellAndTrap spellOrTrapCard : User.getUserByUsername(opponentUsername).getBoard().getSpellOrTrapCards()) {
            if (spellOrTrapCard != null)
                if (spellOrTrapCard instanceof Trap)
                    spellOrTrapCard.setSpellOrTrapCardState(SpellOrTrapCardState.ACTIVATED);
        }
    }
}
