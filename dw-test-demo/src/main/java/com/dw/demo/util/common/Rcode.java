package com.dw.demo.util.common;

/**
 *
 * @author qijiahai on 2019-05-06.
 * @version 1.0
 */
public enum Rcode {
    // 业务成功
    SUCCESS(100200),
    // 业务失败
    FAIL(100400),
    // 未认证
    UNAUTHENTICATION(100401),
    // 数据不存在
    DATA_MISSING(100102),
    // 未授权
    UNAUTHORIZED(100402);

    public int code;

    Rcode(int code) {
        this.code = code;
    }
}
