package com.musicfire.common.config.websocket;


import com.alibaba.fastjson.JSON;
import com.musicfire.common.config.mq.dto.WsMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ServerEncode implements Encoder.Text<WsMessage> {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(WsMessage messagepojo) throws EncodeException {
        return JSON.toJSONString(messagepojo);
    }
}