package com.dw.demo.entity.vo;

import com.dw.demo.entity.Permission;
import com.dw.demo.entity.Role;
import lombok.Data;

import java.util.List;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/4 16:10
 * @version v1.0
 */
@Data
public class RoleVo extends Role {
    /**
     * 权限集合
     */
    List<Permission> permissionList;
}