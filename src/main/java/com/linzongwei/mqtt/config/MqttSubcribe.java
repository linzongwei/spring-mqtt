package com.linzongwei.mqtt.config;

import com.linzongwei.mqtt.processor.SubcribeProcessor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

/**
 * Description: 订阅mqtt主题
 *
 * @author linli
 * @since 2021/1/25 14:56
 */
@Slf4j
@Service
public class MqttSubcribe {

    private MqttPahoMessageDrivenChannelAdapter adapter;

    private final Map<String, SubcribeProcessor> subcribeProcessorMap = new HashMap();

    public void addTopic(String topic) {
        if (StringUtils.isBlank(topic)) {
            log.warn("add topic id null");
            return;
        }

        List<String> topics = Arrays.asList(adapter.getTopic());
        if (topics.contains(topic)) {
            log.warn("add topic is duplicated");
            return;
        }

        adapter.addTopic(topic, 1);
    }

    @Bean
    @ServiceActivator(inputChannel = "dataInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = (String) message.getPayload();
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

            log.info("receive topic : " + topic);
            log.info("receive message : " + payload);
            SubcribeProcessor subcribeProcessor = subcribeProcessorMap.get(topic);
            if (subcribeProcessor == null) {
                log.warn("no processor can process this topic");
            }
            subcribeProcessor.process(payload);
        };
    }

    @Autowired
    public void setAdapter(
            MqttPahoMessageDrivenChannelAdapter adapter) {
        this.adapter = adapter;
    }

    @Autowired
    public void subcribeTopics(List<SubcribeProcessor> subcribeProcessors) {
        for (SubcribeProcessor subcribeProcessor : subcribeProcessors) {
            addTopic(subcribeProcessor.subcribeTopic());
            subcribeProcessorMap.put(subcribeProcessor.subcribeTopic(), subcribeProcessor);
        }
    }
}
