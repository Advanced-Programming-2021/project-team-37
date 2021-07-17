package controller;

import model.Message;

import java.util.ArrayList;

public class ChatPageController {
    private ArrayList<Message> chatMessages;
    private static ChatPageController instance;

    private ChatPageController() {

    }

    public static ChatPageController getInstance() {
        if (instance == null) instance = new ChatPageController();
        return instance;
    }

    public ArrayList<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addNewMessage(Message newMessage) {
        chatMessages.add(newMessage);
    }
}
