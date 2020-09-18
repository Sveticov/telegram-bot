package com.svetikov.telegrambot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageTelegram {
    private String name;
    private String message;
}
