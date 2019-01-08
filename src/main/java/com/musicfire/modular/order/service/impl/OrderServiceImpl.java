package com.musicfire.modular.order.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.mobile.entity.WeChatMpUser;
import com.musicfire.mobile.enums.ResultEnum;
import com.musicfire.mobile.service.AliPayService;
import com.musicfire.mobile.service.IWeChatMpUserService;
import com.musicfire.modular.commodity.entity.Dto.CommodityDto;
import com.musicfire.modular.commodity.service.ICommodityService;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.merchant.entity.Merchant;
import com.musicfire.modular.merchant.service.IMerchantService;
import com.musicfire.modular.order.dao.OrderMapper;
import com.musicfire.modular.order.dto.OrderDto;
import com.musicfire.modular.order.dto.OrderReport;
import com.musicfire.modular.order.dto.ReportParam;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import com.musicfire.modular.room.entity.Room;
import com.musicfire.modular.room.service.IRoomService;
import com.musicfire.modular.system.service.IAliPaySettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
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

    @Resource
    private IMerchantService merchantService;

    @Resource
    private AliPayService aliPayService;

    @Resource
    private IWeChatMpUserService weChatMpUserService;

    @Resource
    private IRoomService roomService;

    @Override
    public OrderPage list(OrderPage orderPage) {
        Integer count = mapper.countByPage(orderPage);
        if (count < 1) {
            return orderPage;
        }
        List<OrderDto> page = mapper.orderByPage(orderPage);
        page.forEach(order -> {
            if (null != order.getPaymentMethod()) {
                if (order.getPaymentMethod() == 1) {
                    EntityWrapper<AliPayUserInfo> aliPayUserInfoEntityWrapper = new EntityWrapper<>();
                    aliPayUserInfoEntityWrapper.eq("user_id", order.getAccountAccount());
                    AliPayUserInfo aliPayUserInfo = aliPayService.selectOne(aliPayUserInfoEntityWrapper);
                    if (null != aliPayUserInfo) {
                        order.setUserName(aliPayUserInfo.getNickName());
                        order.setHeadImg(aliPayUserInfo.getAvatar());
                    }

                } else if (order.getPaymentMethod() == 2) {
                    EntityWrapper<WeChatMpUser> wrapper = new EntityWrapper<>();
                    wrapper.eq("open_id", order.getAccountAccount());
                    WeChatMpUser weChatMpUser = weChatMpUserService.selectOne(wrapper);
                    if (null != weChatMpUser) {
                        order.setUserName(weChatMpUser.getNickname());
                        order.setHeadImg(weChatMpUser.getHeadImgUrl());
                    }

                }
            }

        });
        orderPage.setList(page);
        orderPage.setTotalCount(count);
        return orderPage;
    }

    @Transactional
    @Override
    public BigDecimal inserAll(List<Integer> ids, String unifiedNum, String userId, Integer payType) {
        EntityWrapper<MachinePosition> machinePositionEntityWrapper = new EntityWrapper<>();
        machinePositionEntityWrapper.in("id", ids);
        List<MachinePosition> machinePositions = machinePositionService.selectList(machinePositionEntityWrapper);
        List<Integer> commodityId = machinePositions.stream().map(MachinePosition::getCommodityId).collect(Collectors.toList());
        List<CommodityDto> commodityDto = commodityService.queryByIds(commodityId);
        BigDecimal reduce = commodityDto.stream().map(CommodityDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        List<Order> orders = new ArrayList<>();
        Integer machineId = machinePositions.get(0).getMachineId();
        Machine machine = machineService.selectById(machineId);

        Map<Integer, Integer> collect = machinePositions.stream().collect(Collectors.toMap(MachinePosition::getCommodityId, MachinePosition::getId));

        //生成预订单
        commodityDto.forEach(commodity -> {
            Order order = new Order();
            order.setAccountAccount(userId);
            order.setCommodityId(commodity.getId());
            order.setMachineId(machineId);
            order.setPrice(commodity.getPrice());
            order.setMerchantId(machine.getMerchantId());
            order.setNumber(UUIDTool.getOrderIdByUUId());
            order.setState(ResultEnum.ORDER_STATE_UNPAID.getCode());
            order.setUnifiedNum(unifiedNum);
            order.setMachinePositionNum(collect.get(commodity.getId()));
            order.setPaymentMethod(payType);
            mapper.insert(order);
            orders.add(order);
        });
        redisDao.add(unifiedNum, orders);
        return reduce;
    }

    @Override
    public Map<String, Object> total(OrderPage page) {
        if (null == page.getMerchantId()) {
            throw new BusinessException("该用户不是商家不能查看信息");
        }
        //销售额
        page.setState(1);
        List<OrderDto> orders = mapper.orderByPage(page);

        Map<String, Object> map = new HashMap<>();
        BigDecimal sellingPrice = new BigDecimal(0);
        if (CollectionUtils.isEmpty(orders)) {
            map.put("totalTo", 0);
            map.put("singular", 0);
            map.put("sellingPrice", 0);
            map.put("profit", 0);
            map.put("data", "");
            return map;
        }
        for (Order order : orders) {
            sellingPrice = sellingPrice.add(order.getPrice());
        }

        List<Integer> commodityIds = orders.stream().map(Order::getCommodityId).collect(Collectors.toList());
        //总金额
        Integer profit = mapper.profit(commodityIds);
        //30天成交量
        List<Order> totalTo = mapper.totalTo(page.getMerchantId());
        //成交详情
        List<Map<String, String>> data = mapper.data(page);
        data.removeAll(Collections.singleton(null));
        map.put("totalTo", totalTo.size());
        map.put("singular", commodityIds.size());
        map.put("sellingPrice", sellingPrice);
        map.put("profit", sellingPrice.subtract(new BigDecimal(ObjectUtils.isEmpty(profit) ? 0 : profit)));
        map.put("data", data);


        return map;
    }

}
