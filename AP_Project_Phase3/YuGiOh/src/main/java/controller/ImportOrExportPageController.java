package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import model.*;
import view.ImportOrExportPage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImportOrExportPageController extends Controller {

    private static ImportOrExportPageController instance;


    private ImportOrExportPageController() {

    }

    public static ImportOrExportPageController getInstance() {
        if (instance == null)
            instance = new ImportOrExportPageController();
        return instance;
    }

    public void importMonster(String cardName) {
        try {
            File src = new File("src/main/resources/importedCards/" + cardName + ".json");
            File dst = new File("src/main/resources/cards/monsters/" + cardName + ".json");
            Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/cards/monsters/" + cardName + ".json")));
            Monster monster = new Gson().fromJson(data, new TypeToken<Monster>() {
            }.getType());
            Card.getCards().add(monster);
            Monster.getMonsters().add(monster);
            if (!Monster.monsterData.containsKey(cardName))
                addMonsterToCSV(monster);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importSpell(String cardName) {
        try {
            File src = new File("src/main/resources/importedCards/" + cardName + ".json");
            File dst = new File("src/main/resources/cards/spells/" + cardName + ".json");
            Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/cards/spells/" + cardName + ".json")));
            Spell spell = new Gson().fromJson(data, new TypeToken<Spell>() {
            }.getType());
            Card.getCards().add(spell);
            Spell.getSpells().add(spell);
            if (Spell.spellData.containsKey(cardName))
                addSpellAndTrapToCSV(spell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void importTrap(String cardName) {
        try {
            File src = new File("src/main/resources/importedCards/" + cardName + ".json");
            File dst = new File("src/main/resources/cards/traps/" + cardName + ".json");
            Files.copy(src.toPath(), dst.toPath(), StandardCopyOption.REPLACE_EXISTING);
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/cards/traps/" + cardName + ".json")));
            Trap trap = new Gson().fromJson(data, new TypeToken<Trap>() {
            }.getType());
            Card.getCards().add(trap);
            Trap.getTraps().add(trap);
            if (!Trap.trapData.containsKey(cardName))
                addSpellAndTrapToCSV(trap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSpellAndTrapToCSV(SpellAndTrap card) {
        String cardName = card.getCardName();
        String cardType = card.getCardType();
        String icon = card.getIcon().toString();
        String description = card.getDescription();
        String status = card.getStatus();
        String price = Integer.toString(card.getPrice());
        String effectName = card.getEffectName();
        String[] entries = {cardName, cardType, icon, description, status, price, effectName};
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/" + cardType + ".csv", true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(entries);
            csvWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addMonsterToCSV(Monster card) {
        String cardName = card.getCardName();
        String level = Integer.toString(card.getLevel());
        String attribute = card.getAttribute();
        String monsterType = card.getMonsterType();
        String cardType = card.getCardType();
        String attack = Integer.toString(card.getOriginalAttack());
        String defense = Integer.toString(card.getOriginalDefense());
        String description = card.getDescription();
        String price = Integer.toString(card.getPrice());
        String effectName = card.getEffectName();
        String[] entries = {cardName, level, attribute, monsterType, cardType, attack, defense, description, price, effectName};
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/Monster.csv", true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            csvWriter.writeNext(entries);
            csvWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importCard(String cardName) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(new FileReader("src/main/resources/importedCards/"
                    + cardName + ".json"));
            String cardType = jsonObject.get("cardType").getAsString();
            switch (cardType) {
                case "Monster":
                    importMonster(cardName);
                    break;
                case "Spell":
                    importSpell(cardName);
                    break;
                case "Trap":
                    importTrap(cardName);
                    break;
            }
            ImportOrExportPage.setMessage("Card was imported successfully!");
        } catch (FileNotFoundException e) {
            ImportOrExportPage.setMessage("File doesn't exist!");
        }
    }

    public void exportCard(String cardName) {
        boolean cardExists = false;
        Card.setCards();
        for (Card card : Card.getCards()) {
            if (card.getCardName().equals(cardName)) {
                cardExists = true;
                break;
            }
        }
        if (!cardExists) {
            ImportOrExportPage.setMessage("Card doesn't exist!");
            return;
        }
        Card card = Card.getCardByName(cardName);
        if (card instanceof Monster) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/exportedCards/monsters/" + cardName + ".json");
                jsonWriter.write(new Gson().toJson((Monster) card));
                jsonWriter.close();
                ImportOrExportPage.setMessage("Card was exported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (card instanceof Spell) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/exportedCards/spells/" + cardName + ".json");
                jsonWriter.write(new Gson().toJson((Spell) card));
                jsonWriter.close();
                ImportOrExportPage.setMessage("Card was exported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (card instanceof Trap) {
            try {
                FileWriter jsonWriter = new FileWriter("src/main/resources/exportedCards/traps/" + cardName + ".json");
                jsonWriter.write(new Gson().toJson((Trap) card));
                jsonWriter.close();
                ImportOrExportPage.setMessage("Card was exported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Card.setCards();
//        ImportOrExportPageController.getInstance().exportCard("Black Pendant");
//        ImportOrExportPageController.getInstance().exportCard("Feral Imp");
        ImportOrExportPageController.getInstance().importCard("Feral Imp");
//        ImportOrExportPageController.getInstance().exportCard("Torrential Tribute");
//        ImportOrExportPageController.getInstance().importCard("Torrential Tribute");

    }
}
