package model;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static model.Monster.monsterData;
import static model.SpellAndTrap.spellData;
import static model.SpellAndTrap.trapData;

public class Card {
    protected String cardName;
    protected String description;
    protected int price;
    protected boolean HaveCardPositionChangedInThisTurn = false;

    public CardState cardState;
    protected SpellOrTrapCardState spellOrTrapCardState; // maybe it is better to send this to SpellAndTrapCard
    public String statusOnField;
    public static ArrayList<Card> cards = new ArrayList<>(); // this is all cards
    private static ArrayList<Card> shopCards = new ArrayList<>();
    protected String cardType;
    protected boolean isCardSelected = false;
    protected String effectName;
    private boolean isForbiddenToBuy;
    private int availableNumberInShop;

    public static ArrayList<Card> getShopCards() {
        return shopCards;
    }

    public static void setShopCards(ArrayList<Card> shopCards) {
        Card.shopCards = shopCards;
    }

    protected boolean isCardSetPositionInThisTurn = false;
    protected boolean cardAlreadyAttackedInThisTurn = false;

    protected String attribute;
    public String status;
    protected String position;
    protected boolean isDestroyed;
    public String id;

    public Card(String cardName) {
        this.cardName = cardName;
    }

    public Card() {

    }

    public String getStatus() {
        return this.status;
    }

    public String getAttribute() {
        return this.attribute;
    }

    public void setCardState(CardState cardState) {
        this.cardState = cardState;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static boolean isCardWithThisNameExists(String cardName) {
        for (Card card : cards) {
            if (card.cardName.equals(cardName)) return true;
        }
        return false;
    }

    public static Card getCardByName(String name) {
        Card temp = null;
        for (Card card : cards) {
            if (card.cardName.equals(name)) {
                temp = card;
                break;
            }
        }
        return temp;
    }

    public static void setCards() {
        setData("src/main/resources/Monster.csv", monsterData);
        monsterData.remove("Name");
        setData("src/main/resources/Spell.csv", spellData);
        spellData.remove("Name");
        setData("src/main/resources/Trap.csv", trapData);
        trapData.remove("Name");
        trapData.remove("");
        for (String cardName : monsterData.keySet()) {
            Monster monster = CreateMonster.getInstance().makeMonster(cardName, monsterData.get(cardName).get(8));
            cards.add(monster);
            Monster.getMonsters().add(monster);
        }
        for (String cardName : spellData.keySet()) {
            Spell spell = CreateSpell.getInstance().makeSpell(cardName, spellData.get(cardName).get(6));
            cards.add(spell);
            Spell.getSpells().add(spell);
        }

        for (String cardName : Trap.trapData.keySet()) {
            Trap trap = CreateTrap.getInstance().makeTrap(cardName, trapData.get(cardName).get(6));
            cards.add(trap);
            Trap.getTraps().add(trap);
        }
        cards.sort(new SortByCardName());
        Monster.getMonsters().sort(new SortByCardName());
        Trap.getTraps().sort(new SortByCardName());
        Spell.getSpells().sort(new SortByCardName());

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setAvailableNumberInShop(10);
        }
    }


    public static Monster addMonster(String cardName, String effectName) {
        setData("src/main/resources/Monster.csv", monsterData);
        monsterData.remove("Name");
        Monster monster = CreateMonster.getInstance().makeMonster(cardName, effectName);
        cards.add(monster);
        Monster.getMonsters().add(monster);
        return monster;
    }

    public static Spell addSpell(String cardName, String effectName) {
        setData("src/main/resources/Spell.csv", spellData);
        spellData.remove("Name");
        Spell spell = CreateSpell.getInstance().makeSpell(cardName, effectName);
        cards.add(spell);
        Spell.getSpells().add(spell);
        return spell;
    }

    public static Trap addTrap(String cardName, String effectName) {
        setData("src/main/resources/Trap.csv", trapData);
        trapData.remove("Name");
        Trap trap = CreateTrap.getInstance().makeTrap(cardName, effectName);
        cards.add(trap);
        Trap.getTraps().add(trap);
        return trap;
    }


