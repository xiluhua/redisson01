package com.debug.boot.redisson.api.service;

import java.io.Serializable;

import java.io.Serializable;

/**
 **/
public enum  RemoteStatus implements Serializable {

    Success(200,"成功"),
    Fail(-1,"失败"),

    ;

    private Integer code;
    private String msg;

    RemoteStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}