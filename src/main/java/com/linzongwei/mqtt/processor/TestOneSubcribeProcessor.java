package com.linzongwei.mqtt.processor;

import org.springframework.stereotype.Service;

/**
 * Description: 主题处理器1
 *
 * @author linli
 * @since 2021/1/25 14:54
 */
@Service
public class TestOneSubcribeProcessor implements SubcribeProcessor{

    @Override
    public String subcribeTopic() {
        return "test1";
    }

    @Override
    public void process(String payload) {
        System.out.println("process topic1,receive payload content:" + payload);
    }
}
