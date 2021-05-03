package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String nickname;
    private String password;
    private double money;
    private int lifePoints;
    static private ArrayList<User> users;
    private ArrayList<Card> cards;
    private ArrayList<Deck> decks;
    private Deck activatedDeck;
    private Board board;

    public Deck getActivatedDeck() {
        return activatedDeck;
    }

    public void setActivatedDeck(Deck activatedDeck) {
        this.activatedDeck = activatedDeck;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
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

    public static User getUserByUsername(String name) {
        for (User user : users) {
            if (user.username.equals(name)) return user;
        }
        return null; // be careful
    }


    private boolean isUsernameAlreadyExists(String username) {

        return true;
    }

    private boolean isNicknameAlreadyExists(String username) {

        return true;

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

    public static User getUserByUsername() {

    }

    public void setGameBoard() {

    }
}
