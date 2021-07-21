package model.commands;

public class ScoreboardCommand extends CommandClass {

    public ScoreboardCommand() {
        commandType = CommandType.SCOREBOARD;
    }

    private String topUsers = new String();

    public String getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(String topUsers) {
        this.topUsers = topUsers;
    }
}
