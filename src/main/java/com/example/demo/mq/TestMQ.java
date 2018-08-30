package com.example.demo.mq;

import com.example.demo.config.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * Created by hadoop on 2018/2/27.
 *
 * @author hadoop
 */
@Component
public class TestMQ {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = {RabbitConfig.TEST_QUEUE_NAME})
    @RabbitHandler
    public void processMessage(byte[] body) {
        String message;
        try {
            message = new String(body ,  "UTF-8");
            log.info("rabbmitMQ 读取消息：" + message);

            Thread.sleep(100);


        } catch (Exception e) {
            log.error("读取mq消息异常" , e);
        }

    }
}
