package com.cryptotelegram.entity;

import javax.persistence.*;

@Entity
public class UserCoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "chatId")
    private UserChat userChat = new UserChat();

    private String userCoinId;
    private double userCurrentPriceCoin;
    private double stepPercentNotification;
    private double currentPriceForPercentage;

    public UserCoin() {
    }

    public UserCoin(String userCoinId, UserChat userChat) {
        this.userCoinId = userCoinId;
        this.userChat = userChat;
    }

    public double getCurrentPriceForPercentage() {
        return currentPriceForPercentage;
    }

    public void setCurrentPriceForPercentage(double currentPriceForPercentage) {
        this.currentPriceForPercentage = currentPriceForPercentage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserChat getUserChat() {
        return userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
    }

    public String getUserCoinId() {
        return userCoinId;
    }

    public void setUserCoinId(String userCoinId) {
        this.userCoinId = userCoinId;
    }

    public double getUserCurrentPriceCoin() {
        return userCurrentPriceCoin;
    }

    public void setUserCurrentPriceCoin(double userCurrentPriceCoin) {
        this.userCurrentPriceCoin = userCurrentPriceCoin;
    }

    public double getStepPercentNotification() {
        return stepPercentNotification;
    }

    public void setStepPercentNotification(double stepPercentNotification) {
        this.stepPercentNotification = stepPercentNotification;
    }

    @Override
    public String toString() {
        return "\nId Coin - " + userCoinId + "\n" +
                "Current сoin course - " + userCurrentPriceCoin + "\n" +
                "Percentage step for notification - " + stepPercentNotification + "\n";
    }
}
