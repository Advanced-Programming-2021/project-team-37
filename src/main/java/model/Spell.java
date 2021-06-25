package model;

import java.util.ArrayList;

public class Spell extends SpellAndTrap {

    private int counterMonsterCards;
    private int counter;
    protected int turnsActivated;
    protected Monster target;
    protected ArrayList<Monster> targets;

    public static ArrayList<Spell> getSpells() {
        return spells;
    }

    public static void setSpells(ArrayList<Spell> spells) {
        Spell.spells = spells;
    }

    public static ArrayList<Spell> spells = new ArrayList<>();


    public Spell(String cardName) {
        ArrayList<String> cardData = spellData.get(cardName);
        this.originalName = cardName;
        this.cardName = cardName;
        this.cardType = cardData.get(1);
        this.icon = Icon.getIcon(cardData.get(2));
        this.description = cardData.get(3);
        this.status = cardData.get(4);
        this.price = Integer.parseInt(cardData.get(5));
        this.id = this.toString();
        this.turnsActivated = 0;
    }


    public void effect() {

    }

    public void transferToGraveYard(String cardName)
    {

    }

    public void scanMonsterCards(String input)
    {

    }

    public void selectNormalMonsterCard()
    {

    }

    public boolean typeWarrior(String cardName) {
        return true;
    }

    public boolean isEqual() {
        return true;
    }

    public boolean isAttack() {
        return true;
    }

    public void increaseAttackPower()
    {

    }

    public void increaseDefencePower()
    {

    }



    public boolean isMonsterCardUp(String cardName) {
        return true;
    }





    public boolean isEquipped() {
        return true;
    }

    public void increaseAttack()
    {

    }


    public void decreaseDefencePower()
    {

    }

    public int scanGraveYardCards(String input) {
        return 0;
    }


    public void scanMonsterCard(String input)
    {

    }


    public void scanMonsterCards()
    {

    }

    public void increaseDefence()
    {

    }

    public void decreaseDefence()
    {

    }

    public void Neutralizer(String input)
    {

    }

    public void removeCards(String input)
    {

    }

}
