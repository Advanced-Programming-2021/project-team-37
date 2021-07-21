package model.commands;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.User;
import model.exceptions.MyException;

public class CommandClass {

    protected CommandType commandType;
    protected MyException myException;
    protected String token;
    protected String username;
    protected String message;
    protected User user;


    public CommandClass(CommandType commandKind) {
        this.commandType = commandKind;
    }


    public CommandClass() {

    }


    public static String makeJson(CommandClass commandClass) {
        YaGson yaGson = new YaGsonBuilder().create();
        return yaGson.toJson(commandClass);
    }


    public CommandType getCommandType() {
        return this.commandType;
    }

    public MyException getMyException() {
        return this.myException;
    }

    public void setMyException(MyException myException) {
        this.myException = myException;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
