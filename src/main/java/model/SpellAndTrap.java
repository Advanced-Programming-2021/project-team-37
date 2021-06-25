package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellAndTrap extends Card {

    public static HashMap<String, ArrayList<String>> spellData = new HashMap<>();
    public static HashMap<String, ArrayList<String>> trapData = new HashMap<>();

    protected String originalName;
    protected Icon icon;
    private boolean isUsed;
    protected boolean usedEffect;
    protected boolean usedEffectsInThisTurn;
    public boolean isOnField;
    protected CardState state;

    public static void main(String[] args) {
        setData("src/main/resources/Spell.csv", spellData);
        setData("src/main/resources/Trap.csv", trapData);
        for (String key : spellData.keySet()) {
            System.out.println("\"" + key + "\"");
            System.out.println(spellData.get(key));
            System.out.println("------------------------------------------------");
        }
        for (String key : trapData.keySet()) {
            System.out.println("\"" + key + "\"");
            System.out.println(trapData.get(key));
            System.out.println("------------------------------------------------");
        }
    }


    public Icon getIcon() {
        return this.icon;
    }

    public void effect() {

    }

    @Override
    public void action() {

    }

    @Override
    public void action(Monster target) {

    }

    @Override
    public void action(Card target) {

    }

    @Override
    public void action(User target) {

    }

    @Override
    public void actionWhenAttacked() {

    }

    @Override
    public void calculatePower() {

    }

    @Override
    public void actionWhenSummoned() {

    }

    @Override
    public void endAction() {

    }
}
