package com.dw.demo.rabbitmq.DirectRabbit;

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
 * @Date: 2020/9/25 13:58
 * @version v1.0
 */
@Api(tags = "RabbitController", description = "RabbitMQ功能测试")
@Controller
@RequestMapping("/directRabbit")
public class DirectRabbitController {

    @Autowired
    private DirectSender directSender;

    @ApiOperation("路由模式")
    @RequestMapping(value = "/direct", method = RequestMethod.GET)
    @ResponseBody
    public R directTest() {
        for(int i=0;i<10;i++){
            directSender.send(i);
        }
        return R.success();
    }
}