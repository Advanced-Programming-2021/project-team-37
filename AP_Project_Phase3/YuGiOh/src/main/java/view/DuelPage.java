package view;

import client.ReadMessage;
import client.SendMessage;
import controller.AI;
import controller.DuelPageController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;
import model.CardState;
import model.SelectedCardField;
import model.User;
import model.commands.BattleCommand;

import java.util.ArrayList;

public class DuelPage extends Application {
    private static String message;
    public static DuelPageController duelPageController;
    public ProgressBar opponentLifePointBar = new ProgressBar();
    public Label opponentLifePoint = new Label();
    public ProgressBar myLifePointBar = new ProgressBar();
    public Label myLifePoint = new Label();
    public ImageView drawPhaseImage = new ImageView();
    public ImageView selectedCardImage = new ImageView();
    public ImageView standbyPhaseImage = new ImageView();
    public ImageView battlePhaseImage = new ImageView();
    public ImageView endPhaseImage = new ImageView();
    public ImageView mainPhase1Image = new ImageView();
    public ImageView mainPhase2Image = new ImageView();
    public ImageView opponentGraveyard = new ImageView();
    public ImageView myGraveyard = new ImageView();
    public GridPane opponentSpellAndTrapCards = new GridPane();
    public GridPane opponentMonsterCards = new GridPane();
    public GridPane myMonsterCards = new GridPane();
    public GridPane mySpellAndTrapCards = new GridPane();
    public ImageView myMainDeck = new ImageView();
    public ImageView opponentMainDeck = new ImageView();
    public GridPane opponentInHandCards = new GridPane();
    public ImageView myFieldCard = new ImageView();
    public Label numberOfCardInOpponentMainDeck = new Label();
    public Label numberOfCardInMyMainDeck = new Label();
    public ImageView opponentAvatar = new ImageView();
    public Label numberOfCardInOpponentMainDeck1 = new Label();
    public ImageView myAvatar = new ImageView();
    public Label myUsername = new Label();
    public Label myNickname = new Label();
    public Label opponentUsername = new Label();
    public Label opponentNickname = new Label();
    public Label numberOfCardInMyGraveyard = new Label();
    public Label numberOfCardInOpponentGraveyard = new Label();
    public Text selectedCardDescription = new Text();
    public ImageView opponentFieldCard = new ImageView();
    public GridPane myInHandCards = new GridPane();
    public Button nextPhaseButton = new Button();
    public Button endTurnButton = new Button();
    public GridPane myGraveyardCards = new GridPane();
    public GridPane opponentGraveyardCards = new GridPane();
    public Rectangle opponentGraveyardCardsBackground = new Rectangle();
    public Rectangle myGraveyardCardsBackground = new Rectangle();
    public Button backFromMyGraveyardButton = new Button();
    public Button backFromOpponentGraveyardButton = new Button();
    public Label opponentTurnLabel = new Label();
    public Label myTurnLabel = new Label();
    public Button openCheatMenu = new Button();
    public Button settingButton = new Button();
    public Button setButton = new Button();
    public Button attackButton = new Button();
    public Button summonButton = new Button();
    public Button activateButton = new Button();
    public ImageView gameBoardBackground = new ImageView();
    public Text errorMessage = new Text();
    public Button directAttackButton = new Button();
    public AnchorPane anchorPane;
    public Button changePositionButton = new Button();
    public Button flipSummonButton = new Button();

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
        ReadMessage.setCurrentDuelPage(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/duelPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ((AnchorPane) root).getChildren().get(0).requestFocus();
        stage.show();
    }

    public void initialize() {
        String myUsername = DuelPageController.getInstance().getFirstPlayerUsername();
        String opponentUsername = DuelPageController.getInstance().getSecondPlayerUsername();
        showPlayerInfo(myUsername, opponentUsername);

        makeEffectForMainDecks(myUsername, opponentUsername);
        makeOpenCheatMenu();
        makeSettingButton();
        makeSetButton();
        makeSummonButton();
        makeActivateButton();
        makeAttackButton();
        makeDirectAttackButton();
        makeChangePositionButton();
        makeFlipSummonButton();

        updateGameBoard();
    }

