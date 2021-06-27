
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Card;
import model.User;
import view.Page;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void initializeProgram() {
        try {
            String data = new String(Files.readAllBytes(Paths.get("src/main/resources/Users.json")));
            ArrayList<User> users = new Gson().fromJson(data, new TypeToken<ArrayList<User>>() {
            }.getType());
            User.setUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Card.setCards();
        Card.updateCardsDatabase();
        initializeProgram();
        Page page = new Page();
        page.run();
    }

}
