package com.musicfire.modular.room.query;

import com.musicfire.common.utiles.BasePage;
import lombok.Data;

@Data
public class RoomPage extends BasePage{

    /**
     * 商家名称
     */
    private String merchantName;

    /**
     * 房间名称
     */
    private String roomName;
}
