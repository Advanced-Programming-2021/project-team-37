package model;

import controller.DuelPageController;

import java.util.Scanner;

public class CreateMonster {

    private static CreateMonster instance = null;


    private CreateMonster() {

    }


    public Monster createSuijin() {
        return new Monster("Suijin") {
            @Override
            public void actionWhenAttacked() {
                if (this.state == CardState.DO || this.state == CardState.OO && !this.usedEffect) {
                    this.beingTargetedBy.attack = 0;
                    this.usedEffect = true;
                }
            }
        };
    }

    public Monster createYomiShip() {
        return new Monster("Yomi Ship") {
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

    public Monster createManEaterBug() {
        return new Monster("Man-Eater Bug") {
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

    public Monster createScanner() {
        return new Monster("Scanner") {
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

    public Monster createMarshmallon() {
        return new Monster("Marshmallon") {
            @Override
            public void action(Monster target) {
                System.out.println("Choose a card from opponent's GraveYard!");
                this.copyCard(target);
                this.usedEffectsInThisTurn = true;
            }
        };
    }

    public Monster createBeastKingBarbaros() {
        return new Monster("Scanner") {
            @Override
            public void action(User target) {
                if (this.isSpecialSummoned) {
                    Board opponentBoard = target.getBoard();
                    for (int i = 1; i < 6; i++) {
                        opponentBoard.getMonsterCards()[i].isDestroyed = true;
                    }
                }
            }
        };
    }

    public Monster createTexchanger() {
        return new Monster("Texchanger") {
            @Override
            public void action() {

            }

            @Override
            public void actionWhenAttacked() {
                System.out.println("Which Card you want to summon?");
                this.action();
            }
        };
    }


    public Monster createCalculator() {
        return new Monster("The Calculator") {
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

    public Monster createHeraldOfCreation() {
        return new Monster("Herald of Creation") {
            @Override
            public void action() {
                do {
                    System.out.println("Which Card do you want to revive?");
                } while ((new Monster("")).level < 7);

                System.out.println("Which card do you want to sacrifice?");
                Card card = new Monster("");
                card.isDestroyed = true;
                this.usedEffectsInThisTurn = true;
            }
        };
    }


    public Monster createExploderDragon() {
        return new Monster("Exploder Dragon") {
            @Override
            public void action() {
                this.beingTargetedBy.isDestroyed = true;
            }

            @Override
            public void actionWhenAttacked() {
                if (this.isDestroyed) {
                    this.action();
                }
            }
        };
    }

    public Monster createTerratigerTheEmpoweredWarrior() {
        return new Monster("Terratiger, the Empowered Warrior") {
            @Override
            public void action() {
                System.out.println("Which Monster Card do you want to summon?");
                {
                    Monster temp = new Monster("");
                    while (temp.level > 4 || temp.cardType.equals("Normal")) {
                        System.out.println("Choose another Monster");
                    }
                    temp.conjureDefenceCard();
                }
            }
        };
    }


    public Monster createTheTricky() {
        return new Monster("The Tricky") {

            @Override
            public void specialConjuring() {
                System.out.println("Choose a card From you hand to remove!");
                Monster temp = new Monster("");
                temp.removeCardFromHand();
                this.commonConjuring();
            }
        };
    }

    public Monster makeMonster(String cardName) {
        switch (cardName) {
            case "Suijin":
                return createSuijin();
            case "Yomi Ship":
                return createYomiShip();
            case "Man-Eater Bug":
                return createManEaterBug();
            case "Scanner":
                return createScanner();
            case "Marshmallon":
                return createMarshmallon();
            case "Beast King Barbaros":
                return createBeastKingBarbaros();
            case "Texchanger":
                return createTexchanger();
            case "The Calculator":
                return createCalculator();
            case "Herald of Creation":
                return createHeraldOfCreation();
            case "Exploder Dragon":
                return createExploderDragon();
            case "Terratiger, the Empowered Warrior":
                return createTerratigerTheEmpoweredWarrior();
            case "The Tricky":
                return createTheTricky();
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
