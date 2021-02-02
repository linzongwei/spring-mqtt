package com.linzongwei.mqtt.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * Description:mqtt的配置文件
 *
 */
@Configuration
public class MqttConfig {

    private String url;

    private String port;

    private String clientId;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { url + ":" + port });
        options.setCleanSession(false);
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel dataOutputChannel() {
        return new DirectChannel();
    }

    /**
     * 发送通道
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "dataOutputChannel")
    public MessageHandler outbound() {
        // 在这里进行dataOutputChannel的相关设置
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
        //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        return messageHandler;
    }

    @Bean
    public MessageChannel dataInputChannel() {
        return new DirectChannel();
    }

    /**
     * 接收通道
     * @return
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId,
                mqttClientFactory());
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
//        converter.setPayloadAsBytes(true);
//        adapter.setConverter(converter);
        adapter.setQos(1);
        adapter.setOutputChannel(dataInputChannel());
        return adapter;
    }
    
    @Value("${mqtt.url}")
    public void setUrl(String url) {
        this.url = url;
    }

    @Value("${mqtt.port}")
    public void setPort(String port) {
        this.port = port;
    }

    @Value("${mqtt.client-id}")
    public void setClientIds(String clientId) {
        this.clientId = clientId;
    }
}
