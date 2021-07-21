package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Auction {
    private String auctionName;
    private int startingPrice;
    private int winnerPrice;
    private String winnerUsername;
    private String auctionCreatorUsername;
    private Card card;
    private static ArrayList<Auction> activeAuctions = new ArrayList<>();
    private int startedTimeHour;
    private int startedTimeMinute;
    private int startedTimeSecond;
    private int startedTimeMillis;

    private int resetTimeHour;
    private int resetTimeMinute;
    private int resetTimeSecond;
    private int resetTimeMillis;
    private boolean hadBid;

    public Auction(String auctionCreatorUsername, Card card, int startingPrice) {
        this.auctionName = UUID.randomUUID().toString();
        this.card = card;
        this.auctionCreatorUsername = auctionCreatorUsername;
        this.winnerUsername = auctionCreatorUsername;
        this.startingPrice = startingPrice;
        this.winnerPrice = startingPrice;
        startedTimeHour = LocalDateTime.now().getHour();
        startedTimeMinute = LocalDateTime.now().getMinute();
        startedTimeSecond = LocalDateTime.now().getSecond();
        startedTimeMillis = LocalDateTime.now().getNano() / 1000000;
        activeAuctions.add(this);
    }

    public static Auction getAuctionByAuctionName(String auctionName) {
        for (Auction activeAuction : activeAuctions) {
            if (activeAuction.getAuctionName().equals(auctionName)) return activeAuction;
        }
        return null;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getWinnerPrice() {
        return winnerPrice;
    }

    public void setWinnerPrice(int winnerPrice) {
        this.winnerPrice = winnerPrice;
    }

    public String getWinnerUsername() {
        return winnerUsername;
    }

    public void setWinnerUsername(String winnerUsername) {
        this.winnerUsername = winnerUsername;
    }

    public String getAuctionCreatorUsername() {
        return auctionCreatorUsername;
    }

    public void setAuctionCreatorUsername(String auctionCreatorUsername) {
        this.auctionCreatorUsername = auctionCreatorUsername;
    }

    public boolean isHadBid() {
        return hadBid;
    }

    public void setHadBid(boolean hadBid) {
        this.hadBid = hadBid;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public static ArrayList<Auction> getActiveAuctions() {
        return activeAuctions;
    }

    public static void setActiveAuctions(ArrayList<Auction> activeAuctions) {
        Auction.activeAuctions = activeAuctions;
    }

    public void increaseWinnerPrice(int addAmountBid) {
        winnerPrice += addAmountBid;
    }

    public void resetTime() {
        resetTimeHour = LocalDateTime.now().getHour();
        resetTimeMinute = LocalDateTime.now().getMinute();
        resetTimeSecond = LocalDateTime.now().getSecond();
        resetTimeMillis = LocalDateTime.now().getNano() / 1000000;
    }

    public int getStartedTimeHour() {
        return startedTimeHour;
    }

    public void setStartedTimeHour(int startedTimeHour) {
        this.startedTimeHour = startedTimeHour;
    }

    public int getStartedTimeMinute() {
        return startedTimeMinute;
    }

    public void setStartedTimeMinute(int startedTimeMinute) {
        this.startedTimeMinute = startedTimeMinute;
    }

    public int getStartedTimeSecond() {
        return startedTimeSecond;
    }

    public void setStartedTimeSecond(int startedTimeSecond) {
        this.startedTimeSecond = startedTimeSecond;
    }

    public int getStartedTimeMillis() {
        return startedTimeMillis;
    }

    public void setStartedTimeMillis(int startedTimeMillis) {
        this.startedTimeMillis = startedTimeMillis;
    }

    public int getResetTimeHour() {
        return resetTimeHour;
    }

    public void setResetTimeHour(int resetTimeHour) {
        this.resetTimeHour = resetTimeHour;
    }

    public int getResetTimeMinute() {
        return resetTimeMinute;
    }

    public void setResetTimeMinute(int resetTimeMinute) {
        this.resetTimeMinute = resetTimeMinute;
    }

    public int getResetTimeSecond() {
        return resetTimeSecond;
    }

    public void setResetTimeSecond(int resetTimeSecond) {
        this.resetTimeSecond = resetTimeSecond;
    }

    public int getResetTimeMillis() {
        return resetTimeMillis;
    }

    public void setResetTimeMillis(int resetTimeMillis) {
        this.resetTimeMillis = resetTimeMillis;
    }
}
