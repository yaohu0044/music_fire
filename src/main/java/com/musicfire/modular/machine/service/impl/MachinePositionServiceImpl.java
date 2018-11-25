package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.handler.MqttMessageGateway;
import com.musicfire.modular.commodity.entity.CommodityPic;
import com.musicfire.modular.commodity.service.ICommodityPicService;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    private ICommodityPicService commodityPicService;


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
            List<MachinePosition> machinePositions = machinePositionMapper.selectList(entityWrapper);
            if(machinePositions.size()>=10){
                throw new BusinessException(ErrorCode.POSITION_OVERRUN);
            }
            List<Integer> collect = machinePositions.stream().map(MachinePosition::getNum).collect(Collectors.toList());
            if(collect.size() == 0){
                machinePosition.setNum(1);
            }else {
                Integer max1 = Collections.max(collect);
                machinePosition.setNum(max1 + 1);
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
        machines.sort(Comparator.comparingInt(MachinePosition::getNum));
        page.setList(machines);
        page.setPageCount(count);
        return page;
    }

    @Override
    public void openPosition(String machineCode,Integer num) {

        String topic = "controller/" + machineCode;
        //发送的消息
        if (null != num) {
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
                try {
                    Thread.sleep(500L);
                    Message message = MessageBuilder.withPayload(String.valueOf(index.get()))
                            //发送的主题
                            .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
                    gateway.sendMessage(message);
                    index.addAndGet(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
    }

    @Override
    public List<MachinePositionDto> queryByMachineCode(String code) {
        MachinePositionPage page = new MachinePositionPage();
        page.setMachineCode(code);
        List<MachinePositionDto> machinePositionDTos = machinePositionMapper.queryByMachine(page);
        List<Integer> collect = machinePositionDTos.stream().map(MachinePositionDto::getCommodityId).collect(Collectors.toList());
        EntityWrapper<CommodityPic> commodityPicEntityWrapper = new EntityWrapper<>();
        commodityPicEntityWrapper.in("commodity_id",collect);
        commodityPicEntityWrapper.groupBy("commodity_id");
        List<CommodityPic> commodityPics = commodityPicService.selectList(commodityPicEntityWrapper);
        Map<Integer, String> map = commodityPics.stream().collect(Collectors.toMap(CommodityPic::getCommodityId, CommodityPic::getPath));
        machinePositionDTos.forEach(machinePositionDto -> {
            machinePositionDto.setShrinkageChart(map.get(machinePositionDto.getCommodityId()));
        });

        return machinePositionDTos;
    }
}

