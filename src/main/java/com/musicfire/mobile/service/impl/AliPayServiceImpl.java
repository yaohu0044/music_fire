package com.musicfire.mobile.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.mobile.dao.AliPayUserInfoMapper;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.mobile.enums.ResultEnum;
import com.musicfire.mobile.service.AliPayService;
import com.musicfire.modular.machine.entity.Machine;
import com.musicfire.modular.machine.entity.MachinePosition;
import com.musicfire.modular.machine.service.IMachinePositionService;
import com.musicfire.modular.machine.service.IMachineService;
import com.musicfire.modular.machine.service.impl.MachinePositionServiceImpl;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.service.IOrderService;
import com.musicfire.modular.replenishment.entity.Replenishment;
import com.musicfire.modular.replenishment.service.IReplenishmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AliPayServiceImpl extends ServiceImpl<AliPayUserInfoMapper, AliPayUserInfo> implements AliPayService {

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private RedisDao redisDao;

    @Resource
    private IOrderService orderService;

    @Resource
    private AliPayUserInfoMapper aliPayUserInfoMapper;

    @Resource
    private IMachinePositionService machinePositionService;

    @Resource
    private IMachineService machineService;

    @Resource
    private IReplenishmentService replenishmentService;


    @Transactional
    public int saveAliPayUser(AliPayUserInfo aliPayUserInfo) {
        int ret = 0;
        if (redisDao.exist(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId())) {
            redisDao.update(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId(), aliPayUserInfo);
        } else {
            redisDao.add(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId(), aliPayUserInfo);
        }
        aliPayUserInfoMapper.insert(aliPayUserInfo);

        return ret;
    }

    @Transactional
    @Override
    public void saveAliPayOrder(String out_trade_no, String trade_no, String total_amount,String type) {
        EntityWrapper<Order> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("unified_num",out_trade_no);
        entityWrapper.eq("state",3);
        List<Order> orders = orderService.selectList(entityWrapper);
        if(null == orders || orders.size()<1){
            throw new BusinessException(ErrorCode.ORDER_VERIFICATION_ERR);
        }

        Order order = new Order();
        order.setState(ResultEnum.ORDER_STATE_SUCCESS.getCode());
        order.setPaymentMethod(Integer.parseInt(type));
        order.setTradeNo(trade_no);
        orderService.update(order,entityWrapper);
        //获取机器code
        Machine machine = machineService.selectById(orders.get(0).getMachineId());
        machinePositionService.purchaseErrOpenPosition(machine.getCode(),null,out_trade_no);

        //购买成功修改库存
        EntityWrapper<Replenishment> entit = new EntityWrapper<>();
        entit.eq("commodity_id",order.getCommodityId());
        entit.eq("merchant_id",order.getMerchantId());
        List<Replenishment> replenishments = replenishmentService.selectList(entit);
        if(replenishments.size()>0){
            Replenishment replenishment = replenishments.get(0);
            replenishment.setTotalQuantity(replenishment.getTotalQuantity()-1);
            replenishmentService.updateById(replenishment);
        }

    }
}
