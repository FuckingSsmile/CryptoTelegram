package com.cryptotelegram.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class UserChat {

    @Id
    private String chatId;
    private String typeChat;
    private String replyToBot;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userChat")
    private List<UserCoin> listUserCoins = new ArrayList<>();
    @Transient
    public static ConcurrentHashMap<String, UserChat> telegramChats = new ConcurrentHashMap<>();

    public UserChat(String chatId, String typeChat) {
        this.chatId = chatId;
        this.typeChat = typeChat;

        telegramChats.put(chatId, this);
    }

    public UserChat() {
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getTypeChat() {
        return typeChat;
    }

    public void setTypeChat(String typeChat) {
        this.typeChat = typeChat;
    }

    public String getReplyToBot() {
        return replyToBot;
    }

    public void setReplyToBot(String replyToBot) {
        this.replyToBot = replyToBot;
    }

    public List<UserCoin> getListUserCoins() {
        return listUserCoins;
    }

    public void setListUserCoins(List<UserCoin> listUserCoins) {
        this.listUserCoins = listUserCoins;
    }

    public static ConcurrentHashMap<String, UserChat> getTelegramChats() {
        return telegramChats;
    }

    public static void setTelegramChats(ConcurrentHashMap<String, UserChat> telegramChats) {
        UserChat.telegramChats = telegramChats;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "chatId=" + chatId +
                ", typeChat='" + typeChat + '\'' +
                '}';
    }
}
