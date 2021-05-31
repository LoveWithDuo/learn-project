package com.dw.demo.service;

import com.dw.demo.util.common.R;

/**
 * @author zhanzhihong
 * @date 2021-05-27 15:28
 */
public interface EsUserService {

    R userSaveAll();

    R findUserList(String name, Integer pageNum, Integer pageSize);

    R findUserList(String name,String phone, Integer pageNum, Integer pageSize);
}
