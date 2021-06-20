package model;

import javax.swing.*;
import java.util.ArrayList;

public class Trap extends SpellAndTrap {
    private final Object icon;
    public int limitNumber;
    public String id;
    public static ArrayList<Trap> traps = new ArrayList<>();

    public static ArrayList<Trap> getTraps() {
        return traps;
    }

    public static void setTraps(ArrayList<Trap> traps) {
        Trap.traps = traps;
    }

    public Trap(String cardName) {
        ArrayList<String> cardData = trapData.get(cardName);
        this.originalName = cardName;
        this.cardName = cardName;
        this.type = cardData.get(1);
        this.icon = Icon.getIcon(cardData.get(2));
        this.description = cardData.get(3);
        this.status = cardData.get(4);
        this.price = Integer.parseInt(cardData.get(5));
        this.id = this.toString();
    }



    public void askUser() {

    }

    public void reverseAttack(Object Card)
    {

    }

    public void destroyMonsters()
    {

    }

    public void guessCard()
    {

    }

    public void discardOponnent()
    {

    }

    public void discardYourself()
    {

    }

    public void checkEnemyMonster()
    {

    }

    public void disableNextDraw()
    {

    }

    public void disableAttack()
    {

    }

    public void looseLp()
    {

    }

    public void disableSummon()
    {

    }

    public void looseCard()
    {

    }

    public void disableSpell()
    {

    }

    public void summonFromGraveyard()
    {

    }
}
