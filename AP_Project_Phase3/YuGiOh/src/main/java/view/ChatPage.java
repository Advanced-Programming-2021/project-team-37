package view;

import client.SendMessage;
import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Message;
import model.MessageSender;
import model.commands.ChatRoomCommand;
import model.commands.ChatRoomCommandType;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatPage extends Application {

    public Button sendButton;
    public TextField textField;
    public ListView chatMessages; // all chat messages
    private static ArrayList<Message> allChatMessages = new ArrayList<>();
    public Text selectedAvatarInformation;
    public ImageView selectedAvatarImage;
    private Thread chatThread;
    private Timer timer;

    public static ArrayList<Message> getAllChatMessages() {
        return allChatMessages;
    }

    public static void setAllChatMessages(ArrayList<Message> allChatMessages) {
        ChatPage.allChatMessages = allChatMessages;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/chatPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        checkSendMessageButton();
        updateChatMessagesFromServer();
        showChatMessages();
        updateChatMessages();
    }

    private void updateChatMessagesFromServer() {
        ChatRoomCommand command = new ChatRoomCommand();
        command.setChatRoomCommandType(ChatRoomCommandType.UPDATE_CHAT_MESSAGES);
        SendMessage.getSendMessage().sendMessage(command);
    }

    private void checkSendMessageButton() {
        sendButton.setDisable(textField.getText().equals(""));
    }

    public void back(MouseEvent mouseEvent) {
        try {
            Page.setCurrentMenu(Menu.MAIN);
            new MainPage().start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MouseEvent mouseEvent) {
        ChatRoomCommand command = new ChatRoomCommand();
        command.setChatMessage(new Message(new MessageSender(Controller.currentUser), textField.getText()));
        command.setChatRoomCommandType(ChatRoomCommandType.SEND_MESSAGE);
        SendMessage.getSendMessage().sendMessage(command);

        textField.clear();
    }

    public void updateChatMessages() {
        chatThread = new Thread(() -> {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        try {
                            if (Page.getCurrentMenu() == Menu.CHAT) {
                                updateChatMessagesFromServer();
                                showChatMessages();
                            }
                            checkSendMessageButton();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            long frameTimeInMilliseconds = 500;
            timer.schedule(timerTask, 0, frameTimeInMilliseconds);
        });
        chatThread.start();
    }

    private synchronized void showChatMessages() {
        chatMessages.getItems().clear();

        for (int i = 0; i < allChatMessages.size(); i++) {
            Label label = new Label();
            label.setText(allChatMessages.get(i).getMessageText());
            ImageView avatar = new ImageView(new Image(getClass().getResource
                    (allChatMessages.get(i).getSender().getProfileImageAddress()).toExternalForm()));
            avatar.setFitWidth(40);
            avatar.setFitHeight(40);

            chatMessages.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    int index = chatMessages.getSelectionModel().getSelectedIndex();

                    StringBuilder userInformation = new StringBuilder();
                    MessageSender sender = allChatMessages.get(index / 2).getSender();
                    userInformation.append("username: ").append(sender.getUsername()).append(System.lineSeparator());
                    userInformation.append("nickname: ").append(sender.getNickname()).append(System.lineSeparator());
                    userInformation.append("score: ").append(sender.getScore()).append(System.lineSeparator());
                    userInformation.append("money: ").append(sender.getMoney()).append(System.lineSeparator());

                    selectedAvatarInformation.setText(userInformation.toString());
                    selectedAvatarImage.setImage(new Image(getClass().getResource(sender.getProfileImageAddress())
                            .toExternalForm()));
                }
            });

            chatMessages.getItems().add(avatar);

            chatMessages.getItems().add(label.getText());
        }
    }
}
