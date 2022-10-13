package com.cryptotelegram.controller;

import com.cryptotelegram.entity.Coin;
import com.cryptotelegram.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@Controller
public class TelegramController {

//    public static UserChatRepository userChatRepository;
//    public static UserCoinRepository userCoinRepository;
//    public static UserWantCourseRepository userWantCourseRepository;
//    public static TelegramBotStateRepository telegramBotStateRepository;
    @Autowired
    public static CoinRepository coinRepository;

//    private HttpRequestImpl httpRequestImpl = new HttpRequestImpl();

//    @Autowired
//    public TelegramController(UserChatRepository userChatRepository
//            , UserCoinRepository userCoinRepository
//            , UserWantCourseRepository userWantCourseRepository
//            , TelegramBotStateRepository telegramBotStateRepository
//            , CoinRepository coinRepository) {
//        TelegramController.userChatRepository = userChatRepository;
//        TelegramController.userCoinRepository = userCoinRepository;
//        TelegramController.userWantCourseRepository = userWantCourseRepository;
//        TelegramController.telegramBotStateRepository = telegramBotStateRepository;
//        TelegramController.coinRepository = coinRepository;
//    }

//    @Scheduled(initialDelayString = "${schedule.controller.init}", fixedDelayString = "${schedule.controller.work}")
//    public static void get() {
//
//        String address = "http://CryptoExchange:8081/getListCoins";
////        String address = "http://localhost:8081/getListCoins";
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//
//        HttpGet httpGet = new HttpGet(address);
//
//
//
//
//        try {
//            HttpResponse httpResponse = httpClient.execute(httpGet);
//
//
//            Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
//
//            Gson gson = new Gson();
//            while (scanner.hasNext()) {
//                String text = scanner.nextLine();
//
//                Type datasetListType = new TypeToken<Collection<BdCoins>>() {
//                }.getType();
//
////                List<BdCoins> bdCoins = gson.fromJson(text, datasetListType);
//                gson.fromJson(text, datasetListType);
//
//                System.out.println("Получение данных из первого модуля, прошло успешно" + LocalDateTime.now());
//
//            }
//
//            scanner.close();
//            httpClient.close();
//            System.out.println(BdCoins.bdCoinsList.get(0)+"\n"+ BdCoins.bdCoinsList.get(1));
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println(LocalDateTime.now());
//        }
//
//    }


    public static File getFileWithAllCoins(Long chatId) {

        String stringPath = System.getProperty("user.dir") + "\\" + chatId + ".csv";
        List<Coin> allCoinList = coinRepository.getAllCoinList();

        try {

            FileWriter writer = new FileWriter(stringPath, false);

            StringBuilder text = new StringBuilder();
            text.append("Coin ID").append(",").append("Coin Name").append(",").append("Symbol").append("\n");

            for (Coin coin : allCoinList) {

                text.append(coin.getCoinId()).append(",").append(coin.getCoinName()).append(",").append(coin.getCoinSymbol()).append("\n");

            }
            writer.write(text.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return new File(stringPath);

    }
}