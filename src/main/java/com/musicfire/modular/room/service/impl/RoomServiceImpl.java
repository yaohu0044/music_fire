package com.musicfire.modular.room.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.room.dao.RoomMapper;
import com.musicfire.modular.room.dto.RoomDto;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.query.RoomPage;
import com.musicfire.modular.room.service.IRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private IMachineService machineService;

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
        page.setTotalCount(count);
        return page;
    }

    @Transactional
    @Override
    public void updateByIds(List<Integer> ids) {
        EntityWrapper<Room> roomEntityWrapper = new EntityWrapper<>();
        roomEntityWrapper.in("id",ids);
        List<Room> rooms = mapper.selectList(roomEntityWrapper);
        List<Integer> machineIds = rooms.stream().map(Room::getMachineId).collect(Collectors.toList());
        EntityWrapper<Machine> machineEntityWrapper = new EntityWrapper<>();
        machineEntityWrapper.in("id",machineIds);
        Machine machine = new Machine();
        machine.setIsDistribution(false);
        machineService.update(machine,machineEntityWrapper);
        mapper.updateByIds(ids);
    }

    @Transactional
    @Override
    public void save(Room room) {
        Integer merchantId = room.getMerchantId();
        String name = room.getName();
        Room r = new Room();
        r.setMerchantId(merchantId);
        r.setName(name);
        r.setFlag(false);
        Room room2 = mapper.selectOne(r);
        if(null != room2){
            throw new BusinessException(ErrorCode.ROOM_EXIST);
        }


        if (null != room.getId()) {
            Room room1 = mapper.selectById(room.getId());
            if(room1.getMachineId().intValue()!=room.getMachineId()){
                //还原原来机器被分配状态
                EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("id",room1.getMachineId());
                Machine machine = new Machine();
                machine.setIsDistribution(false);
                machineService.update(machine,entityWrapper);
                // 修改被分配状态
                EntityWrapper<Machine> entityWrapper1 = new EntityWrapper<>();
                entityWrapper1.eq("id",room.getMachineId());
                Machine machine1 = new Machine();
                machine1.setIsDistribution(true);
                machineService.update(machine1,entityWrapper1);
            };
            mapper.updateById(room);
        }else{
            mapper.insert(room);
            EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("id",room.getMachineId());
            Machine machine = new Machine();
            machine.setIsDistribution(true);
            machineService.update(machine,entityWrapper);
        }
    }
}
