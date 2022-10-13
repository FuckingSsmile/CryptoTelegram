package com.cryptotelegram.entity;

import javax.persistence.*;

@Entity
public class UserWantCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Double userAlertPriceCoin;
    private String userCoinId;
    private String chatId;

    public UserWantCourse(String chatId, String userCoinId, Double userAlertPriceCoin) {
        this.chatId = chatId;
        this.userCoinId = userCoinId;
        this.userAlertPriceCoin = userAlertPriceCoin;
    }

    public UserWantCourse(){}

    public Double getUserAlertPriceCoin() {
        return userAlertPriceCoin;
    }

    public void setUserAlertPriceCoin(Double userAlertPriceCoin) {
        this.userAlertPriceCoin = userAlertPriceCoin;
    }

    public String getUserCoinId() {
        return userCoinId;
    }

    public void setUserCoinId(String userCoinId) {
        this.userCoinId = userCoinId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "UserWantCourse{" +
                "id=" + id +
                ", userWantCourseCoin=" + userAlertPriceCoin +
                ", userCoinId='" + userCoinId + '\'' +
                ", chat_id=" + chatId +
                '}';
    }
}
