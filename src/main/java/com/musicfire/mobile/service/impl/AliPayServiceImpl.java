package com.musicfire.mobile.service.impl;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
    public void saveAliPayOrder(String out_trade_no, String trade_no, String total_amount) {
        EntityWrapper<Order> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("unified_num",out_trade_no);
        List<Order> orders = orderService.selectList(entityWrapper);
        if(null == orders || orders.size()<1){
            throw new BusinessException(ErrorCode.ORDER_VERIFICATION_ERR);
        }
        BigDecimal orderTotalAmount = orders.stream().map(Order::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(!orderTotalAmount.toString().equals(total_amount)){
            throw new BusinessException(ErrorCode.PAY_AMOUNT_ERR);
        }
        Order order = new Order();
        order.setState(ResultEnum.ORDER_STATE_SUCCESS.getCode());
        order.setPaymentMethod(ResultEnum.ALI_PAY.getCode());
        order.setTradeNo(trade_no);
        orderService.update(order,entityWrapper);
        //获取机器code
        Machine machine = machineService.selectById(orders.get(0).getMachineId());
        //打开仓门
        orders.forEach(o->{
            try {
                machinePositionService.openPosition(machine.getCode(),o.getMachinePositionNum());
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }
}
