package model.server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import controller.*;
import model.Auction;
import model.Card;
import model.User;
import model.commands.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.UUID;

public class ClientHandler implements Runnable {

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static ArrayList<ChatRoomCommand> messages = new ArrayList<>();
    private static User battleApplicator;


    private Scanner scanner;
    private Socket socket;
    private String username;
    private Formatter formatter;
    private boolean loggedIn;


    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        formatter = new Formatter(this.socket.getOutputStream());
        clientHandlers.add(this);
        scanner = new Scanner(this.socket.getInputStream());
    }

    //TODO set command handlers
    @Override
    public void run() {
        YaGson yaGson = new YaGsonBuilder().create();
        while (true) {
            String message = null;
            message = scanner.nextLine();
            System.out.println(messages);
            CommandClass command = yaGson.fromJson(message, CommandClass.class);
            switch (command.getCommandType()) {
                case REGISTER -> handleRegisterCommand((RegisterCommand) command);
                case SCOREBOARD -> handleScoreBoardCommand((ScoreboardCommand) command);
                case LOGIN -> handleLoginCommand((LoginCommand) command);
                case SHOP -> handleShopCommand((ShopCommand) command);
                case BATTLE -> handleBattle((BattleCommand) command);
                case CHAT -> handleChatRoom((ChatRoomCommand) command);
                case ONLINE_PLAYERS -> handleOnlinePlayers((OnlinePlayersCommand) command);
                case EXIT -> handleExit((ExitCommand) command);
            }
        }
    }

    private void handleScoreBoardCommand(ScoreboardCommand command) {
        command.setTopUsers(ScoreboardPageController.getInstance().showScoreboard());
        formatter.format("%s\n", CommandClass.makeJson(command));
        formatter.flush();
    }

    private void handleLoginCommand(LoginCommand loginCommand) {
        handleLogin(loginCommand);
    }

    private void handleLogout(LoginCommand loginCommand) {

    }

    private void handleRegisterCommand(RegisterCommand registerCommand) {
        RegisterPageController.getInstance().registerUser(registerCommand.getUsername(), registerCommand.getNickname(), registerCommand.getPassword());
        registerCommand.setMessage(RegisterPageController.getInstance().getMessage());
        registerCommand.setTopUsers(ScoreboardPageController.getInstance().showScoreboard());
        formatter.format("%s\n", CommandClass.makeJson(registerCommand));
        formatter.flush();
    }

    private void handleExit(ExitCommand command) {
        setLoggedIn(false);
        Server.saveUsers();
        try {
            formatter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientHandlers.remove(this);
    }

    private void handleOnlinePlayers(OnlinePlayersCommand onlinePlayersCommand) {
        onlinePlayersCommand.setOnlineUsers(Server.getOnlinePlayers());
        formatter.format("%s\n", CommandClass.makeJson(onlinePlayersCommand));
        formatter.flush();
    }

    private void handleShopCommand(ShopCommand shopCommand) {
        switch (shopCommand.getShopCommandType()) {
            case BUY_CARD -> shopCommand = handleBuyCard(shopCommand);
            case SELECT_CARD -> shopCommand = handleSelectCard(shopCommand);
            case GET_SHOP_CARDS -> shopCommand = handleGetShopCards(shopCommand);
            case UPDATE_NUMBER_OF_AVAILABLE_CARDS -> shopCommand = handleUpdateNumberOfAvailableCard(shopCommand);
            case INCREASE -> shopCommand = handleIncrease(shopCommand);
            case DECREASE -> shopCommand = handleDecrease(shopCommand);
            case FORBID_BUYING -> shopCommand = handleForbidBuying(shopCommand);
            case START_BUYING -> shopCommand = handleStartBuying(shopCommand);
            case GET_ACTIVE_AUCTIONS -> shopCommand = handleGetActiveAuctions(shopCommand);
            case MAKE_NEW_AUCTION -> shopCommand = handleMakeNewAuction(shopCommand);
            case NEW_BID_IN_AUCTION -> shopCommand = handleNewBidInAuction(shopCommand);
            case OPEN_SELECTED_AUCTION -> shopCommand = handleOpenSelectedAuction(shopCommand);
            case UPDATE_AUCTION -> shopCommand = handleUpdateAuction(shopCommand);
            case DELETE_AUCTION -> shopCommand = handleDeleteAuction(shopCommand);
        }
        formatter.format("%s\n", CommandClass.makeJson(shopCommand));
        formatter.flush();
    }

    private ShopCommand handleDeleteAuction(ShopCommand shopCommand) {
        if (shopCommand.getNewAuction() != null)
            Auction.getActiveAuctions().remove(Auction.getAuctionByAuctionName(shopCommand.getNewAuction().getAuctionName()));
        return shopCommand;
    }

    private ShopCommand handleUpdateAuction(ShopCommand shopCommand) {
        String auctionName = shopCommand.getNewAuction().getAuctionName();
        Auction auction = Auction.getAuctionByAuctionName(auctionName);
        if (auction != null)
            shopCommand.setNewAuction(auction);

        shopCommand.setUser(User.getUserByUsername(shopCommand.getUser().getUsername()));
        return shopCommand;
    }

    private ShopCommand handleOpenSelectedAuction(ShopCommand shopCommand) {
        Auction auction = Auction.getAuctionByAuctionName(shopCommand.getNewAuction().getAuctionName());
        shopCommand.setNewAuction(auction);
        return shopCommand;
    }

    private ShopCommand handleNewBidInAuction(ShopCommand shopCommand) {
        Auction auction = Auction.getAuctionByAuctionName(shopCommand.getNewAuction().getAuctionName());
        auction.resetTime();
        if (!auction.isHadBid()) User.getUserByUsername(auction.getAuctionCreatorUsername())
                .increaseMoney(auction.getStartingPrice());
        auction.setHadBid(true);
        User.getUserByUsername(auction.getAuctionCreatorUsername()).decreaseMoney(auction.getWinnerPrice());
        removeCardFromPreviousUser(auction);
        auction.increaseWinnerPrice(shopCommand.getAddAmountPrice());
        auction.setWinnerUsername(shopCommand.getUser().getUsername());
        shopCommand.setNewAuction(auction);
        User user = User.getUserByUsername(shopCommand.getUser().getUsername());
        user.getCards().add(Card.getCardByName(auction.getCard().getCardName()));
        user.decreaseMoney(auction.getWinnerPrice());
        User.getUserByUsername(auction.getAuctionCreatorUsername()).increaseMoney(auction.getWinnerPrice());
        shopCommand.setUser(user);
        return shopCommand;
    }

    private void removeCardFromPreviousUser(Auction auction) {
        User user = User.getUserByUsername(auction.getWinnerUsername());
        user.getCards().remove(Card.getCardByName(auction.getCard().getCardName()));
        if (!user.getUsername().equals(auction.getAuctionCreatorUsername()))
            user.increaseMoney(auction.getWinnerPrice());
    }

    private ShopCommand handleMakeNewAuction(ShopCommand shopCommand) {
        Auction auction = shopCommand.getNewAuction();
        Auction auction1 = new Auction(auction.getAuctionCreatorUsername(), auction.getCard(), auction.getStartingPrice());
        shopCommand.setNewAuction(auction1);
        return shopCommand;
    }

    private ShopCommand handleGetActiveAuctions(ShopCommand shopCommand) {
        shopCommand.setActiveAuctions(Auction.getActiveAuctions());
        return shopCommand;
    }

    private ShopCommand handleStartBuying(ShopCommand shopCommand) {
        ShopPageController.getInstance().startBuyingCard(shopCommand.getCard().getCardName());
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        return shopCommand;
    }

    private ShopCommand handleForbidBuying(ShopCommand shopCommand) {
        ShopPageController.getInstance().forbidBuyingCard(shopCommand.getCard().getCardName());
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        return shopCommand;
    }

    private ShopCommand handleDecrease(ShopCommand shopCommand) {
        ShopPageController.getInstance().decreaseNumberOfCardInStock(shopCommand.getCard().getCardName());
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        return shopCommand;
    }

    private ShopCommand handleIncrease(ShopCommand shopCommand) {
        ShopPageController.getInstance().increaseNumberOfCardInStock(shopCommand.getCard().getCardName());
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        return shopCommand;
    }

    private ShopCommand handleUpdateNumberOfAvailableCard(ShopCommand shopCommand) {
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        shopCommand.setUser(User.getUserByUsername(shopCommand.getUser().getUsername()));
        return shopCommand;
    }

    private ShopCommand handleGetShopCards(ShopCommand shopCommand) {
        shopCommand.setShopCards(Card.getCards());
        return shopCommand;
    }

    private ShopCommand handleSelectCard(ShopCommand shopCommand) {
        return shopCommand;
    }

    private ShopCommand handleBuyCard(ShopCommand shopCommand) {
        buyCard(shopCommand);
        shopCommand.setMessage(ShopPageController.getInstance().getMessage());
        shopCommand.setCard(Card.getCardByName(shopCommand.getCard().getCardName()));
        return shopCommand;
    }

    private synchronized void buyCard(ShopCommand result) {
        ShopPageController.setUsername(User.getLoggedInUsers().get(result.getToken()).getUsername());
        ShopPageController.getInstance().buyCard(result.getCard().getCardName());
        result.setUser(User.getLoggedInUsers().get(result.getToken()));
    }

    private void handleLogin(LoginCommand loginCommand) {
        if (loginCommand.getLoginCommandType() == LoginCommandType.LOGIN) {
            LoginPageController.getInstance().loginUser(loginCommand.getUsername(), loginCommand.getPassword());
            loginCommand.setMessage(LoginPageController.getInstance().getMessage());
            if (loginCommand.getMessage().equals("user logged in successfully!")) {
                String token = UUID.randomUUID().toString();
                loginCommand.setToken(token);
                User.getLoggedInUsers().put(token, User.getUserByUsername(loginCommand.getUsername()));
                loginCommand.setCurrentUser(User.getLoggedInUsers().get(token));
                setLoggedIn(true);
                username = loginCommand.getUsername();
            }
        } else if (loginCommand.getLoginCommandType() == LoginCommandType.LOGOUT) {
            User.getLoggedInUsers().remove(loginCommand.getToken());
            setLoggedIn(false);
        }
        formatter.format("%s\n", CommandClass.makeJson(loginCommand));
        formatter.flush();
    }

    private void handleBattle(BattleCommand battleCommand) {
        switch (battleCommand.getBattleCommandType()) {
            case SET, SUMMON, CHANGE_POSITION, FLIP_SUMMON, ATTACK, DIRECT_ATTACK, NEXT_PHASE, END_TURN -> handleRunningBattle(battleCommand);
            case START_BATTLE -> handleStartBattle(battleCommand);
            case ACCEPT_REQUEST -> acceptBattle(battleCommand);
            case CANCEL_REQUEST -> cancelBattle(battleCommand);
        }
    }

    private void cancelBattle(BattleCommand battleCommand) {
        ClientHandler clientHandler = getClientHandlerByUsername(battleCommand.getApplicatorUser().getUsername());
        if (clientHandler == null)
            return;
        clientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
        clientHandler.getFormatter().flush();
    }

    private void acceptBattle(BattleCommand battleCommand) {
        ClientHandler clientHandler = getClientHandlerByUsername(battleCommand.getApplicatorUser().getUsername());
        if (clientHandler == null)
            return;
        clientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
        clientHandler.getFormatter().flush();
    }

    //TODO return back for debug(if there was one)
    private void handleStartBattle(BattleCommand battleCommand) {
        System.out.println("Send request for starting a battle from: " + battleCommand.getApplicatorUser().getUsername());
        if (battleCommand.getUsernameToRequest() == null) {
            battleApplicator = battleCommand.getApplicatorUser();
            return;
        }
        ClientHandler opponentClientHandler = getClientHandlerByUsername(battleCommand.getUsernameToRequest());
        if (opponentClientHandler == null)
            return;
        opponentClientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
        opponentClientHandler.getFormatter().flush();
    }

//    private void sendToClients(User user1, User user2, BattleCommand recievedBattleCommand) {
////        BattleCommand battleCommand = new BattleCommand();
//        sendRequest(user1, user2, true);
//        sendRequest(user1, user2, false);
//        new ServerTV(user1, user2);
//    }
//
//    private void sendRequest(User sendFor, User opponent, boolean firstPlayer) {
//        BattleCommand  battleCommand = new BattleCommand();
//        battleCommand.acceptRequest(opponent, firstPlayer);
//        for (ClientHandler clientHandler : clientHandlers) {
//            if (clientHandler.loggedIn && clientHandler.getUsername().equals(sendFor.getUsername())) {
//                try {
//                    clientHandler.getFormatter().writeUTF(CommandClass.makeJson(battleCommand));
//                    clientHandler.getFormatter().flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    private void handleRunningBattle(BattleCommand battleCommand) {
        handleEndTurn(battleCommand);
        System.out.println("Command Received");
        ClientHandler clientHandler = getClientHandlerByUsername(battleCommand.getOpponent().getUsername());
        if (clientHandler == null) {
            System.out.println("Game Not Found");
            return;
        }
        clientHandler.getFormatter().format("%s\n", CommandClass.makeJson(battleCommand));
        clientHandler.getFormatter().flush();
    }

    private void handleEndTurn(BattleCommand battleCommand) {

    }

    private void handleNEXTPhase(BattleCommand battleCommand) {
    }

    private void handleDirectAttack(BattleCommand battleCommand) {
    }

    private void handleAttack(BattleCommand battleCommand) {
    }

    private void handleFlipSummon(BattleCommand battleCommand) {
    }

    private void handleChangePosition(BattleCommand battleCommand) {
    }

    private void handleSummon(BattleCommand battleCommand) {
    }

    private void handleSet(BattleCommand battleCommand) {
    }


    private void handleChatRoom(ChatRoomCommand chatRoomCommand) {
        switch (chatRoomCommand.getChatRoomCommandType()) {
            case SEND_MESSAGE -> {
                ChatPageController.getInstance().addNewMessage(chatRoomCommand.getChatMessage());
                chatRoomCommand.setChatMessages(ChatPageController.getInstance().getChatMessages());
            }
            case UPDATE_CHAT_MESSAGES -> chatRoomCommand.setChatMessages(ChatPageController.getInstance().getChatMessages());
        }
        formatter.format("%s\n", CommandClass.makeJson(chatRoomCommand));
        formatter.flush();
    }

    public static ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUsername() {
        return this.username;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public static ClientHandler getClientHandlerByUsername(String username) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.username.equals(username))
                return clientHandler;
        }
        return null;
    }
}
