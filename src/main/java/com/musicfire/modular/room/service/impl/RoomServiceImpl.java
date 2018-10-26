package com.musicfire.modular.room.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.room.dao.RoomMapper;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Resource
    private RoomMapper mapper;

    @Override
    public RoomPage queryByRoom(RoomPage page) {
        List<Room> rooms =  mapper.queryByRoom(page);
        int count = mapper.queryByCount(page);
        page.setList(rooms);
        page.setPageCount(count);
        return page;
    }
}
