package com.musicfire.businessException;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 2332608236621015980L;

    private Object data;

    private int code;

    private String msg;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(ErrorCode errorCode, Object data) {
        this.data = data;
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
