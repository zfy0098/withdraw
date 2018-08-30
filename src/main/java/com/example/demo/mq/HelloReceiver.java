package com.example.demo.mq;

import com.example.demo.config.RabbitConfig;
import com.example.demo.service.CreditCardRepaymentPay;
import com.example.demo.service.WithdrawService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by hadoop on 2017/11/20.
 *
 * @author hadoop
 */
@Component
public class HelloReceiver {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private CreditCardRepaymentPay creditCardRepaymentPay;

    @RabbitListener(queues = {RabbitConfig.QUEUE_NAME , RabbitConfig.AGAIN_QUEUE_NAME})
    @RabbitHandler
    public void processMessage(byte[] body) {
        String message;
        try {
            message = new String(body ,  "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("读取mq消息异常" , e);
            return ;
        }
        try {
            log.info("rabbmitMQ 读取消息：" + message);

            JSONObject json = JSONObject.fromObject(message);

            String trade = "Trade";
            String tx = "TX";
            String dfTypeKey = "dfType";

            if(trade.equals(json.getString(dfTypeKey))){
                log.info("发送交易代付请求");
                creditCardRepaymentPay.withdraw(message);
            }else if(tx.equals(json.getString(dfTypeKey))){
                log.info("发送用户提现提请求");
                withdrawService.withdraw(message);
            }
        } catch (Exception e) {
            log.error("json 格式化异常" , e);
        }
    }
}

