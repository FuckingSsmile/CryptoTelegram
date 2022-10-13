package com.cryptotelegram.repository;

import com.cryptotelegram.entity.TelegramBotState;
import com.cryptotelegram.states.BotState;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TelegramBotStateRepository extends CrudRepository<TelegramBotState, Long> {

    //Поменять BotState, у юзеру
    @Modifying
    @Query(value = "UPDATE telegram_bot_state SET bot_state = :botState WHERE chat_id = :chatId", nativeQuery = true)
    void setBotStateFromUser(@Param("chatId") String chat_id, @Param("botState") String bot_state);

    //Получить BotState у юзера
    @Query(value = "SELECT bot_state FROM telegram_bot_state WHERE chat_id = :chatId", nativeQuery = true)
    BotState getBotStateFromUser(@Param("chatId") String chat_id);

    //Сбросить BotState у юзера
    @Modifying
    @Query(value = "UPDATE telegram_bot_state SET bot_state = 'FREESTATE' WHERE chat_id = :chatId", nativeQuery = true)
    void dropBotStateFromUser(@Param("chatId") String chat_id);

}
