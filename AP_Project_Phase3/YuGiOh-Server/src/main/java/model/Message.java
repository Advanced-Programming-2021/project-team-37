package model;

public class Message {

    private String messageText;
    private MessageSender sender;

    public Message(MessageSender sender, String messageText) {
        this.sender = sender;
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public MessageSender getSender() {
        return sender;
    }

    public void setSender(MessageSender sender) {
        this.sender = sender;
    }
}