package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.entity.Coin;
import com.cryptotelegram.entity.UserChat;
import com.cryptotelegram.entity.UserCoin;
import com.cryptotelegram.entity.UserWantCourse;
import com.cryptotelegram.repository.*;
import com.cryptotelegram.states.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class IncomingMessageImpl {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;
    @Autowired
    private UserChatRepository userChatRepository;
    @Autowired
    private UserCoinRepository userCoinRepository;
    @Autowired
    private TelegramBotStateRepository telegramBotStateRepository;
    @Autowired
    private UserWantCourseRepository userWantCourseRepository;
    @Autowired
    private CoinRepository coinRepository;

    private UserChat userChat;

    public void messageAction(BotState botState, String chatIdInComingMessage, String textHasMessage, String replyToBot) {

        userChat = userChatRepository.getUserChat(chatIdInComingMessage);

        double price = 0;

        //TODO возможно ошибки, где то приходит цены, где то приходит айди монеты, нужно тестировать

        switch (botState) {

            case FREESTATE:
                System.out.println("Сейчас FREESTATE");

                dropBotState(chatIdInComingMessage);
                break;

            case WAITINGSETPRICEFORALERT:
                System.out.println("Сейчас WAITINGSETPRICEFORALERT");

                price = getDouble(textHasMessage);

                if (userWantCourseRepository.checkPriceForAlertFromUser(chatIdInComingMessage, replyToBot, price)) {
                    telegramService.sendMessageText("Такая цена уже есть - \n" + replyToBot, chatIdInComingMessage);

                    dropBotState(chatIdInComingMessage);
                    break;
                }
                userWantCourseRepository.save(new UserWantCourse(chatIdInComingMessage, replyToBot, price));

                telegramService.sendMessageText("Цена успешно установлена \n" + userWantCourseRepository
                        .getPriceAlertForCoin(chatIdInComingMessage, replyToBot), chatIdInComingMessage);

                dropBotState(chatIdInComingMessage);
                break;

            case WAITINGDELETEPRICEFORALERT:
                System.out.println("Сейчас WAITINGDELETEPRICEFORALERT");

                price = getDouble(textHasMessage);

                if (!userWantCourseRepository.checkPriceForAlertFromUser(chatIdInComingMessage, replyToBot, price)) {
                    telegramService.sendMessageText("Такой цены нет - " + textHasMessage, chatIdInComingMessage);

                    dropBotState(chatIdInComingMessage);
                    break;
                }

                userWantCourseRepository.deletePriceForAlertFromUser(chatIdInComingMessage, replyToBot, price);

                userCoinRepository.getListCoinFromUser(chatIdInComingMessage);

                telegramService.sendMessageText("Цена успешно удалена \n" + userCoinRepository
                        .getListCoinFromUser(chatIdInComingMessage).toString(), chatIdInComingMessage);

                dropBotState(chatIdInComingMessage);
                break;

            case WAITINGSETPERCENTAGEFORALERT:
                System.out.println("Сейчас WAITINGSETPERCENTAGEFORALERT");

                price = getDouble(textHasMessage);

                userCoinRepository.setPercentageForCoin(chatIdInComingMessage,
                        userChatRepository.getReplyToBot(chatIdInComingMessage),
                        price);

                telegramService.sendMessageText("Шаг в % для оповещения, успешно добавлен \n" + userCoinRepository
                                .getUserCoin(chatIdInComingMessage, userChatRepository.getReplyToBot(chatIdInComingMessage))
                        , chatIdInComingMessage);

                userCoinRepository.setCurrentPriceForPercentage(chatIdInComingMessage
                        , replyToBot
                        , userCoinRepository.getUserCoin(chatIdInComingMessage, replyToBot).getUserCurrentPriceCoin());

                dropBotState(chatIdInComingMessage);
                break;

            case ADDNEWCOIN:
                System.out.println("Сейчас ADDNEWCOIN");

                if (checkCoinInBd(textHasMessage, chatIdInComingMessage)) {

                    if (userCoinRepository.checkCoinIdFromUser(chatIdInComingMessage, textHasMessage)) {

                        telegramService.sendMessageText("Такая Монета - " +
                                textHasMessage + ", уже есть в коллекции", chatIdInComingMessage);
                    } else {
                        userCoinRepository.save(new UserCoin(textHasMessage, userChat));

                        telegramService.sendMessageText("Монета - " +
                                textHasMessage + ", успешно добавлена", chatIdInComingMessage);
                    }
                }

                dropBotState(chatIdInComingMessage);
                break;

            case GETCOININFO:
                System.out.println("Сейчас GETCOININFO");

                if (checkCoinInBd(textHasMessage, chatIdInComingMessage)) {
                    Coin coin = coinRepository.getCoin(textHasMessage);

                    telegramService.sendMessageTextAndPhoto(coin.toString()
                            , chatIdInComingMessage
                            , coin.getCoinImage()
                    );
                }

                dropBotState(chatIdInComingMessage);
                break;

            default:
                System.out.println("Сейчас DEFAULT");
                dropBotState(chatIdInComingMessage);
        }
    }

    private boolean checkCoinInBd(String text, String chatId) {
        if (coinRepository.checkCoinId(text)) {
            return true;
        }
        telegramService.sendMessageText("Такой монеты " + text + " нет", chatId);
        return false;
    }

    private double getDouble(String text) {

        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void dropBotState(String chatId){
        telegramBotStateRepository.dropBotStateFromUser(chatId);
    }
}
