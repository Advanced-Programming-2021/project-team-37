package view;

import controller.CreateCardPageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class CreateCardPage extends Application {

    private static Stage mainStage;
    public static Text monsterEffectDescription = new Text("");
    public static Text monsterPageResult = new Text("");
    public static Text spellEffectDescription = new Text("");
    public static Text spellPageResult = new Text("");
    public static Text trapEffectDescription = new Text("");
    public static Text trapPageResult = new Text("");
    private static Text monsterPrice = new Text("");
    private ComboBox<? extends String> levelComboBox;
    private TextField attack;
    private TextField defense;
    private Thread priceThread;
    private Timer timer;


    private Button exitButton;


    private void setExitButton() {
        exitButton = new Button("Exit");
        exitButton.setOnAction(actionEvent -> {
            try {
                Page.playButtonClickSound();
                new CreateCardPage().start(mainStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void showPrice() {
        if (levelComboBox == null || attack == null || defense == null)
            return;
        int priceValue = 0;
        int levelValue;
        if (levelComboBox.getValue() == "" || levelComboBox.getValue() == null)
            levelValue = 0;
        else
            levelValue = Integer.parseInt(levelComboBox.getValue());
        int attackValue;
        if (attack.getText().equals(""))
            attackValue = 0;
        else
            attackValue = Integer.parseInt(attack.getText());
        int defenseValue;
        if (defense.getText().equals(""))
            defenseValue = 0;
        else
            defenseValue = Integer.parseInt(defense.getText());
        priceValue += 200 * levelValue + 3 * (attackValue + defenseValue);
        monsterPrice.setText("Price: " + priceValue);
    }



    private void showMonsterEffect(String effectName) {
        CreateCardPageController.getInstance().showMonsterEffect(effectName);
    }

    private void showSpellEffect(String effectName) {
        CreateCardPageController.getInstance().showSpellEffect(effectName);
    }

    private void showTrapEffect(String effectName) {
        CreateCardPageController.getInstance().showTrapEffect(effectName);
    }


    private void createMonster(String cardName, String level, String attribute, String monsterType, String cardType, String attack,
                               String defense, String description, String effectName) {
        CreateCardPageController.getInstance().createMonster(cardName, level, attribute, monsterType, cardType, attack, defense,
                description, effectName);
    }


    public void goToCreateMonsterPage() {
        priceThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> showPrice());
                }
            };
            long frameTimeInMilliseconds = 120;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        priceThread.start();
        monsterEffectDescription = new Text("");
        monsterPageResult = new Text("");
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        Label cardNameLabel = new Label("Card Name: ");
        TextField cardName = new TextField();
        cardName.setPromptText("enter a name");
        gridPane.add(cardNameLabel, 0, 0);
        gridPane.add(cardName, 1, 0);
        ObservableList<String> levels = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");
        levelComboBox = new ComboBox<>(levels);
        Label level = new Label("Level: ");
        gridPane.add(level, 0, 1);
        gridPane.add(levelComboBox, 1, 1);
        ObservableList<String> attributes = FXCollections.observableArrayList("EARTH", "WATER", "DARK", "FIRE", "LIGHT", "WIND");
        ComboBox<? extends String> attributesComboBox = new ComboBox<>(attributes);
        Label attribute = new Label("Attribute: ");
        gridPane.add(attribute, 0, 2);
        gridPane.add(attributesComboBox, 1, 2);
        ObservableList<String> monsterTypes = FXCollections.observableArrayList("Beast-Warrior", "Warrior", "Aqua", "Fiend",
                "Beast", "Pyro", "Spellcaster", "Thunder", "Dragon", "Machine", "Rock", "Insect", "Cyberse", "Fairy", "Sea Serpent");
        ComboBox<? extends String> monsterTypesComboBox = new ComboBox<>(monsterTypes);
        Label monsterType = new Label("Monster Type: ");
        gridPane.add(monsterType, 0, 3);
        gridPane.add(monsterTypesComboBox, 1, 3);
        ObservableList<String> cardTypes = FXCollections.observableArrayList("Normal", "Effect", "Ritual");
        ComboBox<? extends String> cardTypesComboBox = new ComboBox<>(cardTypes);
        Label cardType = new Label("Card Type: ");
        gridPane.add(cardType, 0, 4);
        gridPane.add(cardTypesComboBox, 1, 4);
        Label attackLabel = new Label("Attack: ");
        attack = new TextField();
        attack.setPromptText("enter a number");
        Label defenseLabel = new Label("Defense: ");
        defense = new TextField();
        defense.setPromptText("enter a number");
        Label descriptionLabel = new Label("Description: ");
        TextArea description = new TextArea();
        description.setPromptText("Card description...");
        Label effect = new Label("Effect: ");
        ObservableList<String> effects = FXCollections.observableArrayList("NONE", "Suijin", "Yomi Ship", "Man-Eater Bug", "Scanner",
                "The Calculator");
        ComboBox<? extends String> effectsComboBox = new ComboBox<>(effects);
        gridPane.add(attackLabel, 0, 5);
        gridPane.add(attack, 1, 5);
        gridPane.add(defenseLabel, 0, 6);
        gridPane.add(defense, 1, 6);
        gridPane.add(descriptionLabel, 0, 7);
        gridPane.add(description, 1, 7);
        gridPane.add(effect, 0, 8);
        gridPane.add(effectsComboBox, 1, 8);
        Button showEffect = new Button("Show Effect Description");
        showEffect.setOnAction((actionEvent -> {Page.playButtonClickSound(); showMonsterEffect(effectsComboBox.getValue());}));
        gridPane.add(showEffect, 0, 9);
        gridPane.add(monsterEffectDescription, 1, 9);
        gridPane.add(monsterPrice, 2, 9);
        Button createCard = new Button("Create Card");
        createCard.setOnAction((actionEvent -> {Page.playButtonClickSound(); createMonster(cardName.getText(), levelComboBox.getValue(), attributesComboBox.getValue()
                , monsterTypesComboBox.getValue(), cardTypesComboBox.getValue(), attack.getText(), defense.getText(),
                description.getText(), effectsComboBox.getValue());}));
        gridPane.add(createCard, 1, 10);
        GridPane.setHalignment(createCard, HPos.CENTER);
        gridPane.add(monsterPageResult, 1, 11);
        GridPane.setHalignment(monsterPageResult, HPos.CENTER);
        setExitButton();
        gridPane.add(exitButton, 1, 12);
        GridPane.setHalignment(exitButton, HPos.CENTER);
        Image image = new Image(getClass().getResourceAsStream("/Pictures/Backgrounds-1/secondimage.png"));
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.CENTER , new BackgroundSize(800, 600, false, false, true, true))));
        Scene scene = new Scene(gridPane);
        mainStage.setScene(scene);
        mainStage.show();
    }


    public void goToCreateSpellPage() {
        spellEffectDescription = new Text("");
        spellPageResult = new Text("");
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        Label cardNameLabel = new Label("Card Name: ");
        TextField cardName = new TextField();
        cardName.setPromptText("enter a name");
        gridPane.add(cardNameLabel, 0, 0);
        gridPane.add(cardName, 1, 0);
        ObservableList<String> icons = FXCollections.observableArrayList("Normal", "Counter", "Continuous", "Quick-play",
                "Field", "Equip", "Ritual");
        ComboBox<? extends String> iconsComboBox = new ComboBox<>(icons);
        Label icon = new Label("Icon: ");
        gridPane.add(icon, 0, 1);
        gridPane.add(iconsComboBox, 1, 1);
        Label descriptionLabel = new Label("Description: ");
        TextArea description = new TextArea();
        description.setPromptText("Card description...");
        ObservableList<String> statuses = FXCollections.observableArrayList("Unlimited", "Limited");
        ComboBox<? extends String> statusesComboBox = new ComboBox<>(statuses);
        Label status = new Label("Status: ");
        Label effect = new Label("Effect: ");
        ObservableList<String> effects = FXCollections.observableArrayList("Monster Reborn", "Pot of Greed", "Raigeki",
                "Terraforming", "Harpie's Feather Duster", "Dark Hole", "Supply Squad", "Spell Absorption", "Mystical space typhoon",
                "Yami", "Forest", "Closed Forest", "Umiiruka");
        ComboBox<? extends String> effectsComboBox = new ComboBox<>(effects);
        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(description, 1, 2);
        gridPane.add(status, 0, 3);
        gridPane.add(statusesComboBox, 1, 3);
        gridPane.add(effect, 0, 4);
        gridPane.add(effectsComboBox, 1, 4);
        Button showEffect = new Button("Show Effect Description");
        showEffect.setOnAction((actionEvent -> {Page.playButtonClickSound(); showSpellEffect(effectsComboBox.getValue());}));
        gridPane.add(showEffect, 0, 5);
        gridPane.add(spellEffectDescription, 1, 5);
        Label price = new Label("Price:\t3000");
        gridPane.add(price, 2, 5);
        Button createCard = new Button("Create Card");
        createCard.setOnAction((actionEvent -> {Page.playButtonClickSound(); createSpell(cardName.getText(), iconsComboBox.getValue(),
                description.getText(), statusesComboBox.getValue(), effectsComboBox.getValue());}));
        gridPane.add(createCard, 1, 6);
        GridPane.setHalignment(createCard, HPos.CENTER);
        gridPane.add(spellPageResult, 1, 7);
        GridPane.setHalignment(spellPageResult, HPos.CENTER);
        setExitButton();
        gridPane.add(exitButton, 1, 8);
        GridPane.setHalignment(exitButton, HPos.CENTER);
        Image image = new Image(getClass().getResourceAsStream("/Pictures/Backgrounds-1/secondimage.png"));
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.CENTER , new BackgroundSize(800, 600, false, false, true, true))));
        Scene scene = new Scene(gridPane);
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void createSpell(String cardName, String icon, String description, String status, String effectName) {
        CreateCardPageController.getInstance().createSpell(cardName, icon, description, status, effectName);
    }

    private void createTrap(String cardName, String icon, String description, String status, String effectName) {
        CreateCardPageController.getInstance().createTrap(cardName, icon, description, status, effectName);

    }

    public void goToCreateTrapPage() {
        trapEffectDescription = new Text("");
        trapPageResult = new Text("");
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        Label cardNameLabel = new Label("Card Name: ");
        TextField cardName = new TextField();
        cardName.setPromptText("enter a name");
        gridPane.add(cardNameLabel, 0, 0);
        gridPane.add(cardName, 1, 0);
        ObservableList<String> icons = FXCollections.observableArrayList("Normal", "Counter", "Continuous", "Quick-play",
                "Field", "Equip", "Ritual");
        ComboBox<? extends String> iconsComboBox = new ComboBox<>(icons);
        Label icon = new Label("Icon: ");
        gridPane.add(icon, 0, 1);
        gridPane.add(iconsComboBox, 1, 1);
        Label descriptionLabel = new Label("Description: ");
        TextArea description = new TextArea();
        description.setPromptText("Card description...");
        ObservableList<String> statuses = FXCollections.observableArrayList("Unlimited", "Limited");
        ComboBox<? extends String> statusesComboBox = new ComboBox<>(statuses);
        Label status = new Label("Status: ");
        Label effect = new Label("Effect: ");
        ObservableList<String> effects = FXCollections.observableArrayList("Mirror Force", "Magic Cylinder", "Torrential Tribute",
                "Time Seal", "Call of The Haunted");
        ComboBox<? extends String> effectsComboBox = new ComboBox<>(effects);
        gridPane.add(descriptionLabel, 0, 2);
        gridPane.add(description, 1, 2);
        gridPane.add(status, 0, 3);
        gridPane.add(statusesComboBox, 1, 3);
        gridPane.add(effect, 0, 4);
        gridPane.add(effectsComboBox, 1, 4);
        Button showEffect = new Button("Show Effect Description");
        showEffect.setOnAction((actionEvent -> {Page.playButtonClickSound(); showTrapEffect(effectsComboBox.getValue());}));
        gridPane.add(showEffect, 0, 5);
        gridPane.add(trapEffectDescription, 1, 5);
        Label price = new Label("Price:\t3000");
        gridPane.add(price, 2, 5);
        Button createCard = new Button("Create Card");
        createCard.setOnAction((actionEvent -> {Page.playButtonClickSound(); createTrap(cardName.getText(), iconsComboBox.getValue(),
                description.getText(), statusesComboBox.getValue(), effectsComboBox.getValue());}));
        gridPane.add(createCard, 1, 6);
        GridPane.setHalignment(createCard, HPos.CENTER);
        gridPane.add(trapPageResult, 1, 7);
        GridPane.setHalignment(trapPageResult, HPos.CENTER);
        setExitButton();
        gridPane.add(exitButton, 1, 8);
        GridPane.setHalignment(exitButton, HPos.CENTER);
        Image image = new Image(getClass().getResourceAsStream("/Pictures/Backgrounds-1/secondimage.png"));
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
                , BackgroundPosition.CENTER , new BackgroundSize(800, 600, false, false, true, true))));
        Scene scene = new Scene(gridPane);
        mainStage.setScene(scene);
        mainStage.show();
    }


    @Override
    public void start(Stage stage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("Choose a Card Type to create");
        Button monster = new Button("Monster");
        Button spell = new Button("Spell");
        Button trap = new Button("Trap");
        Button goBack = new Button("Back");
        goBack.setOnAction(actionEvent -> {
            try {
                new MainPage().start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gridPane.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.add(monster, 0, 1);
        GridPane.setHalignment(monster, HPos.CENTER);
        gridPane.add(spell, 0, 2);
        GridPane.setHalignment(spell, HPos.CENTER);
        gridPane.add(trap, 0, 3);
        GridPane.setHalignment(trap, HPos.CENTER);
        gridPane.add(goBack, 0, 4);
        GridPane.setHalignment(goBack, HPos.CENTER);
        monster.setOnAction(actionEvent -> {Page.playButtonClickSound(); goToCreateMonsterPage();});
        spell.setOnAction((actionEvent -> {Page.playButtonClickSound(); goToCreateSpellPage();}));
        trap.setOnAction((actionEvent -> {Page.playButtonClickSound(); goToCreateTrapPage();}));
        mainStage = stage;
        Image image = new Image(getClass().getResourceAsStream("/Pictures/Backgrounds-1/createCardPageBackground.png"));
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT
        , BackgroundPosition.CENTER , new  BackgroundSize(100, 100, true, true, true, false))));
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
