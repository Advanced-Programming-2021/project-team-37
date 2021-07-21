package view;

import client.SendMessage;
import controller.Controller;
import controller.ScoreboardPageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;
import model.commands.ScoreboardCommand;

import javax.naming.ldap.Control;
import javax.swing.text.html.ImageView;
import java.util.*;

public class ScoreboardPage extends Application {
    private static String message;
    private static String scoreboardOutput = new String();
    public AnchorPane anchorPane;
    public GridPane scoreboard;
    private Thread scoreboardThread;
    private Timer timer;

    public void setCommandPatterns(String commandPatterns) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public void setUsername(String username) {

    }

    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        Page.setCurrentMenu(Menu.MAIN);
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

    public static String getScoreboardOutput() {
        return scoreboardOutput;
    }

    public static void setScoreboardOutput(String scoreboardOutput) {
        ScoreboardPage.scoreboardOutput = scoreboardOutput;
    }

    public void initialize() {
        showScoreboard();

        updateScoreboard();
    }

    public void updateScoreboard() {
        scoreboardThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            if (Page.getCurrentMenu() == Menu.SCOREBOARD)
                                showScoreboard();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 100;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        scoreboardThread.start();
    }

    private void showScoreboard() {
        ScoreboardCommand scoreboardCommand = new ScoreboardCommand();
        SendMessage.getSendMessage().sendMessage(scoreboardCommand);
        String[] scoreboardParts = scoreboardOutput.split("___");

        int countUser = scoreboardParts.length / 3 + 1;
        if (countUser >= 20) countUser = 21;

        for (int i = 4; i < scoreboard.getChildren().size(); i++) {
            Label label = (Label) scoreboard.getChildren().get(i);
            scoreboard.getChildren().remove(label);
        }

        for (int j = 1; j < countUser; j++) {
            javafx.scene.control.Label rowNumber = new javafx.scene.control.Label();
            rowNumber.setText(String.valueOf(j));
            rowNumber.setAlignment(Pos.CENTER);
            scoreboard.add(rowNumber, 0, j);
            scoreboard.setStyle("-fx-border-color: rgba(0,78,255,0.61)");
            for (int i = 0; i < 3; i++) {
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                label.setText(scoreboardParts[(3 * (j - 1)) + i]);
                if (scoreboardParts[(3 * (j - 1)) + i].equals(Controller.currentUser.getNickname()))
                    label.setStyle("-fx-background-color: #9aff00");
                label.setAlignment(Pos.CENTER);
                scoreboard.add(label, i + 1, j);
            }
        }
    }
}


