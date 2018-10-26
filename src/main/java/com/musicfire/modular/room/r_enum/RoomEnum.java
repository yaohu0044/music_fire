package com.musicfire.modular.room.r_enum;

public enum RoomEnum {

    ROOM_ID_IS_NULL("房间信息或者房间ID不能空",1);

    private String msg;
    private Integer code;

    RoomEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    RoomEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
