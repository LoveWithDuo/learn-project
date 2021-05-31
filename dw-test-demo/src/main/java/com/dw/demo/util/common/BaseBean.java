package com.dw.demo.util.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 基础bean
 *
 * @author liwenjie on 2019/5/9
 * @version 1.0
 */
@Data
public class BaseBean implements Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @KeySql(useGeneratedKeys = true)
    @ApiModelProperty(value = "主键")
    private Long id;

//    @ApiModelProperty(value = "创建时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createdAt;
//
//    @ApiModelProperty(value = "更新时间")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updatedAt;
//
//    @ApiModelProperty(value = "是否删除：0=正常，1=删除")
//    private Boolean archive;
}
