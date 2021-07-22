package view;

import client.ReadMessage;
import client.SendMessage;
import controller.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.commands.BattleCommand;
import model.commands.CommandClass;
import model.commands.OnlinePlayersCommand;

import java.util.ArrayList;

final class CenteredListViewCell extends ListCell<String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            Label label = new Label(item);
            label.setAlignment(Pos.CENTER);

            hBox.getChildren().add(label);
            setGraphic(hBox);
            if (item.equals(Controller.currentUser.getUsername()))
                setDisable(true);
        }
    }
}


public class LobbyPage extends Application {

    private static Alert afterRequestAlert;

    public ListView<String> onlinePlayersList;
    public GridPane gridPane;

    private String selectedUsername;


    public LobbyPage() {
        ReadMessage.setCurrentLobbyPage(this);
    }


    public void showOnlinePlayers(ArrayList<String> onlinePlayers) {
        onlinePlayersList.getItems().clear();
        onlinePlayersList.setCellFactory(StringListView -> new CenteredListViewCell());
        onlinePlayersList.getItems().addAll(onlinePlayers);
        onlinePlayersList.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                ObservableList<? extends Integer> selectedIndices = onlinePlayersList.getSelectionModel().getSelectedIndices();
                selectedUsername = onlinePlayersList.getItems().get(selectedIndices.get(0));
            }
        });
    }

    private void addButtons() {
        Button request = new Button("Request");
        request.setOnAction(actionEvent -> {
            BattleCommand battleCommand = new BattleCommand();
            ButtonType ONE = new ButtonType("1", ButtonBar.ButtonData.OK_DONE);
            ButtonType THREE = new ButtonType("3", ButtonBar.ButtonData.OK_DONE);
            Alert alert = new Alert(Alert.AlertType.NONE, "Select The Number of Rounds.", ONE, THREE);
            alert.showAndWait();
            if (alert.getResult() == ONE) battleCommand.setNumberOfRounds(1);
            else if (alert.getResult() == THREE) battleCommand.setNumberOfRounds(3);
            alert.close();
            battleCommand.startBattle(selectedUsername, Controller.currentUser);
            SendMessage.getSendMessage().sendMessage(battleCommand);
            ButtonType cancelRequest = new ButtonType("Cancel Request", ButtonBar.ButtonData.OK_DONE);
            afterRequestAlert = new Alert(Alert.AlertType.NONE, "Request has been sent.", cancelRequest);
            afterRequestAlert.showAndWait();
            if (afterRequestAlert.getResult() == cancelRequest) {
                BattleCommand newBattleCommand = new BattleCommand();
                newBattleCommand.cancelSentRequest(selectedUsername, Controller.currentUser);
                SendMessage.getSendMessage().sendMessage(newBattleCommand);
            }
            afterRequestAlert.close();
        });
        Button exit = new Button("Exit");
        exit.setOnAction(actionEvent -> {
            try {
                new MainPage().start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> {
            try {
                start(Page.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        gridPane.add(request, 1, 1);
        gridPane.add(refresh, 1, 2);
        gridPane.add(exit, 1, 3);
        GridPane.setHalignment(request, HPos.CENTER);
        GridPane.setHalignment(refresh, HPos.CENTER);
        GridPane.setHalignment(exit, HPos.CENTER);
    }


    public static Alert getAfterRequestAlert() {
        return afterRequestAlert;
    }

    public static void setAfterRequestAlert(Alert afterRequestAlert) {
        afterRequestAlert = afterRequestAlert;
    }

    @Override
    public void start(Stage stage) throws Exception {
        gridPane = new GridPane();
        gridPane.setMinSize(800, 600);
        gridPane.setPadding(new Insets(100, 100, 100, 100));
        gridPane.setVgap(20);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.CENTER);
        onlinePlayersList = new ListView<>();
        onlinePlayersList.setMinSize(800, 600);
        SendMessage.getSendMessage().sendMessage(new OnlinePlayersCommand());
        gridPane.add(onlinePlayersList, 1, 0);
        GridPane.setHalignment(onlinePlayersList, HPos.CENTER);
        addButtons();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    public void run(String[] args) {
        launch(args);
    }

}
