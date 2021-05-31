package com.dw.demo.entity;

import com.dw.demo.util.common.BaseBean;
import lombok.Data;

import javax.persistence.Table;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:50
 * @version v1.0
 */
@Data
@Table(name = "permission")
public class Permission extends BaseBean {


    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限访问路径
     */
    private String url;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 父级权限id
     */
    private Integer parentId;

    /**
     * 类型  0：目录  1：菜单  2：按钮
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 图标
     */
    private String icon;


}