package com.linzongwei.mqtt;

import com.linzongwei.mqtt.config.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 发布主题测试
 *
 * @author linli
 * @since 2021/1/25 14:03
 */
@RestController
public class SendMqttController {

    private MqttClient mqttClient;

    @RequestMapping("sendTest")
    public String sendTest() {
        mqttClient.sendToMqtt("/service/send", "sendText");
        return "success";
    }

    @Autowired
    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
}