    public String getEffectName() {
        return effectName;
    }

    static class SortByCardName implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return a.getCardName().compareTo(b.getCardName());
        }
    }

    public boolean isCardAlreadyAttackedInThisTurn() {
        return cardAlreadyAttackedInThisTurn;
    }

    public void setCardAlreadyAttackedInThisTurn(boolean cardAlreadyAttackedInThisTurn) {
        this.cardAlreadyAttackedInThisTurn = cardAlreadyAttackedInThisTurn;
    }


    public boolean isCardSetInThisTurn() {
        return isCardSetInThisTurn;
    }

    public void setCardSetInThisTurn(boolean cardSetInThisTurn) {
        isCardSetInThisTurn = cardSetInThisTurn;
    }

    protected boolean isCardSetInThisTurn = false;

    public boolean isCardSetPositionInThisTurn() {
        return isCardSetPositionInThisTurn;
    }

    public void setCardSetPositionInThisTurn(boolean cardSetPositionInThisTurn) {
        isCardSetPositionInThisTurn = cardSetPositionInThisTurn;
    }

    public boolean isHaveCardPositionChangedInThisTurn() {
        return HaveCardPositionChangedInThisTurn;
    }

    public void setHaveCardPositionChangedInThisTurn(boolean haveCardPositionChangedInThisTurn) {
        HaveCardPositionChangedInThisTurn = haveCardPositionChangedInThisTurn;
    }

    public CardState getCardState() {
        return cardState;
    }

    public SpellOrTrapCardState getSpellOrTrapCardState() {
        return spellOrTrapCardState;
    }

    public void setSpellOrTrapCardState(SpellOrTrapCardState spellOrTrapCardState) {
        this.spellOrTrapCardState = spellOrTrapCardState;
    }

    public void createCard(String cardType, String cardName) {

    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusOnField() {
        return statusOnField;
    }

    public void setStatusOnField(String statusOnField) {
        this.statusOnField = statusOnField;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public static void setData(String file, HashMap<String, ArrayList<String>> data) {
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(nextRecord));
                data.put(temp.get(0), temp);
            }
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public String getCardType() {
        return this.cardType;
    }

    public void runAction() {
    }

    ;

    public void action() {
    }

    ;

    public void action(int selected, int target) {
    }

    public void action(Monster target) {
    }

    public void action(Card target) {
    }

    public void action(User target) {
    }

    public void action(boolean state) {
    }

    public void action(int selected) {
    }

    public void actionWhenFlipped(int selected) {
    }

    public void actionWhenAttacked() {
    }

    public void actionWhenDestroyed(int selected, int target) {
    }

    public void actionWhenDestroyed() {
    }

    public void calculatePower() {
    }

    public void actionWhenSummoned() {
    }

    public void endAction() {
    }

    public void checkForActionAndExecute() {

    }

    public void checkForActionAndEnd() {
        endAction();
    }

    public static void updateCardsDatabase() {
        for (Monster monster : Monster.getMonsters()) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/cards/monsters/" + monster.getCardName() + ".json");
                jsonWriter.write(new YaGson().toJson(monster, Monster.class));
                jsonWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Spell spell : Spell.getSpells()) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/cards/spells/" + spell.getCardName() + ".json");
                jsonWriter.write(new YaGson().toJson(spell, Spell.class));
                jsonWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (Trap trap : Trap.getTraps()) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/cards/traps/" + trap.getCardName() + ".json");
                jsonWriter.write(new YaGson().toJson(trap, Trap.class));
                jsonWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isForbiddenToBuy() {
        return isForbiddenToBuy;
    }

    public void setForbiddenToBuy(boolean forbiddenToBuy) {
        isForbiddenToBuy = forbiddenToBuy;
    }


    public int getAvailableNumberInShop() {
        return availableNumberInShop;
    }

    public void setAvailableNumberInShop(int availableNumberInShop) {
        this.availableNumberInShop = availableNumberInShop;
    }


    public static void addToShopCards(Card card) {
        shopCards.add(card);
    }

    public static void main(String[] args) {
        setCards();
        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName() + "\t" + card.getCardType());
        }
    }
}