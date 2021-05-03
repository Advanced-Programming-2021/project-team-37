package model;

public class SpellAndTrap extends Card {
    private SpellType type;
    private boolean infinitive;
    public boolean isOnField;


    public void effect() {

    }

    public SpellType getType() {
        return type;
    }

    public void setType(SpellType type) {
        this.type = type;
    }

    public boolean isInfinitive() {
        return infinitive;
    }

    public void setInfinitive(boolean infinitive) {
        this.infinitive = infinitive;
    }

    public boolean isOnField() {
        return isOnField;
    }

    public void setOnField(boolean onField) {
        isOnField = onField;
    }
}
