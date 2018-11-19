package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Conf;
import com.musicfire.common.utiles.QrCodeUtils;
import com.musicfire.modular.machine.dao.MachineMapper;
import com.musicfire.modular.machine.dto.MachineDto;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import com.musicfire.modular.machine.query.MachinePage;
import com.musicfire.modular.machine.service.IMachineService;
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
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements IMachineService {

    @Resource
    private MachineMapper mapper;

    @Resource
    private RedisDao redisDao;

    @Override
    public MachinePage queryByMachine(MachinePage page) {
        int count = mapper.queryByCount(page);
        if(count < 1 ){
            page.setList(null);
            return page;
        }
        List<MachineDto> machines =  mapper.queryByMachine(page);
        machines.forEach(machineDto -> {
            MachineState machineState = redisDao.get(MACHINE_STATE_PRE + machineDto.getCode(),MachineState.class);
            if(null == machineState){
                machineDto.setStateStr(MachineStatusEnum.OFFLINE.getMessage());
                machineDto.setState(MachineStatusEnum.OFFLINE.getCode());
            }else {
                machineDto.setStateStr(machineState.getMachineState().getMessage());
                machineDto.setState(machineState.getMachineState().getCode());
            }
        });
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
        return mapper.selectList(entityWrapper);
    }

    @Override
    public List<Machine> getLonAndLatAll() {
        EntityWrapper<Machine> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("flag",false);
        return mapper.selectList(entityWrapper);
    }

    @Override
    public void save(Machine machine) {
        machine.setQrCodeUrl(QrCodeUtils.encode(Conf.getValue("text"),Conf.getValue("logoPic"),Conf.getValue("picture"),machine.getCode(),true));
        if (null != machine.getId()) {
           mapper.updateById(machine);
        }else{
            mapper.insert(machine);
        }
    }

    @Override
    public void openMachine(Integer id) {



//        String topic = "controller/"+cab.getMachineCode();
////		"controller/4301473331324B4D066BFF36"
//        //发送的消息
//        String cabinetIndex = ""+(cab.getCabOrder()-1);
//        Message message = MessageBuilder.withPayload(cabinetIndex)
//                //发送的主题
//                .setHeader(MqttHeaders.TOPIC, topic) .setHeader(MqttHeaders.QOS, 2).build();
    }
}
