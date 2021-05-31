package com.dw.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dw.demo.util.RedisUtil;
import com.dw.demo.util.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/27 13:47
 * @version v1.0
 */
@RestController
@RequestMapping("/redis")
public class RedisTest {
    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(value = "/test", method = { RequestMethod.GET })
    public R test() {
        String test = "test";
        boolean set = redisUtil.set("redisTest", test);
        return R.success(set);
    }
    @RequestMapping(value = "/getTest", method = { RequestMethod.GET })
    public R getTest() {
        String redisTest = (String)redisUtil.get("redisTest");
        return R.success(redisTest);
    }

    @RequestMapping(value = "/setList", method = { RequestMethod.GET })
    public R setList() {
        List<String> arr = new ArrayList<>();
        arr.add("zhangsan");
        arr.add("lisi");
        arr.add("wangwu");
        String s = JSONObject.toJSONString(arr);
        boolean set = redisUtil.set("name", s);
        return R.success(set);
    }

    @RequestMapping(value = "/getList", method = { RequestMethod.GET })
    public R getList() {
        String name = (String)redisUtil.get("name");
        System.out.println(name);
        List<String> strings = JSONArray.parseArray(name, String.class);
        return R.success(JSONObject.toJSONString(strings));
    }

}