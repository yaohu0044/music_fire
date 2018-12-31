package com.musicfire.modular.machine.dao;

import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.musicfire.modular.machine.query.MachinePositionPage;
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
public interface MachinePositionMapper extends BaseMapper<MachinePosition> {

    int queryByCount(MachinePositionPage page);

    List<MachinePositionDto> queryByMachine(MachinePositionPage page);

    List<MachinePositionDto> queryByIds(@Param("ids") List<Integer> ids);
}
