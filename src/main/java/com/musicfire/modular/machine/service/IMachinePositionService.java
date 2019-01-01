package com.musicfire.modular.machine.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.query.MachinePositionPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IMachinePositionService extends IService<MachinePosition> {

    void save(MachinePosition machinePosition);

    void updateByIds(List<Integer> ids);

    MachinePositionPage queryByMachinePosition(MachinePositionPage page);

    /**
     * 打开仓门
     * @param machineCode 机器code
     * @param  num 仓位 如果没有传入仓位则打开全部仓位
     */
    void openPosition(String machineCode,Integer num);

    /**
     * 打开仓门
     * @param machineCode 机器code
     */
    void openPosition(String machineCode);

    /**
     * 根据机器code获取仓位所有商品信息
     * @param code 机器code
     * @return 商品信息
     */
    List<MachinePositionDto> queryByMachineCode(String code);

    /**
     * 根据仓位Id 获取仓位详情
     * @param ids
     * @return
     */
    List<MachinePositionDto> queryByIds(List<Integer> ids);

    /**
     * 购买失败打开仓门
     * @param code
     * @param num
     * @return
     */
    void purchaseErrOpenPosition(String code, Integer num,String unifiedNum);
}
