package com.musicfire.common.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by jiangs
 * 2017-07-30 23:19
 */
@Component
@ServerEndpoint(value = "/webSocket",encoders = { ServerEncode.class })
@Slf4j
public class MachineSocket {

    private Session session;

    private static CopyOnWriteArraySet<MachineSocket> machineSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        machineSockets.add(this);
        log.info("【websocket消息】有新的连接, 总数:{}", machineSockets.size());
    }

    @OnClose
    public void onClose() {
        machineSockets.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", machineSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (MachineSocket machineSocket : machineSockets) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                machineSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageObj(Object obj) {
        for (MachineSocket machineSocket : machineSockets) {
            log.info("【websocket消息】广播消息, message={}", obj.toString());
            try {
                machineSocket.session.getBasicRemote().sendObject(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
