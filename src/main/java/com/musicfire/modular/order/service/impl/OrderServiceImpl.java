package com.musicfire.modular.order.service.impl;

import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.dao.OrderMapper;
import com.musicfire.modular.order.page.OrderPage;
import com.musicfire.modular.order.service.IOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.modular.system.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
