package com.cryptotelegram.emoji;

import com.vdurmont.emoji.EmojiParser;

public enum Icon {
    DOWN(":exclamation:"),
    UP(":rocket:"),
    INFO(":white_check_mark:"),
    MONEYBAG(":moneybag:"),
    DOLLAR(":dollar:"),
    EURO(":euro:"),
    LEFT(":point_left:"),
    RIGHT(":point_right:"),
    HAND(":raised_hands:"),
    STEP(":walking:"),
    NO(":x:");

    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
}