package view;

import controller.DuelPageController;
import controller.AI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelPage extends Application {
    private static String message;
    public static DuelPageController duelPageController;

    protected int numberOfRounds;
    protected String firstPlayerUsername;
    protected String secondPlayerUsername;

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public String getFirstPlayerUsername() {
        return firstPlayerUsername;
    }

    public void setFirstPlayerUsername(String firstPlayerUsername) {
        this.firstPlayerUsername = firstPlayerUsername;
    }

    public String getSecondPlayerUsername() {
        return secondPlayerUsername;
    }

    public void setSecondPlayerUsername(String secondPlayerUsername) {
        this.secondPlayerUsername = secondPlayerUsername;
    }

    public DuelPage() {
        duelPageController = DuelPageController.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/duelPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void phaseWork() {
        if (DuelPageController.getInstance().getPhaseNumber() == 0)
            for (int i = 0; i < 3; i++)
                work();
        else work();
    }

    private void work() {
        if (DuelPageController.getInstance().getPhaseNumber() == 0) DuelPageController.getInstance().drawPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 1)
            DuelPageController.getInstance().standbyPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 2 ||
                DuelPageController.getInstance().getPhaseNumber() == 4) DuelPageController.getInstance().mainPhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 3) DuelPageController.getInstance().battlePhase();
        else if (DuelPageController.getInstance().getPhaseNumber() == 5) DuelPageController.getInstance().endPhase();
    }

    public static int getTributeMonsterNumberFromPlayer() {
        if (DuelPageController.getInstance().getCurrentTurnUsername().equals("AI"))
            return AI.getTributeNumber();
        return 1;//scanner.nextInt(); // todo get tribute number from user
    }

    public void setUsernameId(String username) {

    }

    public void enterMenu(String menuName) {
        if (menuName.matches("(login|main|duel|deck|scoreboard|profile|shop|import/export)"))
            System.out.println("menu navigation is not possible");
        else System.out.println("invalid menu name");
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        DuelPage.message = message;
    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    private static void selectCard(String selectCardCommand) {
        boolean isAddressValid = false;
        String[] selectCardCommandPatterns = {"^select --monster (\\d+)$",
                "^select --spell (\\d+)$",
                "^select (--monster --opponent|--opponent --monster) (\\d+)$",
                "^select (--spell --opponent|--opponent --spell) (\\d+)$",
                "^select --field$",
                "^select (--field --opponent|--opponent --field)$",
                "^select --hand (\\d+)$"};
        for (int i = 0; i < selectCardCommandPatterns.length; i++) {
            Pattern pattern = Pattern.compile(selectCardCommandPatterns[i]);
            Matcher matcher = pattern.matcher(selectCardCommand);

            if (matcher.find()) {
                isAddressValid = true;
                if (i == 0) duelPageController.selectMyMonsterCard(Integer.parseInt(matcher.group(1)));
                else if (i == 1) duelPageController.selectMySpellCard(Integer.parseInt(matcher.group(1)));
                else if (i == 2) duelPageController.selectOpponentMonsterCard(Integer.parseInt(matcher.group(2)));
                else if (i == 3) duelPageController.selectOpponentMonsterCard(Integer.parseInt(matcher.group(2)));
                else if (i == 4) duelPageController.selectMyFieldCard();
                else if (i == 5) duelPageController.selectOpponentFieldCard();
                else duelPageController.selectInHandCard(Integer.parseInt(matcher.group(1)));
                break;
            }
        }
        if (!isAddressValid) System.out.println("invalid selection");
    }
}
