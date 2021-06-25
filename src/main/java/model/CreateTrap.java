package model;

import controller.DuelPageController;

import java.util.ArrayList;

public class CreateTrap {

    private static CreateTrap instance = null;


    private CreateTrap() {

    }


    public Trap createMirrorForce() {
        return new Trap("Mirror Force"){
            @Override
            public void action() {
                Monster[] opponentMonsters = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getMonsterCards();
                for (Monster monster : opponentMonsters) {
                    if (monster.state == CardState.OO)
                        monster.isDestroyed = true;
                }
            }
        };
    }


    public Trap makeTrap(String cardName) {
        switch (cardName) {
            case "Mirror Force":
                return createMirrorForce();
//            case "":
//                return ;
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
