package com.musicfire.modular.machine.dao;

import com.musicfire.modular.machine.dto.MachineDto;
import com.musicfire.modular.machine.entity.Machine;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.machine.query.MachinePage;
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
public interface MachineMapper extends BaseMapper<Machine> {

    List<MachineDto> queryByMachine(MachinePage page);

    int queryByCount(MachinePage page);

    int notOrderMachineCount(MachinePage page);

    List<MachineDto> notOrderMachinePage(MachinePage page);
}
