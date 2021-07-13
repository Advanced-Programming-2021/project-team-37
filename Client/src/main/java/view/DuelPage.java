package view;

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

import java.util.ArrayList;

public class DuelPage extends Application {
    private static String message;
    public static DuelPageController duelPageController;
    public ProgressBar opponentLifePointBar;
    public Label opponentLifePoint;
    public ProgressBar myLifePointBar;
    public Label myLifePoint;
    public ImageView drawPhaseImage;
    public ImageView selectedCardImage;
    public ImageView standbyPhaseImage;
    public ImageView battlePhaseImage;
    public ImageView endPhaseImage;
    public ImageView mainPhase1Image;
    public ImageView mainPhase2Image;
    public ImageView opponentGraveyard;
    public ImageView myGraveyard;
    public GridPane opponentSpellAndTrapCards;
    public GridPane opponentMonsterCards;
    public GridPane myMonsterCards;
    public GridPane mySpellAndTrapCards;
    public ImageView myMainDeck;
    public ImageView opponentMainDeck;
    public GridPane opponentInHandCards;
    public ImageView myFieldCard;
    public Label numberOfCardInOpponentMainDeck;
    public Label numberOfCardInMyMainDeck;
    public ImageView opponentAvatar;
    public Label numberOfCardInOpponentMainDeck1;
    public ImageView myAvatar;
    public Label myUsername;
    public Label myNickname;
    public Label opponentUsername;
    public Label opponentNickname;
    public Label numberOfCardInMyGraveyard;
    public Label numberOfCardInOpponentGraveyard;
    public Text selectedCardDescription;
    public ImageView opponentFieldCard;
    public GridPane myInHandCards;
    public Button nextPhaseButton;
    public Button endTurnButton;
    public GridPane myGraveyardCards;
    public GridPane opponentGraveyardCards;
    public Rectangle opponentGraveyardCardsBackground;
    public Rectangle myGraveyardCardsBackground;
    public Button backFromMyGraveyardButton;
    public Button backFromOpponentGraveyardButton;
    public Label opponentTurnLabel;
    public Label myTurnLabel;
    public Button openCheatMenu;
    public Button settingButton;
    public Button setButton;
    public Button attackButton;
    public Button summonButton;
    public Button activateButton;
    public ImageView gameBoardBackground;
    public Text errorMessage;
    public Button directAttackButton;
    public AnchorPane anchorPane;
    public Button changePositionButton;
    public Button flipSummonButton;

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
                DuelPageController.getInstance().flipSummon();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeChangePositionButton() {
        String username = DuelPageController.getInstance().getUsername();
        changePositionButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
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
        });
    }

    private void makeDirectAttackButton() {
        directAttackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().directAttack();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeAttackButton() {
        attackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().attack();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeActivateButton() {
        activateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().activateEffect();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeSummonButton() {
        summonButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().summonCard();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeSetButton() {
        setButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Page.playButtonClickSound();
                DuelPageController.getInstance().set();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void showPhase() {
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
        Image image = new Image(getClass().getResource("/Pictures/DuelPage/Field/" + fieldCardName + ".jpg")
                .toExternalForm());
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
                DuelPageController.getInstance().changeTurn();
                try {
                    start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeNextPhaseButton() {

        nextPhaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().nextPhase();
                showPhase();
            }
        });
    }


    private void updateMyGraveyardCard(String myUsername1) {
        Card myGraveYardCard1 = null;
        if (User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size() > 0) {
            myGraveYardCard1 = User.getUserByUsername(myUsername1).getBoard().getGraveyardCards()
                    .get(User.getUserByUsername(myUsername1).getBoard().getGraveyardCards().size() - 1);
            if (myGraveYardCard1 != null) {
                Image fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + myGraveYardCard1.getCardName() + ".jpg").toExternalForm());
                myGraveyard.setImage(fieldCardImage);
                Card finalMyGraveYardCard = myGraveYardCard1;

                myGraveyard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        selectedCardImage.setImage(fieldCardImage);
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
            Image fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + opponentGraveYardCard1.getCardName() + ".jpg").toExternalForm());
            opponentGraveyard.setImage(fieldCardImage);
            Card finalOpponentGraveYardCard = opponentGraveYardCard1;

            opponentGraveyard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(fieldCardImage);
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
        Image cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                + card.getCardName() + ".jpg").toExternalForm());
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedCardImage.setImage(cardImage);
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
            Image fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + myFieldCard1.getCardName() + ".jpg").toExternalForm());
            myFieldCard.setImage(fieldCardImage);

            myFieldCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(fieldCardImage);
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
            Image fieldCardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + opponentFieldCard1.getCardName() + ".jpg").toExternalForm());
            opponentFieldCard.setImage(fieldCardImage);

            opponentFieldCard.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedCardImage.setImage(fieldCardImage);
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
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
            description = card.getDescription();
        } else if (card.getCardState() == CardState.DO) {
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
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
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
            description = card.getDescription();
        } else if (card.getCardState() == CardState.H) {
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                    .toExternalForm()));
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
        if (card.getCardState() == CardState.OO)
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
        else if (card.getCardState() == CardState.DO) {
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
            cardImageView.setRotate(90);
        } else if (card.getCardState() == CardState.DH) {
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                    .toExternalForm()));
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
        if (card.getCardState() == CardState.O)
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                    + card.getCardName() + ".jpg").toExternalForm()));
        else if (card.getCardState() == CardState.H)
            cardImageView.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/Unknown.jpg")
                    .toExternalForm()));

        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DuelPageController.getInstance().selectMySpellCard(mySpellSelectedCardNumber);
                selectedCardImage.setImage(new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                        + card.getCardName() + ".jpg").toExternalForm()));
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
        Image cardImage = new Image(getClass().getResource("/Pictures/Cards/AllCards/"
                + card.getCardName() + ".jpg").toExternalForm());
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitHeight(77);
        cardImageView.setFitWidth(53);

        cardImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedCardImage.setImage(cardImage);
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
