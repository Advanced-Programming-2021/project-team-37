package view;

import controller.DeckPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Card;
import model.Deck;
import model.User;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;

public class DeckPage extends Application {

    public static Card selectedCard;
    private static String message;
    private static Stage mainStage;


    public GridPane deckGrid;
    public ListView<ImageView> deckListOfCards;
    public ImageView cardShow;
    public ListView<String> deckList;
    private static Deck mainDeck;
    public GridPane sideDeckGrid;




    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        DeckPage.message = message;
    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {


    }


    private void createDeck() {
        String deckName = "Deck " + (User.getUserByUsername(DeckPageController.getInstance().getUsername()).getDecks().size() + 1);
        DeckPageController.getInstance().createDeck(deckName);
        System.out.println(message);
    }


    public static void setMainDeck(Deck mainDeck) {
        DeckPage.mainDeck = mainDeck;
    }

    private void deleteDeck() {
        String deckName = mainDeck.getDeckName();
        DeckPageController.getInstance().deleteDeck(deckName);
        System.out.println(message);
    }

    private void setActiveDeck(String name) {
        DeckPageController.getInstance().setActiveDeck(name);
        System.out.println(message);
    }

    private void addCardToDeck(String cardName, String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().addCardToDeck(cardName, deckName, isSide);
        System.out.println(message);
    }

    private void showDeck(String deckName, String side) {
        boolean isSide = false;
        if (side != null) isSide = side.equals(" --side");
        DeckPageController.getInstance().showMainOrSideDeckByName(deckName, isSide);
    }

    private void removeCardFromDeck(String cardName, boolean isSide) {
        String deckName = mainDeck.getDeckName();
        DeckPageController.getInstance().removeCardFromDeck(cardName, deckName, isSide);
        System.out.println(message);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/View/deckPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    public void initialize() throws FileNotFoundException {
        try {
            deckListOfCards.getSelectionModel().selectedItemProperty().addListener(event -> {
                ObservableList<? extends Integer> selectedIndices = deckListOfCards.getSelectionModel().getSelectedIndices();
                selectedCard = Card.getCards().get(Integer.parseInt(selectedIndices.get(0).toString()));
                cardShow.setImage(new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/"
                        + selectedCard.getCardName() + ".jpg")));
            });
            deckList.getSelectionModel().selectedItemProperty().addListener(event -> {
                ObservableList<? extends Integer> selectedIndices = deckList.getSelectionModel().getSelectedIndices();
                if (selectedIndices.size() != 0) {
                    mainDeck = User.getUserByUsername(DeckPageController.getInstance().getUsername())
                            .getDecks().get(Integer.parseInt(selectedIndices.get(0).toString()));
                    updateGrid();
                }
            });
        } catch (Exception e) {
            System.out.println(selectedCard.getCardName());
        }
        for (Card card : Card.getCards()) {
            try {
                Image image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(124);
                imageView.setFitWidth(75);
                deckListOfCards.getItems().add(imageView);
            } catch (Exception e) {
                System.out.println(card.getCardName());
            }
        }

