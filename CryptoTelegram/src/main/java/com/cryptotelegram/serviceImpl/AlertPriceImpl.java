package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.controller.TelegramController;
import com.cryptotelegram.entity.UserCoin;
import com.cryptotelegram.repository.UserCoinRepository;
import com.cryptotelegram.repository.UserWantCourseRepository;
import com.cryptotelegram.service.AlertPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AlertPriceImpl implements AlertPriceService {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    @Autowired
    private UserCoinRepository userCoinRepository;
    @Autowired
    private UserWantCourseRepository userWantCourseRepository;

//    @Scheduled(initialDelayString = "${schedule.alert.init}", fixedDelayString = "${schedule.alert.work}")
    @Override
    public void checkPrice() {

        if (userCoinRepository.count() > 0 && userWantCourseRepository.count() > 0) {

            List<UserCoin> listTable = userCoinRepository.getListTable();

            for (UserCoin userCoin : listTable) {
                List<Double> listPriceAlert = userWantCourseRepository.getPriceAlertForCoin(userCoin.getUserChat().getChatId(), userCoin.getUserCoinId());

                for (int i = 0; i < listPriceAlert.size(); i++) {

                    if(userCoin.getUserCurrentPriceCoin() >= listPriceAlert.get(i)){
                        telegramService.sendMessageText("Цена достигла цели " + listPriceAlert.get(i),userCoin.getUserChat().getChatId());

                        userWantCourseRepository.deletePriceForAlertFromUser(userCoin.getUserChat().getChatId()
                        ,userCoin.getUserCoinId()
                        ,listPriceAlert.get(i));
                    }
                }

            }
        }
    }

//    @Scheduled(initialDelayString = "${schedule.alert.init}", fixedDelayString = "${schedule.alert.work}")
    @Override
    public void checkPercentage() {

        if (userCoinRepository.checkPercentageAlert()) {

            List<UserCoin> listUserCoin = userCoinRepository.getListTable();

            for (UserCoin userCoin : listUserCoin) {

                if(userCoin.getCurrentPriceForPercentage() > 0) {

                    System.out.println("Началось сравнение цены " + LocalDateTime.now());

                    double percent = userCoin.getStepPercentNotification() / 100;
                    double currentPriceForPercentage = userCoin.getCurrentPriceForPercentage();
                    double userCurrentPriceCoin = userCoin.getUserCurrentPriceCoin();
                    double up = currentPriceForPercentage + (currentPriceForPercentage * percent);
                    double down = currentPriceForPercentage - (currentPriceForPercentage * percent);

                    if (up < userCurrentPriceCoin){

                        telegramService.sendMessageText(userCoin.getUserCoinId()+"\nЦена поднялась выше "+ userCoin.getStepPercentNotification() + "%\nБыла цена " + currentPriceForPercentage + "\nстала "+ userCurrentPriceCoin,userCoin.getUserChat().getChatId());
                        userCoinRepository.setCurrentPriceForPercentage(userCoin.getUserChat().getChatId(),userCoin.getUserCoinId(),userCurrentPriceCoin);
                        break;
                    }
                    if(down > userCurrentPriceCoin){

                        telegramService.sendMessageText(userCoin.getUserCoinId()+"\nЦена опустилась ниже "+ userCoin.getStepPercentNotification() + "%\nБыла цена " + currentPriceForPercentage + "\nстала "+ userCurrentPriceCoin,userCoin.getUserChat().getChatId());
                        userCoinRepository.setCurrentPriceForPercentage(userCoin.getUserChat().getChatId(),userCoin.getUserCoinId(),userCurrentPriceCoin);
                        break;
                    }
                }
            }
        }
    }
}
