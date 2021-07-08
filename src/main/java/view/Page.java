package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Page extends Application {
    private static Stage stage;
    private static String message;
    @FXML public MediaView themeMediaView;
    public static MediaPlayer themeMusic;
    public static MediaPlayer fieldMusic;
    private static AudioClip buttonClickSound;
    private static AudioClip coinSound;
    private static AudioClip notEnoughCoin;


    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Page.stage = stage;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Page.message = message;
    }

    protected static int functionNumber;
    protected static boolean isCommandValid = false;
    protected String[] commandPatterns;
    private int commandNumber;

    public void setCommandPatterns(String commandPatterns) {

    }

    public void exitMenu() throws Exception {

    }

    public void showCurrentMenu() {

    }

    public void openLoginPage() throws Exception {
        Page.playButtonClickSound();
        new LoginPage().start(stage);
    }

    public void openRegisterPage() throws IOException {
        Page.playButtonClickSound();
        new RegisterPage().start(stage);
    }

    public void exitProgram() {
        Page.playButtonClickSound();
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws Exception {
        playThemeMusic();
        themeMediaView = new MediaView(themeMusic);
        Page.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/View/firstPage.fxml"));
        stage.setTitle("YuGiOh");
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Pictures/Icon/icon.png"))));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static void playThemeMusic() {
        if (themeMusic == null) {
            File themeMusicFile = new File("src/main/resources/sounds/theme.mp3");
            themeMusic = new MediaPlayer(new Media(themeMusicFile.toURI().toString()));
        }
        if (!themeMusic.isAutoPlay()) {
            themeMusic.setOnEndOfMedia(() -> themeMusic.seek(Duration.ZERO));
            themeMusic.setVolume(2);
            themeMusic.play();
        }
    }


    public static void stopThemeMusic() {
        themeMusic.stop();
    }


    public static void playFieldMusic() {
        File fieldMusicFile = new File("src/main/resources/sounds/fieldMusic.mp3");
        fieldMusic = new MediaPlayer(new Media(fieldMusicFile.toURI().toString()));
        fieldMusic.setOnEndOfMedia(() -> fieldMusic.seek(Duration.ZERO));
        fieldMusic.setVolume(2);
        fieldMusic.play();
    }


    public static void stopFieldMusic() {
        fieldMusic.stop();
    }


    public static void playButtonClickSound() {
        File buttonClickFile = new File("src/main/resources/sounds/buttonClick.mp3");
        buttonClickSound = new AudioClip(buttonClickFile.toURI().toString());
        buttonClickSound.setVolume(2);
        buttonClickSound.play();
    }

    public static void playCoinSound() {
        File coinSoundFile = new File("src/main/resources/sounds/coinSound.wav");
        coinSound = new AudioClip(coinSoundFile.toURI().toString());
        coinSound.setVolume(2);
        coinSound.play();
    }

    public static void playNotEnoughCoin() {
        File notEnoughCoinFile = new File("src/main/resources/sounds/notEnoughCoin.mp3");
        notEnoughCoin = new AudioClip(notEnoughCoinFile.toURI().toString());
        notEnoughCoin.setVolume(2);
        notEnoughCoin.play();
    }


    public void run() {
        launch();
    }
}
