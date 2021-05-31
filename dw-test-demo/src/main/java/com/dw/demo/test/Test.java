package com.dw.demo.test;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/11 16:34
 * @version v1.0
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        List<String> arr = new ArrayList<>();
//        arr.add("a");
        arr.add("b");
        String s = String.join(",",arr);
        System.out.println(s);
    }






}