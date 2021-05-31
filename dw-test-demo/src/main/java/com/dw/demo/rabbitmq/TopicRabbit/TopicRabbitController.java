package com.dw.demo.rabbitmq.TopicRabbit;

import com.dw.demo.util.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/27 13:05
 * @version v1.0
 */
@Api(tags = "RabbitController", description = "RabbitMQ功能测试")
@Controller
@RequestMapping("/topicRabbit")
public class TopicRabbitController {

    @Autowired
    private TopicSender topicSender;

    @ApiOperation("通配符模式")
    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    @ResponseBody
    public R topicTest() throws InterruptedException {
        for(int i=0;i<10;i++){
            topicSender.send(i);
            Thread.sleep(1000);
        }
        return R.success();
    }
}