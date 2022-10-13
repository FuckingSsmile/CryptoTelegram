package com.cryptotelegram.repository;

import com.cryptotelegram.entity.UserCoin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserCoinRepository extends CrudRepository<UserCoin, Long> {

    //Поиск конкретной монеты
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM user_coin WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    Boolean checkCoinIdFromUser(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id);

    //Проверка в целом, есть монеты у пользователя или нет
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM user_coin WHERE chat_id = :chatId", nativeQuery = true)
    Boolean checkAllCoinFromUser(@Param("chatId") String chat_id);

    //Определяет, есть у кого алерт на изменение цены в процентах
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM user_coin where step_percent_notification > 0", nativeQuery = true)
    Boolean checkPercentageAlert();

    //Возвращает List<UserCoin> определенного чата
    @Query(value = "SELECT * FROM user_coin WHERE chat_id = :chatId", nativeQuery = true)
    List<UserCoin> getListCoinFromUser(@Param("chatId") String chat_id);

    //Возвращает всю таблицу List<UserCoin>
    @Query(value = "SELECT * FROM user_coin", nativeQuery = true)
    List<UserCoin> getListTable();

    //Получить конкретный UserCoin
    @Query(value = "SELECT * FROM user_coin WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    UserCoin getUserCoin(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id);

    //Возвращает List айдишников монет(уникальное имя)
    @Query(value = "SELECT user_coin_id FROM user_coin WHERE chat_id = :chatId", nativeQuery = true)
    List<String> getListCoinIdFromUser(@Param("chatId") String chat_id);

    //Устанавливает текущую цену у пользователя и выбранной монете
    @Modifying
    @Query(value = "UPDATE user_coin SET current_Price_For_Percentage = :currentpriceforpercentage WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    void setCurrentPriceForPercentage(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id, @Param("currentpriceforpercentage") Double current_Price_For_Percentage);

    //Устанавливает текущую цену у пользователя и выбранной монете
    @Modifying
    @Query(value = "UPDATE user_coin SET user_current_price_coin = :usercurrentpricecoin WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    void setCurrentPriceForCoin(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id, @Param("usercurrentpricecoin") Double user_current_price_coin);

    //Устанавливает percentage
    @Modifying
    @Query(value = "UPDATE user_coin SET step_percent_notification = :steppercentnotification WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    void setPercentageForCoin(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id, @Param("steppercentnotification") Double coin_percentage);

    //Сбрасываем цену для процентного мониторинга
    @Modifying
    @Query(value = "UPDATE user_coin SET current_price_for_percentage = 0 WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    void deleteCurrentPriceForPercentage(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id);

    //Удаляет конкретную монету у пользователя
    @Modifying
    @Query(value = "DELETE FROM user_coin WHERE chat_id = :chatid AND user_coin_id = :coinid", nativeQuery = true)
    void deleteCoinById(@Param("chatid") String chat_id, @Param("coinid") String coin_Id);


}
