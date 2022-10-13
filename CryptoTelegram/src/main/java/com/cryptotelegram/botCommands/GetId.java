package com.cryptotelegram.botCommands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import com.cryptotelegram.serviceImpl.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class GetId implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramServiceImpl;

    @Override
    public void accept(Message message) {

        String chatId = message.getChatId().toString();

        telegramServiceImpl.sendMessageText("ID chat:" + chatId + "\n"
                + "User ID:" + message.getFrom().getId(), chatId);
    }
}
