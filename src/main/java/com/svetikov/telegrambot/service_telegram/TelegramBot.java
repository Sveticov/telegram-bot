package com.svetikov.telegrambot.service_telegram;

import org.springframework.stereotype.Component;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static String CHAT_ID ="-441992642";

    private static String USER_NAME_BOT ="SvetikovTest_bot";

    private static String TOKEN_BOT ="1291703652:AAE8Ix72jnA4hie7xG9nHsqxM4pcrVrzAP0";

    @PostConstruct
    public void activeBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void SendMyMessage(String text)  {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(CHAT_ID);
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void SendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardButtons = new KeyboardRow();

        keyboardButtons.add(new KeyboardButton("/alex"));
        keyboardButtons.add(new KeyboardButton("/setting"));

        keyboardRowList.add(keyboardButtons);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        System.out.println("11111111111111111111111111");
        if (message != null && message.hasText()) {//
            System.out.println(message.getText());
           // if (message.getText().equals("/alex"))
                SendMsg(message, "Hello Alex");
        }
    }

    @Override
    public String getBotUsername() {
        return USER_NAME_BOT;
    }

    @Override
    public String getBotToken() {
        return TOKEN_BOT;
    }


}
