package com.musicfire.modular.machine.machine_enum;

public enum MachineStatusEnum {

    REGISTER(0, "新注册"),
    ONLINE(1,"在线"),
    STATE(2, "上报中"),
    OFFLINE(3, "断线"),
    ERROR(4, "错误"),
    UNBIND(5, "未绑定") ,
    IN_STOCK(6, "有货") ,
    EMPTY_SPACE(7, "空仓") ,
    ;

    private Integer code;

    private String message;

    MachineStatusEnum(Integer code, String message) {
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
