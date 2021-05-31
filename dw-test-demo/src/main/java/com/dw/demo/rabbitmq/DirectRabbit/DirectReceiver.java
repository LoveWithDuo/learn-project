package com.dw.demo.rabbitmq.DirectRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/25 13:58
 * @version v1.0
 */
public class DirectReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitListener(queues = "#{directQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{directQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    private void receive(String in, int receiver){
        doWork(in,receiver);
    }

    private void doWork(String in,int receiver){
        System.out.println("队列："+receiver+"内容："+in);
    }

}