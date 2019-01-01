package com.musicfire.modular.machine.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.handler.MqttMessageGateway;
import com.musicfire.mobile.enums.ResultEnum;
import com.musicfire.modular.commodity.entity.CommodityPic;
import com.musicfire.modular.commodity.service.ICommodityPicService;
import com.musicfire.modular.machine.dao.MachinePositionMapper;
import com.musicfire.modular.machine.dto.MachinePositionDto;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.entity.MachineState;
import com.musicfire.modular.machine.machine_enum.MachinePositionEnum;
import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import com.musicfire.modular.machine.query.MachinePositionPage;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.musicfire.common.config.redisdao.RedisConstant.MACHINE_STATE_PRE;

/**
 * <p>
 * 服务实现类
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
    private IMachineService machineService;

    @Resource
    private MqttMessageGateway gateway;

    @Resource
    private RedisDao redisDao;

    @Resource
    private IOrderService orderService;

    @Resource
    private IMachinePositionService machinePositionService;

    @Transactional
    @Override
    public void save(MachinePosition machinePosition) {
        if (null != machinePosition.getId()) {
            Integer id = machinePosition.getId();
            MachinePosition mp = machinePositionMapper.selectById(id);
            mp.setCommodityId(machinePosition.getCommodityId());
            machinePositionMapper.updateById(mp);
        } else {
            EntityWrapper<MachinePosition> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("machine_id", machinePosition.getMachineId());
            entityWrapper.eq("flag", false);
            List<MachinePosition> machinePositions = machinePositionMapper.selectList(entityWrapper);
            if (machinePositions.size() >= 10) {
                throw new BusinessException(ErrorCode.POSITION_OVERRUN);
            }
            List<Integer> collect = machinePositions.stream().map(MachinePosition::getNum).collect(Collectors.toList());
            if (collect.size() == 0) {
                machinePosition.setNum(1);
            } else {
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
        entityWrapper.in("id", ids);
        MachinePosition machinePosition = new MachinePosition();
        machinePosition.setFlag(true);
        machinePositionMapper.update(machinePosition, entityWrapper);
    }

    @Override
    public MachinePositionPage queryByMachinePosition(MachinePositionPage page) {
        int count = machinePositionMapper.queryByCount(page);
        if (count < 1) {
            return page;
        }
        List<MachinePositionDto> machines = machinePositionMapper.queryByMachine(page);
        machines.sort(Comparator.comparingInt(MachinePosition::getNum));
        page.setList(machines);
        page.setTotalCount(count);
        return page;
    }

    @Override
    public void openPosition(String machineCode, Integer num) {

        String topic = "controller/" + machineCode;
        //获取机器Id
        EntityWrapper<Machine> machineEntityWrapper = new EntityWrapper<>();
        machineEntityWrapper.eq("code", machineCode);
        Machine machine = machineService.selectOne(machineEntityWrapper);


        //发送的消息
        if (null != num) {
            String positionIndex = String.valueOf(num - 1);
            Message message = MessageBuilder.withPayload(positionIndex)
                    //发送的主题
                    .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
            gateway.sendMessage(message);
            //设置仓门为打开状态
            MachinePosition machinePosition = new MachinePosition();
            EntityWrapper<MachinePosition> machinePositionEntityWrapper = new EntityWrapper<>();
            machinePositionEntityWrapper.eq("machine_id", machine.getId());
            machinePositionEntityWrapper.eq("num", num);
            machinePosition.setAvailable(true);
            machinePositionMapper.update(machinePosition, machinePositionEntityWrapper);

        } else {
            //获取所有的机器仓位
            MachinePositionPage page = new MachinePositionPage();
            page.setMachineCode(machineCode);
            List<MachinePositionDto> machinePositionDTos = machinePositionMapper.queryByMachine(page);
            machinePositionDTos.forEach(machinePositionDto -> {
                try {
                    MachinePosition machinePosition = new MachinePosition();
                    machinePosition.setAvailable(true);
                    machinePosition.setId(machinePositionDto.getId());
                    machinePositionMapper.updateById(machinePosition);

                    Thread.sleep(500L);
                    Message message = MessageBuilder.withPayload((machinePositionDto.getNum() - 1) + "")
                            //发送的主题
                            .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
                    gateway.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }

    }

    @Override
    public void openPosition(String machineCode) {

        String topic = "controller/" + machineCode;
        //发送的消息
        //获取所有的机器仓位
        MachinePositionPage page = new MachinePositionPage();
        List<MachinePositionDto> machinePositionDTos = machinePositionMapper.queryByMachine(page);

        AtomicInteger atomicInteger = new AtomicInteger(0);

        machinePositionDTos.forEach(machinePositionDto -> {
            try {
                MachinePosition machinePosition = new MachinePosition();
                machinePosition.setAvailable(true);
                machinePosition.setId(machinePositionDto.getId());
                machinePositionMapper.updateById(machinePosition);

                Thread.sleep(500L);
                Message message = MessageBuilder.withPayload(atomicInteger.get() + "")
                        //发送的主题
                        .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
                gateway.sendMessage(message);
                atomicInteger.addAndGet(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

    }

//    private void openMachine(String topic, List<MachinePositionDto> machinePositionDTos) {
//        machinePositionDTos.forEach(machinePositionDto -> {
//            try {
//                MachinePosition machinePosition = new MachinePosition();
//                machinePosition.setAvailable(true);
//                machinePosition.setId(machinePositionDto.getId());
//                machinePositionMapper.updateById(machinePosition);
//
//                Thread.sleep(500L);
//                Message message = MessageBuilder.withPayload((machinePositionDto.getNum() - 1) + "")
//                        //发送的主题
//                        .setHeader(MqttHeaders.TOPIC, topic).setHeader(MqttHeaders.QOS, 2).build();
//                gateway.sendMessage(message);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        });
//    }

    @Override
    public List<MachinePositionDto> queryByMachineCode(String code) {
        MachinePositionPage page = new MachinePositionPage();
        page.setMachineCode(code);
        List<MachinePositionDto> machinePositionDTos = machinePositionMapper.queryByMachine(page);
        List<Integer> collect = machinePositionDTos.stream().map(MachinePositionDto::getCommodityId).collect(Collectors.toList());
        EntityWrapper<CommodityPic> commodityPicEntityWrapper = new EntityWrapper<>();
        commodityPicEntityWrapper.in("commodity_id", collect);
        List<CommodityPic> commodityPics = commodityPicService.selectList(commodityPicEntityWrapper);
        Map<Integer, String> map = commodityPics.stream().collect(Collectors.toMap(CommodityPic::getCommodityId, CommodityPic::getPath));
        machinePositionDTos.forEach(machinePositionDto -> {
            machinePositionDto.setShrinkageChart(map.get(machinePositionDto.getCommodityId()));
            MachineState machineState = redisDao.get(MACHINE_STATE_PRE + code,MachineState.class);
            if(null == machineState){
                machinePositionDto.setMachineState(MachineStatusEnum.OFFLINE.getCode());
            }else {
                machinePositionDto.setMachineState(machineState.getMachineState().getCode());
            }
        });
        //如果没有仓位则设置一个机器名称进去。
        if(CollectionUtils.isEmpty(machinePositionDTos)){
            EntityWrapper<Machine> machineEntityWrapper = new EntityWrapper<>();
            machineEntityWrapper.eq("code",code);
            Machine machine = machineService.selectOne(machineEntityWrapper);
            if(null != machine){
                machinePositionDTos = new ArrayList<>();
                MachinePositionDto dto = new MachinePositionDto();
                dto.setMachineName(machine.getName());
                machinePositionDTos.add(dto);
            }
        }



        return machinePositionDTos;
    }

    @Override
    public List<MachinePositionDto> queryByIds(List<Integer> ids) {
        List<MachinePositionDto> dTos = machinePositionMapper.queryByIds(ids);
        return dTos;
    }

    @Override
    public void purchaseErrOpenPosition(String code, Integer num,String unifiedNum) {
        System.out.println("unifiedNum:"+unifiedNum);
        EntityWrapper<Order> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("unified_num",unifiedNum);
        List<Order> orders = orderService.selectList(entityWrapper);

//        Order order = new Order();
//        order.setState(ResultEnum.ORDER_STATE_SUCCESS.getCode());
//        order.setPaymentMethod(Integer.parseInt(type));
//        order.setTradeNo(trade_no);
//        orderService.update(order,entityWrapper);
        //获取机器code
//        Machine machine = machineService.selectById(orders.get(0).getMachineId());
        //打开仓门
        orders.forEach(o->{
            try {
                if(o.getState()!=null && o.getState()==1){
                    //获取仓位
                    MachinePosition mp = machinePositionService.selectById(o.getMachinePositionNum());
                    machinePositionService.openPosition(code,mp.getNum());
                    MachinePosition machinePosition = new MachinePosition();
                    machinePosition.setAvailable(false);
                    machinePosition.setId(o.getMachinePositionNum());
                    machinePositionService.updateById(machinePosition);
                    Thread.sleep(500L);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        System.out.println("code:"+code+"num:"+num);
//
//        openPosition(code,num);
//
//        MachinePosition machinePosition = new MachinePosition();
//        EntityWrapper<Machine> machineEntityWrapper = new EntityWrapper<>();
//        machineEntityWrapper.eq("code",code);
//        Machine machine = machineService.selectOne(machineEntityWrapper);
//
//        machinePosition.setMachineId(machine.getId());
//        machinePosition.setNum(num);
//
//        MachinePosition m = machinePositionMapper.selectOne(machinePosition);
//
//        m.setAvailable(false);
//        machinePositionMapper.updateById(m);

    }
}

