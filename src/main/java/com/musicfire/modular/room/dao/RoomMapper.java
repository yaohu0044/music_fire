package com.musicfire.modular.room.dao;

import com.musicfire.modular.room.entity.Room;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.room.query.RoomPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {

    List<Room> queryByRoom(RoomPage page);

    int queryByCount(RoomPage page);
}