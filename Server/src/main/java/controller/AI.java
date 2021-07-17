package controller;

import model.*;

public class AI {
    public static AI instance;

    private AI() {

    }

    public static AI getInstance() {
        if (instance == null) instance = new AI();
        return instance;
    }

    public static int getTributeNumber() {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername("AI").getBoard().getMonsterCards()[i] != null) return i;
        }
        return 0;
    }

    public void AIPlay() {
        playMonster();
        playSpell();
        playTrap();
        DuelPageController.getInstance().nextPhase();
        playAttack();
        if (canPlayDirectAttack())
            playDirectAttack();

        DuelPageController.getInstance().changeTurn();
        try {
            //new DuelPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean canPlayDirectAttack() {
        String opponentUsername = DuelPageController.getInstance().getOpponentUsername();
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i] != null)
                return false;
        }
        return true;
    }


    public void playDirectAttack() {
        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername("AI").getBoard().getMonsterCards()[i] != null &&
                    User.getUserByUsername("AI").getBoard().getMonsterCards()[i].getCardState() == CardState.OO) {
                DuelPageController.getInstance().selectMyMonsterCard(i);
                DuelPageController.getInstance().directAttack();
            }
        }
    }

    public void playAttack() {
        String opponentUsername = DuelPageController.getInstance().getOpponentUsername();

        for (int i = 1; i < 6; i++) {
            if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i] != null) {
                for (int j = 1; j < 6; j++) {
                    if (User.getUserByUsername("AI").getBoard().getMonsterCards()[j] != null &&
                            User.getUserByUsername("AI").getBoard().getMonsterCards()[j].getCardState() == CardState.OO &&
                            User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i] != null) {
                        if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getCardState() == CardState.OO) {
                            if (User.getUserByUsername("AI").getBoard().getMonsterCards()[j].getAttack() >
                                    User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getAttack()) {
                                DuelPageController.getInstance().selectMyMonsterCard(j);
                                User.getUserByUsername("AI").getBoard().setToBeAttackedCardNumber(i);
                                DuelPageController.getInstance().attack();
                            }
                        } else if (User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getCardState() == CardState.DO) {
                            if (User.getUserByUsername("AI").getBoard().getMonsterCards()[j].getAttack() >
                                    User.getUserByUsername(opponentUsername).getBoard().getMonsterCards()[i].getDefense()) {
                                DuelPageController.getInstance().selectMyMonsterCard(j);
                                User.getUserByUsername("AI").getBoard().setToBeAttackedCardNumber(i);
                                DuelPageController.getInstance().attack();
                            }
                        } else {
                            DuelPageController.getInstance().selectMyMonsterCard(j);
                            User.getUserByUsername("AI").getBoard().setToBeAttackedCardNumber(i);
                            DuelPageController.getInstance().attack();
                        }
                    }
                }
            }
        }
    }

    public void playMonster() {
        for (int i = 0; i < User.getUserByUsername("AI").getBoard().getInHandCards().size(); i++) {
            if (User.getUserByUsername("AI").getBoard().getInHandCards().get(i) instanceof Monster) {
                User.getUserByUsername("AI").getBoard().setSelectedCardNumberInHand(i + 1);
                DuelPageController.getInstance().selectInHandCard(i + 1);
                if (((Monster) User.getUserByUsername("AI").getBoard().getInHandCards().get(i)).getDefense() >= 1800)
                    DuelPageController.getInstance().setMonster();
                else {
                    DuelPageController.getInstance().summonCard();
                }
            }
        }
    }

    public void playSpell() {
        for (int i = 0; i < User.getUserByUsername("AI").getBoard().getInHandCards().size(); i++) {
            if (User.getUserByUsername("AI").getBoard().getInHandCards().get(i) instanceof Spell) {
                User.getUserByUsername("AI").getBoard().setSelectedCardNumberInHand(i);
                DuelPageController.getInstance().setSpellCard();
            }
        }
    }

    public void playTrap() {
        for (int i = 0; i < User.getUserByUsername("AI").getBoard().getInHandCards().size(); i++) {
            if (User.getUserByUsername("AI").getBoard().getInHandCards().get(i) instanceof Trap) {
                User.getUserByUsername("AI").getBoard().setSelectedCardNumberInHand(i);
                DuelPageController.getInstance().setTrapCard();
            }
        }
    }
}
