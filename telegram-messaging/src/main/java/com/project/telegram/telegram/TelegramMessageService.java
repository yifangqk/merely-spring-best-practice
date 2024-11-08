package com.project.telegram.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.LinkPreviewOptions;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.stereotype.Service;

@Service
public class TelegramMessageService implements MessageService {
    @Override
    public void send(Messenger messenger, Message message) {
        var bot = new TelegramBot(messenger.getId());
        var sendMessage = new SendMessage(message.getReceiverId(), message.getData())
                .parseMode(ParseMode.HTML)
                .linkPreviewOptions(new LinkPreviewOptions().isDisabled(true));

        Completable.fromAction(() -> bot.execute(sendMessage))
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }
}
