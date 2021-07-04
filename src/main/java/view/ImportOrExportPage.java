package view;

import controller.ImportOrExportPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportOrExportPage extends Application {
    private static String message;

    public void importCards(User user) {

    }

    public void exportCards(User user) {

    }

    public void setUsername(String username) {

    }

    public void setCommandPatterns(String commandPatterns) {

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
        ImportOrExportPage.message = message;
    }

    public void exitMenu() throws Exception {
        new MainPage().start(Page.getStage());
    }

    public void showCurrentMenu() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/importOrExportPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
