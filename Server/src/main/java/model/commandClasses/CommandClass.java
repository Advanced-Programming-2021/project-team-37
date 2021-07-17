package model.commandClasses;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.User;

public class CommandClass {
    protected String message; // this is the message that we want to show to user
    protected CommandType commandType; // this is for knowing current page
    protected String username;
    protected User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static String makeJson(CommandClass commandClass) {
        YaGson yaGson = new YaGsonBuilder().create();
        return yaGson.toJson(commandClass);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}
