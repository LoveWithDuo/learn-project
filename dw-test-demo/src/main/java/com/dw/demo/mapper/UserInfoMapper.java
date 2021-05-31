package com.dw.demo.mapper;

import com.dw.demo.bean.UserInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @version v1.0
 * @Author: zhanzhihong
 * @Date: 2020/9/27 16:21
 */
@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {
}
