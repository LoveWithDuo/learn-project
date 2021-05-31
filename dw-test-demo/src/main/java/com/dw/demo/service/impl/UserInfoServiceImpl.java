package com.dw.demo.service.impl;

import com.dw.demo.bean.UserInfo;
import com.dw.demo.service.UserInfoService;
import com.dw.demo.util.common.BaseSerVice;
import com.silwings.transform.annotation.PhoneTransform;
import org.springframework.stereotype.Service;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2020/9/27 16:17
 * @version v1.0
 */
@Service
public class UserInfoServiceImpl extends BaseSerVice<UserInfo> implements UserInfoService {
    @Override
    @PhoneTransform
    public String add() {
        return "17622225415";
    }
}