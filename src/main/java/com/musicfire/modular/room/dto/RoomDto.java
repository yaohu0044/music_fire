package com.musicfire.modular.room.dto;

import com.musicfire.modular.room.entity.Room;
import lombok.Data;

@Data
public class RoomDto extends Room {

    /**
     * 机器状态
     */
    private Integer state;

    /**
     * 客户名
     */
    private String merchantName;

    /**
     * 机器code
     */
    private String machineCode ;

    private String stateStr;
    private String machineName;

    /**
     * 机器是否有仓位处于打开状态 true 打开， false 未打开
     */
    private Boolean machinePosition = false;

}
