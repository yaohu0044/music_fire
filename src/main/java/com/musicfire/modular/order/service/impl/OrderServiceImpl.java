package com.musicfire.modular.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.mobile.enums.ResultEnum;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.order.dao.OrderMapper;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper mapper;

    @Resource
    private ICommodityService commodityService;

    @Resource
    private IMachinePositionService machinePositionService;

    @Resource
    private RedisDao redisDao;

    @Resource
    private IMachineService machineService;

    @Override
    public OrderPage list(OrderPage orderPage) {
        Integer count = mapper.countByPage(orderPage);
        if (count < 1) {
            return orderPage;
        }
        List<Order> page = mapper.orderByPage(orderPage);
        orderPage.setList(page);
        orderPage.setTotalCount(count);
        return orderPage;
    }

    @Transactional
    @Override
    public BigDecimal inserAll(List<Integer> ids, String unifiedNum) {
        EntityWrapper<MachinePosition> machinePositionEntityWrapper = new EntityWrapper<>();
        machinePositionEntityWrapper.in("id", ids);
        List<MachinePosition> machinePositions = machinePositionService.selectList(machinePositionEntityWrapper);
        List<Integer> commodityId = machinePositions.stream().map(MachinePosition::getCommodityId).collect(Collectors.toList());
        List<CommodityDto> commodityDto = commodityService.queryByIds(commodityId);
        //BigDecimal reduce = commodityDto.stream().map(CommodityDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Order> orders = new ArrayList<>();
        Integer machineId = machinePositions.get(0).getMachineId();
        Machine machine = machineService.selectById(machineId);
        //生成预订单
        commodityDto.forEach(commodity -> {
            Order order = new Order();
            order.setCommodityId(commodity.getId());
            order.setMachineId(machineId);
            order.setPrice(commodity.getPrice());
            order.setMerchantId(machine.getMerchantId());
            order.setNumber(UUIDTool.getOrderIdByUUId());
            order.setState(ResultEnum.ORDER_STATE_UNPAID.getCode());
            order.setUnifiedNum(unifiedNum);
            mapper.insert(order);
            orders.add(order);
        });
        redisDao.add(unifiedNum,orders);
        return null;
    }
}
