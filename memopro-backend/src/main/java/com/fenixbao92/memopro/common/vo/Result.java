package com.fenixbao92.memopro.common.vo;

public class Result {
    private Integer status;
    private String msg;
    private Object obj;

    private Result() {
    }

    public static Result build() {
        return new Result();
    }

    public static Result ok(String msg, Object obj) {
        return new Result(200, msg, obj);
    }

    public static Result ok(String msg) {
        return new Result(200, msg, null);
    }

    public static Result error(String msg, Object obj) {
        return new Result(500, msg, obj);
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }

    public static Result error(Integer status, String msg) {
        return new Result(status, msg, null);
    }

    private Result(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {

        return status;
    }

    public Result setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public Result setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}

