package model.server;

import model.User;

import java.util.ArrayList;

public class ServerTV {

    private static int counter;
    private static ArrayList<ServerTV> finishedGames = new ArrayList<>();
    private static ArrayList<ServerTV> runningGames = new ArrayList<>();
    private User user1;
    private User user2;
    private int number;

    public ServerTV(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.number = counter++;
        runningGames.add(this);
    }

    public static ArrayList<ServerTV> getFinishedGames() {
        return finishedGames;
    }


    public static void setFinishedGames(ArrayList<ServerTV> finishedGames) {
        ServerTV.finishedGames = finishedGames;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }


    public static ArrayList<ServerTV> getRunningGames() {
        return runningGames;
    }

    public static ClientHandler getOpponentClientHandler(User user) {
        User opponent = getOpponent(user);
        for (ClientHandler clientHandler : ClientHandler.getClientHandlers()) {
            try {
                if (clientHandler.isLoggedIn() && opponent != null && clientHandler.getUsername().equals(opponent.getUsername()))
                    return clientHandler;
            } catch (Exception e) {
                System.out.println("Client Handler Error!");
            }
        }
        return null;
    }


    public static User getOpponent(User user) {
        User opponent = null;
        for (ServerTV runningGame : runningGames)
            if (user.getUsername().equals(runningGame.getUser1().getUsername()) ||
                    user.getUsername().equals(runningGame.getUser2().getUsername())) {
                if (user.getUsername().equals(runningGame.getUser1().getUsername()))
                    opponent = runningGame.getUser2();
                else
                    opponent = runningGame.getUser1();
            }
        return opponent;
    }


}
