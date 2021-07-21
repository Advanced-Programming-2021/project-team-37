package controller;

import com.opencsv.CSVWriter;
import model.Card;
import model.Monster;
import model.Spell;
import model.Trap;
import view.CreateCardPage;
import view.ShopPage;

import java.io.FileWriter;
import java.io.IOException;

import static model.Monster.monsterData;
import static model.SpellAndTrap.spellData;
import static model.SpellAndTrap.trapData;

public class CreateCardPageController extends Controller {

    private static CreateCardPageController instance;


    private CreateCardPageController() {}

    public static CreateCardPageController getInstance() {
        if (instance == null) {
            instance = new CreateCardPageController();
        }
        return instance;
    }


    public void showMonsterEffect(String effectName) {
        switch (effectName) {
            case "Suijin":
                CreateCardPage.monsterEffectDescription.setText("During damage calculation in your opponent's turn, if this card\n" +
                        "is being attacked: You can target the attacking monster; make that target's ATK 0 during damage\n" +
                        "calculation only (this is a Quick Effect). This effect can only be used once while this card is\n" +
                        "face-up on the field.");
                break;
            case "Yomi Ship":
                CreateCardPage.monsterEffectDescription.setText("If this card is destroyed by battle and sent to the GY: Destroy\n" +
                        "the monster that destroyed this card.");
                break;
            case "Man-Eater Bug":
                CreateCardPage.monsterEffectDescription.setText("FLIP: Target 1 monster on the field; destroy that target.");
                break;
            case "Scanner":
                CreateCardPage.monsterEffectDescription.setText("Once per turn, you can select 1 of your opponent's monsters that\n" +
                        "is removed from play. Until the End Phase, this card's name is treated as the selected monster's\n" +
                        "name, and this card has the same Attribute, Level, ATK, and DEF as the selected monster.\n" +
                        "If this card is removed from the field while this effect is applied, remove it from play.");
                break;
            case "The Calculator":
                CreateCardPage.monsterEffectDescription.setText("The ATK of this card is the combined Levels of all face-up\n" +
                        "monsters you control x 300.");
                break;
            default:
                CreateCardPage.monsterEffectDescription.setText("");
        }
    }


