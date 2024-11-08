package com.project.telegram.messaging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.bot.telegram")
public class TelegramBotProperties {

    private BotProperties error;

    @Data
    public static class BotProperties {
        private String channelId;
        private String botId;
    }
}
