package view;

import client.SendMessage;
import controller.Controller;
import controller.ShopPageController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import model.commands.ShopCommand;
import model.commands.ShopCommandType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AdminShopPage extends Application {

    private static String message;
    private static Card draggedSelectedCard;
    private static Card selectedCard;
    private static ArrayList<Card> shopCards = new ArrayList<>();
    public Label inStockCardsNumber;
    public AnchorPane anchorPane;
    public Button increaseButton;
    public Button decreaseButton;
    public Button forbidBuyingButton;
    public Button startBuyingButton;
    private Thread adminShopThread;
    private int shopPageNumber = 0;
    private Timer timer;

    public GridPane gridPane;
    public Label cardPrice;
    public Text selectedCardDescription;
    public ImageView selectedCardImageView;
    public ImageView rightButton;
    public ImageView leftButton;

    public static void setShopCards(ArrayList<Card> shopCards) {
        AdminShopPage.shopCards = shopCards;
    }

    public static Card getSelectedCard() {
        return selectedCard;
    }

    public static void setSelectedCard(Card selectedCard) {
        AdminShopPage.selectedCard = selectedCard;
    }

    public static ArrayList<Card> getShopCards() {
        return shopCards;
    }

    public static String getMessage() {
        return message;
    }

    public void setCommandPatterns(String commandPatterns) {

    }

    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        Controller.currentUser = null;
        Controller.token = null;
        new Page().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        message = "";
        Parent root = FXMLLoader.load(getClass().getResource("/View/adminShopPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        inStockCardsNumber.setText("");
        showShopCards();
        makeRightButton();
        makeLeftButton();
        makeIncreaseButton();
        makeDecreaseButton();
        makeForbidBuyingButton();
        makeStartBuyingButton();
        updateShop();
    }

    private void makeStartBuyingButton() {
        startBuyingButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ShopCommand shopCommand = new ShopCommand();
                shopCommand.setShopCommandType(ShopCommandType.START_BUYING);
                shopCommand.setCard(selectedCard);
                SendMessage.getSendMessage().sendMessage(shopCommand);
            }
        });

    }

    private void makeForbidBuyingButton() {
        forbidBuyingButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ShopCommand shopCommand = new ShopCommand();
                shopCommand.setShopCommandType(ShopCommandType.FORBID_BUYING);
                shopCommand.setCard(selectedCard);
                SendMessage.getSendMessage().sendMessage(shopCommand);
            }
        });

    }

    private void makeDecreaseButton() {
        decreaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ShopCommand shopCommand = new ShopCommand();
                shopCommand.setCard(selectedCard);
                shopCommand.setShopCommandType(ShopCommandType.DECREASE);
                SendMessage.getSendMessage().sendMessage(shopCommand);
            }
        });
    }

    private void makeIncreaseButton() {
        increaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ShopCommand shopCommand = new ShopCommand();
                shopCommand.setShopCommandType(ShopCommandType.INCREASE);
                shopCommand.setCard(selectedCard);
                SendMessage.getSendMessage().sendMessage(shopCommand);
            }
        });
    }

    private void showNumberOfCardInStock() {
        String output = selectedCard.getAvailableNumberInShop() + " in stock";
        inStockCardsNumber.setText(output);
    }

    public void updateShop() {
        adminShopThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            if (Page.getCurrentMenu() == Menu.SHOP && selectedCard != null
                                    && selectedCard.getCardName().equals(selectedCard.getCardName())) {
                                updateNumberOfAvailableCards();
                                showNumberOfCardInStock();
                                checkSetDisableForbidAndStartBuyingButton();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 200;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        adminShopThread.start();
    }

    private void checkSetDisableForbidAndStartBuyingButton() {
        startBuyingButton.setDisable(!selectedCard.isForbiddenToBuy());
        forbidBuyingButton.setDisable(selectedCard.isForbiddenToBuy());
    }

    private void updateNumberOfAvailableCards() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.UPDATE_NUMBER_OF_AVAILABLE_CARDS);
        shopCommand.setCard(selectedCard);
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
                            draggedSelectedCard = shopCards.get(shopPageNumber * 15 + (5 * finalJ) + finalI);
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
        selectedCard = draggedSelectedCard;
        ShopCommand buyShopCommand = new ShopCommand();
        buyShopCommand.setShopCommandType(ShopCommandType.SELECT_CARD);
        buyShopCommand.setCard(selectedCard);
        buyShopCommand.setUsername(Controller.currentUser.getUsername());
        SendMessage.getSendMessage().sendMessage(buyShopCommand);

        Page.playButtonClickSound();
        Image selectedCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                + selectedCard.getCardName() + ".jpg").toExternalForm());
        selectedCardImageView.setImage(selectedCardImage);
        selectedCardDescription.setText(selectedCard.getDescription());
        int selectedCardPrice = selectedCard.getPrice();
        cardPrice.setText(String.valueOf(selectedCardPrice));
    }
}
