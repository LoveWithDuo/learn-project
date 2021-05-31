package com.dw.demo.entity;

import com.dw.demo.util.common.BaseBean;
import lombok.Data;

import javax.persistence.Table;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/3/2 9:59
 * @version v1.0
 */
@Data
@Table(name = "user_role")
public class UserRole extends BaseBean {


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

}