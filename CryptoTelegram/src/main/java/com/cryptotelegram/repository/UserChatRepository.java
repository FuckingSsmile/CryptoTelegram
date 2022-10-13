package com.cryptotelegram.repository;

import com.cryptotelegram.entity.UserChat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserChatRepository extends CrudRepository<UserChat, Long> {

    //Получить List<UserChat>
    @Query(value = "SELECT * FROM user_chat", nativeQuery = true)
    List<UserChat> getAllUserChatToList();

    //Получить конкретного UserChat
    @Query(value = "SELECT * FROM user_chat WHERE chat_id = :chatId", nativeQuery = true)
    UserChat getUserChat(@Param("chatId") String chat_id);

    //Проверка на нового пользователя
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM user_chat WHERE chat_id = :chatId", nativeQuery = true)
    Boolean checkUserChatId(@Param("chatId") String chat_id);

    //Установка значения, при ответе боту(Например выбора монеты)
    @Modifying
    @Query(value = "UPDATE user_chat SET reply_to_bot = :texttobot WHERE chat_id = :chatId", nativeQuery = true)
    void setReplyToBot(@Param("chatId") String chat_id, @Param("texttobot") String text_to_bot);

    //Получить значение reply_to_bot(Например название монеты)
    @Query(value = "SELECT reply_to_bot FROM user_chat WHERE chat_id = :chatId", nativeQuery = true)
    String getReplyToBot(@Param("chatId") String chat_id);

}
