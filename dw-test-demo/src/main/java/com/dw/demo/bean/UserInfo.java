package com.dw.demo.bean;

import com.dw.demo.util.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 *  用户
 * @Author: zhanzhihong
 * @Date: 2020/9/27 16:14
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_info")
public class UserInfo extends BaseBean {

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private Integer sex;
}