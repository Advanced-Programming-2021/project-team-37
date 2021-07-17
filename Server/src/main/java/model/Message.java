package model;

public class Message {

    private String messageText;
    private User sender;

    public Message(User sender, String messageText) {
        this.sender = sender;
        this.messageText = messageText;
    }
}