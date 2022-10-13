package com.cryptotelegram.botCommands;

import com.cryptotelegram.controller.TelegramController;
import com.cryptotelegram.repository.TelegramBotStateRepository;
import com.cryptotelegram.repository.UserCoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.cryptotelegram.serviceImpl.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class SetPercentageForAlert implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramServiceImpl;

    @Autowired
    private UserCoinRepository userCoinRepository;
    @Autowired
    private TelegramBotStateRepository telegramBotStateRepository;

    private final String textForEmptyCollection = "Коллекция монет - пустая";
    private final String textForKeyboard = "Выберите монету";

    @Override
    public void accept(Message message) {

        String chatId = message.getChatId().toString();

        if (userCoinRepository.checkAllCoinFromUser(chatId)) {

            telegramServiceImpl.createAndSendInlineKeyboardsWithAllCoins(chatId, textForKeyboard);
            telegramBotStateRepository.setBotStateFromUser(chatId, "SETPERCENTAGEFORALERT");
        } else {
            telegramServiceImpl.sendMessageText(textForEmptyCollection, chatId);
            telegramBotStateRepository.dropBotStateFromUser(chatId);
        }
    }
}