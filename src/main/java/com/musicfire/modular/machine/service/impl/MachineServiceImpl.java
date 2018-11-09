package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.modular.machine.dto.MachineDto;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.dao.MachineMapper;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachineService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.room.dao.RoomMapper;
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
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements IMachineService {

    @Resource
    private MachineMapper mapper;

    @Override
    public MachinePage queryByMachine(MachinePage page) {
        List<MachineDto> machines =  mapper.queryByMachine(page);
        int count = mapper.queryByCount(page);
        page.setList(machines);
        page.setPageCount(count);
        return page;


    }

    @Override
    public void updateByIds(List<Integer> ids) {
        EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id",ids);
        Machine machine = new Machine();
        machine.setFlag(true);
        mapper.update(machine,entityWrapper);
    }

    @Override
    public List<Machine> queryByMerchantId(Integer merchantId) {
        EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("merchant_id",merchantId);
        entityWrapper.eq("distribution",false);
        List<Machine> machines = mapper.selectList(entityWrapper);
        return machines;
    }
}
