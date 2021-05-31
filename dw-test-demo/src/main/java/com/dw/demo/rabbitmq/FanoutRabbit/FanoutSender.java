package com.dw.demo.rabbitmq.FanoutRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/25 13:18
 * @version v1.0
 */
public class FanoutSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutSender.class);
    @Autowired
    private RabbitTemplate template;

    private static final String exchangeName = "exchange.fanout";

    public void send(int i) {
        StringBuilder builder = new StringBuilder("序号:"+i+"内容:"+"Hello");
        String message = builder.toString();
        template.convertAndSend(exchangeName, "", message);
    }

}