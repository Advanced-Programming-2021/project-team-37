package model;

import java.util.ArrayList;

public class MessageSender {
    private String username;
    private String nickname;
    private int score;
    private int money;
    private String profileImageAddress;

    private ArrayList<MessageSender> senders = new ArrayList<>();

    public MessageSender(User user) {
        this.money = user.getMoney();
        this.score = user.getScore();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileImageAddress = user.getProfileImageAddress();
        senders.add(this);
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getProfileImageAddress() {
        return profileImageAddress;
    }

    public void setProfileImageAddress(String profileImageAddress) {
        this.profileImageAddress = profileImageAddress;
    }

    public ArrayList<MessageSender> getSenders() {
        return senders;
    }

    public void setSenders(ArrayList<MessageSender> senders) {
        this.senders = senders;
    }
}
