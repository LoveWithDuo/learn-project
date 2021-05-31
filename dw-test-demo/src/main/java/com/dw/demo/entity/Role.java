package com.dw.demo.entity;

import com.dw.demo.util.common.BaseBean;
import lombok.Data;

import javax.persistence.Table;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:49
 * @version v1.0
 */
@Data
@Table(name = "role")
public class Role extends BaseBean {

    /**
     * 角色名称
     */
    private String rolename;

    /**
     * 角色描述
     */
    private String description;


}