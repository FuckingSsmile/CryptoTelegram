package com.cryptotelegram.service;

import com.cryptotelegram.serviceImpl.TelegramServiceImpl;

import java.io.File;

public interface TelegramService {

    void sendMessageTextAndPhoto(String text, Long chatId, String photo);
    void sendMessageText(String text, Long chatId);
    void sendMessageFile(String text, File file, Long chatId);
    void createAndSendInlineKeyboardsWithAllCoins(Long chatId);
    public TelegramServiceImpl getInstance();

}
