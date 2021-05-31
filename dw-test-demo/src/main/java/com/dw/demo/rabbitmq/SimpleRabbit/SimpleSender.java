package com.dw.demo.rabbitmq.SimpleRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/24 17:29
 * @version v1.0
 */
public class SimpleSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName="simple.hello";

    public void send() {
        String message = "Hello World!";
        this.template.convertAndSend(queueName, message);
        LOGGER.info(" [x] Sent '{}'", message);
    }

}