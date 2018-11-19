package com.musicfire.modular.room.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import com.musicfire.modular.room.dao.RoomMapper;
import com.musicfire.modular.room.dto.RoomDto;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.musicfire.common.config.redisdao.RedisConstant.MACHINE_STATE_PRE;

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

    @Resource
    private RedisDao redisDao;

    @Override
    public RoomPage queryByRoom(RoomPage page) {
        int count = mapper.queryByCount(page);
        if(count<0){
            page.setList(null);
        }
        List<RoomDto> rooms =  mapper.queryByRoom(page);

        rooms.forEach(room -> {
            MachineState machineState = redisDao.get(MACHINE_STATE_PRE + room.getMachineCode(),MachineState.class);
            if(null == machineState){
                room.setStateStr(MachineStatusEnum.OFFLINE.getMessage());
                room.setState(MachineStatusEnum.OFFLINE.getCode());
            }else {
                room.setState(machineState.getMachineState().getCode());
                room.setStateStr(machineState.getMachineState().getMessage());
            }
        });
        page.setList(rooms);
        page.setPageCount(count);
        return page;
    }

    @Override
    public void updateByIds(List<Integer> ids) {
        mapper.updateByIds(ids);
    }

    @Override
    public void save(Room room) {
        if (null != room.getId()) {
            mapper.updateById(room);
        }else{
            mapper.insert(room);
        }
    }
}
