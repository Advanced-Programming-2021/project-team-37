package view;

import controller.ShopPageController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
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


public class ShopPage extends Application {
    private static String message;
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
    private int shopPageNumber = 0;
    private String selectedCardName;

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
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    private void buyCardByCardName(String cardName) throws InterruptedException {
        ShopPageController.getInstance().buyCard(cardName);
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
        String currentUsername = ShopPageController.getInstance().getUsername();
        username.setText(currentUsername);
        userMoney.setText(String.valueOf(User.getUserByUsername(currentUsername).getMoney()));
        showShopCards();
        rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (shopPageNumber < 5) shopPageNumber++;
                showShopCards();
            }
        });

        leftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (shopPageNumber > 0) shopPageNumber--;
                showShopCards();
            }
        });

        makeBuyButton();
        makeShowAllOfMyCardsButton();

        showNumberOfBoughtCard();
    }

    private void makeShowAllOfMyCardsButton() {
        showAllOfMyCardsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
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
                if (selectedCardName != null) {
                    try {
                        buyCardByCardName(selectedCardName);
                        buyMessage.setText(message);
                        buyMessage.setOpacity(1);
                        fadeTransition();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String currentUsername = ShopPageController.getInstance().getUsername();
                    userMoney.setText(String.valueOf(User.getUserByUsername(currentUsername).getMoney()));

                    buyButton.setDisable(Card.getCardByName(selectedCardName).getPrice()
                            > User.getUserByUsername(currentUsername).getMoney());
                    showNumberOfBoughtCard();

                }
            }
        });
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

        Card.getShopCards();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                int counter = shopPageNumber * 15 + 5 * j + i;
                if (counter < Card.getShopCards().size()) {
                    Image cardImage;
                    try {
                        cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                                + Card.getShopCards().get(counter).getCardName() + ".jpg").toExternalForm());
                    } catch (Exception e) {
                        cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
                    }
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
                            selectedCardName = Card.getShopCards().get(shopPageNumber * 15 + (5 * finalJ) + finalI).getCardName();
                        }
                    });

                    gridPane.add(imageView, i, j);
                }

            }
        }
    }

    public void handleDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.ANY);
    }

    public void handleDrop(DragEvent dragEvent) {
        Page.playButtonClickSound();
        Image selectedCardImage;
        try {
            selectedCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + selectedCardName + ".jpg").toExternalForm());
        } catch (Exception e) {
            selectedCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
        }

        selectedCard.setImage(selectedCardImage);
        selectedCardDescription.setText(Card.getCardByName(selectedCardName).getDescription());
        int selectedCardPrice = Card.getCardByName(selectedCardName).getPrice();
        cardPrice.setText(String.valueOf(selectedCardPrice));
        String currentUsername = ShopPageController.getInstance().getUsername();
        buyButton.setDisable(selectedCardPrice > User.getUserByUsername(currentUsername).getMoney());
        showNumberOfBoughtCard();
    }

    public int howManyCardsAlreadyBoughtWithCardName() {
        String currentUsername = ShopPageController.getInstance().getUsername();
        int counter = 0;
        for (Card boughtCard : User.getUserByUsername(currentUsername).getAllBoughtCards()) {
            if (selectedCardName != null && boughtCard != null)
                if (boughtCard.getCardName().equals(selectedCardName)) counter++;
        }
        return counter;
    }
}
