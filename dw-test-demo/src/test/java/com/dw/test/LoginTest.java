package com.dw.test;

import com.alibaba.fastjson.JSON;
import com.dw.demo.Application;
import com.dw.demo.bean.User;
import com.dw.demo.shiro.MyShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/1 16:43
 * @version v1.0
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class LoginTest {
    @Autowired
    private User user;

    @Test
    public void login() {
        String username = "张三";
        String password = "123456";
        Map<String, Serializable> map = new HashMap<>(16);
        Subject subject = SecurityUtils.getSubject();
        // 判断是否已经登录
        if (subject.getPrincipal() == null) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            Serializable sessionId = subject.getSession().getId();
            map.put(MyShiroRealm.AUTHORIZATION, sessionId);
        }
        System.out.println(JSON.toJSONString(map));
    }
}