    public void createMonster(String cardName, String level, String attribute, String monsterType, String cardType, String attack,
                            String defense, String description, String effectName) {
        if (cardName == null || level == null || attribute == null || monsterType == null || cardType == null || attack == null ||
                defense == null || description == null || effectName == null) {
            CreateCardPage.monsterPageResult.setText("You have to fill in all of the fields!");
            return;
        }


        int priceValue = 0;
        int levelValue = Integer.parseInt(level);
        int attackValue = Integer.parseInt(attack);
        int defenceValue = Integer.parseInt(defense);
        priceValue += 200 * levelValue + 2 * (attackValue + defenceValue);
        String price = Integer.toString(priceValue);
        String[] entries = {cardName, level, attribute, monsterType, cardType, attack, defense, description, price, effectName};
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Monster.csv", true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(entries);
            csvWriter.close();
            fileWriter.close();
            Card.setData("src/main/resources/Monster.csv", monsterData);
            Monster.monsterData.remove("Name");
            Monster monster = Card.addMonster(cardName, effectName);
            Card.addToShopCards(monster);
            CreateCardPage.monsterPageResult.setText("Card created successfully.");
        } catch (IOException e) {
            CreateCardPage.monsterPageResult.setText("An error occurred!");
        }
    }


    public void showSpellEffect(String effectName) {
        switch (effectName) {
            case "Monster Reborn":
                CreateCardPage.spellEffectDescription.setText("Target 1 monster in either GY; Special Summon it.");
                break;
            case "Pot of Greed":
                CreateCardPage.spellEffectDescription.setText("Draw 2 cards.");
                break;
            case "Raigeki":
                CreateCardPage.spellEffectDescription.setText("Destroy all monsters your opponent controls.");
                break;
            case "Terraforming":
                CreateCardPage.spellEffectDescription.setText("Add 1 Field Spell from your Deck to your hand.");
                break;
            case "Harpie's Feather Duster":
                CreateCardPage.spellEffectDescription.setText("Destroy all Spells and Traps your opponent controls.");
                break;
            case "Dark Hole":
                CreateCardPage.spellEffectDescription.setText("Destroy all monsters on the field.");
                break;
            case "Supply Squad":
                CreateCardPage.spellEffectDescription.setText("Once per turn, if a monster(s) you control is destroyed\n" +
                        "by battle or card effect: Draw 1 card.");
                break;
            case "Spell Absorption":
                CreateCardPage.spellEffectDescription.setText("Each time a Spell Card is activated, gain 500 Life Points\n" +
                        "immediately after it resolves.");
                break;
            case "Mystical space typhoon":
                CreateCardPage.spellEffectDescription.setText("Target 1 Spell/Trap on the field; destroy that target.");
                break;
            case "Yami":
                CreateCardPage.spellEffectDescription.setText("All Fiend and Spellcaster monsters on the field gain 200\n" +
                        "ATK/DEF, also all Fairy monsters on the field lose 200 ATK/DEF.");
                break;
            case "Forest":
                CreateCardPage.spellEffectDescription.setText("All Insect, Beast, Plant, and Beast-Warrior monsters on\n" +
                        "the field gain 200 ATK/DEF.");
                break;
            case "Closed Forest":
                CreateCardPage.spellEffectDescription.setText("All Beast-Type monsters you control gain 100 ATK for each\n" +
                        "monster in your Graveyard. Field Spell Cards cannot be activated. Field Spell Cards cannot be\n" +
                        "activated during the turn this card is destroyed.");
                break;
            case "Umiiruka":
                CreateCardPage.spellEffectDescription.setText("Increase the ATK of all WATER monsters by 500 points and\n" +
                        "decrease their DEF by 400 points.");
                break;
            default:
        }
    }

    public void createSpell(String cardName, String icon, String description, String status, String effectName) {
        if (cardName == null || icon == null || description == null || status == null || effectName == null) {
            CreateCardPage.spellPageResult.setText("You have to fill in all of the fields!");
            return;
        }
        String price = "3500";
        String[] entries = {cardName, "Spell", icon, description, status, price, effectName};
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Spell.csv", true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(entries);
            csvWriter.close();
            fileWriter.close();
            Card.setData("src/main/resources/Spell.csv", spellData);
            spellData.remove("Name");
            Spell spell = Card.addSpell(cardName, effectName);
            Card.addToShopCards(spell);
            CreateCardPage.spellPageResult.setText("Card created successfully.");
        } catch (IOException e) {
            CreateCardPage.spellPageResult.setText("An error occurred!");
        }
    }

    public void createTrap(String cardName, String icon, String description, String status, String effectName) {
        if (cardName == null || icon == null || description == null || status == null || effectName == null) {
            CreateCardPage.trapPageResult.setText("You have to fill in all of the fields!");
            return;
        }
        String price = "3500";
        String[] entries = {cardName, "Trap", icon, description, status, price, effectName};
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Trap.csv", true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(entries);
            csvWriter.close();
            fileWriter.close();
            Card.setData("src/main/resources/Monster.csv", trapData);
            trapData.remove("Name");
            Trap trap = Card.addTrap(cardName, effectName);
            Card.addToShopCards(trap);
            CreateCardPage.spellPageResult.setText("Card created successfully.");
        } catch (IOException e) {
            CreateCardPage.spellPageResult.setText("An error occurred!");
        }
    }

    public void showTrapEffect(String effectName) {
        switch (effectName) {
            case "Mirror Force":
                CreateCardPage.spellEffectDescription.setText("When an opponent's monster declares an attack: Destroy all\n" +
                        "your opponent's Attack Position monsters.");
                break;
            case "Magic Cylinder":
                CreateCardPage.spellEffectDescription.setText("When an opponent's monster declares an attack: Target the\n" +
                        "attacking monster; negate the attack, and if you do, inflict damage to your opponent equal to its ATK.");
                break;
            case "Torrential Tribute":
                CreateCardPage.spellEffectDescription.setText("When a monster(s) is Summoned: Destroy all monsters on the field.");
                break;
            case "Time Seal":
                CreateCardPage.spellEffectDescription.setText("Skip the Draw Phase of your opponent's next turn.");
                break;
            case "Call of The Haunted":
                CreateCardPage.spellEffectDescription.setText("Activate this card by targeting 1 monster in your GY; Special\n" +
                        "Summon that target in Attack Position. When this card leaves the field, destroy that monster.\n" +
                        "When that monster is destroyed, destroy this card.");
            default:
        }
    }
}
