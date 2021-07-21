package view;

import client.SendMessage;
import controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Auction;
import model.commands.CommandClass;
import model.commands.ShopCommand;
import model.commands.ShopCommandType;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class AuctionPage extends Application {

    public Text selectedCardDescription;
    public ImageView selectedCardImageView;
    public Label winnerPrice;
    public Label cardPrice;
    public Text numberOfBoughtCard;
    public Label auctionCreatorUsername;
    public Label auctionWinnerUsername;
    public Text stopWatchText;
    public Label startingPrice;
    public Text auctionFinalWinner;
    public TextField addAmountPrice;
    public Button bidButton;
    public Label newBidMessage;
    public Label currentUserUsername;
    public Label userMoney;
    private Timeline timeline;
    private Thread auctionThread;

    private static int secs = 30, millis = 0;

    private static Auction auction;
    private Timeline updateAuctionTimeLine;
    private Timeline finishAuctionTimeline;
    private Timer timer;

    public static Auction getAuction() {
        return auction;
    }

    public static void setAuction(Auction auction) {
        AuctionPage.auction = auction;
    }

    public void exitMenu(ActionEvent actionEvent) {
        back();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/auctionPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        setStopWatch();

        addAmountPrice.setDisable(Controller.currentUser.getUsername().equals(auction.getAuctionCreatorUsername()));
        if (Controller.currentUser.getUsername().equals(auction.getAuctionCreatorUsername())) {
            addAmountPrice.setOpacity(0);
            bidButton.setOpacity(0);
        }

        currentUserUsername.setText(Controller.currentUser.getUsername());
        userMoney.setText(String.valueOf(Controller.currentUser.getMoney()));

        makeStopWatch();
        checkDisableBidButton();

        auctionCreatorUsername.setText(auction.getAuctionCreatorUsername());
        selectedCardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                + auction.getCard().getCardName() + ".jpg").toExternalForm()));
        selectedCardDescription.setText(auction.getCard().getDescription());

        startingPrice.setText(String.valueOf(auction.getStartingPrice()));

        showAuction();
        updateAuction();
        makeBidButton();
    }

    private void setStopWatch() {
        if (auction.isHadBid() && secs < 10) {
            int resetTime = 0;
            resetTime += auction.getResetTimeMillis() * 1;
            resetTime += auction.getResetTimeSecond() * 1000;
            resetTime += auction.getResetTimeMinute() * 1000 * 60;
            resetTime += auction.getResetTimeHour() * 1000 * 60 * 60;

            int numberOfNow = 0;
            LocalDateTime now = LocalDateTime.now();
            numberOfNow += (now.getNano() / 1000000) * 1;
            numberOfNow += now.getSecond() * 1000;
            numberOfNow += now.getMinute() * 1000 * 60;
            numberOfNow += now.getHour() * 1000 * 60 * 60;

            int passedTimeNumber = numberOfNow - resetTime;
            int s = passedTimeNumber / 1000;
            int m = passedTimeNumber % 1000;

            if (m == 0) {
                millis = 0;
                secs = 10 - s;
            } else {
                millis = 1000 - m;
                secs = 9 - s;
            }
        } else {
            int startTimeNumber = 0;
            startTimeNumber += auction.getStartedTimeMillis() * 1;
            startTimeNumber += auction.getStartedTimeSecond() * 1000;
            startTimeNumber += auction.getStartedTimeMinute() * 1000 * 60;
            startTimeNumber += auction.getStartedTimeHour() * 1000 * 60 * 60;

            int numberOfNow = 0;
            LocalDateTime now = LocalDateTime.now();
            numberOfNow += (now.getNano() / 1000000) * 1;
            numberOfNow += now.getSecond() * 1000;
            numberOfNow += now.getMinute() * 1000 * 60;
            numberOfNow += now.getHour() * 1000 * 60 * 60;

            int passedTimeNumber = numberOfNow - startTimeNumber;
            int s = passedTimeNumber / 1000;
            int m = passedTimeNumber % 1000;

            if (m == 0) {
                millis = 0;
                secs = 30 - s;
            } else {
                millis = 1000 - m;
                secs = 29 - s;
            }
        }

    }


    private void makeBidButton() {
        bidButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                while (auction == null) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ShopCommand shopCommand = new ShopCommand();
                shopCommand.setShopCommandType(ShopCommandType.NEW_BID_IN_AUCTION);
                shopCommand.setAddAmountPrice(Integer.parseInt(addAmountPrice.getText()));
                shopCommand.setUser(Controller.currentUser);
                shopCommand.setNewAuction(auction);
                SendMessage.getSendMessage().sendMessage(shopCommand);
                addAmountPrice.clear();
            }
        });


    }

    private void updateAuction() {
        auctionThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (Page.getCurrentMenu() == Menu.AUCTION && auction != null) {
                            updateAuctionFromServer();

                            showAuction();
                            checkDisableBidButton();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 200;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        auctionThread.start();
    }

    private void updateAuctionFromServer() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.UPDATE_AUCTION);
        while (auction == null) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        shopCommand.setNewAuction(auction);
        shopCommand.setUser(Controller.currentUser);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    private void checkDisableBidButton() {
        bidButton.setDisable(addAmountPrice.getText().equals("")
                || auction.getWinnerUsername().equals(Controller.currentUser.getUsername())
                || auction.getAuctionCreatorUsername().equals(Controller.currentUser.getUsername())
                || Controller.currentUser.getMoney() < Integer.parseInt(winnerPrice.getText())
                + Integer.parseInt(addAmountPrice.getText()));
    }

    private void showAuction() {
        userMoney.setText(String.valueOf(Controller.currentUser.getMoney()));
        auctionWinnerUsername.setText(auction.getWinnerUsername());
        winnerPrice.setText(String.valueOf(auction.getWinnerPrice()));
        setStopWatch();
    }

    private void checkAuctionFinalWinner() {
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deleteAuction();
                back();
            }
        });
    }

    private void back() {

        Page.setCurrentMenu(Menu.SHOW_ALL_OF_MY_CARDS);
        try {
            new ShowAllOfMyCardsMenu().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAuction() {
        if (Controller.currentUser.getUsername().equals(auction.getAuctionCreatorUsername())){
            ShopCommand shopCommand = new ShopCommand();
            shopCommand.setNewAuction(auction);
            shopCommand.setShopCommandType(ShopCommandType.DELETE_AUCTION);
            SendMessage.getSendMessage().sendMessage(shopCommand);
        }
    }

    private void makeStopWatch() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeStopWatchText();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
    }

    private void changeStopWatchText() {
        if (secs < 0 || millis < 0) {
            secs = 0;
            millis = 0;
        }

        if (secs <= 0 && millis <= 0) {
            secs = 0;
            millis = 0;
            auctionFinalWinner.setText(auctionWinnerUsername.getText() + " is winner");
            timeline.stop();
            bidButton.setDisable(true);
            timer.cancel();
            deleteAuction();
        } else if (millis == 0) {
            secs--;
            millis = 999;
        }

        stopWatchText.setText((((secs / 10) == 0) ? "0" : "") + secs + ":"
                + (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis--);
    }

}
