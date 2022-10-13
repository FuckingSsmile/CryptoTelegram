package com.cryptotelegram.botCommands;

import com.cryptotelegram.controller.TelegramController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.cryptotelegram.serviceImpl.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class GetAvailableCoinsForMonitoring implements Consumer<Message> {
    @Autowired
    @Lazy
    private TelegramServiceImpl telegramServiceImpl;

    private final String textForMessage = "Список доступных монет";

    @Override
    public void accept(Message message) {
        telegramServiceImpl.sendMessageFile(textForMessage, TelegramController
                .getFileWithAllCoins(message.getChatId()), message.getChatId().toString());
    }
}