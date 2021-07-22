package client;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.Controller;
import controller.DuelPageController;
import controller.MainPageController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.User;
import model.commands.*;
import view.*;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReadMessage extends Thread {

    private static DuelPage currentDuelPage;
    private static LobbyPage currentLobbyPage;
    private static LoginPage currentLoginPage;
    private static RegisterPage currentRegisterPage;


    private Alert requestAlert;


    private Scanner scanner;
    private CommandClass command;


    public ReadMessage(Socket socket) {
        try {
            this.scanner = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoginPage getCurrentLoginPage() {
        return currentLoginPage;
    }

    public static void setCurrentLoginPage(LoginPage currentLoginPage) {
        ReadMessage.currentLoginPage = currentLoginPage;
    }

    public static RegisterPage getCurrentRegisterPage() {
        return currentRegisterPage;
    }

    public static void setCurrentRegisterPage(RegisterPage currentRegisterPage) {
        ReadMessage.currentRegisterPage = currentRegisterPage;
    }


    @Override
    public void run() {
        while (true) {
            String command;
            try {
                command = scanner.nextLine();
            } catch (Exception e) {
                break;
            }
            YaGson yaGson = new YaGsonBuilder().create();
            this.command = yaGson.fromJson(command, CommandClass.class);
            System.out.println("In Message Reader");
            switch (this.command.getCommandType()) {
                case REGISTER -> handleRegisterCommand((RegisterCommand) this.command);
                case SCOREBOARD -> handleScoreboardCommand((ScoreboardCommand) this.command);
                case LOGIN -> handleLoginCommand((LoginCommand) this.command);
                case SHOP -> handleShopCommand((ShopCommand) this.command);
                case BATTLE -> handleBattleCommand((BattleCommand) this.command);
                case CHAT -> handleChatRoomCommand((ChatRoomCommand) this.command);
                case ONLINE_PLAYERS -> handleOnlinePlayersCommand((OnlinePlayersCommand) this.command);
                case TV -> handleTVCommand((TVCommand) this.command); //TODO TVCommand
            }
        }
    }

    private void handleScoreboardCommand(ScoreboardCommand command) {
        ScoreboardPage.setScoreboardOutput(command.getTopUsers());
    }

    private void handleRegisterCommand(RegisterCommand registerCommand) {
        RegisterPage.setMessage(registerCommand.getMessage());
    }

    private void handleBattleCommand(BattleCommand battleCommand) {
        System.out.println(battleCommand);
        switch (battleCommand.getBattleCommandType()) {
            case SET -> set(battleCommand);
            case SUMMON -> summon(battleCommand);
            case CHANGE_POSITION -> changePosition(battleCommand);
            case FLIP_SUMMON -> flipSummon(battleCommand);
            case ATTACK -> attack(battleCommand);
            case DIRECT_ATTACK -> directAttack(battleCommand);
            case ACTIVATE -> activate(battleCommand);
            case NEXT_PHASE -> nextPhase(battleCommand);
            case END_TURN -> endTurn(battleCommand);
            case START_BATTLE -> startBattle(battleCommand);
            case ACCEPT_REQUEST -> acceptRequest(battleCommand);
            case CANCEL_REQUEST -> cancelRequest(battleCommand);
            case CANCEL_SENT_REQUEST -> cancelSentRequest(battleCommand);
        }
    }

    private void cancelSentRequest(BattleCommand battleCommand) {
        if (requestAlert == null) return;
        System.out.println("request canceled");
        Platform.runLater(() -> {
            if (requestAlert.isShowing()) requestAlert.close();
        });
    }

    private void cancelRequest(BattleCommand battleCommand) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "Player didnt accept your request!", ButtonType.OK);
            if (LobbyPage.getAfterRequestAlert().isShowing()) LobbyPage.getAfterRequestAlert().close();
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK)
                alert.close();
            else
                alert.close();
        });
    }

    private void acceptRequest(BattleCommand battleCommand) {
        Platform.runLater(() -> {
            try {
                if (LobbyPage.getAfterRequestAlert() != null)
                    if (LobbyPage.getAfterRequestAlert().isShowing()) LobbyPage.getAfterRequestAlert().close();
                DuelPageController.getInstance().setFirstPlayerUsername(Controller.currentUser.getUsername());
                DuelPageController.getInstance().setSecondPlayerUsername(battleCommand.getOpponent().getUsername());
                DuelPageController.getInstance().setCurrentTurnUsername(Controller.currentUser.getUsername());
                DuelPageController.getInstance().setOpponentUsername(battleCommand.getOpponent().getUsername());
                Controller.currentOpponent = battleCommand.getOpponent();
                User.getUsers().add(battleCommand.getOpponent());
                MainPageController.getInstance().newGameWithAnotherUser(battleCommand.getOpponent().getUsername(),
                        battleCommand.getNumberOfRounds());
                new DuelPage().start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void startBattle(BattleCommand battleCommand) {
        User applicatorUser = battleCommand.getApplicatorUser();
        Platform.runLater(() -> {
            requestAlert = new Alert(Alert.AlertType.NONE, "Player " + applicatorUser.getUsername()
                    + " has sent You a Game Request! Do you accept?", ButtonType.YES, ButtonType.CANCEL);
            requestAlert.showAndWait();
            if (requestAlert.getResult() == ButtonType.YES) {
                battleCommand.acceptRequest(Controller.currentUser);
                User.getUsers().add(battleCommand.getApplicatorUser());
                Platform.runLater(() -> {
                    try {
                        DuelPageController.getInstance().setFirstPlayerUsername(Controller.currentUser.getUsername());
                        DuelPageController.getInstance().setSecondPlayerUsername(battleCommand.getApplicatorUser().getUsername());
                        DuelPageController.getInstance().setCurrentTurnUsername(battleCommand.getApplicatorUser().getUsername());
                        DuelPageController.getInstance().setOpponentUsername(Controller.currentUser.getUsername());
                        Controller.currentOpponent = battleCommand.getApplicatorUser();
                        User.getUsers().add(battleCommand.getApplicatorUser());
                        MainPageController.getInstance().newGameWithAnotherUser(battleCommand.getApplicatorUser().getUsername(),
                                battleCommand.getNumberOfRounds());
                        new DuelPage().start(Page.getStage());
                        currentDuelPage.endTurn();
                        SendMessage.getSendMessage().sendMessage(battleCommand);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else if (requestAlert.getResult() == ButtonType.CANCEL){
                battleCommand.cancelRequest();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
            else if (!requestAlert.isShowing()) {
                battleCommand.cancelRequest();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
            requestAlert.close();
        });
    }


    private void getBattleCommandData(BattleCommand battleCommand) {
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setSelectedMyMonsterCardNumber(battleCommand.getSelectedMyMonsterCardNumber());
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setSelectedMyMonsterCardNumber(battleCommand.getSelectedMyMonsterCardNumber());
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setSelectedMySpellOrTrapCardNumber(battleCommand.getSelectedMySpellOrTrapCardNumber());
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setSelectedOpponentSpellOrTrapCardNumber(battleCommand.getSelectedOpponentSpellOrTrapCardNumber());
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setToBeAttackedCardNumber(battleCommand.getToBeAttackedCardNumber());
//        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).getBoard().
//                setSelectedCardNumberInHand(battleCommand.getSelectedCardNumberInHand());
        User.getUserByUsername(DuelPageController.getInstance().getCurrentTurnUsername()).setBoard(battleCommand.getUserBoard());
        User.getUserByUsername(DuelPageController.getInstance().getOpponentUsername()).setBoard(battleCommand.getOpponentBoard());
    }


    private void activate(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.activate();
        });
    }

    private void endTurn(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.endTurn();
        });
    }

    //TODO doesn't work
    private void nextPhase(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        DuelPageController.getInstance().nextPhase();
        Platform.runLater(() -> {
            currentDuelPage.showPhase();
        });
    }

    private void directAttack(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.directAttack();
        });
    }

    private void attack(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.attack();
        });
    }

    private void flipSummon(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.flipSummon();
        });
    }

    private void changePosition(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.changePosition();
        });
    }

    private void summon(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.summon();
        });
    }

    private void set(BattleCommand battleCommand) {
        getBattleCommandData(battleCommand);
        Platform.runLater(() -> {
            currentDuelPage.set();
        });
    }


    //TODO tv
    private void handleTVCommand(TVCommand tvcommand) {
//        if (tvcommand.getTvCommandType().equals(TVCommandType.GET_REPLAYS_LIST))

    }

    //TODO
    private void handleOnlinePlayersCommand(OnlinePlayersCommand onlinePlayersCommand) {
        System.out.println("Getting Online Players From Server");
        for (String onlineUser : onlinePlayersCommand.getOnlineUsers()) {
            System.out.println(onlineUser);
        }
        currentLobbyPage.showOnlinePlayers(onlinePlayersCommand.getOnlineUsers());
    }

    //TODO
    private void handleChatRoomCommand(ChatRoomCommand chatRoomCommand) {
        switch (chatRoomCommand.getChatRoomCommandType()) {
            case SEND_MESSAGE, UPDATE_CHAT_MESSAGES -> ChatPage.setAllChatMessages(chatRoomCommand.getChatMessages());
        }
    }


    private void handleShopCommand(ShopCommand shopCommand) {
        switch (shopCommand.getShopCommandType()) {
            case BUY_CARD -> handleBuyCard(shopCommand);
            case SELECT_CARD -> handleSelectCard(shopCommand);
            case GET_SHOP_CARDS -> handleGetShopCards(shopCommand);
            case UPDATE_NUMBER_OF_AVAILABLE_CARDS -> handleUpdateNumberOfAvailableCard(shopCommand);
            case INCREASE -> handleIncrease(shopCommand);
            case DECREASE -> handleDecrease(shopCommand);
            case FORBID_BUYING -> handleForbidBuying(shopCommand);
            case START_BUYING -> handleStartBuying(shopCommand);
            case GET_ACTIVE_AUCTIONS -> handleGetActiveAuctions(shopCommand);
            case MAKE_NEW_AUCTION -> handleMakeNewAuction(shopCommand);
            case NEW_BID_IN_AUCTION -> handleNewBidInAuction(shopCommand);
            case OPEN_SELECTED_AUCTION -> handleOpenSelectedAuction(shopCommand);
            case UPDATE_AUCTION -> handleUpdateAuction(shopCommand);
        }
    }

    private void handleUpdateAuction(ShopCommand shopCommand) {
        AuctionPage.setAuction(shopCommand.getNewAuction());
        Controller.currentUser = shopCommand.getUser();
    }

    private void handleOpenSelectedAuction(ShopCommand shopCommand) {
        AuctionPage.setAuction(shopCommand.getNewAuction());
    }

    private void handleNewBidInAuction(ShopCommand shopCommand) {
        Controller.currentUser = shopCommand.getUser();
        AuctionPage.setAuction(shopCommand.getNewAuction());
    }

    private void handleMakeNewAuction(ShopCommand shopCommand) {
        AuctionPage.setAuction(shopCommand.getNewAuction());
    }

    private void handleGetActiveAuctions(ShopCommand shopCommand) {
        ActiveAuctionsPage.setActiveAuctions(shopCommand.getActiveAuctions());
    }

    private void handleStartBuying(ShopCommand shopCommand) {
        AdminShopPage.setSelectedCard(shopCommand.getCard());
    }

    private void handleForbidBuying(ShopCommand shopCommand) {
        AdminShopPage.setSelectedCard(shopCommand.getCard());
    }

    private void handleDecrease(ShopCommand shopCommand) {
        AdminShopPage.setSelectedCard(shopCommand.getCard());
    }

    private void handleIncrease(ShopCommand shopCommand) {
        AdminShopPage.setSelectedCard(shopCommand.getCard());
    }

    private void handleUpdateNumberOfAvailableCard(ShopCommand shopCommand) {
        ShopPage.setSelectedCardToBuy(shopCommand.getCard());
        AdminShopPage.setSelectedCard(shopCommand.getCard());
        Controller.currentUser = shopCommand.getUser();
    }

    private void handleGetShopCards(ShopCommand shopCommand) {
        ShopPage.setMessage(shopCommand.getMessage());
        ShopPage.setShopCards(shopCommand.getShopCards());
        AdminShopPage.setShopCards(shopCommand.getShopCards());
    }

    private void handleSelectCard(ShopCommand shopCommand) {
    }

    private void handleBuyCard(ShopCommand shopCommand) {
        if (shopCommand.getMessage().equals("Card added successfully")) {
            Controller.currentUser = shopCommand.getUser();
            ShopPage.setMessage(shopCommand.getMessage());
            ShopPage.setSelectedCardToBuy(shopCommand.getCard());
        }
    }

    private void handleLoginCommand(LoginCommand loginCommand) {
        handleLogin(loginCommand);
    }

    private void handleLogout(LoginCommand loginCommand) {

    }

    private void handleLogin(LoginCommand loginCommand) {
        if (loginCommand.getLoginCommandType() == LoginCommandType.LOGIN) {
            if (loginCommand.getMessage().equals("user logged in successfully!")) {
                try {
                    LoginPage.setMessage(loginCommand.getMessage());
                    Controller.currentUser = loginCommand.getCurrentUser();
                    User.getUsers().add(Controller.currentUser);
                    System.out.println(Controller.currentUser.getUsername());
                    Controller.token = loginCommand.getToken();
                    if (Controller.currentUser.getUsername().equals("admin")) {
                        Page.setCurrentMenu(Menu.SHOP);
                        Platform.runLater(() -> {
                            try {
                                new AdminShopPage().start(Page.getStage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    } else {
                        Page.setCurrentMenu(Menu.MAIN);
                        Platform.runLater(() -> {
                            try {
                                new MainPage().start(Page.getStage());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else LoginPage.setMessage(loginCommand.getMessage());
        } else if (loginCommand.getLoginCommandType() == LoginCommandType.LOGOUT) {
            Controller.currentUser = null;
            Controller.token = null;
        }
        System.out.println(loginCommand.getMessage());
    }


    public static DuelPage getCurrentDuelPage() {
        return currentDuelPage;
    }

    public static void setCurrentDuelPage(DuelPage currentDuelPage) {
        ReadMessage.currentDuelPage = currentDuelPage;
    }

    public static LobbyPage getCurrentLobbyPage() {
        return currentLobbyPage;
    }

    public static void setCurrentLobbyPage(LobbyPage currentLobbyPage) {
        ReadMessage.currentLobbyPage = currentLobbyPage;
    }
}
