package com.musicfire.modular.room.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IRoomService extends IService<Room> {

    /**
     * 房间分页信息
     * @param page
     * @return
     */
    RoomPage queryByRoom(RoomPage page);
}
