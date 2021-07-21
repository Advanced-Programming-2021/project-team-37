package view;

import client.SendMessage;
import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Auction;
import model.Card;
import model.commands.ShopCommand;
import model.commands.ShopCommandType;


import java.util.ArrayList;

public class ShowAllOfMyCardsMenu extends Application {
    private Card selectedCard1;
    public ImageView selectedCard;
    public Text selectedCardDescription;
    public GridPane allOfMyCards;
    public Button backButton;
    public ImageView rightButton;
    public ImageView leftButton;
    public AnchorPane anchorPane;
    public Button newAuctionButton;
    public TextField newAuctionStartingPrice;
    public Button seeActiveAuctionsButton;
    private int allOfMyCardsPageNumber = 0;
    private Timeline checkDisableButtonTimeline;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/showAllOfMyCardsMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        makeBackButton();
        makeAllOfMyCards();
        makeRightButton();
        makeLeftButton();

        makeNewAuctionButton();
        makeActiveAuctionsButtons();

        checkDisableButtonTimeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (Page.getCurrentMenu() == Menu.SHOW_ALL_OF_MY_CARDS)
                    checkDisableNewAuctionButton();
            }
        }));
        checkDisableButtonTimeline.setCycleCount(Timeline.INDEFINITE);
        checkDisableButtonTimeline.play();
    }

    private void checkDisableNewAuctionButton() {
        newAuctionButton.setDisable(newAuctionStartingPrice.getText().equals("") || selectedCard1 == null);
    }

    private void makeActiveAuctionsButtons() {
        seeActiveAuctionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.setCurrentMenu(Menu.ACTIVE_AUCTIONS);
                try {
                    new ActiveAuctionsPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeNewAuctionButton() {
        newAuctionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                makeNewAuction();

                Page.setCurrentMenu(Menu.AUCTION);
                try {
                    new AuctionPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeNewAuction() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.MAKE_NEW_AUCTION);
        Auction auction = new Auction(Controller.currentUser.getUsername(), selectedCard1,
                Integer.parseInt(newAuctionStartingPrice.getText()));
        shopCommand.setNewAuction(auction);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    private void makeRightButton() {
        ArrayList<Card> cards = Controller.currentUser.getCards();
        rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (allOfMyCardsPageNumber < Math.floor(cards.size() / 120.0)) allOfMyCardsPageNumber++;
                makeAllOfMyCards();

                leftButton.setDisable(allOfMyCardsPageNumber <= 0);
                rightButton.setDisable(allOfMyCardsPageNumber >= Math.floor(cards.size() / 120.0));
            }
        });

        rightButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                rightButton.setEffect(new Glow());
            }
        });

        rightButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                rightButton.setEffect(null);
            }
        });

    }

    private void makeLeftButton() {
        leftButton.setDisable(true);
        ArrayList<Card> cards = Controller.currentUser.getCards();
        leftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (allOfMyCardsPageNumber > 0) allOfMyCardsPageNumber--;
                makeAllOfMyCards();

                leftButton.setDisable(allOfMyCardsPageNumber <= 0);
                rightButton.setDisable(allOfMyCardsPageNumber >= Math.floor(cards.size() / 120.0));
            }
        });

        leftButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                leftButton.setEffect(new Glow());
            }
        });

        leftButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                leftButton.setEffect(null);
            }
        });
    }

    private void makeBackButton() {
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                try {
                    new ShopPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeAllOfMyCards() {
        allOfMyCards.setDisable(true);
        allOfMyCards.setOpacity(0);
        allOfMyCards = new GridPane();
        allOfMyCards.setLayoutX(72);
        allOfMyCards.setLayoutY(31);
        anchorPane.getChildren().add(allOfMyCards);

        ArrayList<Card> cards = Controller.currentUser.getCards();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 8; j++) {
                int counter = allOfMyCardsPageNumber * 120 + j * 17 + i;
                if (counter < cards.size()) {
                    ImageView cardImage = new ImageView(new Image(getClass().getResource("/Pictures/Cards/AllCards/" +
                            cards.get(counter).getCardName() + ".jpg").toExternalForm()));
                    cardImage.setFitHeight(77);
                    cardImage.setFitWidth(53);

                    cardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            selectedCard1 = cards.get(counter);
                            selectedCard.setImage(cardImage.getImage());
                            selectedCardDescription.setText(cards.get(counter).getDescription());
                        }
                    });

                    cardImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cardImage.setEffect(new Glow());
                        }
                    });

                    cardImage.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cardImage.setEffect(null);
                        }
                    });

                    allOfMyCards.add(cardImage, i, j);
                }
            }
        }
    }
}
