package com.dw.demo.util.common;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 统一返回信息封装
 *
 * @author qijiahai on 2019-05-06.
 * @version 1.0
 */
@Data
@ApiModel(value = "返回类")
public class R<T> implements Serializable {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "code")
    private Integer code;
    /**
     * 返回消息
     */
    @ApiModelProperty(value = "描述")
    private String msg;
    /**
     * 数据内容
     */
    @ApiModelProperty(value = "对象")
    private T data;






    public R setCode(Integer code) {
        this.code = code;
        return this;
    }


    public String getMsg() {
        return msg;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static R success() {
        return new R()
                .setCode(Rcode.SUCCESS.code)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    public static R success(Object data) {
        return new R()
                .setCode(Rcode.SUCCESS.code)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }


    public static R fail(Integer code,String message) {
        return new R()
                .setCode(code)
                .setMsg(message);
    }

    public static R fail(String message) {
        return new R()
                .setCode(Rcode.FAIL.code)
                .setMsg(message);
    }

    public static R fail(String message, Object data) {
        return new R()
                .setCode(Rcode.FAIL.code)
                .setMsg(message)
                .setData(data);
    }


    //未认证返回
    public static R unauthentication(String message, Object data) {
        return new R()
                .setCode(Rcode.UNAUTHENTICATION.code)
                .setMsg(message)
                .setData(data);
    }


    //未授权返回
    public static R unauthorized(String message, Object data) {
        return new R()
                .setCode(Rcode.UNAUTHORIZED.code)
                .setMsg(message)
                .setData(data);
    }

    /**
     * 返回分页数据
     * @param page
     * @return
     */
    public static PR pageSuccess(Page page){
        PR<Object> pr = new PR<>();
        pr.setCode(Rcode.SUCCESS.code);
        pr.setMsg(DEFAULT_SUCCESS_MESSAGE);
        pr.setData(page.getResult());
        pr.setTotal(page.getTotal());
        pr.setPageNum(page.getPageNum());
        pr.setPageSize(page.getPageSize());
        return pr;
    }

    public Integer getCode() {
        return code;
    }


    public static String getDefaultSuccessMessage() {
        return DEFAULT_SUCCESS_MESSAGE;
    }

    public R() {
    }



    public static  void main(String [] ad){

        List<String> a = new ArrayList<>();
    }
}
