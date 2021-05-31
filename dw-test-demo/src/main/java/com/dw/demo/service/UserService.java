package com.dw.demo.service;

import com.dw.demo.entity.User;
import com.dw.demo.entity.vo.RoleVo;
import com.dw.demo.util.common.IService;

import java.util.List;

/**
 * @version v1.0
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:46
 */
public interface UserService extends IService<User> {
    List<RoleVo> getRoles(Long userId);

    User getByUsername(String username);
}
