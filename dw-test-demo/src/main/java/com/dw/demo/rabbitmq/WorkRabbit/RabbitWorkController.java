package com.dw.demo.rabbitmq.WorkRabbit;

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
 * @Date: 2020/9/24 18:21
 * @version v1.0
 */
@Api(tags = "RabbitController", description = "RabbitMQ功能测试")
@Controller
@RequestMapping("/rabbitWork")
public class RabbitWorkController {

    @Autowired
    private WorkSender workSender;

    @ApiOperation("工作模式")
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    @ResponseBody
    public R workTest(){
        for(int i=0;i<10;i++){
            workSender.send();
        }
        return R.success(null);
    }
}