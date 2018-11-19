package com.musicfire.common.config;

import com.musicfire.common.config.mq.dto.WsMessage;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.config.websocket.MachineSocket;
import com.musicfire.common.utiles.DateTool;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

import static com.musicfire.common.config.redisdao.RedisConstant.MACHINE_STATE_PRE;

@Configuration
@Slf4j
public class MQTTConfig {

    @Value("${mqtt.broker.serverUri}")
    private String serviceUri;

    @Value("${mqtt.broker.username:}")
    private String username;

    @Value("${mqtt.broker.password:}")
    private String password;

    @Value("${mqtt.pclientId}")
    private String pclientId;

    @Value("${mqtt.cclientId}")
    private String cclientId;

    @Value("${mqtt.topics}")
    private String topics;

    @Value("${mqtt.qos}")
    private Integer qos;

    @Value("${mqtt.completionTimeout}")
    private Integer completionTimeout;

    @Resource
    private RedisDao redisDao;

    @Resource
    private MachineSocket machineSocket;


    private void sendWsMessage(String title, String message, int type) {
        WsMessage wsMessage = new WsMessage(title, message, type);
//        log.info("message:{}",wsMessage);

        machineSocket.sendMessageObj(wsMessage);

    }

    /**
     * 消息通道
     *
     * @return 消息通道
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt服务器配置
     *
     * @return mqtt服务器配置
     */
    @Bean
    public MqttPahoClientFactory clientFactory() {
        DefaultMqttPahoClientFactory clientFactory = new DefaultMqttPahoClientFactory();
        clientFactory.setServerURIs(serviceUri);
//        clientFactory.setUserName(username);
//        clientFactory.setPassword(password);
        return clientFactory;
    }

    /**
     * 通道适配器
     *
     * @return 通道适配器
     */
    @Bean
    public MessageProducer inbound(MqttPahoClientFactory clientFactory, MessageChannel mqttInputChannel) {
        //clientId 客户端ID，生产端和消费端的客户端ID需不同，不然服务器会认为是同一个客户端，会接收不到信息
        //topic 订阅的主题
        String[] topicArray = topics.split(",");
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(cclientId, clientFactory, topicArray);
        //超时时间
        adapter.setCompletionTimeout(completionTimeout);
        //转换器
        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(qos);
        adapter.setQos(0, 1, 2);
        adapter.setOutputChannel(mqttInputChannel);
        return adapter;
    }

    /**
     * @return 消息处理
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {

            MessageHeaders headers = message.getHeaders();
            Set<Map.Entry<String, Object>> entries = headers.entrySet();
            entries.forEach(m -> {
                System.out.println(m.getKey());
                System.out.println(m.getValue());
            });
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String body = message.getPayload().toString();  // new String(message.getPayload());
            String eventName;
            String commandName;
            try {
                assert topic != null;
                if (topic.contains("/")) {
                    eventName = topic.substring(topic.lastIndexOf('/') + 1);
                    commandName = topic.substring(0, topic.indexOf("/"));
                } else {
                    eventName = topic;
                    commandName = topic;
                }
                if (commandName.contains(RedisConstant.TOPIC_REGISTER)) {
                    if (!StringUtils.isEmpty(body) && body.length() == 24) {
                        MachineState ms = new MachineState();
                        ms.setMachineCode(body);
                        ms.setMachineState(MachineStatusEnum.REGISTER);
                        sendWsMessage("设备上线", "序列号：[" + body + "]", MachineStatusEnum.ONLINE.getCode());
                    } else {
                        sendWsMessage("设备注册失败", "设备序列号出错", MachineStatusEnum.REGISTER.getCode());
                    }
                } else if (commandName.contains(RedisConstant.TOPIC_STATE)) {
                    if (!StringUtils.isEmpty(eventName) && eventName.length() == 24) {
                        MachineState ms = new MachineState();
                        ms.setMachineCode(eventName);
                        ms.setMachineState(MachineStatusEnum.STATE);
                        ms.setCabinetState(body);
                        ms.setUpdateTime(DateTool.NOW());
                        if (redisDao.exist(MACHINE_STATE_PRE + eventName)) {
                            redisDao.update(MACHINE_STATE_PRE + eventName, ms);
                        } else {
                            redisDao.add(MACHINE_STATE_PRE + eventName, ms);
                        }
                    } else {
                        log.error("machine sn error");
                    }
                } else if (commandName.contains(RedisConstant.TOPIC_OFFLINE)) {
                    MachineState ms = new MachineState();
                    ms.setMachineCode(body);
                    ms.setMachineState(MachineStatusEnum.OFFLINE);
                    ms.setUpdateTime(DateTool.NOW());
                    redisDao.update(MACHINE_STATE_PRE + body, ms);
                    sendWsMessage("设备下线", "序列号：[" + body + "]", MachineStatusEnum.OFFLINE.getCode());
                }
            } catch (Exception e) {
                log.error("Can not resolve Mqtt message", e);
            }
        };
    }


    /**
     * 消息通道
     *
     * @return 消息通道
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }


    /**
     * 通道适配器
     *
     * @param clientFactory  通道工厂
     * @return 通道适配器
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound(MqttPahoClientFactory clientFactory) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(pclientId, clientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(2);
        messageHandler.setDefaultRetained(false);
        messageHandler.setAsyncEvents(false);
        return messageHandler;
    }
}
