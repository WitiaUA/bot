package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Autowired
    private SendMessageService sendMessageService;

    public TelegramBot(@Value("${telegram.bot.token}") String token) {
        super(token);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.getText().equals("/start")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(String.valueOf(message.getChatId()));
                sendMessage.setText("Вітаю! Якщо ви бажаєте заповнити анкету, натисніть кнопку нижче");
                sendMessageService.sendInlineKeyboard(sendMessage);
            } else if (message.hasText()) {
                sendMessageService.sendResponse(message);
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            sendMessageService.sendCallbackResponse(callbackQuery);
        }
    }
}