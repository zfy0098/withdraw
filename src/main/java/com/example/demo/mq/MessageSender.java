package com.example.demo.mq;

import com.example.demo.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hadoop on 2018/1/17.
 *
 * @author hadoop
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String message){
        /**
         * rabbitTemplate.send(message);  //发消息，参数类型为org.springframework.amqp.core.Message
         * rabbitTemplate.convertAndSend(object); //转换并发送消息。 将参数对象转换为org.springframework.amqp.core.Message后发送
         * rabbitTemplate.convertSendAndReceive(message) //转换并发送消息,且等待消息者返回响应消息。
         */
        rabbitTemplate.convertSendAndReceive(RabbitConfig.AGAIN_EXCHANGE_NAME , "againDF" , message);
    }
}
