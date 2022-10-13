package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.controller.TelegramController;
import com.cryptotelegram.entity.Coin;
import com.cryptotelegram.entity.UserCoin;
import com.cryptotelegram.repository.CoinRepository;
import com.cryptotelegram.repository.UserCoinRepository;
import com.cryptotelegram.service.UpdatePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UpdatePriceImpl implements UpdatePriceService {
    @Autowired
    private CoinRepository coinRepository;
    @Autowired
    private UserCoinRepository userCoinRepository;

//    @Scheduled(initialDelayString = "${schedule.update.init}", fixedDelayString = "${schedule.update.work}")
    public void startUpdate() {

        List<Coin> allCoinList = coinRepository.getAllCoinList();

        //TODO разобраться с условием
        if (coinRepository.count() > 0 && allCoinList.size() > 0) {

            List<UserCoin> listUserCoin = userCoinRepository.getListTable();

            System.out.println("начали обновление цены - " + LocalDateTime.now());

            updatePriceForUser(allCoinList, listUserCoin);

            System.out.println("Завершили обновление цены- " + LocalDateTime.now());

        }
    }

    private void updatePriceForUser(List<Coin> allCoinList, List<UserCoin> listUserCoin){
        for (UserCoin userCoin : listUserCoin) {

            for (Coin coin : allCoinList) {

                if (userCoin.getUserCoinId().equalsIgnoreCase(coin.getCoinId())) {
                    userCoinRepository.setCurrentPriceForCoin(userCoin.getUserChat().getChatId(), userCoin.getUserCoinId(), coin.getCurrent_price());
                    break;
                }
            }
        }
    }
}
