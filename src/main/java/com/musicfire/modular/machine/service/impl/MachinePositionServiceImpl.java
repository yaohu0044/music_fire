package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.handler.MqttMessageGateway;
import com.musicfire.modular.machine.dao.MachinePositionMapper;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.machine_enum.MachinePositionEnum;
import com.musicfire.modular.machine.query.MachinePositionPage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@Service
public class MachinePositionServiceImpl extends ServiceImpl<MachinePositionMapper, MachinePosition> implements IMachinePositionService {

    @Resource
    private MachinePositionMapper machinePositionMapper;

    @Resource
    private MqttMessageGateway gateway;

    @Transactional
    @Override
    public void save(MachinePosition machinePosition) {
        if(null != machinePosition.getId()){
            machinePositionMapper.updateById(machinePosition);
        }else {
            EntityWrapper<MachinePosition> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("machine_id",machinePosition.getMachineId());
            Integer integer = machinePositionMapper.selectCount(entityWrapper);
            if(integer>10){
                throw new BusinessException(ErrorCode.POSITION_OVERRUN);
            }
            machinePosition.setState(MachinePositionEnum.CLOSED.getCode());
            machinePositionMapper.insert(machinePosition);
        }
    }

    @Override
    public void updateByIds(List<Integer> ids) {
        EntityWrapper<MachinePosition> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id",ids);
        MachinePosition machinePosition = new MachinePosition();
        machinePosition.setFlag(true);
        machinePositionMapper.update(machinePosition,entityWrapper);
    }

    @Override
    public MachinePositionPage queryByMachinePosition(MachinePositionPage page) {
        int count = machinePositionMapper.queryByCount(page);
        if(count<1){
            return page;
        }
        List<MachinePositionDto> machines =  machinePositionMapper.queryByMachine(page);
        page.setList(machines);
        page.setPageCount(count);
        return page;
    }

    @Override
    public void openPosition(String machineCode,Integer num) {

        String topic = "controller/" + machineCode;
        //发送的消息
        if (null == num) {
            String positionIndex = String.valueOf(num-1);
            Message message = MessageBuilder.withPayload(positionIndex)
                    //发送的主题
                    .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
            gateway.sendMessage(message);
        } else {
            //获取所有的机器仓位
            MachinePositionPage page = new MachinePositionPage();
            List<MachinePositionDto> machinePositionDTos = machinePositionMapper.queryByMachine(page);
            AtomicInteger index = new AtomicInteger(0);
            machinePositionDTos.forEach(machinePositionDto -> {
                Message message = MessageBuilder.withPayload(String.valueOf(index.get()))
                        //发送的主题
                        .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
                gateway.sendMessage(message);
                index.addAndGet(1);
            });
        }
    }
}

