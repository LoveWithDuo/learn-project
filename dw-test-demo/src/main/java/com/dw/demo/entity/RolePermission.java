package com.dw.demo.entity;

import com.dw.demo.util.common.BaseBean;
import lombok.Data;

import javax.persistence.Table;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/2 10:01
 * @version v1.0
 */
@Data
@Table(name = "role_permission")
public class RolePermission extends BaseBean {


    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;


}