package com.cryptotelegram.botCommands;

import com.cryptotelegram.controller.TelegramController;
import com.cryptotelegram.repository.TelegramBotStateRepository;
import com.cryptotelegram.serviceImpl.TelegramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.function.Consumer;

@Component
public class GetCoinInfo implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramServiceImpl;

    @Autowired
    private TelegramBotStateRepository telegramBotStateRepository;

    private final String textForMessage = "Введите Id монеты, которую хотите посмотреть";

    @Override
    public void accept(Message message) {
        String chatId = message.getChatId().toString();

        telegramServiceImpl.sendMessageText(textForMessage, chatId);
        telegramBotStateRepository.setBotStateFromUser(chatId, "GETCOININFO");

    }
}
