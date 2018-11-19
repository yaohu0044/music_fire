package com.musicfire.modular.room.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.room.dto.RoomDto;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<RoomDto> queryByRoom(RoomPage page);

    int queryByCount(RoomPage page);

    /**
     * 根据ID 批量删除
     * @param ids
     */
    void updateByIds(@Param("ids") List<Integer> ids);
}
