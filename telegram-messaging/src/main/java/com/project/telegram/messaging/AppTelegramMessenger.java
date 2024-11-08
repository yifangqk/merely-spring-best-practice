package com.project.telegram.messaging;

import com.project.telegram.telegram.Message;
import com.project.telegram.telegram.Messenger;
import com.project.telegram.telegram.TelegramMessageService;
import com.project.telegram.telegram.TelegramMessenger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class AppTelegramMessenger {
    private final TelegramBotProperties telegramBotProperties;
    private final Messenger errorMessenger;

    public AppTelegramMessenger(TelegramBotProperties telegramBotProperties,
                                @Value("${spring.application.name}") String applicationName) {
        this.telegramBotProperties = telegramBotProperties;
        this.errorMessenger = new TelegramMessenger(telegramBotProperties.getError().getBotId(), applicationName);
    }

    public void sendError(Exception ex) {
        var message = MessageFormat.format("""
                        {0}
                        
                        <b>Error type:</b> {1}
                        
                        
                        <b>Error message:</b> {2}
                        
                        
                        <b>Stacktrace:</b> {3}
                        
                        
                        """,
                StringUtils.center("============== <b>SERVER ERROR</b> ==============", 100),
                ex.getClass().getSimpleName(),
                StringUtils.left(ex.getMessage(), 500),
                StringUtils.left(ExceptionUtils.getStackTrace(ex), 1000));
        sendError(message);
    }

    public void sendError(String message) {
        var errorMessage = new Message(telegramBotProperties.getError().getChannelId(), message);
        new TelegramMessageService().send(errorMessenger, errorMessage);
    }
}
