package model.commandClasses;

import model.Message;

import java.util.ArrayList;

public class ChatRoomCommand extends CommandClass {
    private ChatRoomCommandType chatRoomCommandType;
    private Message chatMessage;
    private ArrayList<Message> chatMessages;

    public ChatRoomCommand() {
        commandType = CommandType.CHAT;
    }

    public ArrayList<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Message getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(Message chatMessage) {
        this.chatMessage = chatMessage;
    }

    public ChatRoomCommandType getChatRoomCommandType() {
        return chatRoomCommandType;
    }

    public void setChatRoomCommandType(ChatRoomCommandType chatRoomCommandType) {
        this.chatRoomCommandType = chatRoomCommandType;
    }
}
