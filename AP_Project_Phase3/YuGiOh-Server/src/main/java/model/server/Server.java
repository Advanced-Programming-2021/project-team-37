package model.server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;
import model.User;
import utility.NetworkConfiguration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ServerSocket serverSocket = new ServerSocket(NetworkConfiguration.getPort());
    private static final ArrayList<User> allUsers = new ArrayList<>();
    private static final ArrayList<ClientHandler> allClientHandlers = new ArrayList<>();


    public Server() throws Exception {
    }


    @Override
    public void run() {
        Socket socket = null;

        while (true) {
            System.out.println("Server");
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New Client Request Received: " + socket);
            System.out.println("Creating a New Handler For This Client");
            ClientHandler newClient = null;
            try {
                newClient = new ClientHandler(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            allClientHandlers.add(newClient);
            new Thread(newClient).start();
            System.out.println("Adding This Client To Active Clients List");
        }
    }


    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    public static void removeUser(User user) {
        allUsers.remove(user);
    }


    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static ArrayList<ClientHandler> getAllClientHandlers() {
        return allClientHandlers;
    }


    public static ArrayList<User> sortUsers(ArrayList<User> users) {
        ArrayList<User> copiedUsers = new ArrayList<>(users);
        //TODO sort users
        return copiedUsers;
    }


    public static boolean doesUserExistInList(String username, ArrayList<User> users) {
        return findUserInList(username, users) != null;
    }

    private static User findUserInList(String username, ArrayList<User> users) {
        if (username.length() > 0 && users != null) {
            for (User user : users) {
                if (user != null && user.getUsername().equals(username))
                    return user;
            }
        }
        return null;
    }


    public static void saveUsers() {
        YaGson yaGson = new YaGsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter("src/main/resources/Users.json");
            String usersString = yaGson.toJson(allUsers);
            writer.write(usersString);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getOnlinePlayers() {
        ArrayList<String> onlinePlayers = new ArrayList<>();
        for (ClientHandler clientHandler : allClientHandlers)
            if (clientHandler.isLoggedIn())
                onlinePlayers.add(clientHandler.getUsername());
        return onlinePlayers;
    }

}



