package model.commandClasses;

public class RegisterCommand extends CommandClass {
    private String username;
    private String nickname;
    private String password;
    private String topUsers;

    public RegisterCommand() {
        commandType = CommandType.REGISTER;
    }

    public String getTopUsers() {
        return topUsers;
    }

    public void setTopUsers(String topUsers) {
        this.topUsers = topUsers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
