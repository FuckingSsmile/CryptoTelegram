package com.cryptotelegram.entity;

import com.cryptotelegram.states.BotState;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TelegramBotState {

    @Id
    private String chatId;
    private String botState;

    public TelegramBotState() {
    }

    public TelegramBotState(String chatId) {

        this.chatId = chatId;
        this.botState = BotState.FREESTATE.toString();
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getBotState() {
        return botState;
    }

    public void setBotState(String botState) {
        this.botState = botState;
    }
}
