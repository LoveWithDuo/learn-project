package com.dw.demo.service;

import com.dw.demo.bean.UserInfo;
import com.dw.demo.util.common.IService;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/27 16:16
 * @version v1.0
 */
public interface UserInfoService extends IService<UserInfo> {
    String add();
}