package com.musicfire.common.businessException;

public enum ErrorCode {

    NOT_LOGGED_IN(1, "未登录，清登录");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}