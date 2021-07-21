package client;

import model.commands.CommandClass;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;

public class SendMessage {

    private static SendMessage sendMessage = new SendMessage();
    private Formatter formatter;
    private Socket socket = Client.getCurrentClient().getSocket();


    public SendMessage() {
        try {
            formatter = new Formatter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(CommandClass command) {
        String message = CommandClass.makeJson(command);
        System.out.println(message);
        formatter.format("%s\n", message);
        formatter.flush();
        System.out.println("Message Was Sent!");
    }


    public static SendMessage getSendMessage() {
        return sendMessage;
    }


}
