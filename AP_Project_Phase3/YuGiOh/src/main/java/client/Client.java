package client;

import utility.NetworkConfiguration;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Client currentClient;
    private static Scanner input;


    private ReadMessage reader;
    private Socket socket;



    public Client() {
        try {
            socket = new Socket(NetworkConfiguration.getHost(), NetworkConfiguration.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader = new ReadMessage(socket);
        currentClient = this;
    }


    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client currentClient) {
        Client.currentClient = currentClient;
    }

    public static Scanner getInput() {
        return input;
    }

    public static void setInput(Scanner input) {
        Client.input = input;
    }

    public ReadMessage getReader() {
        return reader;
    }

    public void setReader(ReadMessage reader) {
        this.reader = reader;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
