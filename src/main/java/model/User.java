package model;

import com.google.gson.Gson;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class User {
    private static ArrayList<User> users = new ArrayList<>();


    private int maxLifePointInTheRounds;
    private int numberOfWonRoundInCurrentGame;
    private String username;
    private String nickname;
    private String password;
    private int money;
    private int lifePoints;
    private int score;
    private ArrayList<Card> allBoughtCards;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private Deck activatedDeck;
    private Board board;
    private boolean isCardSummonedOrSetInThisTurn = false;
    protected boolean hasLostMonsters = false;
    int canDrawCardInt = 0;
    String profileImageAddress;

    public ArrayList<Card> getAllBoughtCards() {
        return allBoughtCards;
    }

    public void setAllBoughtCards(ArrayList<Card> allBoughtCards) {
        this.allBoughtCards = allBoughtCards;
    }

    public Deck getDeckByDeckName(String name) {
        Deck temp = null;
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(name)) {
                temp = deck;
                break;
            }
        }
        return temp;
    }

    public int getMaxLifePointInTheRounds() {
        return maxLifePointInTheRounds;
    }

    public void setMaxLifePointInTheRounds(int maxLifePointInTheRounds) {
        if (this.maxLifePointInTheRounds <= maxLifePointInTheRounds)
            this.maxLifePointInTheRounds = maxLifePointInTheRounds;
    }

    public int getNumberOfWonRoundInCurrentGame() {
        return numberOfWonRoundInCurrentGame;
    }

    public void setNumberOfWonRoundInCurrentGame(int numberOfWonRoundInCurrentGame) {
        this.numberOfWonRoundInCurrentGame = numberOfWonRoundInCurrentGame;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getProfileImageAddress() {
        return profileImageAddress;
    }

    public void setProfileImageAddress(String profileImageAddress) {
        this.profileImageAddress = profileImageAddress;
    }

    public User(String username, String nickname, String password) {
        Random rand = new Random();
        profileImageAddress = "/Pictures/RandomProfileImages/" + rand.nextInt(23) + ".png";
        allBoughtCards = new ArrayList<>();
        cards = new ArrayList<>();
        decks = new ArrayList<>();
        board = new Board();
        this.money = 100000;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        users.add(this);
        updateUsers();
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

    public String getPassword() {
        return password;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        if (lifePoints < 0) lifePoints = 0;
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


    public Board getBoard() {
        return board;
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


    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public boolean isDeckWithThisNameAlreadyExists(String name) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(name)) return true;
        }
        return false;
    }

    public boolean isCardWithThisNameAlreadyExists(String name) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHasLostMonsters(boolean hasLostMonsters) {
        this.hasLostMonsters = hasLostMonsters;
    }


    public int getCanDrawCardInt() {
        return this.canDrawCardInt;
    }

    public void reduceCanDrawCardInt() {
        this.canDrawCardInt--;
    }

}
