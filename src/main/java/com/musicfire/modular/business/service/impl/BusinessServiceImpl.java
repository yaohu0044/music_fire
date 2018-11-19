package com.musicfire.modular.business.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.modular.business.service.BusinessService;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Resource
    private IMachinePositionService machinePositionService;

    @Resource
    private IMachineService machineService;

    @Transactional
    @Override
    public void replenishment(Integer machinePositionId) {
        MachinePosition machinePosition = new MachinePosition();
        machinePosition.setState(1);
        machinePosition.setId(machinePositionId);
        machinePositionService.updateById(machinePosition);

        MachinePosition mp = machinePositionService.selectById(machinePositionId);
        Machine machine = machineService.selectById(mp.getMachineId());
        machinePositionService.openPosition(machine.getCode(),mp.getNum());

    }

    @Override
    public void replenishmentAll(Integer machineId) {
        EntityWrapper<MachinePosition> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("machine_id",machineId);
        MachinePosition machinePosition = new MachinePosition();
        machinePosition.setState(1);
        machinePositionService.update(machinePosition,entityWrapper);


        Machine machine = machineService.selectById(machineId);
        machinePositionService.openPosition(machine.getCode(),null);
    }
}
