package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardPage extends Application {
    private static String message;

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    private void showScoreboard() {
        ArrayList<User> users = User.getUsers();
        Collections.sort(users, new UserSortingComparator());
        int rank = 0;
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getUsername().equals(("AI"))) {
                if (!(i > 0 && users.get(i).getScore() == users.get(i - 1).getScore())) rank = i;
                System.out.println((rank + 1) + "- " + users.get(i).getNickname() + ": " + users.get(i).getScore());
            }
        }
    } // write a test for here

    public void setUsername(String username) {

    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage()); // todo check this
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        ScoreboardPage.message = message;
    }

    private ArrayList<User> sortUsers(ArrayList<User> users) {

        return users;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/scoreboardPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    static class UserSortingComparator
            implements Comparator<User> {

        @Override
        public int compare(User a, User b) {
            // for comparison
            int scoreCompare = b.getScore() - a.getScore();
            int nicknameCompare = a.getNickname().compareTo(b.getNickname());

            // 2-level comparison
            return (scoreCompare == 0) ? nicknameCompare : scoreCompare;
        }
    }
}


