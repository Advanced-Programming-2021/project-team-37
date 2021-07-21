package model.commands;

import java.util.ArrayList;

import static model.commands.CommandType.ONLINE_PLAYERS;

public class OnlinePlayersCommand extends CommandClass {

    private ArrayList<String> onlineUsers;

    public OnlinePlayersCommand() {
        super(ONLINE_PLAYERS);
    }




    public ArrayList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}