package com.dw.demo.mapper;

import com.dw.demo.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @version v1.0
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:54
 */
@Repository
public interface UserMapper extends Mapper<User> {
}
