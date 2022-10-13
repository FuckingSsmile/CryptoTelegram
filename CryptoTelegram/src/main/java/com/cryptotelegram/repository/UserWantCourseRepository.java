package com.cryptotelegram.repository;

import com.cryptotelegram.entity.UserWantCourse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserWantCourseRepository extends CrudRepository<UserWantCourse, Long> {

    //Берем все цены конкретной монеты у юзера
    @Query(value = "select user_alert_price_coin from user_want_course WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    List<Double> getPriceAlertForCoin(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id);

    //Поиск конкретного юзера и монеты
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM user_want_course WHERE chat_id = :chatId AND user_coin_id = :coinid AND user_alert_price_coin = :useralertpricecoin ", nativeQuery = true)
    Boolean checkPriceForAlertFromUser(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id, @Param("useralertpricecoin") Double user_alert_price_coin);

    //удалить цену
    @Modifying
    @Query(value = "DELETE from user_want_course WHERE chat_id = :chatId AND user_coin_id = :coinid AND user_alert_price_coin = :useralertpricecoin ", nativeQuery = true)
    void deletePriceForAlertFromUser(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id, @Param("useralertpricecoin") Double user_alert_price_coin);

    //удалить монету
    @Modifying
    @Query(value = "DELETE from user_want_course WHERE chat_id = :chatId AND user_coin_id = :coinid", nativeQuery = true)
    void deleteCoinIdFromUser(@Param("chatId") String chat_id, @Param("coinid") String user_coin_id);
}
