package view;

import controller.ImportOrExportPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ImportOrExportPage extends Application {
    private static String message;

    public TextField importCardName;
    public TextField exportCardName;
    public Text result = new Text("");


    public void importCard() {
        Page.playButtonClickSound();
        String cardName = importCardName.getText();
        if (cardName.equals(""))
            message = "you must enter a card name";
        else
            ImportOrExportPageController.getInstance().importCard(cardName);
        result.setText(message);
    }

    public void exportCard() {
        Page.playButtonClickSound();
        String cardName = exportCardName.getText();
        if (cardName.equals(""))
            message = "you must enter a card name";
        else
            ImportOrExportPageController.getInstance().exportCard(cardName);
        result.setText(message);
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
        Page.playButtonClickSound();
        new MainPage().start(Page.getStage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/importOrExportPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }
}
