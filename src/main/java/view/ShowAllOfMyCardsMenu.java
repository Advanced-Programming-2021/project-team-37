package view;

import controller.ShopPageController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Card;
import model.User;

import java.util.ArrayList;

public class ShowAllOfMyCardsMenu extends Application {
    public ImageView selectedCard;
    public Text selectedCardDescription;
    public GridPane allOfMyCards;
    public Button backButton;
    public ImageView rightButton;
    public ImageView leftButton;
    public AnchorPane anchorPane;
    private int allOfMyCardsPageNumber = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/showAllOfMyCardsMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        makeBackButton();
        makeAllOfMyCards();
        makeRightButton();
        makeLeftButton();
    }

    private void makeRightButton() {
        String username = ShopPageController.getInstance().getUsername();
        ArrayList<Card> cards = User.getUserByUsername(username).getCards();
        rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (allOfMyCardsPageNumber < Math.floor(cards.size() / 120.0)) allOfMyCardsPageNumber++;
                makeAllOfMyCards();

                leftButton.setDisable(allOfMyCardsPageNumber <= 0);
                rightButton.setDisable(allOfMyCardsPageNumber >= Math.floor(cards.size() / 120.0));
            }
        });

        rightButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rightButton.setEffect(new Glow());
            }
        });

        rightButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                rightButton.setEffect(null);
            }
        });

    }

    private void makeLeftButton() {
        leftButton.setDisable(true);
        String username = ShopPageController.getInstance().getUsername();
        ArrayList<Card> cards = User.getUserByUsername(username).getCards();
        leftButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (allOfMyCardsPageNumber > 0) allOfMyCardsPageNumber--;
                makeAllOfMyCards();

                leftButton.setDisable(allOfMyCardsPageNumber <= 0);
                rightButton.setDisable(allOfMyCardsPageNumber >= Math.floor(cards.size() / 120.0));
            }
        });

        leftButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                leftButton.setEffect(new Glow());
            }
        });

        leftButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                leftButton.setEffect(null);
            }
        });
    }

    private void makeBackButton() {
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ShopPage().start(Page.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeAllOfMyCards() {
        allOfMyCards.setDisable(true);
        allOfMyCards.setOpacity(0);
        allOfMyCards = new GridPane();
        allOfMyCards.setLayoutX(72);
        allOfMyCards.setLayoutY(31);
        anchorPane.getChildren().add(allOfMyCards);

        String username = ShopPageController.getInstance().getUsername();
        ArrayList<Card> cards = User.getUserByUsername(username).getCards();
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 8; j++) {
                int counter = allOfMyCardsPageNumber * 120 + j * 17 + i;
                if (counter < cards.size()) {
                    ImageView cardImage = new ImageView(new Image(getClass().getResource("/Pictures/Cards/AllCards/" +
                            cards.get(counter).getCardName() + ".jpg").toExternalForm()));
                    cardImage.setFitHeight(77);
                    cardImage.setFitWidth(53);

                    cardImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            selectedCard.setImage(cardImage.getImage());
                            selectedCardDescription.setText(cards.get(counter).getDescription());
                        }
                    });

                    cardImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cardImage.setEffect(new Glow());
                        }
                    });

                    cardImage.setOnMouseExited(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            cardImage.setEffect(null);
                        }
                    });

                    allOfMyCards.add(cardImage, i, j);
                }
            }
        }
    }
}
