package view;

import client.SendMessage;
import controller.Controller;
import controller.ShopPageController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Card;
import model.User;

import model.commands.CommandClass;
import model.commands.ShopCommand;
import model.commands.ShopCommandType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class ShopPage extends Application {
    private static String message;
    private static Card cardToBuy;
    private static Card selectedCardToBuy;
    private static ArrayList<Card> shopCards = new ArrayList<>();
    public Label inStockCardsNumber;
    public ImageView forbiddenSign;
    private Thread shopThread;
    private int shopPageNumber = 0;
    private Timer timer;

    public GridPane gridPane;
    public Label cardPrice;
    public Button buyButton;
    public Text numberOfBoughtCard;
    public Text selectedCardDescription;
    public ImageView selectedCard;
    public Label userMoney;
    public Label username;
    public ImageView rightButton;
    public ImageView leftButton;
    public Label buyMessage;
    public Button showAllOfMyCardsButton;
    public AnchorPane anchorPane;

    public static Card getSelectedCardToBuy() {
        return selectedCardToBuy;
    }

    public static void setSelectedCardToBuy(Card selectedCardToBuy) {
        ShopPage.selectedCardToBuy = selectedCardToBuy;
    }

    public static ArrayList<Card> getShopCards() {
        return shopCards;
    }

    public static void setShopCards(ArrayList<Card> shopCards) {
        ShopPage.shopCards = shopCards;
    }

    public static String getMessage() {
        return message;
    }


    public static void setMessage(String message) {
        ShopPage.message = message;
    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        Page.setCurrentMenu(Menu.MAIN);
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        message = "";
        Parent root = FXMLLoader.load(getClass().getResource("/View/shopPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        inStockCardsNumber.setText("");

        username.setText(Controller.currentUser.getUsername());
        userMoney.setText(String.valueOf(Controller.currentUser.getMoney()));
        showShopCards();
        makeRightButton();
        makeLeftButton();

        makeBuyButton();
        makeShowAllOfMyCardsButton();

        updateShop();
    }

    private void showNumberOfCardInStock() {
        String output = selectedCardToBuy.getAvailableNumberInShop() + " in stock";
        inStockCardsNumber.setText(output);
    }

    public void updateShop() {
        shopThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            if (Page.getCurrentMenu() == Menu.SHOP && selectedCardToBuy != null
                                    && cardToBuy.getCardName().equals(selectedCardToBuy.getCardName())) {
                                updateNumberOfAvailableCards();
                                showNumberOfCardInStock();
                                setShopCardsFromServer();
                                checkSetDisableBuyButton();
                                checkSetDisableForbiddenSign();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 100;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        shopThread.start();
    }

    private void checkSetDisableForbiddenSign() {
        if (selectedCardToBuy.isForbiddenToBuy()) forbiddenSign.setOpacity(1);
        else forbiddenSign.setOpacity(0);
    }

    private void updateNumberOfAvailableCards() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.UPDATE_NUMBER_OF_AVAILABLE_CARDS);
        shopCommand.setCard(selectedCardToBuy);
        shopCommand.setUser(Controller.currentUser);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    private void makeLeftButton() {
        leftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (shopPageNumber > 0) shopPageNumber--;
                showShopCards();
            }
        });
    }

    private void makeRightButton() {
        rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (shopPageNumber < 4) shopPageNumber++;
                showShopCards();
            }
        });
    }

    private void makeShowAllOfMyCardsButton() {
        showAllOfMyCardsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.setCurrentMenu(Menu.SHOW_ALL_OF_MY_CARDS);
                Page.playButtonClickSound();
                try {
                    new ShowAllOfMyCardsMenu().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeBuyButton() {
        buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (selectedCardToBuy.getCardName() != null) {
                    ShopCommand buyShopCommand = new ShopCommand();
                    buyShopCommand.setShopCommandType(ShopCommandType.BUY_CARD);
                    buyShopCommand.setCard(selectedCardToBuy);
                    buyShopCommand.setToken(Controller.token);
                    SendMessage.getSendMessage().sendMessage(buyShopCommand);

                    buyMessage.setText(message);
                    buyMessage.setOpacity(1);
                    fadeTransition();
                    userMoney.setText(String.valueOf(Controller.currentUser.getMoney()));
                    showNumberOfCardInStock();

                    checkSetDisableBuyButton();


                    showNumberOfBoughtCard();
                }
            }
        });
    }

    private void checkSetDisableBuyButton() {
        buyButton.setDisable(selectedCardToBuy.getPrice()
                > Controller.currentUser.getMoney() || selectedCardToBuy.getAvailableNumberInShop() <= 0
                || selectedCardToBuy.isForbiddenToBuy());
    }

    private void showNumberOfBoughtCard() {
        if (howManyCardsAlreadyBoughtWithCardName() > 0)
            numberOfBoughtCard.setText("you have already bought " + howManyCardsAlreadyBoughtWithCardName()
                    + " number of this card");
        else numberOfBoughtCard.setText("");
    }

    private void fadeTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setByValue(1);
        fadeTransition.setDelay(Duration.millis(1000));
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setToValue(0);
        fadeTransition.setNode(buyMessage);
        fadeTransition.play();
    }

    private void showShopCards() {
        gridPane.setDisable(true);
        gridPane.setOpacity(0);
        gridPane = new GridPane();
        gridPane.setLayoutX(100);
        gridPane.setLayoutY(131);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        anchorPane.getChildren().add(gridPane);


        setShopCardsFromServer();

        while (shopCards.size() < 75) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                int counter = shopPageNumber * 15 + 5 * j + i;
                if (counter < shopCards.size()) {
                    Image cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                            + shopCards.get(counter).getCardName() + ".jpg").toExternalForm());
                    ImageView imageView = new ImageView(cardImage);
                    imageView.setFitHeight(153.5);
                    imageView.setFitWidth(105.25);
                    int finalJ = j;
                    int finalI = i;
                    imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            Page.playButtonClickSound();
                            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent clipboardContent = new ClipboardContent();
                            clipboardContent.putImage(imageView.getImage());
                            dragboard.setContent(clipboardContent);
                            cardToBuy = shopCards.get(shopPageNumber * 15 + (5 * finalJ) + finalI);
                            //selectedCard.setCardName(shopCards.get(shopPageNumber * 15 + (5 * finalJ) + finalI).getCardName());
                        }
                    });
                    gridPane.add(imageView, i, j);
                }
            }
        }
    }

    private void setShopCardsFromServer() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.GET_SHOP_CARDS);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    public void handleDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void handleDrop(DragEvent dragEvent) {
        selectedCardToBuy = cardToBuy;

        ShopCommand buyShopCommand = new ShopCommand();
        buyShopCommand.setShopCommandType(ShopCommandType.SELECT_CARD);
        buyShopCommand.setCard(selectedCardToBuy);
        buyShopCommand.setUsername(Controller.currentUser.getUsername());
        SendMessage.getSendMessage().sendMessage(buyShopCommand);

        Page.playButtonClickSound();
        Image selectedCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                + selectedCardToBuy.getCardName() + ".jpg").toExternalForm());
        selectedCard.setImage(selectedCardImage);
        selectedCardDescription.setText(selectedCardToBuy.getDescription());
        int selectedCardPrice = selectedCardToBuy.getPrice();
        cardPrice.setText(String.valueOf(selectedCardPrice));
        checkSetDisableBuyButton();
        showNumberOfBoughtCard();
        showNumberOfCardInStock();
    }

    public int howManyCardsAlreadyBoughtWithCardName() {
        int counter = 0;
        for (Card boughtCard : Controller.currentUser.getCards()) {
            if (selectedCardToBuy.getCardName() != null && boughtCard != null)
                if (boughtCard.getCardName().equals(selectedCardToBuy.getCardName())) counter++;
        }
        return counter;
    }
}
