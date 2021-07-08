package model;

import controller.DuelPageController;

import java.util.Scanner;

public class CreateMonster {

    private static CreateMonster instance = null;


    private CreateMonster() {

    }


    public Monster createSuijin(String cardName) {
        return new Monster(cardName) {
            @Override
            public void actionWhenAttacked() {
                if (this.state == CardState.DO || this.state == CardState.OO && !this.usedEffect) {
                    this.beingTargetedBy.attack = 0;
                    this.usedEffect = true;
                }
            }
        };
    }

    public Monster createYomiShip(String cardName) {
        return new Monster(cardName) {
            @Override
            public void actionWhenDestroyed(int selected, int target) {
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
                        getGraveyardCards().add(this.beingTargetedBy);
                this.beingTargetedBy.actionWhenDestroyed();
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().getMonsterCards()
                        [selected] = null;
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).setHasLostMonsters(true);
                System.out.println("your monster is destroyed (Yomi Ship effect)");
            }
        };
    }

    public Monster createManEaterBug(String cardName) {
        return new Monster(cardName) {
            @Override
            public void actionWhenFlipped(int selected) {
                int target;
                Scanner input = new Scanner(System.in);
                System.out.println("Choose a Monster Card Number to destroy! (Man-Eater Bug effect)");
                target = input.nextInt();
                Monster opponentMonster = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard()
                        .getMonsterCards()[target];
                opponentMonster.actionWhenDestroyed(selected, target);
                User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().
                        getGraveyardCards().add(opponentMonster);
                User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard()
                        .getMonsterCards()[target] = null;
                User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).setHasLostMonsters(true);
                System.out.println("your opponent’s monster is destroyed");
                this.usedEffect = true;
            }
        };
    }

    public Monster createScanner(String cardName) {
        return new Monster(cardName) {
            @Override
            public void action() {
                if (!usedEffectsInThisTurn) {
                    System.out.println("Choose a card from opponent's GraveYard!");
                    int target;
                    Scanner input = new Scanner(System.in);
                    target = input.nextInt();
                    this.copyCard(User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard()
                            .getMonsterCards()[target]);
                    this.hasSetEffect = true;
                }
                this.usedEffectsInThisTurn = true;
            }
        };
    }


    public Monster createCalculator(String cardName) {
        return new Monster(cardName) {
            @Override
            public void calculatePower() {
                int sumOfLevels = 0;
                if (User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()) != null) {
                    Board board = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard();
                    for (int i = 1; i < 6; i++) {
                        if (board.getMonsterCards()[i] != null) {
                            if (board.getMonsterCards()[i].cardState == CardState.DO
                                    || board.getMonsterCards()[i].cardState == CardState.OO
                                    || board.getMonsterCards()[i].cardState == CardState.O)
                                sumOfLevels += ((Monster) board.getMonsterCards()[i]).getLevel();
                            this.attack = sumOfLevels * 300;
                        }
                    }
                }
            }
        };
    }


    public Monster makeMonster(String cardName, String effectName) {
        switch (effectName) {
            case "Suijin":
                return createSuijin(cardName);
            case "Yomi Ship":
                return createYomiShip(cardName);
            case "Man-Eater Bug":
                return createManEaterBug(cardName);
            case "Scanner":
                return createScanner(cardName);
            case "The Calculator":
                return createCalculator(cardName);
            default:
                return new Monster(cardName);
        }
    }

    public static CreateMonster getInstance() {
        if (instance == null)
            instance = new CreateMonster();
        return instance;
    }

}
