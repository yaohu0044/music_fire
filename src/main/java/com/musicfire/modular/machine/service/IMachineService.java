package com.musicfire.modular.machine.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.query.MachinePage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
public interface IMachineService extends IService<Machine> {

    MachinePage queryByMachine(MachinePage page);

    void updateByIds(List<Integer> ids);

    /**
     * 根据商家Id获取机器信息
     * @param merchantId 商家Id
     * @return 机器信息
     */
    List<Machine> queryByMerchantId(Integer merchantId);

    /**
     *
     * @return 所有机器信息
     */
    List<Machine> getLonAndLatAll();

    void save(Machine machine);

    /**
     * 打开机器所有舱门
     * @param id
     */
    void openMachine(Integer id);
}
