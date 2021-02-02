package com.linzongwei.mqtt.processor;

import org.springframework.stereotype.Service;

/**
 * Description: 主题处理器1
 *
 * @author linli
 * @since 2021/1/25 14:54
 */
@Service
public class TestTwoSubcribeProcessor implements SubcribeProcessor{

    @Override
    public String subcribeTopic() {
        return "test2";
    }

    @Override
    public void process(String payload) {
        System.out.println("process topic2,receive payload content:" + payload);
    }
}