    private void makeFlipSummonButton() {
        flipSummonButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.flipSummon(user.getBoard().getSelectedMyMonsterCardNumber(), user);
                flipSummon();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }


    //TODO set parameters
    public void flipSummon() {
        DuelPageController.getInstance().flipSummon();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void makeChangePositionButton() {
        changePositionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.changePosition(user.getBoard().getSelectedMyMonsterCardNumber(), user);
                changePosition();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    public void changePosition() {
        String username = DuelPageController.getInstance().getUsername();
        Card selectedCard = User.getUserByUsername(username).getBoard().getSelectedCard();
        if (selectedCard != null && User.getUserByUsername(username).getBoard().getSelectedCardField() == SelectedCardField.MY_MONSTER) {
            if (selectedCard.getCardState() == CardState.OO)
                DuelPageController.getInstance().setPosition("defence");
            else if (selectedCard.getCardState() == CardState.DO)
                DuelPageController.getInstance().setPosition("attack");
        }
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void makeDirectAttackButton() {
        directAttackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.directAttack(user.getBoard().getSelectedMyMonsterCardNumber(), user);
                directAttack();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    //TODO set parameters
    public void directAttack() {
        DuelPageController.getInstance().directAttack();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeAttackButton() {
        attackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                attack();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.attack(user.getBoard().getSelectedMyMonsterCardNumber(), user.getBoard().getToBeAttackedCardNumber(),
                        user);
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    //TODO set parameters
    public void attack() {
        DuelPageController.getInstance().attack();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeActivateButton() {
        activateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.activate(user.getBoard().getSelectedCardNumberInHand(), user.getBoard().getSelectedMySpellOrTrapCardNumber(),
                        user);
                activate();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }


    public void activate() {
        DuelPageController.getInstance().activateEffect();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeSummonButton() {
        summonButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.summon(user.getBoard().getSelectedCardNumberInHand(), user);
                summon();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    //TODO set parameters
    public void summon() {
        DuelPageController.getInstance().summonCard();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeSetButton() {
        setButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.set(user.getBoard().getSelectedCardNumberInHand(), user);
                set();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    //TODO set parameters for command
    public void set() {
        DuelPageController.getInstance().set();
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeSettingButton() {
        settingButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                try {
                    new PauseMenu().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeOpenCheatMenu() {
        openCheatMenu.setOnKeyPressed((EventHandler<KeyEvent>) keyEvent -> {
            Page.playButtonClickSound();
            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode().getName().equals("C")) {
                try {
                    new CheatMenu().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showTurn() {
        String currentTurnUsername = DuelPageController.getInstance().getCurrentTurnUsername();
        if (currentTurnUsername.equals(myUsername.getText())) {
            myTurnLabel.setOpacity(1);
            opponentTurnLabel.setOpacity(0);
        } else {
            myTurnLabel.setOpacity(0);
            opponentTurnLabel.setOpacity(1);
        }
    }

    private void makeEffectForMainDecks(String myUsername, String opponentUsername) {
        myMainDeck.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                myMainDeck.setEffect(new DropShadow());
            }
        });

        myMainDeck.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                myMainDeck.setEffect(null);
            }
        });

        opponentMainDeck.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                opponentMainDeck.setEffect(new DropShadow());
            }
        });

        opponentMainDeck.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                opponentMainDeck.setEffect(null);
            }
        });
    }

    public void showPhase() {
        showTurn();
        if (DuelPageController.getInstance().getPhaseNumber() == 2) {
            mainPhase2Image.setEffect(null);
            mainPhase1Image.setEffect(new Glow());
        } else if (DuelPageController.getInstance().getPhaseNumber() == 3) {
            mainPhase1Image.setEffect(null);
            battlePhaseImage.setEffect(new Glow());
        } else if (DuelPageController.getInstance().getPhaseNumber() == 4) {
            battlePhaseImage.setEffect(null);
            mainPhase2Image.setEffect(new Glow());
        }
    }

    private void updateGameBoard() {
        String currentPlayerUsername = DuelPageController.getInstance().getCurrentTurnUsername();
        if (currentPlayerUsername.equals("AI")) {
            phaseWork();
            AI.getInstance().AIPlay();
        }

        showTurn();
        phaseWork();
        DuelPageController.getInstance().roundWinChecker();
        showPhase();

        String myUsername1 = DuelPageController.getInstance().getFirstPlayerUsername();
        String opponentUsername1 = DuelPageController.getInstance().getSecondPlayerUsername();

        updateLifePoints(myUsername1, opponentUsername1);
        updateMyInHandCards(myUsername1);
        updateMyMonsterCards(myUsername1);
        updateMySpellAndTrapCards(myUsername1);
        updateMyFieldCard(myUsername1);
        updateMyGraveyardCard(myUsername1);
        updateOpponentMonsterCards(opponentUsername1);
        updateOpponentSpellAndTrapCards(opponentUsername1);
        updateOpponentInHandCards(opponentUsername1);
        updateOpponentFieldCard(opponentUsername1);
        updateOpponentGraveyardCard(opponentUsername1);
        updateNumberOfGraveyardCards(myUsername1, opponentUsername1);
        updateNumberOfMainDeckCards(myUsername1, opponentUsername1);
        makeNextPhaseButton();
        makeEndTurnButton();
        makeBackFromGraveyardButtons();
        changeGameBoardBackGroundByFieldCard();
        updateMessageError();

        //DuelPageController.getInstance().showGameBoard();
        //DuelPageController.getInstance().showSelectedCard();
    }

    private void updateMessageError() {
        errorMessage.setText(message);
    }

    public void changeGameBoardBackGroundByFieldCard() {
        String fieldCardName = DuelPageController.getInstance().getFieldCardName();
        Image image = null;
        try {
            image = new Image(getClass().getResource("/Pictures/DuelPage/Field/" + fieldCardName + ".jpg")
                    .toExternalForm());
        } catch (Exception e) {
            image = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png")
                    .toExternalForm());
        }
        gameBoardBackground.setImage(image);
    }

    public void closeMyGraveyard(ActionEvent actionEvent) {
        myGraveyardCards.setOpacity(0);
        myGraveyardCards.setDisable(true);
        myGraveyardCardsBackground.setOpacity(0);
        myGraveyardCardsBackground.setDisable(true);
        backFromMyGraveyardButton.setOpacity(0);
        backFromMyGraveyardButton.setDisable(true);
    }

    private void makeBackFromGraveyardButtons() {
        backFromOpponentGraveyardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                opponentGraveyardCards.setOpacity(0);
                opponentGraveyardCards.setDisable(true);
                opponentGraveyardCardsBackground.setOpacity(0);
                opponentGraveyardCardsBackground.setDisable(true);
                backFromOpponentGraveyardButton.setOpacity(0);
                backFromOpponentGraveyardButton.setDisable(true);
            }
        });
    }

    private void showMyGraveyard(String myUsername1) {
        ArrayList<Card> myGraveYardCards1 = User.getUserByUsername(myUsername1).getBoard().getGraveyardCards();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (myGraveYardCards1.size() > (9 * j + i)) {
                    ImageView imageView = getImageView(myGraveYardCards1.get(9 * j + i));
                    myGraveyardCards.add(imageView, i, j);
                }
            }
        }
    }

