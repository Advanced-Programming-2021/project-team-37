package view;

import controller.DuelPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Random;

public class RockPaperScissorMenu extends Application {
    public ImageView paper;
    public ImageView rock;
    public ImageView scissor;
    public ImageView randomRockPaperScissor;
    public Button startGameButton;
    public Label roundResult;
    public Button oneRoundButton;
    public Button threeRoundButton;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/rockPaperScissorMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        makeRock();
        makeScissor();
        makePaper();
        makeStartButton();
        makeNumberOfRoundsButton();
    }

    private void makeNumberOfRoundsButton() {
        oneRoundButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().setNumberOfRounds(1);
                oneRoundButton.setDisable(true);
                threeRoundButton.setDisable(true);
            }
        });

        threeRoundButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().setNumberOfRounds(3);
                oneRoundButton.setDisable(true);
                threeRoundButton.setDisable(true);
            }
        });
    }

    private void makeStartButton() {
        startGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                try {
                    new DuelPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void lose() {
        rock.setDisable(true);
        scissor.setDisable(true);
        paper.setDisable(true);
        roundResult.setText("Loser");
        startGameButton.setDisable(false);
        DuelPageController.getInstance().setCurrentTurnUsername(DuelPageController.getInstance().getSecondPlayerUsername());
        DuelPageController.getInstance().setOpponentUsername(DuelPageController.getInstance().getFirstPlayerUsername());
    }

    private void win() {
        rock.setDisable(true);
        scissor.setDisable(true);
        paper.setDisable(true);
        roundResult.setText("Winner");
        startGameButton.setDisable(false);
        DuelPageController.getInstance().setCurrentTurnUsername(DuelPageController.getInstance().getFirstPlayerUsername());
        DuelPageController.getInstance().setOpponentUsername(DuelPageController.getInstance().getSecondPlayerUsername());
    }

    private void draw() {
        roundResult.setText("Try Again");
        paper.setDisable(false);
        scissor.setDisable(false);
        rock.setDisable(false);
        paper.setEffect(null);
        rock.setEffect(null);
        scissor.setEffect(null);
    }

    private void makePaper() {
        paper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rock.setDisable(true);
                scissor.setDisable(true);
                paper.setEffect(new Glow());
                Random random = new Random();
                int number = random.nextInt(3);
                if (number == 0) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/rock.bmp").toExternalForm()));
                    win();
                }
                else if (number == 1) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/scissor.bmp").toExternalForm()));
                    lose();
                }
                else {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/paper.bmp").toExternalForm()));
                    draw();
                }
            }
        });

        paper.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                paper.setEffect(new DropShadow());
            }
        });

        paper.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                paper.setEffect(null);
            }
        });
    }

    private void makeScissor() {
        scissor.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rock.setDisable(true);
                paper.setDisable(true);
                scissor.setEffect(new Glow());
                Random random = new Random();
                int number = random.nextInt(3);
                if (number == 0) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/rock.bmp").toExternalForm()));
                    lose();
                }
                else if (number == 1) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/scissor.bmp").toExternalForm()));
                    draw();
                }
                else {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/paper.bmp").toExternalForm()));
                    win();
                }
            }
        });

        scissor.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                scissor.setEffect(new DropShadow());
            }
        });

        scissor.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                scissor.setEffect(null);
            }
        });
    }

    private void makeRock() {
        rock.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                paper.setDisable(true);
                scissor.setDisable(true);
                rock.setEffect(new Glow());
                Random random = new Random();
                int number = random.nextInt(3);
                if (number == 0) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/rock.bmp").toExternalForm()));
                    draw();
                }
                else if (number == 1) {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/scissor.bmp").toExternalForm()));
                    win();
                }
                else {
                    randomRockPaperScissor.setImage(new Image(getClass().getResource
                            ("/Pictures/RockPaperScissor/paper.bmp").toExternalForm()));
                    lose();
                }
            }
        });

        rock.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                rock.setEffect(new DropShadow());
            }
        });

        rock.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                rock.setEffect(null);
            }
        });
    }
}
