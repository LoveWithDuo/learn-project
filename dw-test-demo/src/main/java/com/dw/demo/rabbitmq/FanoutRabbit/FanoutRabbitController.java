package com.dw.demo.rabbitmq.FanoutRabbit;

import com.dw.demo.util.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  发布/订阅模式
 * @Author: zhanzhihong
 * @Date: 2020/9/25 13:19
 * @version v1.0
 */
@Api(tags = "RabbitController", description = "RabbitMQ功能测试")
@Controller
@RequestMapping("/fanoutRabbit")
public class FanoutRabbitController {

    @Autowired
    private FanoutSender fanoutSender;

    @ApiOperation("发布/订阅模式")
    @RequestMapping(value = "/fanout", method = RequestMethod.GET)
    @ResponseBody
    public R fanoutTest(){
        for(int i=0;i<10;i++){
            fanoutSender.send(i);
        }
        return R.success();
    }
}