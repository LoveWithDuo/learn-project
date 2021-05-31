package com.dw.demo.rabbitmq.WorkRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/24 18:20
 * @version v1.0
 */
@RabbitListener(queues = "work.hello")
public class WorkReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkReceiver.class);

    private final int instance;

    public WorkReceiver(int i) {
        this.instance = i;
    }

    @RabbitHandler
    public void receive(String in){
        doWork(in);
    }

    private void doWork(String in){
        System.out.println("队列："+this.instance + "接收消息："+in);
    }

}