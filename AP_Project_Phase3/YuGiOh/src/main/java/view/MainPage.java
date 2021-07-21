package view;

import controller.Controller;
import controller.MainPageController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.commands.LoginCommand;
import model.commands.LoginCommandType;

public class MainPage extends Application {
    public Button lobbyButton;
    private static String message;
    public Button duelButton;
    public Button importOrExportButton;
    public Button shopButton;
    public Button createCardButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Text makeDuelError;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        MainPage.message = message;
    }


    public void exitMenu() throws Exception {
        Page.playButtonClickSound();
        new Page().start(Page.getStage());
    }

    public void openDuelPage() throws Exception {
        Page.playButtonClickSound();
        if (MainPageController.getInstance().newGameWithAI(1)) {
            new RockPaperScissorMenu().start(Page.getStage());
        }
        else makeDuelError.setText(message);
    }

    public void openDeckPage() throws Exception {
        Page.playButtonClickSound();
        new DeckPage().start(Page.getStage());
    }

    public void openScoreboardPage() throws Exception {
        Page.playButtonClickSound();
        Page.setCurrentMenu(Menu.SCOREBOARD);

        new ScoreboardPage().start(Page.getStage());
    }

    private boolean userIsAdmin() {
        return true;
    }

    public void openProfilePage() throws Exception {
        Page.playButtonClickSound();
        //new ProfilePage().start(Page.getStage());
    }

    public void openShopPage() throws Exception {
        Page.playButtonClickSound();
        Page.setCurrentMenu(Menu.SHOP);

        if (Controller.currentUser.getUsername().equals("admin")) {
            new AdminShopPage().start(Page.getStage());
        }
        new ShopPage().start(Page.getStage());
    }

    public void openCreateCardPage(ActionEvent actionEvent) throws Exception {
        Page.playButtonClickSound();
        //new CreateCardPage().start(Page.getStage());
    }

    public void openImportOrExportPage() throws Exception {
        Page.playButtonClickSound();
//        new ImportOrExportPage().start(Page.getStage());
    }

    public void logout() throws Exception {
        Page.playButtonClickSound();
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setToken(Controller.token);
        loginCommand.setLoginCommandType(LoginCommandType.LOGOUT);
        new Page().start(Page.getStage());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void openChatroom(ActionEvent actionEvent) {
        try {
            Page.setCurrentMenu(Menu.CHAT);
            new ChatPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openLobbyPage(ActionEvent actionEvent) throws Exception {
        new LobbyPage().start(Page.getStage());
    }
}
