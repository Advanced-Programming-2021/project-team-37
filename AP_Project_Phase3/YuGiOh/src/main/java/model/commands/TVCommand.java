package model.commands;

import java.util.ArrayList;

//TODO TVCommand
public class TVCommand extends CommandClass {

    private final TVCommandType tvCommandType;
    private ArrayList<String> runningGames = new ArrayList<>();
    private ArrayList<String> finishedGames = new ArrayList<>();
    private String userNameOfFirstPlayersOfARequestedReplayOfABattle;
    private String userNameOfSecondPlayersOfARequestedReplayOfABattle;

    public TVCommand(TVCommandType tvCommandType) {
        super(CommandType.TV);
        this.tvCommandType = tvCommandType;
    }


    public TVCommandType getTvCommandType() {
        return tvCommandType;
    }

    public ArrayList<String> getRunningGames() {
        return runningGames;
    }

    public void setRunningGames(ArrayList<String> runningGames) {
        this.runningGames = runningGames;
    }

    public ArrayList<String> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(ArrayList<String> finishedGames) {
        this.finishedGames = finishedGames;
    }

    public String getUserNameOfFirstPlayersOfARequestedReplayOfABattle() {
        return userNameOfFirstPlayersOfARequestedReplayOfABattle;
    }

    public void setUserNameOfFirstPlayersOfARequestedReplayOfABattle(String userNameOfFirstPlayersOfARequestedReplayOfABattle) {
        this.userNameOfFirstPlayersOfARequestedReplayOfABattle = userNameOfFirstPlayersOfARequestedReplayOfABattle;
    }

    public String getUserNameOfSecondPlayersOfARequestedReplayOfABattle() {
        return userNameOfSecondPlayersOfARequestedReplayOfABattle;
    }

    public void setUserNameOfSecondPlayersOfARequestedReplayOfABattle(String userNameOfSecondPlayersOfARequestedReplayOfABattle) {
        this.userNameOfSecondPlayersOfARequestedReplayOfABattle = userNameOfSecondPlayersOfARequestedReplayOfABattle;
    }
}
