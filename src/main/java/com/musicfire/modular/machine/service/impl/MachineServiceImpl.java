package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.QrCodeUtils;
import com.musicfire.common.utiles.QrCodeUtils1;
import com.musicfire.modular.machine.dao.MachineMapper;
import com.musicfire.modular.machine.dto.MachineDto;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements IMachineService {

    @Resource
    private MachineMapper mapper;
    @Autowired
    private IMachinePositionService positionService;

    @Resource
    private RedisDao redisDao;

    @Override
    public MachinePage queryByMachine(MachinePage page) {
        int count = mapper.queryByCount(page);
        if(count < 1 ){
            return page;
        }
        List<MachineDto> machines =  mapper.queryByMachine(page);
        getMachineState(machines);
        page.setList(machines);
        page.setTotalCount(count);
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
        entityWrapper.eq("flag",false);
        return mapper.selectList(entityWrapper);
    }

    @Override
    public List<Machine> getLonAndLatAll() {
        EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("flag",false);
        return mapper.selectList(entityWrapper);
    }

    @Transactional
    @Override
    public void save(Machine machine) {

        Machine machine1 = new Machine();
        machine1.setCode(machine.getCode());
        machine1.setFlag(false);
        Machine mach = mapper.selectOne(machine1);
        machine.setQrCodeUrl(QrCodeUtils.encode(machine.getCode(),Conf.getValue("logoPic"),Conf.getValue("picture"),Conf.getValue("text")));
        if (null != machine.getId()) {
            if(null != mach){
                if(mach.getId().intValue()==machine.getId().intValue()){
                    if(null == machine.getMerchantId()){
                        machine.setMerchantId(0);
                        machine.setIsDistribution(false);
                    }
                    mapper.updateById(machine);
                }else{
                    throw new BusinessException(ErrorCode.MACHINE_EXIST);
                }
            }else{
                mapper.updateById(machine);
            }
        }else{
            if(null == mach){
                mapper.insert(machine);
            }else{
                throw new BusinessException(ErrorCode.MACHINE_EXIST);
            }
        }
    }

    @Override
    public void openMachine(Integer id) {
        Machine machine = mapper.selectById(id);
        positionService.openPosition(machine.getCode());

//        String topic = "controller/"+cab.getMachineCode();
////		"controller/4301473331324B4D066BFF36"
//        //发送的消息
//        String cabinetIndex = ""+(cab.getCabOrder()-1);
//        Message message = MessageBuilder.withPayload(cabinetIndex)
//                //发送的主题
//                .setHeader(MqttHeaders.TOPIC, topic) .setHeader(MqttHeaders.QOS, 2).build();
    }

    @Override
    public MachinePage notOrderMachine(MachinePage page) {

        int count = mapper.notOrderMachineCount(page);
        if(count < 1 ){
            return page;
        }
        List<MachineDto> machines =  mapper.notOrderMachinePage(page);
        getMachineState(machines);
        page.setList(machines);
        page.setTotalCount(count);

        return page;
    }

    private void getMachineState(List<MachineDto> machines) {
        machines.forEach(machineDto -> {
            MachineState machineState = redisDao.get(MACHINE_STATE_PRE + machineDto.getCode(),MachineState.class);
            if(null == machineState){
                machineDto.setStateStr(MachineStatusEnum.OFFLINE.getMessage());
                machineDto.setState(MachineStatusEnum.OFFLINE.getCode());
            }else {
                machineDto.setStateStr(machineState.getMachineState().getMessage());
                machineDto.setState(machineState.getMachineState().getCode());
                machineDto.setMachinePositionStr(machineState.getCabinetState());
                machineDto.setUptime(machineState.getUpdateTime());
            }
        });
    }
}
