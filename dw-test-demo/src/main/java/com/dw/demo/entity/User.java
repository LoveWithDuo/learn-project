package com.dw.demo.entity;

import com.dw.demo.util.common.BaseBean;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @Author: zhanzhihong
 * @Date: 2021/2/25 17:47
 * @version v1.0
 */
@Data
@Table(name = "user")
public class User extends BaseBean {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐值
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 年龄：1男2女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 是否删除：0=正常，1=删除
     */
    private Boolean archive;

}