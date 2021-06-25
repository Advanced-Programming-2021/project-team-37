package model;



public class CreateCard {

    private static CreateCard instance = null;


    private CreateCard() {

    }


    public Card creatCardByTypeAndName(CardType type, String cardName) {
        if (type == CardType.MONSTER)
            return CreateMonster.getInstance().makeMonster(cardName);
        if (type == CardType.SPELL)
            return CreateSpell.getInstance().makeSpell(cardName);
        return CreateTrap.getInstance().makeTrap(cardName);
    }


    public static CreateCard getInstance() {
        if (instance == null)
            instance = new CreateCard();
        return instance;
    }
}