    private void showOpponentGraveyard(String opponentUsername1) {
        ArrayList<Card> myGraveYardCards1 = User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (myGraveYardCards1.size() > (9 * j + i)) {
                    ImageView imageView = getImageView(myGraveYardCards1.get(9 * j + i));
                    opponentGraveyardCards.add(imageView, i, j);
                }
            }
        }
    }

    private void makeEndTurnButton() {
        endTurnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.endTurn(user);
                endTurn();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }

    public void endTurn() {
        System.out.println("End Turn");
        DuelPageController.getInstance().changeTurn();
        System.out.println("turn: " + DuelPageController.getInstance().getCurrentTurnUsername());
        try {
            start(Page.getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeNextPhaseButton() {

        nextPhaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().nextPhase();
                BattleCommand battleCommand = new BattleCommand();
                DuelPageController DPController = DuelPageController.getInstance();
                User user = User.getUserByUsername(DPController.getCurrentTurnUsername());
                battleCommand.nextPhase(user);
                showPhase();
                SendMessage.getSendMessage().sendMessage(battleCommand);
            }
        });
    }


    private void updateMyGraveyardCard(String myUsername1) {
        Card myGraveYardCard1 = null;
        if (User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size() > 0) {
            myGraveYardCard1 = User.getUserByUsername(myUsername1).getBoard().getGraveyardCards()
                    .get(User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size() - 1);
            if (myGraveYardCard1 != null) {
                Image fieldCardImage = null;
                try {
                    fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                            + myGraveYardCard1.getCardName() + ".jpg").toExternalForm());
                } catch (Exception e) {
                    fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
                }
                myGraveyard.setImage(fieldCardImage);
                Card finalMyGraveYardCard = myGraveYardCard1;

                Image finalFieldCardImage = fieldCardImage;
                myGraveyard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedCardImage.setImage(finalFieldCardImage);
                        selectedCardDescription.setText(finalMyGraveYardCard.getDescription());
                        myGraveyardCards.setOpacity(1);
                        myGraveyardCards.setDisable(false);
                        myGraveyardCardsBackground.setOpacity(1);
                        myGraveyardCardsBackground.setDisable(false);
                        backFromMyGraveyardButton.setOpacity(1);
                        backFromMyGraveyardButton.setDisable(false);
                        showMyGraveyard(myUsername1);
                    }
                });

                myGraveyard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        myGraveyard.setEffect(new Glow());
                    }
                });

                myGraveyard.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        myGraveyard.setEffect(null);
                    }
                });
            }
        }
    }

    private void updateOpponentGraveyardCard(String opponentUsername1) {
        Card opponentGraveYardCard1 = null;
        if (User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards().size() > 0)
            opponentGraveYardCard1 = User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards()
                    .get(User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards().size() - 1);

        if (opponentGraveYardCard1 != null) {
            Image fieldCardImage = null;
            try {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + opponentGraveYardCard1.getCardName() + ".jpg").toExternalForm());
            } catch (Exception e) {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
            }
            opponentGraveyard.setImage(fieldCardImage);
            Card finalOpponentGraveYardCard = opponentGraveYardCard1;

            Image finalFieldCardImage = fieldCardImage;
            opponentGraveyard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(finalFieldCardImage);
                    selectedCardDescription.setText(finalOpponentGraveYardCard.getDescription());
                    opponentGraveyardCards.setOpacity(1);
                    opponentGraveyardCards.setDisable(false);
                    opponentGraveyardCardsBackground.setOpacity(1);
                    opponentGraveyardCardsBackground.setDisable(false);
                    backFromOpponentGraveyardButton.setOpacity(1);
                    backFromOpponentGraveyardButton.setDisable(false);
                    showOpponentGraveyard(opponentUsername1);
                }
            });

            opponentGraveyard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    opponentGraveyard.setEffect(new Glow());
                }
            });

            opponentGraveyard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    opponentGraveyard.setEffect(null);
                }
            });
        }
    }

    private void updateMyInHandCards(String myUsername1) {
        ArrayList<Card> myInHandCards1 = User.getUserByUsername(myUsername1).getBoard().getInHandCards();

        for (int i = 0; i < myInHandCards1.size(); i++) {
            Card card = myInHandCards1.get(i);

            if (card != null) {
                ImageView cardImageView = getImageViewForMyInHandCards(card, i + 1);

                myInHandCards.add(cardImageView, i, 0);
            }
        }
    }

    private ImageView getImageViewForMyInHandCards(Card card, int selectedInHandCardNumber) {
        Image cardImage = null;
        try {
            cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm());
        } catch (Exception e) {
            cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
        }
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        Image finalCardImage = cardImage;
        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedCardImage.setImage(finalCardImage);
                selectedCardDescription.setText(card.getCardName() + "\r\n" + card.getDescription());
                DuelPageController.getInstance().selectInHandCard(selectedInHandCardNumber);
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
                cardImageView.setEffect(new Glow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }

    private void updateOpponentInHandCards(String opponentUsername1) {
        ArrayList<Card> opponentInHandCards1 = User.getUserByUsername(opponentUsername1).getBoard().getInHandCards();
        for (int i = 0; i < opponentInHandCards1.size(); i++) {
            Card card = opponentInHandCards1.get(i);
            if (card != null) {
                ImageView cardImageView = getImageView(card);
                Image cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg").toExternalForm());
                cardImageView.setFitHeight(77);
                cardImageView.setFitWidth(53);

                cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedCardImage.setImage(cardImage);
                        selectedCardDescription.setText("");
                    }
                });

                cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImageView.setEffect(new DropShadow());
                    }
                });

                cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImageView.setEffect(null);
                    }
                });

                cardImageView.setImage(cardImage);
                opponentInHandCards.add(cardImageView, i, 0);
            }
        }
    }

    private void updateMyFieldCard(String myUsername1) {
        Card myFieldCard1 = User.getUserByUsername(myUsername1).getBoard().getFieldCard();
        if (myFieldCard1 != null) {
            Image fieldCardImage;
            try {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + myFieldCard1.getCardName() + ".jpg").toExternalForm());
            } catch (Exception e) {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
            }
            myFieldCard.setImage(fieldCardImage);

            Image finalFieldCardImage = fieldCardImage;
            myFieldCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(finalFieldCardImage);
                    selectedCardDescription.setText(myFieldCard1.getDescription());
                }
            });

            myFieldCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    myFieldCard.setEffect(new Glow());
                }
            });

            myFieldCard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    myFieldCard.setEffect(null);
                }
            });
        }
    }

    private void updateOpponentFieldCard(String opponentUsername1) {
        Card opponentFieldCard1 = User.getUserByUsername(opponentUsername1).getBoard().getFieldCard();
        if (opponentFieldCard1 != null) {
            Image fieldCardImage;
            try {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + opponentFieldCard1.getCardName() + ".jpg").toExternalForm());
            } catch (Exception e) {
                fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
            }
            opponentFieldCard.setImage(fieldCardImage);

            Image finalFieldCardImage = fieldCardImage;
            opponentFieldCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(finalFieldCardImage);
                    selectedCardDescription.setText(opponentFieldCard1.getDescription());
                }
            });

            opponentFieldCard.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    opponentFieldCard.setEffect(new Glow());
                }
            });

            opponentFieldCard.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    opponentFieldCard.setEffect(null);
                }
            });
        }
    }

    private void updateOpponentMonsterCards(String opponentUsername1) {
        for (int i = 0; i < 5; i++) {
            Card card = User.getUserByUsername(opponentUsername1).getBoard().getMonsterCards()[i + 1];
            if (card != null) {
                ImageView cardImageView = getImageViewForOpponentMonsterCards(card, i + 1);
                opponentMonsterCards.add(cardImageView, i, 0);
            } else {
                ImageView emptyCard = new ImageView();
                emptyCard.setFitHeight(77);
                emptyCard.setFitWidth(53);

                opponentMonsterCards.add(emptyCard, i, 0);
            }
        }
    }

    private ImageView getImageViewForOpponentMonsterCards(Card card, int opponentMonsterSelectedNumber) {
        String description = "";
        ImageView cardImageView = new ImageView();
        if (card.getCardState() == CardState.OO) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
            description = card.getDescription();
        } else if (card.getCardState() == CardState.DO) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
            cardImageView.setRotate(90);
            description = card.getDescription();
        } else if (card.getCardState() == CardState.DH) {
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                    .toExternalForm()));
            cardImageView.setRotate(90);
        }

        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        String finalDescription = description;
        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().selectOpponentMonsterCard(opponentMonsterSelectedNumber);
                selectedCardImage.setImage(cardImageView.getImage());
                selectedCardDescription.setText(finalDescription);
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }

    private void updateOpponentSpellAndTrapCards(String opponentUsername1) {
        for (int i = 0; i < 5; i++) {
            Card card = User.getUserByUsername(opponentUsername1).getBoard().getSpellOrTrapCards()[i + 1];
            if (card != null) {
                ImageView cardImageView = getImageViewForOpponentSpellCards(card, i + 1);
                opponentSpellAndTrapCards.add(cardImageView, i, 0);
            }
        }
    }

    private ImageView getImageViewForOpponentSpellCards(Card card, int selectedSpellOpponentCardNumber) {
        String description = "";
        ImageView cardImageView = new ImageView();
        if (card.getCardState() == CardState.O) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
            description = card.getDescription();
        } else if (card.getCardState() == CardState.H) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                        .toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
        }

        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        String finalDescription = description;
        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().selectOpponentSpellCard(selectedSpellOpponentCardNumber);
                selectedCardImage.setImage(cardImageView.getImage());
                selectedCardDescription.setText(finalDescription);
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }

    private void updateMyMonsterCards(String myUsername1) {

        for (int i = 1; i < 6; i++) {
            Card card = User.getUserByUsername(myUsername1).getBoard().getMonsterCards()[i];
            if (card != null) {
                ImageView cardImageView = getImageViewForMyMonsterCards(card, i);

                myMonsterCards.add(cardImageView, i - 1, 0);
            } else {
                ImageView emptyCard = new ImageView();
                emptyCard.setFitHeight(77);
                emptyCard.setFitWidth(53);

                myMonsterCards.add(emptyCard, i - 1, 0);
            }
        }
    }

    private ImageView getImageViewForMyMonsterCards(Card card, int myMonsterCardSelectedNumber) {
        ImageView cardImageView = new ImageView();
        if (card.getCardState() == CardState.OO) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
        } else if (card.getCardState() == CardState.DO) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
            cardImageView.setRotate(90);
        } else if (card.getCardState() == CardState.DH) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                        .toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
            cardImageView.setRotate(90);
        }

        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().selectMyMonsterCard(myMonsterCardSelectedNumber);
                selectedCardImage.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
                selectedCardDescription.setText(card.getCardName() + "\r\n" + card.getDescription());
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }

    private void updateMySpellAndTrapCards(String myUsername1) {
        for (int i = 0; i < 5; i++) {
            Card card = User.getUserByUsername(myUsername1).getBoard().getSpellOrTrapCards()[i + 1];
            if (card != null) {
                ImageView cardImageView = getImageViewForMySpellCards(card, i + 1);
                mySpellAndTrapCards.add(cardImageView, i, 0);
            }
        }
    }

    private ImageView getImageViewForMySpellCards(Card card, int mySpellSelectedCardNumber) {
        ImageView cardImageView = new ImageView();
        if (card.getCardState() == CardState.O) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
        } else if (card.getCardState() == CardState.H) {
            try {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                        .toExternalForm()));
            } catch (Exception e) {
                cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm()));
            }
        }

        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().selectMySpellCard(mySpellSelectedCardNumber);
                try {
                    selectedCardImage.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                            + card.getCardName() + ".jpg").toExternalForm()));
                } catch (Exception e) {
                    selectedCardImage.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png")
                            .toExternalForm()));
                }

                selectedCardDescription.setText(card.getDescription());
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }

    private ImageView getImageView(Card card) {
        Image cardImage = null;
        try {
            cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm());
        } catch (Exception e) {
            cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/createdCard.png").toExternalForm());
        }
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        Image finalCardImage = cardImage;
        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedCardImage.setImage(finalCardImage);
                selectedCardDescription.setText(card.getDescription());
            }
        });

        cardImageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(new DropShadow());
            }
        });

        cardImageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                cardImageView.setEffect(null);
            }
        });

        return cardImageView;
    }


    private void updateNumberOfGraveyardCards(String myUsername1, String opponentUsername1) {
        if (User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size() > 0)
            numberOfCardInMyGraveyard.setText
                    (String.valueOf(User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size()));
        if (User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards().size() > 0)
            numberOfCardInOpponentGraveyard.setText
                    (String.valueOf(User.getUserByUsername(opponentUsername1).getBoard().getGraveyardCards().size()));
    }

    private void updateNumberOfMainDeckCards(String myUsername1, String opponentUsername1) {
        numberOfCardInMyMainDeck.setText
                (String.valueOf(User.getUserByUsername(myUsername1).getBoard().getMainDeckCards().size()));
        numberOfCardInOpponentMainDeck.setText
                (String.valueOf(User.getUserByUsername(opponentUsername1).getBoard().getMainDeckCards().size()));
    }

    private void showPlayerInfo(String myUsername1, String opponentUsername1) {
        myUsername.setText(myUsername1);
        myNickname.setText(User.getUserByUsername(myUsername1).getNickname());
        Image myAvatarImage = new Image(getClass().getResource
                (User.getUserByUsername(myUsername1).getProfileImageAddress()).toExternalForm());
        myAvatar.setImage(myAvatarImage);
        opponentUsername.setText(opponentUsername1);
        opponentNickname.setText(User.getUserByUsername(opponentUsername1).getNickname());
        Image opponentAvatarImage = new Image(getClass().getResource
                (User.getUserByUsername(opponentUsername1).getProfileImageAddress()).toExternalForm());
        opponentAvatar.setImage(opponentAvatarImage);
    }

    private void updateLifePoints(String myUsername, String opponentUsername) {
        int myLifePointNumber = User.getUserByUsername(myUsername).getLifePoints();
        int opponentLifePointNumber = User.getUserByUsername(opponentUsername).getLifePoints();
        myLifePointBar.setProgress(myLifePointNumber / 8000.0);
        opponentLifePointBar.setProgress(opponentLifePointNumber / 8000.0);
        myLifePoint.setText(String.valueOf(myLifePointNumber));
        opponentLifePoint.setText(String.valueOf(opponentLifePointNumber));
    }

    public void phaseWork() {
        if (DuelPageController.getInstance().getPhaseNumber() == 0)
            for (int i = 0; i < 3; i++) {
                work();
            }
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
        return 1;//scanner.nextInt();
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        DuelPage.message = message;
    }

    public void exitMenu() throws Exception {
        Page.stopFieldMusic();
        Page.playThemeMusic();
        new MainPage().start(Page.getStage());
    }

