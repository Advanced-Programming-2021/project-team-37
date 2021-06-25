package model;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String username;
    private String nickname;
    private String password;
    private int money;

    private int lifePoints;
    private int score;
    private static ArrayList<User> users = new ArrayList<>();
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private Deck activatedDeck;
    private Board board;
    private boolean isCardSummonedOrSetInThisTurn = false;

    public Deck getDeckByDeckName (String name) {
        Deck temp = null;
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(name)) {
                temp = deck;
                break;
            }
        }
        return temp;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User(String username, String nickname, String password) {
        cards = new ArrayList<>();
        decks = new ArrayList<>();
        board = new Board();
        this.money = 100000;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        users.add(this);
    }

    public static void newUsers() {
        users = new ArrayList<>();
    }

    public boolean isCardSummonedOrSetInThisTurn() {
        return isCardSummonedOrSetInThisTurn;
    }

    public void setCardSummonedOrSetInThisTurn(boolean cardSummonedOrSetInThisTurn) {
        isCardSummonedOrSetInThisTurn = cardSummonedOrSetInThisTurn;
    }

    public Deck getActivatedDeck() {
        return activatedDeck;
    }

    public void setActivatedDeck(Deck activatedDeck) {
        this.activatedDeck = activatedDeck;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public static boolean isUsernameAlreadyExists(String username) {
        for (User user : users) {
            if (user.username.equals(username)) return true;
        }
        return false;
    }

    public static boolean isNicknameAlreadyExists(String nickname) {
        for (User user : users) {
            if (user.nickname.equals(nickname)) return true;
        }
        return false;
    }

    private boolean isPasswordCorrect(String username) {
        return true;
    }

    private boolean getPassword(String username) {

        return true;

    }

    private void setPassword(String password) {

        this.password = password;

    }

    private String[] getNicknamePlusScoreOfAllUsers() {

        return new String[]{"", " "};

    }

    private boolean isDeckAlreadyExists(String deckName) {

        return true;
    }

    public void addCardToDeck(String deckName, String cardName) {


    }

    public void deleteCardFromDeck(String deckName, String cardName) {


    }

    public static User getUserByUsername(String username) {
        int i;
        for (i = 0; i < users.size(); i++) {
            if (users.get(i).username.equals(username)) break;
        }
        return users.get(i);
    }

    public void setGameBoard() {

    }

    public boolean isDeckWithThisNameAlreadyExists(String name){
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(name)) return true;
        }
        return false;
    }

    public boolean isCardWithThisNameAlreadyExists(String name){
        for (Card card : cards) {
            if (card.getCardName().equals(name)) return true;
        }
        return false;
    }

    public static void updateUsers() {
        try {
            FileWriter jsonWriter = new FileWriter("src/main/resources/Users.json");
            jsonWriter.write(new Gson().toJson(users));
            jsonWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Card.setCards();
        User user = new User("username","user", "password");
        user.getCards().add(new Monster("Axe Raider"));
        user.getCards().add(new Monster("Crawling dragon"));
        user.getCards().add(new Trap("Magic Cylinder"));
        user.getCards().add(new Spell("Forest"));
        updateUsers();
    }
}
