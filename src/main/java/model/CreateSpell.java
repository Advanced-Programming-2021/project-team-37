package model;

import controller.DuelPageController;

import java.util.*;

public class CreateSpell {

    private static CreateSpell instance = null;


    private CreateSpell() {

    }



    public Spell createMonsterReborn() {
        return new Spell("Monster Reborn") {
            @Override
            public void action() {
                DuelPageController duelPageController = DuelPageController.getInstance();
                if (duelPageController.howManyMonsterFieldAreOccupied(duelPageController.getCurrentTurnUsername()) == 5) {
                    System.out.println("Monster Zone is full!");
                    this.setSpellOrTrapCardState(SpellOrTrapCardState.SET);
                }
                else {
                    System.out.println("Choose a Graveyard! (My GY/Opponent's GY)");
                    Scanner input = new Scanner(System.in);
                    String answer = input.nextLine();
                    if (answer.equals("My GY")) {
                        summonFromGV(duelPageController.getCurrentTurnUsername(), duelPageController, input);
                    }
                    else if (answer.equals("Opponent's GY")) {
                        summonFromGV(duelPageController.getOpponentUsername(), duelPageController, input);
                    }
                    else {
                        System.out.println("invalid command!");
                        this.setSpellOrTrapCardState(SpellOrTrapCardState.SET);
                    }
                }
            }

            private void summonFromGV(String username, DuelPageController duelPageController, Scanner input) {
                boolean done = false;
                int index = 0;
                System.out.println("Monster cards in Graveyard:");
                for (Card graveyardCard : User.getUserByUsername(username).getBoard().getGraveyardCards()) {
                    if (graveyardCard instanceof Monster)
                        System.out.println("index: "  + index + "\t" + "Monster: " + graveyardCard.getCardName());
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
                        User.getUserByUsername(username).getBoard().getGraveyardCards().remove(card);
                        done = true;
                        System.out.println("Monster summoned successfully! (Monster Reborn effect)");
                    }
                    else
                        System.out.println("Selected Card is not a Monster");
                }
            }
        };
    }


    public Spell createPotOfGreed() {
        return new Spell("Pot of Greed") {
            @Override
            public void action() {
                DuelPageController.getInstance().drawCard(DuelPageController.getInstance().getCurrentTurnUsername());
                DuelPageController.getInstance().drawCard(DuelPageController.getInstance().getCurrentTurnUsername());
                System.out.println("Two cards were added to your hand from top of your main deck (Pot of Greed effect)");
            }
        };
    }

    public Spell createRaigeki() {
        return new Spell("Raigeki"){
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

                System.out.println("You destroyed all of your opponent's Monster cards on board (Raigeki effect)");
            }
        };
    }


    public Spell createTerraforming() {
        return new Spell("Terraforming") {

            private ArrayList<Spell> showFieldCards(){
                ArrayList<Spell> fieldCards = new ArrayList<>();
                ArrayList<Card> cards = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                        .getMainDeckCards();
                for (Card card : cards) {
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
                boolean done = false;
                ArrayList<Spell> fieldCards = showFieldCards();
                Scanner input = new Scanner(System.in);
                while (!done) {
                    System.out.println("Select Field Card by ID");
                    String id = input.nextLine();
                    Spell field = getSpellByID(id, fieldCards);
                    if (field == null)
                        System.out.println("There is no Field Card with this ID!");
                    else {
                        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                .getInHandCards().add(field);
                        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                .getMainDeckCards().remove(field);
                        System.out.println("Field Card was added to your hand from Main Deck (Terraforming effect)");
                        Collections.shuffle(User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard()
                                .getMainDeckCards());
                        done = true;
                    }
                }

            }
        };

    }

    public Spell createHarpiesFeatherDuster() {
        return new Spell("Harpie's Feather Duster") {
            @Override
            public void action() {
                for (Card spellOrTrap : User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard()
                        .getSpellOrTrapCards())
                    if (spellOrTrap != null)
                        User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getGraveyardCards()
                                .add(spellOrTrap);
                for (int i = 0; i < 5; i++) {
                    User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                            .getBoard().getSpellOrTrapCards()[i] = null;
                }
                if (User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getFieldCard() != null) {
                    User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().getGraveyardCards()
                            .add(User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                                    .getBoard().getFieldCard());
                    User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).getBoard().setFieldCard(null);
                }
                System.out.println("You destroyed all of your opponent's Spell and Trap cards on board (Harpie's Feather Duster effect)");
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

                System.out.println("You destroyed all of Monster cards on board (Raigeki effect)");
            }
        };
    }


    public Spell createSupplySquad() {
        return new Spell("Supply Squad"){
            @Override
            public void action(User user) {
                if (user.hasLostMonsters) {
                    Scanner input = new Scanner(System.in);
                    System.out.println("You can add a Card from your deck to your hand! would you like to? <Yes/No> (Supply Squad effect)");
                    String answer = input.nextLine();
                    while (!answer.equals("No")) {
                        if (answer.equals("Yes")) {
                            DuelPageController.getInstance().drawCard(user.getUsername());
                            break;
                        }
                        else
                            System.out.println("Invalid respond!");
                        answer = input.nextLine();
                    }
                }
            }
        };
    }


    public Spell createSpellAbsorption() {
        return new Spell("Spell Absorption") {
            @Override
            public void action(User user) {
                user.setLifePoints(user.getLifePoints() + 500);
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


    public Spell createMysticalSpaceTyphoon() {
        return new Spell("Mystical space typhoon") {
            @Override
            public void action() {
                boolean done = false;
                HashMap<User, SpellAndTrap> spellAndTraps = getSpellAndTrapCards();
                System.out.println("Spell and Trap cards on board:");
                for (User user : spellAndTraps.keySet()) {
                    System.out.println("card owner: " + user.getUsername() + "  " + "card: " + spellAndTraps.get(user).getCardName()
                     + "  " + "ID: " + spellAndTraps.get(user).id);
                }

                System.out.println("Select a Spell or Trap Card By ID to Destroy:");
                Scanner input = new Scanner(System.in);
                while (!done) {
                    SpellAndTrap card = getSpellOrTrapById(input.nextLine());
                    if (card != null) {
                        User user = getUser(card);
                        Objects.requireNonNull(user).getBoard().getGraveyardCards().add(card);
                        if (card.getIcon() == Icon.FIELD) {
                            user.getBoard().setFieldCard(null);
                        }
                        else
                            user.getBoard().getSpellOrTrapCards()[getIndex(user, card)] = null;
                        System.out.println("Card: " + card.getCardName() + " was destroyed! (Mystical space typhoon effect)");
                        done = true;
                    }
                    else
                        System.out.println("There No card with this ID!");
                }
            }

            private HashMap<User, SpellAndTrap> getSpellAndTrapCards() {
                HashMap<User, SpellAndTrap> cards = new HashMap<>();
                for (SpellAndTrap spellOrTrapCard : User.getUserByUsername(DuelPageController.getInstance()
                        .getCurrentTurnUsername()).getBoard().getSpellOrTrapCards()) {
                    if (spellOrTrapCard != null)
                        cards.put(User.getUserByUsername(DuelPageController.getInstance()
                                .getCurrentTurnUsername()), spellOrTrapCard);
                }
                for (SpellAndTrap spellOrTrapCard : User.getUserByUsername(DuelPageController.getInstance()
                        .getOpponentUsername()).getBoard().getSpellOrTrapCards()) {
                    if (spellOrTrapCard != null)
                        cards.put(User.getUserByUsername(DuelPageController.getInstance()
                                .getOpponentUsername()), spellOrTrapCard);
                }
                cards.put(User.getUserByUsername(DuelPageController.getInstance()
                        .getCurrentTurnUsername()), (SpellAndTrap) User.getUserByUsername(DuelPageController.getInstance()
                        .getCurrentTurnUsername()).getBoard().getFieldCard());
                cards.put(User.getUserByUsername(DuelPageController.getInstance()
                        .getOpponentUsername()), (SpellAndTrap) User.getUserByUsername(DuelPageController.getInstance()
                        .getOpponentUsername()).getBoard().getFieldCard());
                return cards;
            }

            private SpellAndTrap getSpellOrTrapById(String id) {
                HashMap<User, SpellAndTrap> spellAndTraps = getSpellAndTrapCards();
                for (SpellAndTrap card : spellAndTraps.values()) {
                    if (card.id.equals(id))
                        return card;
                }
                return null;
            }

            private User getUser(SpellAndTrap card) {
                HashMap<User, SpellAndTrap> spellAndTraps = getSpellAndTrapCards();
                for (User user : spellAndTraps.keySet()) {
                    if (spellAndTraps.get(user).id.equals(card.id))
                        return user;
                }
                return null;
            }
            private int getIndex(User user, SpellAndTrap card) {
                int index = 1;
                for ( ; index < 6; index++) {
                    if (user.getBoard().getSpellOrTrapCards()[index].id.equals(card.id))
                        return index;
                }
                return index;
            }
        };
    }

    public Spell createYami() {
        return new Spell("Yami") {
            @Override
            public void action() {
                DuelPageController.getInstance().setCurrentUserFiledCard(this);
            }

            @Override
            public void action(boolean state) {
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
            }
        };
    }

    private void applyEffect(Monster monsterCard) {
        if (monsterCard != null) {
            if (monsterCard.getMonsterType().equals("Fiend") || monsterCard.getMonsterType().equals("Spellcaster")) {
                monsterCard.setAttack(monsterCard.getAttack() + 200);
                monsterCard.setDefense(monsterCard.getDefense() + 200);
            }
            else if (monsterCard.getMonsterType().equals("Fairy")) {
                monsterCard.setAttack(monsterCard.getAttack() - 200);
                monsterCard.setDefense(monsterCard.getDefense() - 200);
            }
        }
    }


    public Spell createForest() {
        return new Spell("Forest") {
            @Override
            public void action() {
                DuelPageController.getInstance().setCurrentUserFiledCard(this);
            }

            @Override
            public void action(boolean state) {
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
            }

            private void applyEffect(Monster monsterCard) {
                if (monsterCard != null) {
                    if (monsterCard.getMonsterType().equals("Beast") || monsterCard.getMonsterType().equals("Insect")
                            || monsterCard.getMonsterType().equals("Beast-Warrior")) {
                        monsterCard.setAttack(monsterCard.getAttack() + 200);
                        monsterCard.setDefense(monsterCard.getDefense() + 200);
                    }
                }
            }
        };
    }


    public Spell createClosedForest() {
        return new Spell("Closed Forest") {
            @Override
            public void action() {
                DuelPageController.getInstance().setCurrentUserFiledCard(this);
            }

            @Override
            public void action(boolean state) {
                int graveyardSize = User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getGraveyardCards().size();
                graveyardSize += User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                        .getBoard().getGraveyardCards().size();
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getMonsterCards())
                    if (monsterCard != null)
                        if (monsterCard.getMonsterType().equals("Beast"))
                            monsterCard.setAttack(monsterCard.getAttack() + (graveyardSize * 200));
            }
        };
    }

    public Spell createUmiiruka() {
        return new Spell ("Umiiruka") {
            @Override
            public void action() {
                DuelPageController.getInstance().setCurrentUserFiledCard(this);
            }

            @Override
            public void action(boolean state) {
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
                for (Monster monsterCard : User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername())
                        .getBoard().getMonsterCards()) {
                    applyEffect(monsterCard);
                }
            }

            private void applyEffect(Monster monsterCard) {
                if (monsterCard != null) {
                    if (monsterCard.getMonsterType().equals("Aqua")) {
                        monsterCard.setAttack(monsterCard.getAttack() + 500);
                        monsterCard.setDefense(monsterCard.getDefense() - 400);
                    }
                }
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
            case "Terraforming":
                return createTerraforming();
            case "Harpie's Feather Duster":
                return createHarpiesFeatherDuster();
            case "Dark Hole":
                return createDarkHole();
            case "Supply Squad":
                return createSupplySquad();
            case "Spell Absorption":
                return createSpellAbsorption();
            case "Mystical space typhoon":
                return createMysticalSpaceTyphoon();
            case "Yami":
                return createYami();
            case "Forest":
                return createForest();
            case "Closed Forest":
                return createClosedForest();
            case "Umiiruka":
                return createUmiiruka();
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
