package com.dw.demo.rabbitmq.WorkRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/24 18:20
 * @version v1.0
 */
public class WorkSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSender.class);

    @Autowired
    private RabbitTemplate template;

    private static final String queueName = "work.hello";

    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        String message = builder.toString();
        template.convertAndSend(queueName, message);
    }

}