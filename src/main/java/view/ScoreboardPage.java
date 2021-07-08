package view;

import controller.ScoreboardPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardPage extends Application {
    private static String message;
    public GridPane scoreboard;

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    private String showScoreboard() {
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

    public void setUsername(String username) {

    }

    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        new MainPage().start(Page.getStage());
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

    public void initialize() {
        String scoreboardOutput = showScoreboard();
        String[] scoreboardParts = scoreboardOutput.split("___");

        int countUser = scoreboardParts.length / 3 + 1;
        if (countUser >= 20) countUser = 21;

        for (int j = 1; j < countUser; j++) {
            javafx.scene.control.Label rowNumber = new javafx.scene.control.Label();
            rowNumber.setText(String.valueOf(j));
            rowNumber.setAlignment(Pos.CENTER);
            scoreboard.add(rowNumber, 0, j);
            scoreboard.setStyle("-fx-border-color: rgba(0,78,255,0.61)");
            for (int i = 0; i < 3; i++) {
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                label.setText(scoreboardParts[(3 * (j - 1)) + i]);
                if (scoreboardParts[(3 * (j - 1)) + i].equals(ScoreboardPageController.getInstance().getUsername()))
                    label.setStyle("-fx-background-color: #9aff00");
                label.setAlignment(Pos.CENTER);
                scoreboard.add(label, i + 1, j);
            }

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


