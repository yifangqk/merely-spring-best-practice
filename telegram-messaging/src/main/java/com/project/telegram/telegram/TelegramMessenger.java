package com.project.telegram.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramMessenger implements Messenger {
    private String id;

    private String name;
}
