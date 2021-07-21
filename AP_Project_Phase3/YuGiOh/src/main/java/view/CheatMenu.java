package view;

import controller.DuelPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CheatMenu extends Application {
    public Rectangle cheatMenuBackGround;
    public TextField cheatIncreaseLPAmount;
    public Button cheatIncreaseLPButton;
    public Button backFromCheatMenu;
    public TextField cheatSetDuelWinnerNickname;
    public Button cheatSetDuelWinnerButton;
    public TextField cheatIncreaseMoneyAmount;
    public Button cheatIncreaseMoneyButton;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/cheatMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        makeIncreaseLPButton();
        makeSetWinnerButton();
        makeIncreaseMoneyButton();
    }

    private void makeIncreaseMoneyButton() {
        cheatIncreaseLPButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().increaseLifePoint(Integer.parseInt(cheatIncreaseLPAmount.getText()));
            }
        });
    }

    private void makeSetWinnerButton() {
        cheatSetDuelWinnerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                if (DuelPageController.getInstance().setWinner(cheatSetDuelWinnerNickname.getText()))
                    try {
                        new EndOfGameMenu().start(Page.getStage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    private void makeIncreaseLPButton() {
        cheatIncreaseMoneyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().increaseMoney(Integer.parseInt(cheatIncreaseMoneyAmount.getText()));
            }
        });
    }

    public void backToDuelPage() {
        Page.playButtonClickSound();
        try {
            new DuelPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
