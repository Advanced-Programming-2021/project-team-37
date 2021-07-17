import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import com.google.gson.reflect.TypeToken;
import controller.ServerController;
import model.Card;
import model.User;
import model.commandClasses.CommandClass;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static String message; // this is the result

    public static void main(String[] args) {
        Card.setCards();

        for (Card card : Card.getCards()) {
            System.out.println(card.getCardName());
        }

        initializeProgram();
        runNewServer();
    }

    public static void initializeProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
            ArrayList<User> users = new YaGson().fromJson(data, new TypeToken<ArrayList<User>>() {
            }.getType());
            User.setUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void runNewServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(7777);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    makeNewThread(serverSocket, socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeNewThread(ServerSocket serverSocket, Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    String input = dataInputStream.readUTF();
                    YaGson yaGson = new YaGsonBuilder().create();
                    CommandClass command = yaGson.fromJson(input, CommandClass.class);
                    CommandClass r = ServerController.getInstance().processResult(command);
                    String result = CommandClass.makeJson(r);
                    dataOutputStream.writeUTF(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