//    private static void selectCard(String selectCardCommand) {
//        boolean isAddressValid = false;
//        String[] selectCardCommandPatterns = {"^select --monster (\\d+)$",
//                "^select --spell (\\d+)$",
//                "^select (--monster --opponent|--opponent --monster) (\\d+)$",
//                "^select (--spell --opponent|--opponent --spell) (\\d+)$",
//                "^select --field$",
//                "^select (--field --opponent|--opponent --field)$",
//                "^select --hand (\\d+)$"};
//        for (int i = 0; i < selectCardCommandPatterns.length; i++) {
//            Pattern pattern = Pattern.compile(selectCardCommandPatterns[i]);
//            Matcher matcher = pattern.matcher(selectCardCommand);
//
//            if (matcher.find()) {
//                isAddressValid = true;
//                if (i == 0) duelPageController.selectMyMonsterCard(Integer.parseInt(matcher.group(1)));
//                else if (i == 1) duelPageController.selectMySpellCard(Integer.parseInt(matcher.group(1)));
//                else if (i == 2) duelPageController.selectOpponentMonsterCard(Integer.parseInt(matcher.group(2)));
//                else if (i == 3) duelPageController.selectOpponentSpellCard(Integer.parseInt(matcher.group(2)));
//                else if (i == 4) duelPageController.selectMyFieldCard();
//                else if (i == 5) duelPageController.selectOpponentFieldCard();
//                else duelPageController.selectInHandCard(Integer.parseInt(matcher.group(1)));
//                break;
//            }
//        }
//        if (!isAddressValid) System.out.println("invalid selection");
//    }
}