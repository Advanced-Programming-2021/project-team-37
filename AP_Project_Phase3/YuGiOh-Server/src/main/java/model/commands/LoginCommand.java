package model.commands;

import model.User;

public class LoginCommand extends CommandClass {
    private String username;
    private String password;
    private User currentUser;
    protected LoginCommandType loginCommandType;
    private String message;

    public LoginCommandType getLoginCommandType() {
        return loginCommandType;
    }

    public void setLoginCommandType(LoginCommandType loginCommandType) {
        this.loginCommandType = loginCommandType;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public LoginCommand(String username, String password) {
        commandType = CommandType.LOGIN;
        this.username = username;
        this.password = password;
    }





    public LoginCommand() {
        commandType = CommandType.LOGIN;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
