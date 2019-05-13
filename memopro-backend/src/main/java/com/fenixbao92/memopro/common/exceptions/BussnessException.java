package com.fenixbao92.memopro.common.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by fenixbao92 on 2019/5/13.
 */
@Getter
@Setter
public class BussnessException extends RuntimeException {
    private Integer status;
    private String msg;

    public BussnessException() {
        this.status = 500;
        this.msg = "服务器处理这个请求出现了问题T.T 请联系管理员";
    }

    public BussnessException(String msg) {
        this.status = 500;
        this.msg = msg;
    }

    public BussnessException(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
