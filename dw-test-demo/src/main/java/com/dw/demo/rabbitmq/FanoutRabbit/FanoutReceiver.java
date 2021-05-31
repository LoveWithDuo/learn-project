package com.dw.demo.rabbitmq.FanoutRabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/25 13:19
 * @version v1.0
 */
public class FanoutReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(FanoutReceiver.class);

    @RabbitListener(queues = "#{fanoutQueue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{fanoutQueue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    private void receive(String in, int receiver) throws InterruptedException {
        doWork(in,receiver);
    }

    private void doWork(String in,Integer receiver) throws InterruptedException {
        System.out.println("队列："+receiver+"收到的信息："+in);
    }

}