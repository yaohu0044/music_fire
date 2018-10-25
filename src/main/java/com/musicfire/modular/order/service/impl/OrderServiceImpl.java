package com.musicfire.modular.order.service.impl;

import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.dao.OrderMapper;
import com.musicfire.modular.order.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
