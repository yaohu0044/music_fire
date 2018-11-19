package com.musicfire.common.config.mq.dto;

import lombok.Data;


@Data
public class WsMessage {
    String title;
    String message;
    int type;

    public WsMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public WsMessage(String title, String message, int type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
}
