package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hadoop on 2017/11/21.
 *
 * @author hadoop
 */
@Configuration
public class RabbitConfig {

    public final static String QUEUE_NAME = "queue_delay_done";

    public final static String EXCHANGE_NAME = "exchange_delay_done";

    public final static String AGAIN_QUEUE_NAME = "queue_again";

    public final static String AGAIN_EXCHANGE_NAME = "exchange_again";

    public final static String TEST_QUEUE_NAME = "queue_test";

    public final static String TEST_EXCHANGE_NAME = "exchange_test";


    @Bean
    public Queue createTestQueue(){
        return new Queue(TEST_QUEUE_NAME , true , false , false , null);
    }

    @Bean
    DirectExchange testExchange(){
        return new DirectExchange(TEST_EXCHANGE_NAME , true , false);
    }

    @Bean
    Binding bindingTestExchange(Queue createTestQueue , DirectExchange testExchange){
        return BindingBuilder.bind(createTestQueue).to(testExchange).with("test");
    }


    @Bean
    public Queue createQueue() {
        return new Queue(QUEUE_NAME , true , false, false, null);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME ,  true , false );
    }

    @Bean
    Binding bindingExchangeA(Queue createQueue, DirectExchange exchange) {
        return BindingBuilder.bind(createQueue).to(exchange).with("DF");
    }


    @Bean
    public Queue createAgainQueue(){
        return new Queue(AGAIN_QUEUE_NAME , true , false , false , null);
    }

    @Bean
    DirectExchange againExchange(){
        return new DirectExchange(AGAIN_EXCHANGE_NAME , true , false);
    }

    @Bean
    Binding bindingExchangeB(Queue createAgainQueue , DirectExchange againExchange){
        return BindingBuilder.bind(createAgainQueue).to(againExchange).with("againDF");
    }

}
