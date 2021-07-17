package controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.Card;
import model.User;
import model.commandClasses.*;

import java.io.IOException;

public class ServerController {
    private static ServerController instance;
    private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        ServerController.message = message;
    }

    private ServerController() {

    }

    public static ServerController getInstance() {
        if (instance == null) instance = new ServerController();
        return instance;
    }

    public CommandClass processResult(CommandClass result) {
        if (result.getCommandType() == CommandType.REGISTER) result = handleRegisterResult((RegisterCommand) result);
        else if (result.getCommandType() == CommandType.LOGIN) result = handleLoginResult((LoginCommand) result);
        else if (result.getCommandType() == CommandType.SCOREBOARD) result = handleScoreboardResult((ScoreboardCommand) result);
        else if (result.getCommandType() == CommandType.SHOP) result = handleShopResult((ShopCommand) result);
        else if (result.getCommandType() == CommandType.CHAT) result = handleChatResult((ChatRoomCommand) result);

        return result;
    }

    private CommandClass handleChatResult(ChatRoomCommand result) {
        if (result.getChatRoomCommandType() == ChatRoomCommandType.SEND_MESSAGE) {
            ChatPageController.getInstance().addNewMessage(result.getChatMessage());
            result.getChatMessages().add(result.getChatMessage());
        }
        return result;
    }

    private CommandClass handleShopResult(ShopCommand result) {
        if (result.getShopCommandType() == ShopCommandType.BUY_CARD) {
            buyCard(result);
            result.setMessage(ShopPageController.getInstance().getMessage());
            result.setCard(Card.getCardByName(result.getCardName()));
        } else if (result.getShopCommandType() == ShopCommandType.GET_SHOP_CARDS) {
            result.setShopCards(Card.getCards());
        } else if (result.getShopCommandType() == ShopCommandType.UPDATE_NUMBER_OF_AVAILABLE_CARDS) {
            result.setCard(Card.getCardByName(result.getCard().getCardName()));
        }
        return result;
    }

    private synchronized void buyCard(ShopCommand result) {
        ShopPageController.setUsername(result.getUsername());
        ShopPageController.getInstance().buyCard(result.getCardName());
    }

    private CommandClass handleScoreboardResult(ScoreboardCommand result) {
        result.setTopUsers(ScoreboardPageController.getInstance().showScoreboard());
        return result;
    }

    private CommandClass handleLoginResult(LoginCommand result) {
        LoginPageController.getInstance().loginUser(result.getUsername(), result.getPassword());
        result.setMessage(LoginPageController.getInstance().getMessage());
        if (result.getMessage().equals("user logged in successfully!"))
            result.setCurrentUser(User.getUserByUsername(result.getUsername()));
        return result;
    }

    private CommandClass handleRegisterResult(RegisterCommand result) {
        RegisterPageController.getInstance().registerUser(result.getUsername(), result.getNickname(), result.getPassword());
        result.setMessage(RegisterPageController.getInstance().getMessage());
        result.setTopUsers(ScoreboardPageController.getInstance().showScoreboard());
        if (result.getMessage().equals("user created successfully!")) {

        }
        return result;
    }
}
