package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Monster extends Card {
    public static HashMap<String, ArrayList<String>> monsterData = new HashMap<>();


    protected String originalName;
    private String monsterType;
    protected int level;
    protected final int originalAttack;
    protected int attack;
    protected final int originalDefense;
    protected int defense;
    protected Monster target;
    protected Monster beingTargetedBy;
    private boolean isUsed;
    protected boolean usedEffect;
    protected boolean usedEffectsInThisTurn;
    protected boolean isSpecialSummoned;
    protected CardState state;
    protected boolean opponentHasAccess;
    protected boolean monsterCanAttack;
    protected int roundsToResetAttack = 1;
    protected int roundsToResetDefense = 1;
    protected boolean hasSetEffect = true;

    public static ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public static void setMonsters(ArrayList<Monster> monsters) {
        Monster.monsters = monsters;
    }

    public static ArrayList<Monster> monsters = new ArrayList<>();

    public static void main(String[] args) {
        setData("src/main/resources/Monster.csv", monsterData);
        for (String key : monsterData.keySet()) {
            System.out.println("\"" + key + "\"");
            System.out.println(monsterData.get(key));
            System.out.println("------------------------------------------------");
        }
    }

    public Monster(String cardName) {
        ArrayList<String> cardData = monsterData.get(cardName);
        this.originalName = cardName;
        this.cardName = cardName;
        this.level = Integer.parseInt(cardData.get(1));
        this.attribute = cardData.get(2);
        this.monsterType = cardData.get(3);
        this.cardType = cardData.get(4);
        this.originalAttack = Integer.parseInt(cardData.get(5));
        this.originalDefense = Integer.parseInt(cardData.get(6));
        this.attack = Integer.parseInt(cardData.get(5));
        this.defense = Integer.parseInt(cardData.get(6));
        this.description = cardData.get(7);
        this.price = Integer.parseInt(cardData.get(8));
        this.id = this.toString();
        this.monsterCanAttack = true;
        if (cardName.equals("Scanner"))
            this.hasSetEffect = false;
        this.calculatePower();
    }

    public static HashMap<String, ArrayList<String>> getMonsterData() {
        return monsterData;
    }

    public static void setMonsterData(HashMap<String, ArrayList<String>> monsterData) {
        Monster.monsterData = monsterData;
    }


    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

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

    public int getOriginalAttack() {
        return originalAttack;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getOriginalDefense() {
        return originalDefense;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public Monster getTarget() {
        return target;
    }

    public void setTarget(Monster target) {
        this.target = target;
    }

    public Monster getBeingTargetedBy() {
        return beingTargetedBy;
    }

    public void setBeingTargetedBy(Monster beingTargetedBy) {
        this.beingTargetedBy = beingTargetedBy;
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

    public boolean isUsedEffectsInThisTurn() {
        return usedEffectsInThisTurn;
    }

    public void setUsedEffectsInThisTurn(boolean usedEffectsInThisTurn) {
        this.usedEffectsInThisTurn = usedEffectsInThisTurn;
    }

    public boolean isSpecialSummoned() {
        return isSpecialSummoned;
    }

    public void setSpecialSummoned(boolean specialSummoned) {
        isSpecialSummoned = specialSummoned;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    public boolean isOpponentHasAccess() {
        return opponentHasAccess;
    }

    public void setOpponentHasAccess(boolean opponentHasAccess) {
        this.opponentHasAccess = opponentHasAccess;
    }

    public boolean isMonsterCanAttack() {
        return monsterCanAttack;
    }

    public void setMonsterCanAttack(boolean monsterCanAttack) {
        this.monsterCanAttack = monsterCanAttack;
    }

    public String newToString() {
        return "name: " + this.cardName + "\n" +
                "level: " + this.level + "\n" +
                "attribute: " + this.attribute + "\n" +
                "monster type: " + this.monsterType + "\n" +
                "card type: " + this.cardType + "\n" +
                "attack: " + this.originalAttack + "\n" +
                "defense: " + this.originalDefense + "\n" +
                "description: " + this.description + "\n" +
                "price: " + this.price + "\n";
    }

    @Override
    public void runAction() {
    }

    @Override
    public void action() {
    }

    @Override
    public void action(int selected, int target) {

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
    public void actionWhenFlipped(int selected) {

    }

    @Override
    public void actionWhenAttacked() {
    }

    @Override
    public void actionWhenDestroyed(int selected, int target) {

    }

    @Override
    public void actionWhenDestroyed() {

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


    public void copyThisCard(Monster card) {
        this.cardName = card.cardName;
        this.level = card.level;
        this.attribute = card.attribute;
        this.monsterType = card.monsterType;
        this.cardType = card.cardType;
        this.attack = card.originalAttack;
        this.defense = card.originalDefense;
        this.description = card.description;
        this.price = card.price;
    }


    public void copyCard(Monster target) {
        copyThisCard(target);
    }

    public void revertToOrigin() {
        Monster origin = new Monster(this.originalName);
        copyThisCard(origin);
    }

    public void conjureDefenceCard() {

    }

    public void removeCardFromHand() {

    }


    public void commonConjuring() {

    }


    public int getRoundsToResetAttack() {
        return roundsToResetAttack;
    }

    public int getRoundsToResetDefense() {
        return roundsToResetDefense;
    }

    public void setHasSetEffect(boolean hasSetEffect) {
        this.hasSetEffect = hasSetEffect;
    }

    public boolean getHasSetEffect() {
        return this.hasSetEffect;
    }


}
