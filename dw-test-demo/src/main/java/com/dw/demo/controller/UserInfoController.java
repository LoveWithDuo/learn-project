package com.dw.demo.controller;

import com.dw.demo.bean.UserInfo;
import com.dw.demo.service.UserInfoService;
import com.dw.demo.util.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/27 16:24
 * @version v1.0
 */
@RestController
@RequestMapping("/userInfo")
@Api(tags = "UserInfoController",description = "用户中心")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    @ApiOperation("查询所有")
    @RequestMapping(value = "/findAll", method = {RequestMethod.GET, RequestMethod.POST})
    public R<UserInfo> findAll(){
        List<UserInfo> userInfo = userInfoService.selectByExample(Example.builder(UserInfo.class).andWhere(Sqls.custom().andEqualTo("archive",false)).build());
        return R.success(userInfo);
    }

    @GetMapping("/add")
    public String add(){
        return userInfoService.add();
    }


}