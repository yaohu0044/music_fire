package com.musicfire.modular.machine.machine_enum;

/**
 * Created by Sommer Jiang
 * 2017-06-11 17:12
 */
public enum MachinePositionEnum{

    CLOSED(0, "仓门关闭"),
    OPEN(1, "仓门打开"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    MachinePositionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
