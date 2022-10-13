package com.cryptotelegram.serviceImpl;

import com.cryptotelegram.service.ChatService;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChatServiceImpl implements ChatService {

    private Pattern pattern;
    private Matcher matcher;

    public ChatServiceImpl() {
    }

    @Override
    public String parseChatType(String text) {
        pattern = Pattern.compile("(?<=type=)'\\w+'");
        matcher = pattern.matcher(text);
        while (matcher.find()) {
            return matcher.group().replaceAll("'", "");
        }
        return "No match";
    }
}
