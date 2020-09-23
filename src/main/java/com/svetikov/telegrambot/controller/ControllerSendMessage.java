package com.svetikov.telegrambot.controller;

import com.svetikov.telegrambot.service_telegram.MessageTelegram;
import com.svetikov.telegrambot.service_telegram.TelegramBot;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class ControllerSendMessage {

    private final TelegramBot telegramBot;

    public ControllerSendMessage(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @SneakyThrows
    @GetMapping("/send/{message}")
    public ResponseEntity<MessageTelegram> send(@PathVariable String message){
        MessageTelegram messageTelegram=new MessageTelegram("name 1",message);
        telegramBot.SendMyMessage(messageTelegram.toString());
        return ResponseEntity.ok(messageTelegram);
    }
}
