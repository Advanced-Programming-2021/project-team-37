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
        this.cardType = cardData.get(2);
        this.description = cardData.get(3);
        this.status = cardData.get(4);
        this.price = Integer.parseInt(cardData.get(5));
        this.id = this.toString();
        this.turnsActivated = 0;
    }


    public void effect() {

    }

    public boolean isAttack() {
        return true;
    }


}
