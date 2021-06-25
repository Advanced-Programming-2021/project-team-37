package model;

import controller.DuelPageController;

import java.util.ArrayList;

public class CreateSpell {

    private static CreateSpell instance = null;


    private CreateSpell() {

    }


    public Spell createMonsterReborn() {
        return new Spell("Monster Reborn") {
            @Override
            public void action() {
                System.out.println("which card do you want to summon from graveYard?");
                Monster temp = new Monster("");
                temp.specialConjuring();
            }
        };
    }


    public Spell createPotOfGreed() {
        return new Spell("Pot of Greed") {
            @Override
            public void action() {
                System.out.println("Pick up to cards from top of your deck!");

            }
        };
    }

    public Spell createRaigeki() {
        return new Spell("Raigeki"){
            @Override
            public void action() {
                for (Card monster : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().getMonsterCards()) {
                    monster.isDestroyed = true;
                }
            }
        };
    }

    public Spell createChangeOfHeart() {
        return new Spell("Change of Heart"){
            @Override
            public void action() {
                System.out.println("Choose one of opponents cards to control!");
                Monster temp = new Monster("");
                this.target = temp;
                temp.opponentHasAccess = true;
            }
            @Override
            public void endAction() {
                this.target.opponentHasAccess = false;
            }
        };
    }


    public Spell createTerraforming() {
        return new Spell("Terraforming") {
            private ArrayList<Spell> showFieldCards(){
                ArrayList<Spell> fieldCards = new ArrayList<>();
                Deck deck = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getActivatedDeck();
                for (Card card : deck.cards) {
                    if (card instanceof Spell) {
                        if (((Spell) card).icon == Icon.FIELD) {
                            System.out.println(card.cardName + "\t" + card.id);
                            fieldCards.add((Spell) card);
                        }
                    }
                }
                return fieldCards;
            }

            private Spell getSpellByID(String id, ArrayList<Spell> Cards) {
                for (Spell card : Cards)
                    if (card.id.equals(id))
                        return card;
                return null;
            }
            @Override
            public void action() {
                ArrayList<Spell> fieldCards = showFieldCards();
                System.out.println("Choose name and the id of field Card.");
                String id = "";
                Spell field = getSpellByID(id, fieldCards);
            }
        };

    }

    public Spell createHarpiesFeatherDuster() {
        return new Spell("Harpie's Feather Duster") {
            @Override
            public void action() {
                for (SpellAndTrap card : User.getUserByUsername(DuelPageController.getInstance().
                        getCurrentTurnUsername()).getBoard().getSpellOrTrapCards()) {
                    card.isDestroyed = true;
                }
            }
        };
    }

    public Spell createSwordsOfRevealingLight() {
        return new Spell("Swords of Revealing Light"){

            @Override
            public void checkForActionAndEnd() {
                if (this.turnsActivated >= 3)
                    endAction();
            }

            @Override
            public void action() {
                Card[] temp = User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getMonsterCards();
                ArrayList<Monster> monsters = new ArrayList<>();
                for (Card card : temp) {
                    monsters.add((Monster) card);
                }
                this.targets = monsters;
                for (Monster monster : monsters)
                    monster.monsterCanAttack = false;
            }
            @Override
            public void endAction() {

                for (Monster monster : this.targets)
                    monster.monsterCanAttack = true;
                this.isDestroyed = true;
            }
        };
    }

    public Spell createDarkHole() {
        return new Spell("Dark Hole"){
            @Override
            public void action() {
                Monster[] currentUserMonsters =  User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().getMonsterCards();
                Monster[] opponentMonsters =  User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getMonsterCards();
                for (Monster currentUserMonster : currentUserMonsters) {
                    currentUserMonster.isDestroyed = true;
                }

                for (Monster opponentMonster : opponentMonsters) {
                    opponentMonster.isDestroyed = true;
                }
            }
        };
    }


    public Spell createSupplySquad() {
        return new Spell("Supply Squad"){
            @Override
            public void action() {

            }
        };
    }


    public Spell createSpellAbsorption() {
        return new Spell("Spell Absorption") {
            @Override
            public void action() {
            }
        };
    }


    public Spell createMessengerOfPeace() {
        return new Spell("Messenger of peace") {

        };
    }

    public Spell createTwinTwisters() {
        return new Spell("Twin Twisters"){
            @Override
            public void action() {
                System.out.println("Choose on card from your hand to spare");
                Card temp = new Monster("");

            }
        };
    }

    public Spell makeSpell(String cardName) {
        switch (cardName) {
            case "Monster Reborn":
                return createMonsterReborn();
            case "Pot of Greed":
                return createPotOfGreed();
            case "Raigeki":
                return createRaigeki();
            case "Change of Heart":
                return createChangeOfHeart();
            case "Terraforming":
                return createTerraforming();
            case "Harpie's Feather Duster":
                return createHarpiesFeatherDuster();
            case "Swords of Revealing Light":
                return createSwordsOfRevealingLight();
            case "Dark Hole":
                return createDarkHole();
            case "Supply Squad":
                return createSupplySquad();
            case "Messenger of peace":
                return createMessengerOfPeace();
            case "Spell Absorption":
                return createSpellAbsorption();
            case "Twin Twisters":
//                return createTerratigerTheEmpoweredWarrior();
//            case "The Tricky":
//                return createTheTricky();
            default:
                return new Spell(cardName);
        }
    }

    public static CreateSpell getInstance() {
        if (instance == null)
            instance = new CreateSpell();
        return instance;
    }

}
