package model;

import controller.DuelPageController;

import java.util.ArrayList;
import java.util.Scanner;

public class CreateTrap {

    private static CreateTrap instance = null;


    private CreateTrap() {

    }


    public Trap createMirrorForce() {
        return new Trap("Mirror Force") {
            @Override
            public void action() {
                for (int i = 1; i < 6; i++) {
                    if (User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                            .getMonsterCards()[i] != null) {
                        if (User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                .getMonsterCards()[i].cardState == CardState.OO) {
                            User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                    .getGraveyardCards().add(User.getUserByUsername(DuelPageController.getInstance().
                                    getCurrentTurnUsername()).getBoard().getMonsterCards()[i]);
                            User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                    .getMonsterCards()[i] = null;
                        }
                    }
                }
                System.out.println("All of your Monsters were destroyed (Mirror Force tarp activated)");
            }
        };
    }


    public Trap createMagicCylinder() {
        return new Trap("Magic Cylinder") {
            @Override
            public void action(int selected) {
                Monster monster = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getMonsterCards()[selected];
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).setLifePoints(
                        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getLifePoints()
                                - monster.getAttack());
                System.out.println("Your attack was canceled and you took Damage: " + monster.getAttack() + " (Magic Cylinder trap" +
                        "activated)");
                System.out.println();
            }
        };
    }

    public Trap createTorrentialTribute() {
        return new Trap("Torrential Tribute") {
            @Override
            public void action() {
                for (Card monster : User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard()
                        .getMonsterCards())
                    if (monster != null)
                        User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getGraveyardCards()
                                .add(monster);

                for (int i = 0; i < 5; i++) {
                    User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                            .getBoard().getMonsterCards()[i] = null;
                }
                User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).setHasLostMonsters(true);

                for (Card monster : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                        .getMonsterCards())
                    if (monster != null)
                        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().getGraveyardCards()
                                .add(monster);

                for (int i = 0; i < 5; i++) {
                    User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                            .getBoard().getMonsterCards()[i] = null;
                }
                User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).setHasLostMonsters(true);

                System.out.println("You destroyed all of Monster cards on board (Torrential Tribute trap activated)");
            }
        };
    }

    public Trap createTimeSeal() {
        return new Trap("Time Seal") {
            @Override
            public void action(User user) {
                user.canDrawCardInt = 2;
            }
        };
    }

    public Trap createCallOfHaunted() {
        return new Trap("Call of The Haunted") {
            @Override
            public void action() {
                DuelPageController duelPageController = DuelPageController.getInstance();
                if (duelPageController.howManyMonsterFieldAreOccupied(duelPageController.getCurrentTurnUsername()) == 5) {
                    System.out.println("Monster Zone is full!");
                    this.setSpellOrTrapCardState(SpellOrTrapCardState.SET);
                } else
                    summonFromGV(duelPageController.getCurrentTurnUsername(), duelPageController);
            }

            private void summonFromGV(String username, DuelPageController duelPageController) {
                Scanner input = new Scanner(System.in);
                boolean done = false;
                int index = 0;
                System.out.println("Monster cards in Graveyard:");
                for (Card graveyardCard : User.getUserByUsername(username).getBoard().getGraveyardCards()) {
                    if (graveyardCard instanceof Monster)
                        System.out.println("index: " + index + "\t" + "Monster: " + graveyardCard.getCardName());
                    index++;
                }
                while (!done) {
                    System.out.println("Select a Card by index!");
                    int card = input.nextInt();
                    if (card >= index)
                        System.out.println("Index is out of Bound!");
                    else if (User.getUserByUsername(username).getBoard()
                            .getGraveyardCards().get(card) instanceof Monster) {
                        int zone = duelPageController.getFirstFreeMonsterFieldNumber();
                        User.getUserByUsername(duelPageController.getCurrentTurnUsername()).getBoard().getMonsterCards()[zone]
                                = (Monster) User.getUserByUsername(username).getBoard().getGraveyardCards().get(card);
                        User.getUserByUsername(duelPageController.getCurrentTurnUsername()).getBoard().getMonsterCards()[zone]
                                .setCardState(CardState.OO);
                        User.getUserByUsername(username).getBoard().getGraveyardCards().remove(card);
                        done = true;
                        System.out.println("Monster summoned in attack position successfully! (Call of The Haunted effect)");
                    } else
                        System.out.println("Selected Card is not a Monster");
                }
            }
        };
    }

    public Trap makeTrap(String cardName) {
        switch (cardName) {
            case "Mirror Force":
                return createMirrorForce();
            case "Magic Cylinder":
                return createMagicCylinder();
            case "Torrential Tribute":
                return createTorrentialTribute();
            case "Time Seal":
                return createTimeSeal();
            case "Call of The Haunted":
                return createCallOfHaunted();
            default:
                return new Trap(cardName);
        }
    }

    public static CreateTrap getInstance() {
        if (instance == null)
            instance = new CreateTrap();
        return instance;
    }
}