        for (Deck deck : User.getUserByUsername(DeckPageController.getInstance().getUsername()).getDecks()) {
            deckList.getItems().add(deck.getDeckName());
        }
        createGrid();
    }

    private void createGrid() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                try {
                    Image image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/Unknown.jpg"));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(124);
                    imageView.setFitWidth(75);
                    imageView.setId("mainDeckCard" + i + "_" + j);
                    deckGrid.add(imageView, j, i);
                    int finalJ = j;
                    int finalI = i;
                    imageView.setOnMouseClicked(event -> {
                        if (mainDeck != null && finalI * 10 + finalJ < mainDeck.getMainDeckCards().size()) {
                            selectedCard = mainDeck.getMainDeckCards().get(finalI * 10 + finalJ);
                            cardShow.setImage(new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/"
                                    + selectedCard.getCardName() + ".jpg")));
                        }
                    });
                } catch (Exception ignored) {

                }
            }
        }
        for (int j = 0; j < 15; j++) {
            try {
                Image image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/Unknown.jpg"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(124);
                imageView.setFitWidth(75);
                imageView.setId("sideDeckCard" + j);
                sideDeckGrid.add(imageView, j, 0);
                int finalJ = j;
                imageView.setOnMouseClicked(event -> {
                    if (mainDeck != null && finalJ < mainDeck.getSideDeckCards().size()) {
                        selectedCard = mainDeck.getSideDeckCards().get(finalJ);
                        cardShow.setImage(new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/"
                                + selectedCard.getCardName() + ".jpg")));
                    }
                });
            } catch (Exception ignored) {
                System.out.println(selectedCard.getCardName());
            }
        }
    }


    private void updateGrid() {
        int a = 0, b = 0;
        if (mainDeck == null) {
            a = 1000;
            b = 1000;
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                try {
                    Image image = null;
                    if (mainDeck == null || a >= mainDeck.getMainDeckCards().size()) {
                        image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/Unknown.jpg"));
                    } else {
                        image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/" +
                                mainDeck.getMainDeckCards().get(a).getCardName() + ".jpg"));
                    }
                    ImageView imageView = (ImageView) deckGrid.lookup("#mainDeckCard" + i + "_" + j);
                    imageView.setImage(image);
                    a++;
                } catch (Exception ignored) {

                }
            }
        }
        for (int j = 0; j < 15; j++) {
            try {
                Image image = null;
                if (mainDeck == null || b >= mainDeck.getSideDeckCards().size()) {
                    image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/Unknown.jpg"));
                } else {
                    image = new Image(getClass().getResourceAsStream("/Pictures/Cards/AllCards/" +
                            mainDeck.getSideDeckCards().get(b).getCardName() + ".jpg"));
                }
                ImageView imageView = (ImageView) sideDeckGrid.lookup("#sideDeckCard" + j);
                imageView.setImage(image);
                b++;
            } catch (Exception ignored) {

            }
        }
    }

    private void updateDeckList() {
        deckList.getItems().clear();
        for (Deck deck : User.getUserByUsername(DeckPageController.getInstance().getUsername()).getDecks()) {
            deckList.getItems().add(deck.getDeckName());
        }
        if (deckList.getItems().size() != 0) {
            deckList.getSelectionModel().select(deckList.getItems().size() - 1);
        } else mainDeck = null;
    }

    private void activateDeck() {
        String deckName = mainDeck.getDeckName();
        DeckPageController.getInstance().setActiveDeck(deckName);
        System.out.println(message);
    }

    private void addCardToDeck(String cardName, boolean isSide) {
        User user = User.getUserByUsername(DeckPageController.getInstance().getUsername());
        String deckName = mainDeck.getDeckName();
        DeckPageController.getInstance().addCardToDeck(cardName, deckName, isSide);
    }

    public void addToMainDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null || selectedCard == null) return;
        addCardToDeck(selectedCard.getCardName(), false);
        updateGrid();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        Page.playButtonClickSound();
        new MainPage().start(mainStage);
    }

    public void createDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        createDeck();
        updateGrid();
        updateDeckList();
    }

    public void removeCardFromMainDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null || selectedCard == null) return;
        removeCardFromDeck(selectedCard.getCardName(), false);
        updateGrid();
    }

    public void removeCardFromSideDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null || selectedCard == null) return;
        removeCardFromDeck(selectedCard.getCardName(), true);
        updateGrid();
    }

    public void addToSideDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null || selectedCard == null) return;
        addCardToDeck(selectedCard.getCardName(), true);
        updateGrid();
    }

    public void activateDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null) return;
        activateDeck();
        updateGrid();
    }

    public void deleteDeck(MouseEvent mouseEvent) {
        Page.playButtonClickSound();
        if (mainDeck == null) return;
        deleteDeck();
        updateDeckList();
        updateGrid();
    }


    public void run(String[] args) {
        launch(args);
    }
}
