package com.dw.demo.controller;

import com.dw.demo.bean.IpModel;
import com.dw.demo.service.BusinessService;
import com.dw.demo.util.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/asp")
public class AspectJTestServerRest {
    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/ip", method = { RequestMethod.GET })
    public ResultModel welCome(HttpServletRequest request) {
        IpModel ipModel = businessService.getIpInfo(request, 1);
        return ResultModel.ok(ipModel);
    }

    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    public ResultModel test(HttpServletRequest request) {
        Integer ipModel = businessService.getTestInfo(1);
        return ResultModel.ok(ipModel);
    }
}