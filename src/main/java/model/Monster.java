package model;

public class Monster extends Card {
    private String monsterType;
    public int level;
    protected int attack;
    private int defense;
    private boolean isUsed;
    private boolean usedEffect;

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUsedEffect() {
        return usedEffect;
    }

    public void setUsedEffect(boolean usedEffect) {
        this.usedEffect = usedEffect;
    }

    public Monster(String cardName) {

    }


    public void specialConjuring() {

    }

    public void conjureDeffenseCard() {

    }

    public void destroyEnemyAttackerCard() {

    }

    public void removeOneCardAndConjuringFromGraveyard() {

    }

    public void calculateAttack() {

    }


    public void commonConjuring() {

    }

    public void reduceLifeOfEnemy() {

    }

    public void destroyOneOfEnemyMonsters() {

    }

    public void makeEnemyMonsterAttackZero(Monster monster) {

    }

    public void addAttackToAllCardsOfBoard() {

    }
}
