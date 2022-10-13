package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.entity.UserChat;
import com.cryptotelegram.repository.TelegramBotStateRepository;
import com.cryptotelegram.repository.UserChatRepository;
import com.cryptotelegram.repository.UserCoinRepository;
import com.cryptotelegram.repository.UserWantCourseRepository;
import com.cryptotelegram.states.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CallBackQueryImpl {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    @Autowired
    private UserChatRepository userChatRepository;
    @Autowired
    private TelegramBotStateRepository telegramBotStateRepository;
    @Autowired
    private UserCoinRepository userCoinRepository;
    @Autowired
    private UserWantCourseRepository userWantCourseRepository;

    public void CallBackQuery(BotState botState, String chatIdCallBack, String data, UserChat userChatCallback) {


        switch (botState) {

            case SETPRICEFORALERT:
                System.out.println("Сейчас hasCallbackQuery SETPRICEFORALERT");

                userChatRepository.setReplyToBot(chatIdCallBack, data);

                telegramService.sendMessageText("Вы выбрали монету - "
                        + data + "\nВведите требуемый курс для оповещения", chatIdCallBack);

                telegramBotStateRepository.setBotStateFromUser(chatIdCallBack, "WAITINGSETPRICEFORALERT");
                break;

            case SETPERCENTAGEFORALERT:
                System.out.println("Сейчас hasCallbackQuery SETPERCENTAGEFORALERT");

                userChatRepository.setReplyToBot(chatIdCallBack, data);

                telegramService.sendMessageText("Вы выбрали монету - " + data +
                        "\nВведите шаг в % изменения курса", chatIdCallBack);

                telegramBotStateRepository.setBotStateFromUser(chatIdCallBack, "WAITINGSETPERCENTAGEFORALERT");
                break;

            case DELETECOIN:
                System.out.println("Сейчас hasCallbackQuery DELETENEWCOIN");

                if (userCoinRepository.checkCoinIdFromUser(chatIdCallBack, data)) {

                    userCoinRepository.deleteCoinById(chatIdCallBack, data);
                    userWantCourseRepository.deleteCoinIdFromUser(chatIdCallBack, data);

                    telegramService.sendMessageText("Монета " + data + " - удалена", chatIdCallBack);
                }

                dropBotState(chatIdCallBack);
                break;

            case DELETEPRICEFORALERT:
                System.out.println("Сейчас hasCallbackQuery DELETEPRICEFORALERT");

                if (userCoinRepository.checkCoinIdFromUser(chatIdCallBack, data)) {

                    userChatCallback.setReplyToBot(data);

                    telegramService.sendMessageText("Вы выбрали монету - " + data +
                            "\nВведите цену, которую необходимо удалить из мониторинга", chatIdCallBack);

                    telegramBotStateRepository.setBotStateFromUser(chatIdCallBack, "WAITINGDELETEPRICEFORALERT");
                    break;

                } else {
                    telegramService.sendMessageText("Ошибка, повторите попытку", chatIdCallBack);

                }

                dropBotState(chatIdCallBack);
                break;

            case DELETEPERCENTAGEFORALERT:
                System.out.println("Сейчас hasCallbackQuery DELETEPERCENTAGEFORALERT");

                if (userCoinRepository.checkCoinIdFromUser(chatIdCallBack, data)) {

                    telegramService.sendMessageText("Вы выбрали монету - " + data, chatIdCallBack);

                    userCoinRepository.setPercentageForCoin(chatIdCallBack,
                            userChatRepository.getReplyToBot(chatIdCallBack), 0.0);

                    telegramService.sendMessageText("Цена для мониторинга монеты в %, сброшена \n" + userCoinRepository
                            .getUserCoin(chatIdCallBack, data).toString(), chatIdCallBack);

                    userCoinRepository.deleteCurrentPriceForPercentage(chatIdCallBack, data);

                } else {
                    telegramService.sendMessageText("Ошибка, повторите попытку", chatIdCallBack);
                }

                dropBotState(chatIdCallBack);
                break;

            default:
                dropBotState(chatIdCallBack);
        }

    }

    private void dropBotState(String chatId) {
        telegramBotStateRepository.dropBotStateFromUser(chatId);
    }

}
