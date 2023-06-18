package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SendMessageService {
    private final TelegramBot telegramBot;

    public SendMessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendInlineKeyboard(SendMessage sendMessage) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Заповнити анкету");
        button.setCallbackData("fill_form");
        row.add(button);

        keyboard.add(row);
        markup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(markup);
        try {
            telegramBot.execute(sendMessage);
        } catch (Exception e) {
            log.error("Error while sending message", e);
        }
    }

    public void sendResponse(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText("Ви ввели: " + message.getText() + ". Чи все вірно введено?");
        try {
            telegramBot.execute(sendMessage);
        } catch (Exception e) {
            log.error("Error while sending message", e);
        }
    }

    public void sendCallbackResponse(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(String.valueOf(callbackQuery.getId()));
        answerCallbackQuery.setText("Почніть заповнювати форму, натиснувши на \"Заповнити анкету\"");
        try {
            telegramBot.execute(answerCallbackQuery);
        } catch (Exception e) {
            log.error("Error while sending message", e);
        }
    }

}