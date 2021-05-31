package com.dw.demo.rabbitmq.SimpleRabbit;

import com.dw.demo.util.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/24 17:12
 * @version v1.0
 */
@Api(tags = "RabbitController", description = "RabbitMQ功能测试")
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private SimpleSender simpleSender;

    @ApiOperation("简单模式")
    @RequestMapping(value = "/simple", method = {RequestMethod.GET,RequestMethod.POST})
    public R simpleTest(){
        for(int i=0;i<10;i++){
            simpleSender.send();
        }
        return R.success("发送成功");
    }
}