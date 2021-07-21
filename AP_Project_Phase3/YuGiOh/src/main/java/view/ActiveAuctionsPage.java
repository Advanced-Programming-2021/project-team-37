package view;

import client.SendMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Auction;
import model.commands.CommandClass;
import model.commands.ShopCommand;
import model.commands.ShopCommandType;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActiveAuctionsPage extends Application {

    public ListView activeAuctionsListView;
    public Button openSelectedAuctionButton;
    public Text selectedAuctionInformation;
    private Thread activeAuctionsThread;
    private Timer timer;
    private static ArrayList<Auction> activeAuctions = new ArrayList<>();
    private static Auction selectedActiveAuction;

    public static ArrayList<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public static void setActiveAuctions(ArrayList<Auction> activeAuctions) {
        ActiveAuctionsPage.activeAuctions = activeAuctions;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/activeAuctionsPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        showListOfActiveAuctions();
        updateActiveAuctions();
    }

    private void updateActiveAuctions() {
        activeAuctionsThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            if (Page.getCurrentMenu() == Menu.ACTIVE_AUCTIONS) {
                                showListOfActiveAuctions();
                                checkDisableOpenSelectedAuctionButton();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 500;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        activeAuctionsThread.start();
    }

    private void checkDisableOpenSelectedAuctionButton() {
        openSelectedAuctionButton.setDisable(selectedActiveAuction == null);
    }

    private void showListOfActiveAuctions() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.GET_ACTIVE_AUCTIONS);
        SendMessage.getSendMessage().sendMessage(shopCommand);

        activeAuctionsListView.getItems().clear();
        for (Auction activeAuction : activeAuctions) {
            Label label = new Label();
            label.setText("Auctions creator username: " + activeAuction.getAuctionCreatorUsername() + " _ Card name: "
                    + activeAuction.getCard().getCardName() + " _ Starting price: " + activeAuction.getStartingPrice() +
                    " _ Winner price: " + activeAuction.getWinnerPrice());
            activeAuctionsListView.getItems().add(label);
        }

        activeAuctionsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int selectedItemIndex = activeAuctionsListView.getSelectionModel().getSelectedIndex();
                selectedActiveAuction = activeAuctions.get(selectedItemIndex);

                selectedAuctionInformation.setText("Auctions creator username: "
                        + selectedActiveAuction.getAuctionCreatorUsername() + "\r\nCard name: "
                        + selectedActiveAuction.getCard().getCardName() + "\r\nStarting price: "
                        + selectedActiveAuction.getStartingPrice()
                        + "\r\nWinner price: " + selectedActiveAuction.getWinnerPrice());
            }
        });
    }

    public void openSelectedActiveAuction() {
        getSelectedActiveAuctionFromServer();

        Page.setCurrentMenu(Menu.AUCTION);
        try {
            new AuctionPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSelectedActiveAuctionFromServer() {
        ShopCommand shopCommand = new ShopCommand();
        shopCommand.setShopCommandType(ShopCommandType.OPEN_SELECTED_AUCTION);
        shopCommand.setNewAuction(selectedActiveAuction);
        SendMessage.getSendMessage().sendMessage(shopCommand);
    }

    public void back(ActionEvent actionEvent) {
        Page.setCurrentMenu(Menu.SHOW_ALL_OF_MY_CARDS);
        try {
            new ShowAllOfMyCardsMenu().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
