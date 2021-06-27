package controller;

import model.*;
import view.DeckPage;
import view.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DeckPageController extends Controller {
    private static DeckPageController instance;

    private DeckPageController() {

    }

    public static DeckPageController getInstance() {
        if (instance == null)
            instance = new DeckPageController();
        return instance;
    }

    public void removeCardFromDeck(String cardName, String deckName, boolean isSide) {
        if (!User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(deckName))
            DeckPage.setMessage("deck with name " + deckName + " does not exist");
        else if (isSide) {
            if (!User.getUserByUsername(username).getDeckByDeckName(deckName).isCardExistsInSideDeck(cardName))
                DeckPage.setMessage("card with name " + cardName + " does not exist in side deck");
            else {
                User.getUserByUsername(username).getDeckByDeckName(deckName).getCards().remove(Card.getCardByName(cardName));
                User.getUserByUsername(username).getDeckByDeckName(deckName).getSideDeckCards().remove(Card.getCardByName(cardName));
                DeckPage.setMessage("card removed form deck successfully");
            }
        } else {
            if (!User.getUserByUsername(username).getDeckByDeckName(deckName).isCardExistsInMainDeck(cardName))
                DeckPage.setMessage("card with name " + cardName + " does not exist in main deck");
            else {
                User.getUserByUsername(username).getDeckByDeckName(deckName).getCards().remove(Card.getCardByName(cardName));
                User.getUserByUsername(username).getDeckByDeckName(deckName).getMainDeckCards().remove(Card.getCardByName(cardName));
                DeckPage.setMessage("card removed form deck successfully");
            }
        }
    }

    public void createDeck(String name) {
        if (User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(name))
            Page.setMessage("deck with name " + name + " already exists");
        else {
            User.getUserByUsername(username).getDecks().add(new Deck(name));
            Page.setMessage("deck created successfully!");
        }
    }

    public void deleteDeck(String name) {
        if (!User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(name))
            Page.setMessage("deck with name " + name + " does not exist");
        else {
            ArrayList<Card> cards = User.getUserByUsername(username).getDeckByDeckName(name).getCards();
            for (Card card : cards) {
                User.getUserByUsername(username).getCards().add(card);
            }
            User.getUserByUsername(username).getDecks().remove(User.getUserByUsername(username).getDeckByDeckName(name));
            Page.setMessage("deck deleted successfully");
        }
    }

    public void setActiveDeck(String name) {
        if (!User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(name))
            Page.setMessage("deck with name " + name + " does not exist");
        else {
            User.getUserByUsername(username).getDeckByDeckName(name).setActivated(true);
            User.getUserByUsername(username).setActivatedDeck(User.getUserByUsername(username).getDeckByDeckName(name));
            Page.setMessage("deck activated successfully");
        }
    }

    public void addCardToDeck(String cardName, String deckName, boolean isSide) {
        if (!User.getUserByUsername(username).isCardWithThisNameAlreadyExists(cardName))
            DeckPage.setMessage("card with name " + cardName + " does not exist");
        else if (!User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(deckName))
            DeckPage.setMessage("deck with name " + deckName + " does not exist");
        else if (isSide) {
            if (User.getUserByUsername(username).getDeckByDeckName(deckName).getSideDeckCards().size() == 15)
                DeckPage.setMessage("side deck is full");
            else if (User.getUserByUsername(username).getDeckByDeckName(deckName).howManyCardWithThisName(cardName) == 3)
                DeckPage.setMessage("there are already three cards with name " + cardName + " in deck " + deckName);
            else {
                addCardToSideDeck(deckName, cardName);
                DeckPage.setMessage("card added to deck successfully");
            }
        } else {
            if (User.getUserByUsername(username).getDeckByDeckName(deckName).getMainDeckCards().size() == 60)
                DeckPage.setMessage("main deck is full");
            else if (User.getUserByUsername(username).getDeckByDeckName(deckName).howManyCardWithThisName(cardName) == 3)
                DeckPage.setMessage("there are already three cards with name " + cardName + " in deck " + deckName);
            else {
                addCardToMainDeck(deckName, cardName);
                DeckPage.setMessage("card added to deck successfully");
            }
        }
    }

    private void addCardToMainDeck(String deckName, String cardName) {
        Card card = Card.getCardByName(cardName);
        User.getUserByUsername(username).getDeckByDeckName(deckName).getCards().add(card);
        User.getUserByUsername(username).getDeckByDeckName(deckName).getMainDeckCards().add(card);
        User.getUserByUsername(username).getCards().remove(Card.getCardByName(cardName));
    }

    private void addCardToSideDeck(String deckName, String cardName) {
        Card card = Card.getCardByName(cardName);
        User.getUserByUsername(username).getDeckByDeckName(deckName).getCards().add(card);
        User.getUserByUsername(username).getDeckByDeckName(deckName).getSideDeckCards().add(card);
        User.getUserByUsername(username).getCards().remove(Card.getCardByName(cardName));
    }

    private void isDeckAlreadyExists(String deckName) {

    }

    private void isMainOrSideDeckFull(String deckName) {

    }

    private void isDeckFullFromCard(String deckName, Card cardName) {

    }

    private void deleteCardFromDeck(String deckName, Card cardName) {

    }

    private void isCardAlreadyInMainOrSideDeck(String deckName, Card cardName) {


    }

    private void showActivatedDeck() {


    }

    private void sortDecksLexicographically(String deckName, Card cardName) {


    }

    public void showUserDecks() {
        System.out.println("Decks:");
        // show active deck
        System.out.println("Active deck:");
        Deck activeDeck = User.getUserByUsername(username).getActivatedDeck();
        if (activeDeck != null) showDeck(activeDeck);

        // show other decks
        ArrayList<Deck> decks = User.getUserByUsername(username).getDecks();
        System.out.println("Other decks:");
        for (Deck deck : decks) {
            if (!deck.isActivated())
                showDeck(deck);
        }
    }

    public void showDeck(Deck deck) {
        String deckValidity = "";
        if (deck.isDeckValid()) deckValidity = "valid";
        else deckValidity = "invalid";
        System.out.println(deck.getDeckName() + ": main deck " + deck.getMainDeckCards().size()
                + ", side deck " + deck.getSideDeckCards().size() + ", " + deckValidity);
    }

    public void showMainOrSideDeckByName(String deckName, boolean isSide) {
        Deck deck = User.getUserByUsername(username).getDeckByDeckName(deckName);
        if (!User.getUserByUsername(username).isDeckWithThisNameAlreadyExists(deckName))
            DeckPage.setMessage("deck with name " + deckName + " does not exist");
        else if (isSide) showSideDeckCards(deckName, deck);
        else showMainDeckCards(deckName, deck);
    }

    private void showMainDeckCards(String deckName, Deck deck) {
        System.out.println("Deck: " + deckName);
        System.out.println("Main deck:");
        System.out.println("Monsters:");
        // show monster card
        deck.getMainDeckCards().sort(new SortByName());
        for (Card mainDeckCard : deck.getMainDeckCards()) {
            if (mainDeckCard instanceof Monster)
                System.out.println(mainDeckCard.getCardName() + ": " + mainDeckCard.getDescription());
        }
        System.out.println("Spell and Traps:");
        // show spell and traps cards
        deck.getMainDeckCards().sort(new SortByName());
        for (Card mainDeckCard : deck.getMainDeckCards()) {
            if (mainDeckCard instanceof Spell || mainDeckCard instanceof Trap)
                System.out.println(mainDeckCard.getCardName() + ": " + mainDeckCard.getDescription());
        }
    }

    private void showSideDeckCards(String deckName, Deck deck) {
        System.out.println("Deck: " + deckName);
        System.out.println("Side deck:");
        System.out.println("Monsters:");
        // show monster cards
        deck.getSideDeckCards().sort(new SortByName());
        for (Card sideDeckCard : deck.getSideDeckCards()) {
            if (sideDeckCard instanceof Monster)
                System.out.println(sideDeckCard.getCardName() + ": " + sideDeckCard.getDescription());
        }
        System.out.println("Spell and Traps:");
        // show spell and traps cards
        deck.getSideDeckCards().sort(new SortByName());
        for (Card sideDeckCard : deck.getSideDeckCards()) {
            if (sideDeckCard instanceof Spell || sideDeckCard instanceof Trap)
                System.out.println(sideDeckCard.getCardName() + ": " + sideDeckCard.getDescription());
        }
    }

    private void sortCardsOfDeckLexicographically(String deckName) {


    }

    public void showAllCards() {
        ArrayList<Card> cards = User.getUserByUsername(username).getCards();
        cards.sort(new SortByName());
        for (Card card : cards) {
            System.out.println(card.getCardName() + ":" + card.getDescription());
        }
    }

    private void sortAllCardsByName(String deckName) {


    }
}

class SortByName implements Comparator<Card> {
    // Used for sorting in ascending order of name
    public int compare(Card a, Card b) {
        return a.getCardName().compareTo(b.getCardName());
    }
}
