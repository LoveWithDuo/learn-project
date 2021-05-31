package com.dw.demo.mapper;

import com.dw.demo.entity.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/2/25 18:02
 * @version v1.0
 */
@Repository
public interface PermissionMapper extends Mapper<Permission> {
}