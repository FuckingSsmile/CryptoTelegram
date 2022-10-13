package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.botCommands.*;
import com.cryptotelegram.controller.TelegramController;
import com.cryptotelegram.entity.*;
import com.cryptotelegram.repository.CoinRepository;
import com.cryptotelegram.repository.TelegramBotStateRepository;
import com.cryptotelegram.repository.UserChatRepository;
import com.cryptotelegram.repository.UserCoinRepository;
import com.cryptotelegram.states.BotState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class TelegramServiceImpl extends TelegramLongPollingBot {

    private final String parseMode = "HTML";
    private final String addNewUser = "Добавлен новый пользователь";
    private final double columnsForKeyBoard = 4;
    private HashMap<String, Consumer<Message>> commandMap = new HashMap<>();

    private BotState botState;

    @Autowired
    private UserChatRepository userChatRepository;
    @Autowired
    private TelegramBotStateRepository telegramBotStateRepository;
    @Autowired
    private UserCoinRepository userCoinRepository;

    @Autowired
    private ChatServiceImpl chatServiceImpl;
    @Autowired
    private CallBackQueryImpl callBackQueryImpl;
    @Autowired
    private IncomingMessageImpl incomingMessageImpl;

    @Value("${bot.userName}")
    private String userName;
    @Value("${bot.token}")
    private String token;

    @Autowired
    public TelegramServiceImpl(AddNewCoin addNewCoin
            , SetPriceForAlert setPriceForAlert
            , SetPercentageForAlert setPercentageForAlert
            , GetAllCoins getAllCoins
            , GetId getId
            , GetAvailableCoinsForMonitoring getAvailableCoinsForMonitoring
            , DeleteCoin deleteCoin
            , DeletePercentageForAlert deletePercentageForAlert
            , DeletePriceForAlert deletePriceForAlert
            , GetCoinInfo getCoinInfo) {

        commandMap.put("/addnewcoin", addNewCoin);
        commandMap.put("/setpriceforalertlong", setPriceForAlert);
        commandMap.put("/setpercentageforalert", setPercentageForAlert);
        commandMap.put("/getallcoins", getAllCoins);
        commandMap.put("/getid", getId);
        commandMap.put("/getavailablecoinsformonitoring", getAvailableCoinsForMonitoring);
        commandMap.put("/deletecoin", deleteCoin);
        commandMap.put("/deletepercentageforalert", deletePercentageForAlert);
        commandMap.put("/deletepriceforalert", deletePriceForAlert);
        commandMap.put("/getcoininfo", getCoinInfo);
    }

    //Работа с потоком сообщений
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage()) {

            String textHasMessage = message.getText().replace(getBotUsername(), "");

            String chatIdMessage = message.getChatId().toString();


            if (userChatRepository.checkUserChatId(chatIdMessage)) {


                botState = telegramBotStateRepository.getBotStateFromUser(chatIdMessage);
                String replyToBot = userChatRepository.getReplyToBot(chatIdMessage);

                incomingMessageImpl.messageAction(botState, chatIdMessage, textHasMessage, replyToBot);

                Consumer<Message> messageConsumer = commandMap.get(textHasMessage);
                if (messageConsumer != null) {

                    messageConsumer.accept(message);
                }
            } else {
                userChatRepository.save(
                        new UserChat(chatIdMessage, chatServiceImpl.parseChatType(message.getChat().toString())));
                telegramBotStateRepository.save(new TelegramBotState(chatIdMessage));

                sendMessageText(addNewUser, chatIdMessage);
            }
        }

        if (update.hasCallbackQuery()) {

            Message messageCallBack = update.getCallbackQuery().getMessage();
            String chatIdCallBack = messageCallBack.getChatId().toString();

            if (userChatRepository.checkUserChatId(chatIdCallBack)) {

                String data = update.getCallbackQuery().getData();

                botState = telegramBotStateRepository.getBotStateFromUser(chatIdCallBack);
                UserChat userChatCallback = UserChat.getTelegramChats().get(chatIdCallBack);

                callBackQueryImpl.CallBackQuery(botState, chatIdCallBack, data, userChatCallback);

            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    //    Отправка сообщения + фото
    public void sendMessageTextAndPhoto(String text, String chatId, String urlPhoto) {
        try {
            execute(SendPhoto
                            .builder()
                            .chatId(chatId)
                            .photo(new InputFile(urlPhoto))
                            .caption(text)
                            .parseMode(parseMode)
                            .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    //Отправка сообщения
    public void sendMessageText(String text, String chatId) {

        try {
            execute(SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(text)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //Отправка сообщения и файла
    public void sendMessageFile(String text, File file, String chatId) {
        try {
            execute(SendDocument
                    .builder()
                    .chatId(chatId)
                    .document(new InputFile(file))
                    .caption(text)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void createAndSendInlineKeyboardsWithAllCoins(String chatId, String textForKeyboard) {

        List<String> listCoins = userCoinRepository.getListCoinIdFromUser(chatId);

        List<InlineKeyboardButton> buttons = listCoins.stream()
                .map(text -> InlineKeyboardButton
                        .builder()
                        .text(text)
                        .callbackData(text)
                        .build()
                )
                .collect(Collectors.toList());

        double rowsCount = Math.ceil(buttons.size() / columnsForKeyBoard);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        Iterator<InlineKeyboardButton> iterator = buttons.iterator();

        for (int i = 0; i < rowsCount; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            rows.add(row);
            for (int j = 0; j < columnsForKeyBoard & iterator.hasNext(); j++) {
                row.add(iterator.next());
            }
        }
//        List<List<InlineKeyboardButton>> keyboards = rows;

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rows);
        try {
            execute(SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(textForKeyboard)
                    .replyMarkup(inlineKeyboardMarkup)
                    .parseMode(parseMode)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
