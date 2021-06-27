package model;

import java.util.ArrayList;

public class Trap extends SpellAndTrap {
    public int limitNumber;
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
        this.cardType = cardData.get(1);
        this.icon = Icon.getIcon(cardData.get(2));
        this.cardType = cardData.get(2);
        this.description = cardData.get(3);
        this.status = cardData.get(4);
        this.price = Integer.parseInt(cardData.get(5));
        this.id = this.toString();
        this.spellOrTrapCardState = SpellOrTrapCardState.INACTIVATED;
    }

}
