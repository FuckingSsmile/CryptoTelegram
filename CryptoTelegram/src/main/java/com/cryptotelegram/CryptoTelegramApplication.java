package com.cryptotelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass = true)
public class CryptoTelegramApplication {

    public static void main(String[] args) {

        SpringApplication.run(CryptoTelegramApplication.class, args);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        return scheduler;
    }
}

//TODO Реализовать еще одну команду ShowCoinInformation, будет показывать подробную информацию о монете, максимальная цена, изменения за 24ч, ссылку на коингеко и логотип

//TODO Реализовать взаимодействие двух модулей, один обновляет цену, другой считывает и проходится по монетам у пользователей, меняет цену

//TODO использовать разные сиквенсы под разные таблицы(чтобы счет шел с 0)

//TODO разобраться со связью между таблицами - user_want_course, TelegramBotState, как связать и нужно ли

//TODO Как сделать красивый вывод toString()

//TODO Разобраться со статичными переменными, так как всю инфу будем брать из БД, нужно перестать брать из джавы.

//TODO Начать уже НАКОНЕЦ делать юнит тесты