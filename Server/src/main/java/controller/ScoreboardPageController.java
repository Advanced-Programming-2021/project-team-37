package controller;

import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardPageController extends Controller {
    private static ScoreboardPageController instance;

    private ScoreboardPageController() {

    }

    public static ScoreboardPageController getInstance() {
        if (instance == null)
            instance = new ScoreboardPageController();
        return instance;
    }

    public String showScoreboard() {
        ArrayList<User> users = User.getUsers();
        Collections.sort(users, new UserSortingComparator());
        StringBuilder scoreboardOutput = new StringBuilder();
        int rank = 0;
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getUsername().equals(("AI"))) {
                if (!(i > 0 && users.get(i).getScore() == users.get(i - 1).getScore())) rank = i;
                scoreboardOutput.append((rank + 1) + "___" + users.get(i).getNickname() + "___"
                        + users.get(i).getScore() + "___");
            }
        }
        return scoreboardOutput.toString();
    }
    
    public void updateAllUsersScoreboard() {
        for (User user : User.getUsers()) {
            
        }
    }

    static class UserSortingComparator
            implements Comparator<User> {

        @Override
        public int compare(User a, User b) {
            int scoreCompare = b.getScore() - a.getScore();
            int nicknameCompare = a.getNickname().compareTo(b.getNickname());

            return (scoreCompare == 0) ? nicknameCompare : scoreCompare;
        }
    }
}
