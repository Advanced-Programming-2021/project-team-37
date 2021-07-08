package view;

import controller.DuelPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EndOfGameMenu extends Application {
    public Label showWinnerLabel;
    public Button backToMainMenuButton;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/endOfGameMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        backToMainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new MainPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        showWinnerLabel.setText(DuelPageController.getInstance().getGameWinnerUsername() + " is winner");
    }
}
