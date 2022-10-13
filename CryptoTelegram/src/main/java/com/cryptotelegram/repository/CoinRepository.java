package com.cryptotelegram.repository;

import com.cryptotelegram.entity.Coin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CoinRepository extends CrudRepository<Coin, Long> {

    //Получить лист коинов из БД
    @Query(value = "select * from coin", nativeQuery = true)
    List<Coin> getAllCoinList();

    //Проверка есть монета в бд или нет
    @Query(value = "SELECT CASE WHEN (count(*) > 0)  THEN 'true' ELSE 'false' END FROM coin WHERE coin_id = :coinId", nativeQuery = true)
    Boolean checkCoinId(@Param("coinId") String coin_id);

    //Получить Coin
    @Query(value = "SELECT * FROM coin WHERE coin_id = :coinId", nativeQuery = true)
    Coin getCoin(@Param("coinId") String coin_id);
}
