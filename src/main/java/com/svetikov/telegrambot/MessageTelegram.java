package com.svetikov.telegrambot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageTelegram {
    private String name;
    private String message;


}
